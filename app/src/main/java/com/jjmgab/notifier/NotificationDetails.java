package com.jjmgab.notifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jjmgab.notifier.constants.IntentExtraConstants;

public class NotificationDetails extends AppCompatActivity {

    private String mTitle;
    private String mDetails;
    private String mDate;
    private int mId;

    private static final String ERROR_ID_NOT_FOUND = "Id not found or provided.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        Intent i = getIntent();
        if (i.hasExtra(IntentExtraConstants.ID)
            && i.hasExtra(IntentExtraConstants.TITLE)
            && i.hasExtra(IntentExtraConstants.DETAILS)
            && i.hasExtra(IntentExtraConstants.DATE)) {
            mTitle = i.getStringExtra(IntentExtraConstants.TITLE);
            mDetails = i.getStringExtra(IntentExtraConstants.DETAILS);
            mDate = i.getStringExtra(IntentExtraConstants.DATE);
            mId = i.getIntExtra(IntentExtraConstants.ID, -1);

            if (mId == -1) {
                throw new IndexOutOfBoundsException(ERROR_ID_NOT_FOUND);
            }

            EditText editTextTitle = findViewById(R.id.editTextTitle);
            EditText editTextDetails = findViewById(R.id.editTextDescription);
            TextView textViewDate = findViewById(R.id.textViewDate);

            editTextTitle.setText(mTitle);
            editTextDetails.setText(mDetails);
            textViewDate.setText(mDate);
        }
    }

    public void onDeleteButtonClick(View view) {
        Intent i = new Intent();
        i.putExtra(IntentExtraConstants.ID, mId);
        setResult(RESULT_CANCELED, i);
        this.finish();
    }

    public void onAcceptButtonClick(View view) {
        setResult(RESULT_OK);
        this.finish();
    }
}
