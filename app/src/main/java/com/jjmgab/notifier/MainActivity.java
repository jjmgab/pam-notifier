package com.jjmgab.notifier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;

public class MainActivity
        extends AppCompatActivity
        implements NotificationItemFragment.OnListFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    private TabAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private FloatingActionButton mFloatingActionButton;

    private NotificationItemFragment mNotificationItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);

        mNotificationItemFragment = new NotificationItemFragment();

        mAdapter = new TabAdapter(getSupportFragmentManager());
        mAdapter.addFragment(mNotificationItemFragment, "Notification list");
        mAdapter.addFragment(new SettingsFragment(), "Settings");

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mFloatingActionButton = findViewById(R.id.floating_action_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open new notification activity here
                Intent intent = new Intent(getApplicationContext(), AddNotification.class);
                startActivity(intent);
            }
        });

        // initialize database connection
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
