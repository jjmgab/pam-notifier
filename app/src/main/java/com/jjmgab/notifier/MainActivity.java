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

    private final int REQUEST_CODE_ADD_NOTIFICATION = 10001;

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
                Intent intent = new Intent(getApplicationContext(), AddNotification.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTIFICATION);
            }
        });

        // initialize database connection
        NotificationItemSource.init(this, mNotificationItemFragment, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_NOTIFICATION) {
            boolean hasTitle = data.hasExtra("title") &&
                    data.hasExtra("description");
            boolean hasDate = data.hasExtra("year") &&
                    data.hasExtra("month") &&
                    data.hasExtra("day");
            boolean hasTime = data.hasExtra("hour") &&
                    data.hasExtra("minute");

            if (hasTitle && hasTime && hasDate) {
                Bundle b = data.getExtras();
                NotificationItemSource.addItem(new com.jjmgab.notifier.database.Notification(
                        b.getString("title"),
                        b.getString("description"),
                        LocalDateTime.of(
                                b.getInt("year"),
                                b.getInt("month"),
                                b.getInt("day"),
                                b.getInt("hour"),
                                b.getInt("minute")
                        )
                ));
            }
        }
    }

    /**
     * Called on tap on notification item list.
     * @param item tapped notification item
     */
    @Override
    public void onListFragmentInteraction(com.jjmgab.notifier.database.Notification item) {
        // for testing purposes; this should be added when creating new notification
        NotificationCreator.createNotificationChannel(this);
        Notification n = NotificationCreator.createNotification(this, item.title, item.details);
        NotificationCreator.scheduleNotification(this, n, 5000);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
