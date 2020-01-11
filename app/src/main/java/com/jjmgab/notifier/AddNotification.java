package com.jjmgab.notifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjmgab.notifier.pickers.DatePickerFragment;
import com.jjmgab.notifier.pickers.TimePickerFragment;

import java.time.LocalDateTime;
import java.util.Locale;


public class AddNotification extends AppCompatActivity implements TimePickerFragment.OnPickTimeListener, DatePickerFragment.OnPickDateListener {

    private boolean mIsTimeSet;
    private int mHour;
    private int mMinute;

    private boolean mIsDateSet;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnPickDateListener(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setOnPickTimeListener(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimePicked(int hourOfDay, int minute) {
        mIsTimeSet = true;

        mHour = hourOfDay;
        mMinute = minute;

        TextView textView = findViewById(R.id.textViewTime);
        textView.setText(String.format(Locale.getDefault(), "Time selected: %02d:%02d", mHour, mMinute));
    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        mIsDateSet = true;

        mYear = year;
        mMonth = month + 1;
        mDay = day;

        TextView textView = findViewById(R.id.textViewDate);
        textView.setText(String.format(Locale.getDefault(), "Date selected: %04d-%02d-%02d", mYear, mMonth, mDay));
    }

    public void onCancelButtonClick(View view) {
        setResult(RESULT_CANCELED);
        super.finish();
    }

    public void onAcceptButtonClick(View view) {
        EditText inputTitle = findViewById(R.id.editTextTitle);
        EditText inputDescription = findViewById(R.id.editTextDescription);

        String title = inputTitle.getText().toString();
        String description = inputDescription.getText().toString();

        // validate for empty fields
        if("".equals(title) || "".equals(description) || !mIsDateSet || !mIsTimeSet) {
            Toast.makeText(this, "Data not filled correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        // validate for date and time set in the past
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime selected = LocalDateTime.of(mYear, mMonth, mDay, mHour, mMinute);

        boolean isDateInThePast = now.toLocalDate().compareTo(selected.toLocalDate()) > 0;
        boolean isDateNow = now.toLocalDate().compareTo(selected.toLocalDate()) == 0;
        boolean isTimeEarlierOrNow = now.toLocalTime().compareTo(selected.toLocalTime()) >= 0;

        if (isDateInThePast || (isDateNow && isTimeEarlierOrNow)) {
            Toast.makeText(this, "Date and time cannot be set to past", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("year", mYear);
        data.putExtra("month", mMonth);
        data.putExtra("day", mDay);
        data.putExtra("hour", mHour);
        data.putExtra("minute", mMinute);
        data.putExtra("title", title);
        data.putExtra("description", description);

        setResult(RESULT_OK, data);
        super.finish();
    }
}
