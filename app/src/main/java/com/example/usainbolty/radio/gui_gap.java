package com.example.usainbolty.radio;

// GUI API < Activity

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

    // GUI API definition:
public interface gui_gap {

	public abstract boolean   gap_state_set       (String state);
	public abstract void      gap_service_update  (Intent intent);
	public abstract void onClicked(View v);
	public abstract Dialog createDialog(int id, Bundle args);
	public abstract boolean onMenuCreate(Menu menu);
	public abstract boolean onMenuSelect(int itemid);

}
