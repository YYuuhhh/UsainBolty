package com.example.usainbolty;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MFA extends FragmentStateAdapter{

    private Fragment bf1 = new Fragment();


    public MFA(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int pos5) {
            return new TxtFragInst(pos5);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
