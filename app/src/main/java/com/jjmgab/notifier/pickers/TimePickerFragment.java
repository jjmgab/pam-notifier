package com.jjmgab.notifier.pickers;

import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OnPickTimeListener mCallback;

    public interface OnPickTimeListener {
        void onTimePicked(int hourOfDay, int minute);
    }

    public void setOnPickTimeListener(OnPickTimeListener callback) {
        this.mCallback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);

        // use this when format varies:
        //DateFormat.is24HourFormat(getActivity())
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mCallback.onTimePicked(hourOfDay, minute);
    }
}
