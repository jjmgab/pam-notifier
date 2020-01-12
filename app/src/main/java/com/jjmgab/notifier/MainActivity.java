package com.jjmgab.notifier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.jjmgab.notifier.constants.IntentExtraConstants;
import com.jjmgab.notifier.helpers.DeviceStateHelper;
import com.jjmgab.notifier.helpers.PreferenceHelper;
import com.jjmgab.notifier.helpers.TimeHelper;

import java.time.LocalDateTime;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements NotificationItemFragment.OnListFragmentInteractionListener {

    private TabAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;
    private NotificationItemFragment mNotificationItemFragment;

    private final int REQUEST_CODE_ADD_NOTIFICATION = 10001;
    private final int REQUEST_CODE_VIEW_NOTIFICATION = 20001;

    private final int NOTIFICATION_TIME_MS = 3000;

    private static final String FRAGMENT_TITLE_NOTIFICATION_LIST = "Notification list";
    private static final String FRAGMENT_TITLE_SETTINGS = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeviceStateHelper.init(this);

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);

        mNotificationItemFragment = new NotificationItemFragment();

        mAdapter = new TabAdapter(getSupportFragmentManager());
        mAdapter.addFragment(mNotificationItemFragment, FRAGMENT_TITLE_NOTIFICATION_LIST);
        mAdapter.addFragment(new SettingsFragment(), FRAGMENT_TITLE_SETTINGS);

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
        NotificationItemSource.init(this, mNotificationItemFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_NOTIFICATION) {
            boolean hasTitle = data.hasExtra(IntentExtraConstants.TITLE) &&
                    data.hasExtra(IntentExtraConstants.DETAILS);
            boolean hasDate = data.hasExtra(IntentExtraConstants.YEAR) &&
                    data.hasExtra(IntentExtraConstants.MONTH) &&
                    data.hasExtra(IntentExtraConstants.DAY);
            boolean hasTime = data.hasExtra(IntentExtraConstants.HOUR) &&
                    data.hasExtra(IntentExtraConstants.MINUTE);

            if (hasTitle && hasTime && hasDate) {
                Bundle b = data.getExtras();

                // create new notification
                createNotification(new com.jjmgab.notifier.database.Notification(
                        b.getString(IntentExtraConstants.TITLE),
                        b.getString(IntentExtraConstants.DETAILS),
                        LocalDateTime.of(
                                b.getInt(IntentExtraConstants.YEAR),
                                b.getInt(IntentExtraConstants.MONTH),
                                b.getInt(IntentExtraConstants.DAY),
                                b.getInt(IntentExtraConstants.HOUR),
                                b.getInt(IntentExtraConstants.MINUTE)
                        )));
            }
        }
        if (resultCode == RESULT_CANCELED && requestCode == REQUEST_CODE_VIEW_NOTIFICATION) {
            if (data.hasExtra(IntentExtraConstants.ID)) {
                int id = data.getIntExtra(IntentExtraConstants.ID, -1);
                Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();

                NotificationCreator.cancelNotification(this, id);
                NotificationItemSource.removeItemById(id);
            }
        }


    }

    /**
     * Called on tap on notification item list.
     * @param item tapped notification item
     */
    @Override
    public void onListFragmentInteraction(com.jjmgab.notifier.database.Notification item) {
        Intent intent = new Intent(getApplicationContext(), NotificationDetails.class);
        intent.putExtra(IntentExtraConstants.ID, item.id);
        intent.putExtra(IntentExtraConstants.TITLE, item.title);
        intent.putExtra(IntentExtraConstants.DETAILS, item.details);
        intent.putExtra(IntentExtraConstants.DATE, item.date.toString());

        startActivityForResult(intent, REQUEST_CODE_VIEW_NOTIFICATION);
    }

    private void createNotification(com.jjmgab.notifier.database.Notification notification) {
        // add notification to the list
        NotificationItemSource.addItem(notification);

        // create notification channel & notification itself
        NotificationCreator.createNotificationChannel(getApplicationContext());
        Notification n = NotificationCreator.createNotification(getApplicationContext(), notification.title, notification.details);

        // setting time
        int time;
        if (PreferenceHelper.getBooleanPref(this, PreferenceHelper.PREF_DEBUG_FIXED_TIME)) {
            time = NOTIFICATION_TIME_MS;
        } else {
            time = TimeHelper.getSecondsToDateFromNow(notification.date);
        }

        if (PreferenceHelper.getBooleanPref(this, PreferenceHelper.PREF_DEBUG_TOASTS)) {
            Toast.makeText(this, String.valueOf(time), Toast.LENGTH_SHORT).show();
        }

        // schedule notification to appear at set time
        NotificationCreator.scheduleNotification(getApplicationContext(), n, notification.id, time);
    }
}
