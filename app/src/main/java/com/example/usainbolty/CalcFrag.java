package com.example.usainbolty;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;


public class CalcFrag extends Fragment {

    public static ViewPager2 viewPager = null;
    public static int time;
    public static int dist;
    public static int mult;
    public static TextView TextResult;
    public static TextView textResultInfo;
    public static TextView textResultTip;
    int prevPg = 1;

    public CalcFrag (){ }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.pager);
        viewPager.setId(View.generateViewId());
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(
                new PagerAdapter(this)
        );
        viewPager.setSaveEnabled(false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0) {
                    prevPg = viewPager.getCurrentItem();
                    viewPager.setCurrentItem(0,true);
                    MainActivity.menu.findItem(R.id.arrow_back).setVisible(false);
                    MainActivity.menu.findItem(R.id.arrow_forward).setVisible(false);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Счётчик");
                }
                else{
                    viewPager.setCurrentItem(prevPg, true);
                    switch(prevPg) {
                        case 1:
                            MainActivity.menu.findItem(R.id.arrow_back).setVisible(false);
                            MainActivity.menu.findItem(R.id.arrow_forward).setVisible(true);
                            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Выбор устройства");
                            break;
                        case 2:
                            MainActivity.menu.findItem(R.id.arrow_back).setVisible(true);
                            MainActivity.menu.findItem(R.id.arrow_forward).setVisible(true);
                            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Время и расстояние");
                            break;
                        case 3:
                            MainActivity.menu.findItem(R.id.arrow_back).setVisible(true);
                            MainActivity.menu.findItem(R.id.arrow_forward).setVisible(false);
                            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Результат");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + prevPg);
                    }
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.calc_frag, container, false);
        return view;
    }

    private static class PagerAdapter extends FragmentStateAdapter {
        private Fragment Fragment4 = new CalcFragInst(0);
        private Fragment Fragment1 = new CalcFragInst(1);
        private Fragment Fragment2 = new CalcFragInst(2);
        private Fragment Fragment3 = new CalcFragInst(3);

        public PagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return Fragment4;
                case 1:
                    return Fragment1;
                case 2:
                    return Fragment2;
                default:
                    return Fragment3;
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
