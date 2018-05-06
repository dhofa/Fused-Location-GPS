package id.ac.pens.student.it.ahmadmundhofa.fusedlocationmaps.Utils;

import android.Manifest;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

public class PermissonRuntime {
    public static final int RP_CAMERA = 711;
    public static final int RP_CALENDAR = 713;
    public static final int RP_RW_STORAGE = 715;
    public static final int RP_LOCATION = 717;
    Activity activity;
    private View mLayout;

    public PermissonRuntime(Activity activity, View mLayout) {
        this.activity = activity;
        this.mLayout = mLayout;
    }

    /**
     * request camera permission
     */
    public void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Snackbar.make(mLayout, "Camera access is required to display the camera preview.", Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, RP_CAMERA);
            }).show();
        } else {
            Snackbar.make(mLayout, "Permission is not available. Requesting camera permission.", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, RP_CAMERA);
        }
    }

    public void requestPermissionCalendar() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Snackbar.make(mLayout, "Calendar access is required ", Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, RP_CALENDAR);
            }).show();
        } else {
            Snackbar.make(mLayout, "Permission is not available. Requesting calendar permission.", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR}, RP_CAMERA);
        }
    }

    /**
     * request storage permission (read and write)
     */
    public void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(mLayout, "Storage permission is required.", Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RP_RW_STORAGE);
            }).show();
        } else {
            Snackbar.make(mLayout, "Permission is not available. Requesting storage permission.", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RP_RW_STORAGE);
        }
    }

    /**
     * request gps permission
     */
    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Snackbar.make(mLayout, "GPS permission is required.", Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RP_LOCATION);
            }).show();
        } else {
            Snackbar.make(mLayout, "Permission is not available. Requesting GPS permission.", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RP_LOCATION);
        }
    }
}
