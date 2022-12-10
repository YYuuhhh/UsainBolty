package com.example.usainbolty;

import static com.example.usainbolty.MainActivity.b;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.ekn.gruzer.gaugelibrary.FullGauge;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalcFragInst extends Fragment {
    private ArrayList<MaterialCardView> CardList = new ArrayList();
    private ArrayList<Appliances> AppliancesList = new ArrayList();
    private View view = null;
    private int page;
    private int cardnumba = 16;
    private int CurrentNum;
    public static int click = 10;
    Thread thread = null;
    public static TextView numba;
    public CalcFragInst (){ }
    public CalcFragInst(int page){
        this.page = page;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppliancesList.add(new Appliances("Аналоговые часы", "Наручные часы швейцарского производства", 2, R.drawable.watch));
        AppliancesList.add(new Appliances("Электрическая открывашка для консервов", "Очень опасна для простых смертных", 60, R.drawable.canopener));
        AppliancesList.add(new Appliances("Зарядное устройство", "Зарядное устройство USB type-c", 3, R.drawable.charger));
        AppliancesList.add(new Appliances("Медиа центр", "Устройство для воспроизведения телевидения, музыки, видео на DVD", 7, R.drawable.boombox1));
        AppliancesList.add(new Appliances("Индукционная плита", "Разогревает железосодержащую посуду", 7, R.drawable.oven1));
        AppliancesList.add(new Appliances("Стиральная машина", "Автономная установка для стирки текстильных изделий", 8, R.drawable.washingmachine));
        AppliancesList.add(new Appliances("Мультиварка", "Электрокастрюля для автоматического выполнения различных видов тепловой обработки пищи", 5, R.drawable.multicooker));
        AppliancesList.add(new Appliances("Холодильник", "Устройство, поддерживающее низкую температуру в теплоизолированной камере", 9, R.drawable.fridge));
        AppliancesList.add(new Appliances("Миксер", "Оборудование, предназначенное для замешивания и взбивания кондитерских масс", 15, R.drawable.mixer));
        AppliancesList.add(new Appliances("Кофеварка", "Устройство для приготовления кофе", 13, R.drawable.coffeemaker));
        AppliancesList.add(new Appliances("Робот пылесос", "Устройство для уборки в автоматическом режиме", 11, R.drawable.robotvacuumcleaner));
        AppliancesList.add(new Appliances("Кондиционер", "Аппарат для охлаждения воздуха в помещении", 19, R.drawable.conditioner));
        AppliancesList.add(new Appliances("Ноутбук", "Компактный портативный компьютер", 6, R.drawable.notebook));
        AppliancesList.add(new Appliances("Тостер", "Устройство для приготовления тостов", 2, R.drawable.toaster1));
        AppliancesList.add(new Appliances("Увлажнитель", "Прибор, увеличивающий количество водяных паров в воздухе", 5, R.drawable.humidifier));
        AppliancesList.add(new Appliances("Пылесос", "Аппарат для очистки от пыли помещений, мебели", 18, R.drawable.vacuumcleaner1));
        AppliancesList.add(new Appliances("Чайник", "Устройство для нагревания воды до температуры кипения", 10, R.drawable.kettle));



        switch (page) {
            case 1:
                view = inflater.inflate(R.layout.calc_frag_pg1, container, false);
                ListView listView = view.findViewById(R.id.list);
                ArrayList<String> Text = new ArrayList<String>();
                ArrayList<String> Subtext = new ArrayList<String>();
                ArrayList<Integer> Multiplier = new ArrayList<Integer>();
                ArrayList<Integer> Image = new ArrayList<Integer>();
                for (int i = 0; i < AppliancesList.size(); i++) {
                    Text.add(AppliancesList.get(i).gettext());
                    Subtext.add(AppliancesList.get(i).getsubtext());
                    Multiplier.add(AppliancesList.get(i).getmultiplier());
                    Image.add(AppliancesList.get(i).getimage());
                }
                ListAdapter adapter = new ListAdapter(getContext(), Text, Subtext, Multiplier, Image);
                listView.setAdapter(adapter);
                return view;
            case 2:
                view = inflater.inflate(R.layout.calc_frag_pg2, container, false);
                SeekBar timeBar = view.findViewById(R.id.TimeBar);
                SeekBar distBar = view.findViewById(R.id.DistBar);
                TextView timeViewCount = view.findViewById(R.id.textTimeCount);
                TextView distViewCount = view.findViewById(R.id.textDistCount);
                SeekBar.OnSeekBarChangeListener seekTimeBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        timeViewCount.setText(progress + " Hours");
                        CalcFrag.time = progress;
                        if (CalcFrag.TextResult != null) {
                            setCurrentText();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };
                SeekBar.OnSeekBarChangeListener seekDistBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        distViewCount.setText(progress + " Centimeters");
                        CalcFrag.dist = progress;
                        if (CalcFrag.TextResult != null) {
                            setCurrentText();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                };

                timeBar.setOnSeekBarChangeListener(seekTimeBarChangeListener);
                distBar.setOnSeekBarChangeListener(seekDistBarChangeListener);

                int timeCount = timeBar.getProgress();
                int distCount = distBar.getProgress();

                timeViewCount.setText(timeCount + " Hours");
                distViewCount.setText(distCount + " Centimeters");
                CalcFrag.time = timeCount;
                CalcFrag.dist = distCount;

                return view;
            case 3:
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) inflater.inflate(R.layout.calc_frag_pgf, container, false);

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
                    if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });
                swipe.setBackgroundColor(cardview.getCardBackgroundColor().getDefaultColor());
                return coordinatorLayout;
            default:
                view = inflater.inflate(R.layout.calc_frag_nfc, container, false);

                HorizontalScrollView scrollView = view.findViewById(R.id.main_hsv);

                scrollView.setOnTouchListener(this::onTouch);

                HalfGauge fullGauge = view.findViewById(R.id.halfGauge);

                Range range1 = new Range();
                range1.setColor(Color.parseColor("#00b20b"));
                range1.setFrom(0.0);
                range1.setTo(50.0);
                Range range2 = new Range();
                range2.setColor(Color.parseColor("#E3E500"));
                range2.setFrom(50.0);
                range2.setTo(100.0);
                Range range3 = new Range();
                range3.setColor(Color.parseColor("#ce0000"));
                range3.setFrom(100.0);
                range3.setTo(150.0);

                fullGauge.setMinValue(0.0);
                fullGauge.setMaxValue(150.0);
                fullGauge.setValue(140.0);

                fullGauge.addRange(range1);
                fullGauge.addRange(range2);
                fullGauge.addRange(range3);
                Log.d("fdfdfdfdfd", Build.MODEL);
                if(Build.MODEL == "G2333") {

                    Handler handler = new Handler(Looper.getMainLooper());

                    thread = new Thread(() -> {
                        Random rand = new Random();
                        while (true) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(() -> {
                                fullGauge.setValue(rand.nextInt(140));
                            });
                        }
                    });
                    thread.start();
                }
                ArrayList<BarData> dataList = new ArrayList<>();

                BarData data = new BarData("MON", 3.4f, "3.4€");
                dataList.add(data);

                data = new BarData("TUE", 8f, "8€");
                dataList.add(data);

                data = new BarData("WEN", 1.8f, "1.8€");
                dataList.add(data);

                data = new BarData("THU", 7.3f, "7.3€");
                dataList.add(data);

                data = new BarData("FRI", 6.2f, "6.2€");
                dataList.add(data);

                data = new BarData("SAT", 3.3f, "3.3€");
                dataList.add(data);

                data = new BarData("NOW", 3.3f, "3.3€");
                dataList.add(data);

                ChartProgressBar mChart = (ChartProgressBar) view.findViewById(R.id.ChartProgressBar);

                mChart.setDataList(dataList);
                mChart.build();

                ImageView iv = (ImageView) view.findViewById(R.id.alert);

                ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                        iv,
                        PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.2f));
                scaleDown.setDuration(800);

                scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
                scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

                scaleDown.start();

                WindowManager.LayoutParams wp = getActivity().getWindow().getAttributes();
                wp.dimAmount = 0.75f;

                return view;
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
//            Log.d("gfgfgfgfgfgf", String.valueOf(cardnumba));
//            cardView.setOnClickListener(v -> {
//                for(int i=0;i<numba;i++) {
//                    if (CardList.get(i).isChecked())
//                        CardList.get(i).toggle();
//                }
//                for(int i=0;i<cardnumba;i++) {
//                    Log.d("gfgfgfgfgfgf", String.valueOf(CardList.get(i)));
//                    if (CardList.get(i).equals(cardView.toString())) {
//                        cardView.toggle();
//                        CalcFrag.mult = AppliancesList.get(i).getmultiplier();
//                    }
//                    if(CalcFrag.TextResult!=null)
//                        setCurrentText();
//                }
//                MainActivity.menu.findItem(R.id.arrow_forward).setVisible(true);
//            });

            cardView.setOnClickListener(v -> {
                for(int i=0;i<CardList.size();i++) {
                    if (CardList.get(i).isChecked())
                        CardList.get(i).toggle();
                    if (CardList.get(i).toString().equals(cardView.toString())) {
                        cardView.toggle();
                        CalcFrag.mult = AppliancesList.get(i).getmultiplier();
                    }
                    if(CalcFrag.TextResult!=null)
                        setCurrentText();
                }
                MainActivity.menu.findItem(R.id.arrow_forward).setVisible(true);
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

    public boolean onTouch(View v, MotionEvent event) {
        return click < 10;
    }
}
