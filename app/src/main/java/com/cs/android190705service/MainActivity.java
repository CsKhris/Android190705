package com.cs.android190705service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    // Bound Service 변수와 Bound 여부를 저장할 변수
    BoundService myService;
    boolean isBound;

    ServiceConnection myConnection = new ServiceConnection() {

        // Service를 생성/연결
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            // Service를 생성하여 myService에 연결
            BoundService.MyLoaclBinder binder = (BoundService.MyLoaclBinder)iBinder;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView resultView = (TextView)findViewById(R.id.resultview);
        Button boundService = (Button)findViewById(R.id.boundservice);
        // BoundService를 Bound Service로 생성
        Intent intent1 = new Intent(MainActivity.this, BoundService.class);
        bindService(intent1, myConnection, Context.BIND_AUTO_CREATE);

        boundService.setOnClickListener(view -> {
            // myService가 가진 Method 호출
            // 이 Activity가 종료되더라도 Service의 Method는 수행 됩니다.
            String currentTime = myService.getCurrentTime();
            resultView.setText(currentTime);
        });


        Button broadcastSender = (Button)findViewById(R.id.broadcastsender);

        broadcastSender.setOnClickListener(view -> {
            // com.example.sendbroadcast 를 호출하도록 방송을 발송
            Intent intent = new Intent();
            intent.setAction("com.example.sendbroadcast");

            // 한번도 화면에 보여진 적이 없는 경우에도 출력하도록 설정
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

            // 방송 발송
            sendBroadcast(intent);
        });

        Button startIntentService = (Button)findViewById(R.id.startintentservice);
        startIntentService.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);

            startService(intent);
        });

        Button startService = (Button)findViewById(R.id.startservice);
        startService.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MyService.class);

            intent.putExtra("Num", 100);
            startService(intent);
        });

        // Alarm Button을 눌렀을 때 동작할 Code
        Button alarmService = (Button)findViewById(R.id.alarmservice);
        alarmService.setOnClickListener(view -> {
            // Alarm관리 객체 생성
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

            // Alarm에 사용할 Intent 만들기
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

            // 뒤의 숫자는 구분하기 위한 Code 입니다.
            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            // Alarm 시간에서 30초 후 만들기
            // long amtime = System.currentTimeMillis() + 30000;
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.SECOND, 10);

            // Alarm 설정
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

        });

    }
}
