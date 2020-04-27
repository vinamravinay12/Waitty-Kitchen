package com.waitty.kitchen.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.waitty.kitchen.R;
import com.waitty.kitchen.databinding.DialogLogoutBinding;

public class Dialog {

    private static AlertDialog dialogLogout,dialogPermission;

    // Application logout dialog
    public static void showLogoutDialog(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_logout, null, false);
        builder.setView(dialogLogoutBinding.getRoot());

        dialogLogout = builder.create();
        dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogout.setCanceledOnTouchOutside(false);
        dialogLogout.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLogout.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogLogout.show();

        dialogLogoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
            }
        });

        dialogLogoutBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
                Utility.LogOut(context);
            }
        });
    }

    // Show dialog alert for token expire and block by admin
    public static void showAdminBlockTokenExpireDialog(final Context context, String head, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_logout, null, false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        builder.setView(dialogLogoutBinding.getRoot());

        dialogLogoutBinding.txtHeading.setText(head);
        dialogLogoutBinding.txtMSG.setText(msg);
        dialogLogoutBinding.btnCancel.setVisibility(View.GONE);
        dialogLogoutBinding.btnLogout.setText(R.string.ok);

        dialogLogout = builder.create();
        dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogout.setCanceledOnTouchOutside(false);
        dialogLogout.setCancelable(false);
        dialogLogout.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLogout.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogLogout.show();

        dialogLogoutBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
                Utility.LogOut(context);
            }
        });
    }

    // Show dialog for order already start preparing
    public static void orderAlreadyStartDialog(final Context context, String head, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_logout, null, false);
        builder.setView(dialogLogoutBinding.getRoot());

        dialogLogoutBinding.txtHeading.setText(head);
        dialogLogoutBinding.txtMSG.setText(msg);
        dialogLogoutBinding.btnCancel.setVisibility(View.GONE);
        dialogLogoutBinding.btnLogout.setText(R.string.ok);

        dialogLogout = builder.create();
        dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogout.setCanceledOnTouchOutside(false);
        dialogLogout.setCancelable(false);
        dialogLogout.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLogout.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogLogout.show();

        dialogLogoutBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
            }
        });
    }

    // Application update dialog
    public static void showApplicationUpdateDialog(final Context context,String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_logout, null, false);
        builder.setView(dialogLogoutBinding.getRoot());

        dialogLogoutBinding.txtHeading.setText(R.string.app_name);
        dialogLogoutBinding.txtMSG.setText(msg);
        dialogLogoutBinding.btnCancel.setText(R.string.later);
        dialogLogoutBinding.btnLogout.setText(R.string.update);

        dialogLogout = builder.create();
        dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogout.setCanceledOnTouchOutside(false);
        dialogLogout.setCancelable(false);
        dialogLogout.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLogout.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogLogout.show();

        dialogLogoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
                final AppCompatActivity activity = (AppCompatActivity) context;
                activity.finish();
                System.exit(0);
            }
        });

        dialogLogoutBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://play.google.com/store/apps/details?id=" + context.getPackageName();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
                dialogLogout.dismiss();
                final AppCompatActivity activity = (AppCompatActivity) context;
                activity.finish();
                System.exit(0);
            }
        });
    }

    // Permission mandatory dialog
    public static void checkIfPermissionsGranted(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(context.getString(R.string.permission));
        alertDialogBuilder.setPositiveButton(context.getString(R.string.txt_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myAppSettings);
            }
        });

        alertDialogBuilder.setNegativeButton(context.getString(R.string.txt_dismiss), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogPermission = alertDialogBuilder.create();
        dialogPermission.show();
        dialogPermission.getButton(dialogPermission.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorYallow));
        dialogPermission.getButton(dialogPermission.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorYallow));
    }
}
