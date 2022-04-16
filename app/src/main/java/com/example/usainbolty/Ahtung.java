package com.example.usainbolty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class Ahtung extends DialogFragment {
@NonNull
public Dialog onCreateDialog(Bundle savedInstanceState) {
AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
return builder.setTitle("Предупреждение").setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("OK", null).setMessage("Все данные являются приблизительными").create();
}
}

