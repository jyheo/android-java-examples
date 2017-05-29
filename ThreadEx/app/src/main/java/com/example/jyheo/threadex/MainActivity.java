package com.example.jyheo.threadex;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Handler mHandler;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(bundle.getString("jmlee") + " " + count++);
            }
        };
    }

    public void onLongWaitClick(View v) {
        long    futureTime= System.currentTimeMillis()+10000;
        while(System.currentTimeMillis() < futureTime){
            synchronized (this){
                try{
                    wait(futureTime-System.currentTimeMillis());
                }catch(Exception e){ }
            }
        }
        TextView textView= (TextView)findViewById(R.id.textView);
        textView.setText("Hello, jmlee");
    }

    public void onThreadExceptionClick(View v) {
        MyThread    myThread= new MyThread();
        myThread.start();
    }

    public void onThreadHandlerClick(View v) {
        MyThreadHandler    myThread= new MyThreadHandler();
        myThread.start();
    }

    public void onThreadRunOnUIClick(View v) {
        MyThreadRunOnUI     myThread = new MyThreadRunOnUI();
        myThread.start();
    }

    private class MyThread extends Thread{
        public void run(){
            long    futureTime= System.currentTimeMillis()+10000;
            while(System.currentTimeMillis() < futureTime){
                synchronized (this){
                    try{
                        wait(futureTime-System.currentTimeMillis());
                    }catch(Exception e){ }
                }
            }
            TextView textView= (TextView)findViewById(R.id.textView);
            textView.setText("Hello, jmlee");
        }
    }

    private class MyThreadHandler extends Thread{
        public void run(){
            long futureTime = System.currentTimeMillis() + 10000;
            while (System.currentTimeMillis() < futureTime) {
                synchronized (this){
                    try {
                        wait(futureTime - System.currentTimeMillis());
                    } catch (Exception e) {}
                }
            }
            Bundle bundle = new Bundle();
            bundle.putString("jmlee", "Hello, Jmlee");
            Message msg = new Message();
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }

    private class MyThreadRunOnUI extends Thread{
        public void run(){
            long    futureTime= System.currentTimeMillis()+10000;
            while(System.currentTimeMillis() < futureTime){
                synchronized (this){
                    try{
                        wait(futureTime-System.currentTimeMillis());
                    }catch(Exception e){ }
                }
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView textView= (TextView)findViewById(R.id.textView);
                    textView.setText("Hello, jmlee");
                }
            });
        }
    }
}
