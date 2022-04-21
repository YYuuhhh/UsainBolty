package com.example.usainbolty;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class TipsFrag extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tips_frag, container, false);
        ListView listView = root.findViewById(R.id.listView);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar;
                snackbar = Snackbar.make(view, "Нажмите на номер совета, чтобы свернуть информацию", Snackbar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.lightgr));
                snackbar.show();
            }
        });
        ArrayList<String> main_text = new ArrayList<String>();
        int[] nazv={R.string.n1,R.string.n2,R.string.n3,R.string.n4,R.string.n5,R.string.n6,R.string.n7};
        for(int i=0;i<nazv.length;i++){
            main_text.add(getResources().getString(nazv[i]));

        }
        MyAdapter adapter = new MyAdapter( getContext(), main_text);
        listView.setAdapter(adapter);

        return root;
    }

    private class MyAdapter extends BaseAdapter {
        int[]images={R.drawable.k1,R.drawable.k2,R.drawable.k3,R.drawable.k4,R.drawable.k5,R.drawable.k6,R.drawable.k7};
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
            TextView txt1 = (TextView) convertView.findViewById(R.id.main_text);
            TextView txt2 =(TextView) convertView.findViewById(R.id.heading);
            ImageView img =(ImageView)convertView.findViewById(R.id.ig);
           int ps=position+1;

            LinearLayout ln =(LinearLayout) convertView.findViewById(R.id.linear);

            CardView card =(CardView) convertView.findViewById(R.id.card_view1);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 int v=(txt1.getVisibility()==View.GONE)? view.VISIBLE: View.GONE;
                    TransitionManager.beginDelayedTransition(ln,new AutoTransition());
                    txt1.setVisibility(v);

                }
            });
            txt2.setText("Совет №"+ps);
            txt1.setText(data1.get(position));
            img.setImageResource(images[position]);
            return convertView;
        }
    }
}
