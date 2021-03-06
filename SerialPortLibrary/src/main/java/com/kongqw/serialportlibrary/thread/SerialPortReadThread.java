package com.kongqw.serialportlibrary.thread;

import android.util.Log;

import com.kongqw.serialportlibrary.SerialPort;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kongqw on 2017/11/14.
 * 串口消息读取线程
 */

public abstract class SerialPortReadThread extends Thread {

    public abstract void onDataReceived(byte[] bytes,int size);

    private static final String TAG = SerialPortReadThread.class.getSimpleName();
    private InputStream mInputStream;
    private byte[] mReadBuffer;
    private byte[] bytes = null;

    public SerialPortReadThread(InputStream inputStream) {
        mInputStream = inputStream;
        mReadBuffer = new byte[1024];
//        try {
//            int bufflenth = inputStream.available();
//            mReadBuffer = new byte[bufflenth];
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @Override
    public void run() {
        super.run();

        while (!isInterrupted()) {
            try {
                if (null == mInputStream) {
                    return;
                }
                int available = mInputStream.available();

                if(available > 0){
                    int size = mInputStream.read(mReadBuffer);
                    if(size > 0){
                        byte[] temp = new byte[size];
                        System.arraycopy(mReadBuffer, 0, temp, 0, size);
                        onDataReceived(temp,size);
                    }
                }
//                int size = mInputStream.read(mReadBuffer);
//
//                if (-1 == size || 0 >= size) {
//                    return;
//                }
//
//                byte[] readBytes = new byte[size];
//
//                System.arraycopy(mReadBuffer, 0, readBytes, 0, size);
//
//                //Log.i(TAG, "run: readBytes = " + new String(readBytes));
//                onDataReceived(readBytes);

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * 关闭线程 释放资源
     */
    public void release() {
        interrupt();

        if (null != mInputStream) {
            try {
                mInputStream.close();
                mInputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
