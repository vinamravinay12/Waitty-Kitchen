package com.waitty.kitchen.utility;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import com.waitty.kitchen.R;
import java.util.ArrayList;
import java.util.List;

public class PermissionsUtils {

    private Context context;
    private static PermissionsUtils permissionsUtils = null;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 2;

    // Class constructor
    private PermissionsUtils(Context context) {
        this.context = context;
    }

    // Get instance
    public static PermissionsUtils getInstance(Context context) {
        if (permissionsUtils == null) {
            permissionsUtils = new PermissionsUtils(context);
        }
        return permissionsUtils;
    }

    // For multiple permissions
    public boolean requiredPermissionsGranted(final Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsNeeded = new ArrayList<String>();
            final List<String> permissionsList = new ArrayList<String>();

            if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionsNeeded.add(context.getString(R.string.txt_write_external_storage));
            }

            if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
                permissionsNeeded.add(context.getString(R.string.txt_camera));
            }

            if (permissionsList.size() > 0) {
                if (permissionsNeeded.size() > 0) {
                    // Need Rationale
                    String message = context.getString(R.string.txt_grant_access)+" "+ permissionsNeeded.get(0);
                    for (int i = 1; i < permissionsNeeded.size(); i++) {
                        message = message + ", " + permissionsNeeded.get(i);
                    }
                    showMessageOKCancel(context, message,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        ((Activity) context).requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                    }
                                }
                            });
                    return false;
                }
                ((Activity) context).requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                return false;
            }

        }
        return true;
    }

    // For single permissions
    public boolean isPermissionGranted(final Context context, String permission, String text, int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsNeeded = new ArrayList<String>();
            final List<String> permissionsList = new ArrayList<String>();

            if (!addPermission(permissionsList, permission)) {
                permissionsNeeded.add(text);
            }

            if (permissionsList.size() > 0) {
                if (permissionsNeeded.size() > 0) {
                    // Need Rationale
                    String message =context.getString(R.string.txt_grant_access)+" "+ permissionsNeeded.get(0);
                    for (int i = 1; i < permissionsNeeded.size(); i++) {
                        message = message + ", " + permissionsNeeded.get(i);
                    }
                    if(type==1){
                        ((Activity) context).requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                REQUEST_CODE_ASK_PERMISSIONS);
                    }else{
                        showMessageOKCancel(context, message,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            ((Activity) context).requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                                    REQUEST_CODE_ASK_PERMISSIONS);
                                        }
                                    }
                                });
                    }

                    return false;
                }
                ((Activity) context).requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    // For show dialog
    private void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.ok), okListener)
                .create()
                .show();
    }

    // Add permission
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);

                // Check for Rationale Option
                if (!((Activity) context).shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    // For check permissions
    public boolean areAllPermissionsGranted(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

}
