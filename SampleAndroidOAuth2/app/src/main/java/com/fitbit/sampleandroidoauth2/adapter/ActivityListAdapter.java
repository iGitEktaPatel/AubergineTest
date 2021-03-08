package com.fitbit.sampleandroidoauth2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fitbit.api.models.ActivityData;
import com.fitbit.sampleandroidoauth2.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter {
    private List<ActivityData> list = new ArrayList<>();
    private Context mContext;

    /***
     * @param list = list of activity
     * @param mContext = activity context
     */
    public ActivityListAdapter(List<ActivityData> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_activity_list, parent, false);

        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ActivityData activityData = (ActivityData) getLists().get(position);

        ((ViewHolder) holder).txtName.setText(activityData.getName());

    }

    @Override
    public int getItemCount() {
        return getLists().size();
    }


    /***
     * @info view holder
     */
    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;

        public ViewHolder(View v) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txtTitle);
        }
    }

    public List<ActivityData> getLists() {
        return this.list;
    }

    public void setLists(List<ActivityData> keywordLists) {
        this.list = keywordLists;
    }

}