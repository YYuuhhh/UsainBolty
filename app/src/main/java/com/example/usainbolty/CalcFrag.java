package com.example.usainbolty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


public class CalcFrag extends Fragment {

    public static ViewPager2 viewPager = null;
    public static int time;
    public static int dist;
    public static int mult;
    public static TextView TextResult;
    public static TextView textResultInfo;
    public static TextView textResultTip;

    public CalcFrag (){ }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (getActivity()).findViewById(R.id.pager);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(
                new PagerAdapter(this)
        );
        viewPager.setSaveEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.calc_frag, container, false);
        return view;
    }

    private static class PagerAdapter extends FragmentStateAdapter {
        private Fragment Fragment1 = new CalcFragInst(0);
        private Fragment Fragment2 = new CalcFragInst(1);
        private Fragment Fragment3 = new CalcFragInst(2);

        public PagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return Fragment2;
                case 2:
                    return Fragment3;
                default:
                    return Fragment1;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
