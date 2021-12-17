package com.subing.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
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

import com.subing.recyclerviewexample.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
//    private NotificationFragment notificationFragment;
//    private RecyclerFragment recyclerFragment;
    private ActivityMainBinding binding;    // DataBinding 객체 생성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        notificationFragment = (NotificationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_noti);
//        recyclerFragment = (RecyclerFragment) getSupportFragmentManager().findFragmentById(R.id.recyclerView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_noti, new NotificationFragment(), NotificationFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();

        FragmentManager fragmentManager2 = getSupportFragmentManager();
        fragmentManager2
                .beginTransaction()
                .replace(R.id.fragment_recycler, new RecyclerFragment(), RecyclerFragment.class.getSimpleName())
                .commit();
    }
}