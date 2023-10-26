package au.edu.federation.itech3107.studentattendance30395752.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395752.R;

public class CustomAdapter extends BaseAdapter {
    private List<String> mData;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, List<String> data) {
        this.mData = data;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_spinner_dropdown, null);
        }

        CheckedTextView textView = convertView.findViewById(R.id.text1);
        textView.setText(mData.get(position).toString());
       TextView textWeek =  convertView.findViewById(R.id.tv_week);
        textWeek.setText( (position + 1) + "week ");

        return convertView;
    }
}
