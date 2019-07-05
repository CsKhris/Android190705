package com.cs.android190705service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoundService extends Service {
    // BoundService를 만들기 위한 Binder Calss를 생성하고,
    // getService()를 재정의하여 현재 Service 객체를 Return

    public class MyLoaclBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }

    // 위에서 만든 Class의 Instance 생성
    IBinder myBinder = new MyLoaclBinder();

    public BoundService() {
    }

    @Override
    // Start Service에서는 이 Method가 별다른 의미가 없지만,
    // BoundService에서는 이 Method가 중요 합니다.
    public IBinder onBind(Intent intent) {
        return  myBinder;
    }

    // 다른 Process에서 호출할 Method 만들기
    public String getCurrentTime(){
        try{
            Thread.sleep(10000);
        }catch (Exception e){}
        // 현재 날짜 및 시간을 문자열로 Return
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = dateFormat.format(new Date());
        return currentTime;
    }
}
