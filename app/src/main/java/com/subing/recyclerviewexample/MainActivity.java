package com.subing.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import com.subing.recyclerviewexample.databinding.ActivityMainBinding;

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