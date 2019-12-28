package com.jjmgab.notifier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity
        extends AppCompatActivity
        implements NotificationItemFragment.OnListFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new NotificationItemFragment(), "Notification list");
        adapter.addFragment(new SettingsFragment(), "Settings");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onListFragmentInteraction(NotificationItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
