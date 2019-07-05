package com.cs.android190705service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class MyIntentService extends IntentService {

    // Intent Service Class는 매개변수가 없는 생성자(DefaultConstructor)가 없기 때문에
    // 상속 받아서 사용하는 경우, 상위 Class의 생성자를 직접 호출해야 합니다.
    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    public void onHandleIntent(Intent arg){
        for(int i=0 ; i<10 ; i=i+1){
            try {
                Thread.sleep(1000);
                Log.e("Back", i + "");
            }catch (Exception e){}
        }
    }
}
