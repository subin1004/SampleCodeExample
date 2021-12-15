package com.subing.recyclerviewexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationFragment extends Fragment implements View.OnClickListener{
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

    private View view;
    private Button btn_bigText;
    private Button btn_bigPicture;
    private Button btn_inbox;


    // notification
    NotificationManagerCompat mNotificationManagerCompat;
    Spinner spinner;
    private int mSelectedNotification = 0;

    // spinner
    int imgThumb ;
    String textTitle;
    String textContent;
    int buttonNumber;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);

        spinner = view.findViewById(R.id.spinner);
        btn_bigText = view.findViewById(R.id.btn_bigText);
        btn_bigPicture = view.findViewById(R.id.btn_bigPicture);
        btn_inbox = view.findViewById(R.id.btn_inbox);

        mNotificationManagerCompat = NotificationManagerCompat.from(getContext());

        createNotificationChannel();    // channel을 매번 만들 필요가 없기 때문에 일단 위로
        // Button OnClickListener 생성
        btn_bigText.setOnClickListener(this);
        btn_bigPicture.setOnClickListener(this);
        btn_inbox.setOnClickListener(this);

        // spinner의 adapter 생성
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        Data.getTitles());

        // spinner의 layout지정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner의 어댑터 적용
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


        return view;
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

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /////// fragment에서는 layout의 onClick 속성을 사용할 수 없음!!!!
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), DetailActivity.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID);

        switch (v.getId())
        {
            case R.id.btn_bigText:
                Log.i("subin", "btn_bigText case");
                NotificationCompat.BigTextStyle style_bigText = new NotificationCompat.BigTextStyle()
                        .bigText(textContent);
                builder.setStyle(style_bigText);
                buttonNumber = 0;
                break;

            case R.id.btn_bigPicture:
                Log.i("subin", "btn_bigPicture case");
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
                Log.i("subin", "btn_inbox case");
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

        intent.putExtra(DetailActivity.EXTRA_PARAM_ID, spinner.getSelectedItem().toString());
        //Log.i("subin", "spinner selected item: " + spinner.getSelectedItem().toString());
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(DATA_TITLE[buttonNumber])                         // 접혀있을때 보이는 title
                .setContentText(NOTIFICATION_STYLES_DESCRIPTION[buttonNumber])     // 접혀있을때 보이는 text
                .setPriority(NotificationCompat.PRIORITY_HIGH)                     // Head Up Notification: DEFAULT -> HIGH
                .setFullScreenIntent(pendingIntent, true)                 // Head Up Notification added
                // 위에서 정의한 pendingIntent 지정
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //호출
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(notificationId, builder.build());
    }
}
