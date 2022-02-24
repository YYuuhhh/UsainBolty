package com.example.usainbolty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class TipsFrag extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tips_frag, container, false);
        ListView listView = root.findViewById(R.id.listView);

        ArrayList<String> Names = new ArrayList<String>();
        int[] nazv={R.string.n1,R.string.n2,R.string.n3,R.string.n4,R.string.n5,R.string.n6,R.string.n7};
        for(int i=0;i<nazv.length;i++){
            Names.add(getResources().getString(nazv[i]));
        }
        MyAdapter adapter = new MyAdapter( getContext(),Names);
        listView.setAdapter(adapter);

        return root;
    }

    private class MyAdapter extends BaseAdapter {
        
        ArrayList<String> data1 = new ArrayList<String>();
        Context context;
        MyAdapter(Context context, ArrayList<String> dt1){
            data1=dt1;

            this.context=context;
        }
        @Override
        public int getCount() {
            return data1.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item, parent, false);
            }
            TextView txt1 = (TextView) convertView.findViewById(R.id.textView);
            txt1.setText(data1.get(position));

            return convertView;
        }
    }
}
