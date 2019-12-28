package com.jjmgab.notifier;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjmgab.notifier.NotificationItemFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NotificationItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyNotificationItemRecyclerViewAdapter extends RecyclerView.Adapter<MyNotificationItemRecyclerViewAdapter.ViewHolder> {

    private final List<NotificationItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyNotificationItemRecyclerViewAdapter(List<NotificationItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notificationitem, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called on item binding.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mTitleView.setText(mValues.get(position).title);
        holder.mDateView.setText(mValues.get(position).date.toString());
        holder.mTimeView.setText(mValues.get(position).time.toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Holds the view for {@link NotificationItem}.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mTitleView;
        public final TextView mDateView;
        public final TextView mTimeView;
        public NotificationItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mTitleView = view.findViewById(R.id.title);
            mDateView = view.findViewById(R.id.date);
            mTimeView = view.findViewById(R.id.time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
