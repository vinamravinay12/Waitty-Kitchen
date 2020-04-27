package com.waitty.kitchen.utility;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.Window;
import com.waitty.kitchen.R;
import com.waitty.kitchen.databinding.LayoutCustomLoadingBinding;

public class MyLoading {

    private Dialog dialog;
    private LayoutCustomLoadingBinding layoutCustomLoadingBinding;

    // Initialize loader
    public MyLoading(Context context) {
        try {
            dialog = new Dialog(context);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            layoutCustomLoadingBinding = (LayoutCustomLoadingBinding)DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_custom_loading, null, false);
            dialog.setContentView(layoutCustomLoadingBinding.getRoot());
            dialog.getWindow().setLayout(-1, -2);
            dialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show loader
    public void show(String message) {
        layoutCustomLoadingBinding.txtMessage.setText(message);
        dialog.show();
    }

    // Stop loader
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}


