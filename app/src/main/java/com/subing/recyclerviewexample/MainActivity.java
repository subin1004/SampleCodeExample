package com.subing.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnListItemSelectedInterface{

    public static final int NOTIFICATION_ID = 888;

    static final String CHANNEL_ID = "channelId";
    static final int notificationId = 0;

    // Notification 유형 String
    private static final String BIG_TEXT_STYLE = "BIG_TEXT_STYLE";
    private static final String BIG_PICTURE_STYLE = "BIG_PICTURE_STYLE";
    private static final String INBOX_STYLE = "INBOX_STYLE";
    private static final String MESSAGING_STYLE = "MESSAGING_STYLE";

    private static final String[] DATA_TITLE = {
            BIG_TEXT_STYLE, BIG_PICTURE_STYLE, INBOX_STYLE, MESSAGING_STYLE
    };

    private static final String[] NOTIFICATION_STYLES_DESCRIPTION = {
            "Demos reminder type app using BIG_TEXT_STYLE",
            "Demos social type app using BIG_PICTURE_STYLE + inline notification response",
            "Demos email type app using INBOX_STYLE",
            "Demos messaging app using MESSAGING_STYLE + inline notification responses"
    };


    // notification
    NotificationManagerCompat mNotificationManagerCompat;
    Spinner spinner;
    private int mSelectedNotification = 0;

    // spinner
    int imgThumb ;
    String textTitle;
    String textContent;
    int buttonNumber;

    // recyclerview
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerInit();
        notificationInit();
    }

    private void recyclerInit(){
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Data> data = new ArrayList<>(Data.DATA);

        recyclerAdapter = new RecyclerAdapter(data, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void notificationInit(){
        spinner = (Spinner)findViewById(R.id.spinner);

        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        // adapter 생성
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item,
                        Data.getTitles());

        // spinner의 layout지정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 어댑터 적용
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("subin", "onItemSelected(): position: " + position + " id: " + id);
                mSelectedNotification = position;

                Data data = Data.getItem(Data.getTitles().get(mSelectedNotification));
                imgThumb = data.getPhoto();
                textTitle = data.getTitle();
                textContent = data.getContent();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectedNotification = 0;
            }
        });

    }



    // recyclerview item click
    @Override
    public void onItemSelected(View view, int position) {
        RecyclerAdapter.ViewHolder viewHolder = (RecyclerAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

        String extraTitle = viewHolder.textView.getText().toString();   // intentExtra = title
        Toast.makeText(this, extraTitle, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PARAM_ID, extraTitle);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
//                        MainActivity.this, view, ViewCompat.getTransitionName(view) // 이거는 창 전체를 이동할 때
                        // View와 ViewName을 쌍으로 여러개 전달
                        MainActivity.this,
                        new Pair<>(view.findViewById(R.id.imageView), DetailActivity.VIEW_NAME_HEADER_IMAGE),
                        new Pair<>(view.findViewById(R.id.textView), DetailActivity.VIEW_NAME_HEADER_TITLE),
                        new Pair<>(view.findViewById(R.id.textView2), DetailActivity.VIEW_NAME_HEADER_CONTENT)
                );

        startActivity(intent, options.toBundle());
    }

    // Button들 onClick Event
    public void onClickButton(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(DATA_TITLE[buttonNumber])                         // 접혀있을때 보이는 title
                .setContentText(NOTIFICATION_STYLES_DESCRIPTION[buttonNumber])     // 접혀있을때 보이는 text
                .setPriority(NotificationCompat.PRIORITY_HIGH)                     // Head Up Notification: DEFAULT -> HIGH
                .setFullScreenIntent(pendingIntent, true)                 // Head Up Notification added
                // 위에서 정의한 pendingIntent 지정
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        switch (view.getId())
        {
            case R.id.btn_bigText:
                NotificationCompat.BigTextStyle style_bigText = new NotificationCompat.BigTextStyle()
                        .bigText(textContent);
                builder.setStyle(style_bigText);
                buttonNumber = 0;
                break;

            case R.id.btn_bigPicture:
                NotificationCompat.BigPictureStyle style_bigPicture = new NotificationCompat.BigPictureStyle()
                        .bigPicture(
                                BitmapFactory.decodeResource(
                                        getResources(),
                                        imgThumb))
                        .setBigContentTitle(textTitle)
                        .setSummaryText(textContent);
                builder.setStyle(style_bigPicture);
                buttonNumber = 1;
                break;

            case R.id.btn_inbox:
                NotificationCompat.InboxStyle style_inbox = new NotificationCompat.InboxStyle()
                        .addLine("one")
                        .addLine("two")
                        .addLine("three")
                        .addLine("four")
                        .addLine("five");
                builder.setStyle(style_inbox);
                buttonNumber = 2;
                break;

            default:
                break;
        }

//        Bundle bundle = new Bundle();
//        bundle.putInt("picture", imgThumb);
//        bundle.putString("title", textTitle);
//        bundle.putString("content", textContent);
//        intent.putExtras(bundle);

        // intent putExtra 부분을 함수로 따로 빼서 하는게 나을것같음 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        //호출
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());

    }

    // channel 만들기 method
    private void createNotificationChannel() {
        // Android8.0 이상인지 확인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 채널에 필요한 정보 제공
            CharSequence name = "name"; //getString(R.string.channel_name);
            String description = "descrption"; //getString(R.string.channel_description);

            // 중요도 설정, Android7.1 이하는 다른 방식으로 지원한다.
            int importance = NotificationManager.IMPORTANCE_HIGH;   // Head Up Notification: DEFAULT -> HIGH

            // 채널 생성
            // (id, name, importance)
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}