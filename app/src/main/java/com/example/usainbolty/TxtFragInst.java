package com.example.usainbolty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class TxtFragInst extends Fragment {
    private int i;
    public TxtFragInst(){ }
    public TxtFragInst(int i){
        this.i = i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch(i) {
            case 1:
                return inflater.inflate(R.layout.txt_frag_2, container, false);
            case 2:
                return inflater.inflate(R.layout.txt_frag_3, container, false);
            default:
                return inflater.inflate(R.layout.txt_frag_1, container, false);
        }
    }
}