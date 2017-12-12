package com.goman.oops;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    BluetoothProfile mBluetoothProfile;
    BluetoothAdapter mBluetoothAdapter;
    AcceptThread acceptThread;
    BluetoothServerSocket serverSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            mBluetoothAdapter.getProfileProxy(this,
                    new BluetoothProfile.ServiceListener() {
                        @Override
                        public void onServiceConnected(int profile, BluetoothProfile proxy) {
                            Log.e("MainActivity", "onServiceConnected");
                            try {
                                if (profile == 4) {
                                    mBluetoothProfile = proxy;
                                    List<BluetoothDevice> pairedDevices = mBluetoothProfile.getConnectedDevices();
                                    // 如果存储的设备信息大于0
                                    for (BluetoothDevice device : pairedDevices) {
                                        // 遍历然后打印出每个设备的信息
                                        Log.e("MainActivity", device.getName() + "\n" + device.getAddress());
                                        ParcelUuid[] uuids = device.getUuids();
                                        acceptThread = new AcceptThread(uuids[0].getUuid());
                                        acceptThread.start();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onServiceDisconnected(int profile) {
                            Log.i("MainActivity", "onServiceDisconnected");
                        }
                    }, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static final int TYPE_RFCOMM = 1;
    private static final int TYPE_SCO = 2;
    private static final int TYPE_L2CAP = 3;

    /**
     * Create a BluetoothSocket using L2CAP protocol
     * Useful for HID Bluetooth devices
     * @return BluetoothSocket
     */
    private static BluetoothSocket createL2CAPBluetoothSocket(BluetoothDevice device, UUID id){
        int type        = TYPE_L2CAP; // L2CAP protocol
        int fd          = -1;         // Create a new socket
        boolean auth    = false;      // No authentication
        boolean encrypt = false;      // Not encrypted
        int port        = 0;          // port to use (useless if UUID is given)
        ParcelUuid uuid = new ParcelUuid(id); // Bluetooth UUID service

        try {
            Constructor<BluetoothSocket> constructor = BluetoothSocket.class.getDeclaredConstructor(
                    int.class, int.class, boolean.class, boolean.class,
                    BluetoothDevice.class, int.class, ParcelUuid.class);
            constructor.setAccessible(true);
            BluetoothSocket clientSocket = (BluetoothSocket) constructor.newInstance(
                    type, fd, auth, encrypt, device, port, uuid);
            return clientSocket;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), String.valueOf(msg.obj),
                    Toast.LENGTH_LONG).show();
        }
    };

    //服务端监听客户端的线程类
    private class AcceptThread extends Thread {

        BluetoothSocket socket;
        InputStream is;//输入流

        public AcceptThread(UUID uuid) {
            try {
                serverSocket = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("barcode scanner", uuid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void run() {
            try {
                socket = serverSocket.accept();
                is = socket.getInputStream();
                while(true) {
                    byte[] buffer =new byte[1024];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    handler.sendMessage(msg);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        Log.e("key event", event.toString());
//        return super.dispatchKeyEvent(event);
//    }
}
