 package id.ac.pens.student.it.ahmadmundhofa.fusedlocationmaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import id.ac.pens.student.it.ahmadmundhofa.fusedlocationmaps.Utils.PermissonRuntime;

 public class MainActivity extends AppCompatActivity {
    private FusedLocationGPS fusedLocationGPS;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private Location myLocation;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = (TextView) findViewById(R.id.test);
        fusedLocationGPS = new FusedLocationGPS(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (fusedLocationGPS.getmRequestingLocationUpdates() && checkPermissions()) {
            fusedLocationGPS.startLocationUpdates();
            saveCurentLocation();
        } else if (!checkPermissions()) {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

     private void requestPermissions() {
         boolean shouldProvideRationale =
                 ActivityCompat.shouldShowRequestPermissionRationale(this,
                         Manifest.permission.ACCESS_FINE_LOCATION);
         if (shouldProvideRationale) {
             showSnackbar(R.string.permission_rationale,
                     android.R.string.ok, new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             // Request permission
                             ActivityCompat.requestPermissions(MainActivity.this,
                                     new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                     REQUEST_PERMISSIONS_REQUEST_CODE);
                         }
                     });
         } else {
             ActivityCompat.requestPermissions(MainActivity.this,
                     new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                     REQUEST_PERMISSIONS_REQUEST_CODE);
         }
     }

     private void saveCurentLocation() {
         //TODO : mendapatkan data lokasi dari kelas fusedGPS dan disimpan ke shared prefference
         if (!isLocationEnabled()) {
             showAlertGps();
             return;
         }
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                 && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
             //DATA MASIH NULL
             mFusedLocationClient = fusedLocationGPS.getmFusedLocationClient();
             mFusedLocationClient.getLastLocation()
                     .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                         @Override
                         public void onComplete(@NonNull Task<Location> task) {
                             if (task.isSuccessful() && task.getResult() != null) {
                                 myLocation = task.getResult();
                                 if (myLocation != null) {
                                     if (!isMockLocationCurrently(myLocation)) {
                                         //TODO : SAVE OR show your Location HERE
                                         test.setText(String.valueOf("Latitude : "+myLocation.getLatitude()+"\n Longitude : "+String.valueOf(myLocation.getLongitude())));
                                     }
                                 }
                             }
                         }
                     });

         } else {
             ConstraintLayout main_layout = findViewById(R.id.main_layout);
             PermissonRuntime permissonRuntime = new PermissonRuntime(this, main_layout);
             permissonRuntime.requestLocationPermission();
         }
     }

     private boolean isLocationEnabled() {
         locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         Boolean isEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
         return isEnable;
     }

     private void showAlertGps() {
         final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
         dialog.setTitle("Enable Location")
                 .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to use this app")
                 .setPositiveButton("Location Settings", (dialogInterface, i) -> {
                     Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                     startActivity(myIntent);
                 });
         dialog.setCancelable(false);
         dialog.show();
     }

     private void showSnackbar(final int mainTextStringId, final int actionStringId,
                               View.OnClickListener listener) {
         Snackbar.make(
                 findViewById(android.R.id.content),
                 getString(mainTextStringId),
                 Snackbar.LENGTH_INDEFINITE)
                 .setAction(getString(actionStringId), listener).show();
     }

     public boolean isMockLocationCurrently(Location location) {
         boolean isMock = false;
         if (android.os.Build.VERSION.SDK_INT >= 18) {
             isMock = location.isFromMockProvider();
         } else {
             isMock = !Settings.Secure.getString(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
         }
         return isMock;
     }
}
