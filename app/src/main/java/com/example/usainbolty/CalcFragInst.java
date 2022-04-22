package com.example.usainbolty;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CalcFragInst extends Fragment {
    private ArrayList<MaterialCardView> CardList = new ArrayList();
    private ArrayList<Appliances> AppliancesList = new ArrayList();
    private View view = null;
    private int page;
    private int CurrentNum;
    public CalcFragInst (){ }
    public CalcFragInst(int page){
        this.page = page;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppliancesList.add(new Appliances("Печь","Типичная деревенская печь",1,R.drawable.oven));
        AppliancesList.add(new Appliances("Аналоговые часы","Наручные часы швейцарского производства",2,R.drawable.watch));
        AppliancesList.add(new Appliances("Электрическая открывашка для консервов","Очень опасна для простых смертных",60,R.drawable.canopener));
        AppliancesList.add(new Appliances("Зарядное устройство","Зарядное устройство USB type-c",3,R.drawable.charger));
        switch (page) {
            default:
                view = inflater.inflate(R.layout.calc_frag_pg1, container, false);
                ListView listView = view.findViewById(R.id.list);
                ArrayList<String> Text = new ArrayList<String>();
                ArrayList<String> Subtext = new ArrayList<String>();
                ArrayList<Integer> Multiplier = new ArrayList<Integer>();
                ArrayList<Integer> Image = new ArrayList<Integer>();
                for(int i=0;i<AppliancesList.size();i++){
                    Text.add(AppliancesList.get(i).gettext());
                    Subtext.add(AppliancesList.get(i).getsubtext());
                    Multiplier.add(AppliancesList.get(i).getmultiplier());
                    Image.add(AppliancesList.get(i).getimage());
                }
                ListAdapter adapter = new ListAdapter( getContext(),Text,Subtext,Multiplier,Image);
                listView.setAdapter(adapter);
                return view;
            case 1:
                view = inflater.inflate(R.layout.calc_frag_pg2, container, false);
                SeekBar timeBar = view.findViewById(R.id.TimeBar);
                SeekBar distBar = view.findViewById(R.id.DistBar);
                TextView timeViewCount = view.findViewById(R.id.textTimeCount);
                TextView distViewCount = view.findViewById(R.id.textDistCount);
                SeekBar.OnSeekBarChangeListener seekTimeBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        timeViewCount.setText(progress+" Hours");
                        CalcFrag.time=progress;
                        if(CalcFrag.TextResult!=null) {
                            setCurrentText();
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) { }
                };
                SeekBar.OnSeekBarChangeListener seekDistBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        distViewCount.setText(progress+" Centimeters");
                        CalcFrag.dist=progress;
                        if(CalcFrag.TextResult!=null) {
                            setCurrentText();
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) { }
                };

                timeBar.setOnSeekBarChangeListener(seekTimeBarChangeListener);
                distBar.setOnSeekBarChangeListener(seekDistBarChangeListener);

                int timeCount = timeBar.getProgress();
                int distCount = distBar.getProgress();

                timeViewCount.setText(timeCount+" Hours");
                distViewCount.setText(distCount+" Centimeters");
                CalcFrag.time=timeCount;
                CalcFrag.dist=distCount;

                return view;
            case 2:
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout)inflater.inflate(R.layout.calc_frag_pgf, container, false);

                LinearLayout contentLayout = coordinatorLayout.findViewById(R.id.contentLayout);
                View swipe = coordinatorLayout.findViewById(R.id.swiperefresh);
                CardView cardview = coordinatorLayout.findViewById(R.id.card_view);
                CalcFrag.TextResult = coordinatorLayout.findViewById(R.id.textResult);
                CalcFrag.textResultInfo = coordinatorLayout.findViewById(R.id.textResultInfo);
                CalcFrag.textResultTip = coordinatorLayout.findViewById(R.id.textResultTip);

                setCurrentText();
                BottomSheetBehavior<LinearLayout> sheetBehavior = BottomSheetBehavior.from(contentLayout);
                sheetBehavior.setHalfExpandedRatio(0.000001f);
                sheetBehavior.setFitToContents(false);
                sheetBehavior.setHideable(false);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                cardview.setOnClickListener(v -> {
                    if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                    else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });
                swipe.setBackgroundColor(cardview.getCardBackgroundColor().getDefaultColor());
                return coordinatorLayout;
        }
    }
    private class ListAdapter extends BaseAdapter {

        ArrayList<String> text = new ArrayList<String>();
        ArrayList<String> subtext = new ArrayList<String>();
        ArrayList<Integer> multiplier = new ArrayList<Integer>();
        ArrayList<Integer> image = new ArrayList<Integer>();
        Context context;
        ListAdapter(Context context, ArrayList<String> dt1, ArrayList<String> dt2, ArrayList<Integer> dt3, ArrayList<Integer> dt4){
            this.text=dt1;
            this.subtext=dt2;
            this.multiplier=dt3;
            this.image=dt4;
            this.context=context;
        }
        @Override
        public int getCount() {
            return text.size();
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
                convertView = inflater.inflate(R.layout.appliances_item, parent, false);
            }
            MaterialCardView cardView = convertView.findViewById(R.id.card_view);
            if(CardList.contains(cardView)){ }
            else{
                CardList.add(cardView);
            }
            TextView txt1 = (TextView) convertView.findViewById(R.id.textViewer);
            TextView txt2 = (TextView) convertView.findViewById(R.id.textViewerer);
            ImageView img = (ImageView) convertView.findViewById(R.id.imageViewer);
            cardView.setOnClickListener(v -> {
                for(int i=0;i<CardList.size();i++) {
                    if (CardList.get(i).isChecked())
                        CardList.get(i).toggle();
                    if (CardList.get(i).toString().equals(cardView.toString())) {
                        CalcFrag.mult = AppliancesList.get(i).getmultiplier();
                    }
                    if(CalcFrag.TextResult!=null)
                        setCurrentText();
                }
                cardView.toggle();
                MainActivity.menu.findItem(R.id.arrow_forward).setEnabled(true);

            });
            txt1.setText(text.get(position));
            txt2.setText(subtext.get(position));
            img.setImageResource(image.get(position));
            return convertView;
        }
    }
    private void setCurrentText(){
        CurrentNum = (int) (1.0/(CalcFrag.dist)*CalcFrag.time*CalcFrag.time*CalcFrag.mult*0.25);
        CalcFrag.TextResult.setText(CurrentNum+" мГс");
        if(CurrentNum<=30) {
            CalcFrag.textResultInfo.setText(R.string.n8);
            CalcFrag.textResultTip.setText(R.string.n18);
            CalcFrag.TextResult.setTextColor(getContext().getColor(R.color.Gradient1));
        }
        if(CurrentNum>30&&CurrentNum<=100){
            CalcFrag.textResultInfo.setText(R.string.n9);
            CalcFrag.textResultTip.setText(R.string.n19);
            CalcFrag.TextResult.setTextColor(getContext().getColor(R.color.Gradient2));
        }
        if(CurrentNum>100&&CurrentNum<=350){
            CalcFrag.textResultInfo.setText(R.string.n10);
            CalcFrag.textResultTip.setText(R.string.n20);
            CalcFrag.TextResult.setTextColor(getContext().getColor(R.color.Gradient3));
        }
        if(CurrentNum>350){
            CalcFrag.textResultInfo.setText(R.string.n11);
            CalcFrag.textResultTip.setText(R.string.n21);
            CalcFrag.TextResult.setTextColor(getContext().getColor(R.color.Gradient4));
        }

    }
}
