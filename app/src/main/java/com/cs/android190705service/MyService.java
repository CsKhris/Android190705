package com.cs.android190705service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    // Service가 만들어 질 때 호출되는  Method
    @Override
    public void onCreate(){
        Log.e("Service", "Start");
    }

    // Background에서 작업을 수행할 Method
    // 첫번째 매개변수는 이 Service를 호출할 때 사용한 Intent
    // 두번째 매개변수는 Intent를 만들 때 설정한 Option
    // 세번째 매개변수는 Service를 구분하기 위한 ID
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e("Service", "Work Start");
        int start = intent.getIntExtra("Num", 0);
        Thread th = new Thread(){
            @Override
            public void run(){
                for(int i=0 ; i<10 ; i=i+1){
                    try{
                        Thread.sleep(1000);
                        Log.e("Working...", i * 10 + "%" + start);
                    }catch (Exception e){}
                }
                // 작업이 중단되면 이 Method를 호출하여 Service를 중단시켜야 합니다.
                stopSelf();
            }
        };
        th.start();

        // 여기서 Return 하는 값은 재시작과 관련된 Option
        // 소멸되고 난 후, 빨리 다시 시작 되어야 한다는 설정
        return  Service.START_STICKY;
        // 위 Option 이외에도 START_NOT_STICKY; : 다시 시작하지 않겠다는 Option
        // START_REDELIVERY_INTENT; : 현재의 Intent를 onStartCommand에게 다시 전송하여 Service가 다시 시작 되도록 설정
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Service", "Binding");
        throw null;
    }

    @Override
    public void onDestroy(){
        Log.e("Service", "Shutdown");
    }
}
