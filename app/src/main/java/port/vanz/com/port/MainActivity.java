package port.vanz.com.port;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kongqw.serialportlibrary.ByteUtil;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "====>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SerialPortManager serialPortManager = new SerialPortManager();
        serialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes,int size) {
                Log.i(TAG, "onDataReceived: "+ ByteUtil.byteArrayToString(bytes,0,size));
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });

        SerialPortFinder serialPortFinder = new SerialPortFinder();
        ArrayList<Device> devices = serialPortFinder.getDevices();
        for(Device device : devices){
            if(device.getName().equals("ttyS3")){
               Boolean b = serialPortManager.openSerialPort(device.getFile(),9600);
               Log.i(TAG, "onCreate: "+b);
            }
        }

    }


}
