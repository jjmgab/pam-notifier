package com.jjmgab.notifier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;

public class MainActivity
        extends AppCompatActivity
        implements NotificationItemFragment.OnListFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private NotificationItemFragment mNotificationItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        mNotificationItemFragment = new NotificationItemFragment();

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(mNotificationItemFragment, "Notification list");
        adapter.addFragment(new SettingsFragment(), "Settings");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        NotificationItemSource.init(this, mNotificationItemFragment, false);

        // for test purposes
        NotificationItemSource.addItem(new com.jjmgab.notifier.database.Notification(
                "Manually added title",
                "Manual description",
                LocalDateTime.now()
        ));
    }

    @Override
    public void onListFragmentInteraction(com.jjmgab.notifier.database.Notification item) {
        NotificationCreator.createNotificationChannel(this);
        Notification n = NotificationCreator.createNotification(this, item.title, item.details);
        NotificationCreator.scheduleNotification(this, n, 5000);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
