package com.ordinefacile.root.ordinefacile.ui.scan;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.zxing.Result;
import com.ordinefacile.root.ordinefacile.R;
import com.ordinefacile.root.ordinefacile.ui.main_menu.MainMenuActivity;
import com.ordinefacile.root.ordinefacile.utils.BaseActivity;
import com.ordinefacile.root.ordinefacile.utils.LocaleManager;
import com.ordinefacile.root.ordinefacile.utils.Util;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler,ScannerView {

    private String TAG = "ScannerActivity ";

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;

    ScannerPresenter scannerPresenter;
    Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        scannerPresenter = new ScannerPresenter(this);
        scannerPresenter.checkForLanguage();
        t = Toast.makeText(getApplicationContext(), R.string.permission_denied, Toast.LENGTH_LONG);
        scannerPresenter.checkForPermission();

      //  Intent i = new Intent(this, MainMenuActivity.class);
      //  i.putExtra("storeId","1");
      //  startActivity(i);

    }

    @Override
    public boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), R.string.permission_granted, Toast.LENGTH_SHORT).show();
                    }else {
                        t.show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                showMessageOKCancel(getString(R.string.need_to_allow_access),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

    }
    @Override
    public void goToMenuActivity() {
        Intent i = new Intent(this, MainMenuActivity.class);
       // i.putExtra("storeId",s);
        startActivity(i);
        //finish();
    }

    @Override
    public void getAppLanguageIt() {
      // LocaleManager.setLocale(getApplicationContext(), "it");
    }
    @Override
    public void getAppLanguageEn() {
     //   LocaleManager.setLocale(getApplicationContext(), "en");
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (scannerPresenter.checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera(camId);
            } else {
                scannerPresenter.requestPermission();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        t.cancel();
        mScannerView.stopCamera();
        mScannerView = null;
    }

    @Override
    public void handleResult(Result rawResult) {

        final String result = rawResult.getText();
        Log.e(TAG, rawResult.getText());
        Log.e(TAG, rawResult.getBarcodeFormat().toString());

        scannerPresenter.getStoreDetail(Util.substring(result,"http://"));

    }

    @Override
    public void checkIfPermissionNeeded() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), R.string.permission_already, Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    public void qrCodeNotValid() {
        Toast.makeText(getApplicationContext(),R.string.qrcode_not_valid,Toast.LENGTH_LONG).show();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {

            Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_language) {
            Intent intent = new Intent(getApplicationContext(),SelectLanguageActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    protected void onStop () {
        super.onStop();
        t.cancel();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}