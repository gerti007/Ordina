package com.ordinefacile.root.ordinefacile.ui.code_scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.amitshekhar.DebugDB;
import com.ordinefacile.root.ordinefacile.R;
import com.ordinefacile.root.ordinefacile.ui.help.HelpActivity;
import com.ordinefacile.root.ordinefacile.ui.main_menu.MainMenuActivity;
import com.ordinefacile.root.ordinefacile.ui.scan.ScannerActivity;
import com.ordinefacile.root.ordinefacile.ui.select_language.SelectLanguageActivity;
import com.ordinefacile.root.ordinefacile.utils.BaseActivity;
import com.ordinefacile.root.ordinefacile.utils.LocaleManager;


public class CodeOrScanActivity extends BaseActivity implements  CodeOrScanView{

    private Button btn_scan;
    private Button btn_pin;
    private  AlertDialog alertDialog;

    CodeOrScanPresenter codeOrScanPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_or_scan);

        codeOrScanPresenter = new CodeOrScanPresenter(getApplicationContext(),this);
        codeOrScanPresenter.checkForLanguage();
        codeOrScanPresenter.checkSharePreference();

        Intent intent = getIntent();
        String service = intent.getStringExtra("service");
        codeOrScanPresenter.checkService(service);



        btn_scan = (Button)findViewById(R.id.button_scan_qr);
        btn_pin = (Button)findViewById(R.id.button_isert_pin);


        String x = DebugDB.getAddressLog();

      //  Open http://192.168.100.58:8080 in your browser
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                startActivity(intent);

            }
        });

        btn_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMaterialDialog();


            }
        });


    }

    private void showMaterialDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_send_pin, null);
        dialogBuilder.setView(dialogView);

        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.FILL_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        final Button btn_insert = (Button) dialogView.findViewById(R.id.button_insert);
        final Button btn_cancel = (Button) dialogView.findViewById(R.id.button_cancel);
        final EditText edt_pin = (EditText) dialogView.findViewById(R.id.editText_inser_pin);



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pin_voucher = edt_pin.getText().toString();
             //   codeOrScanPresenter.getStoreDetailByPin(pin_voucher);
             //   codeOrScanPresenter.getStoreDetailsByVoucherCode(pin_voucher);

                codeOrScanPresenter.checkCharacter(pin_voucher);

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void pinInvalid() {
        Toast.makeText(getApplicationContext(),R.string.pin_not_valid,Toast.LENGTH_LONG).show();

    }

    @Override
    public void goToMenuAtivity() {
        alertDialog.dismiss();
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

    @Override
    public void getAppLanguageIt() {
      //  LocaleManager.setLocale(getApplicationContext(), "it");
    }
    @Override
    public void getAppLanguageEn() {
      //  LocaleManager.setLocale(getApplicationContext(), "en");
    }

    @Override
    public void goToMenuAtivitys() {

        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

    @Override
    public void showAlertLogout() {

        showMaterialDialogService();

    }


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

    private void showMaterialDialogService() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_token_expired, null);
        dialogBuilder.setView(dialogView);

        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.FILL_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);


       final Button btn_cancel = (Button) dialogView.findViewById(R.id.button_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
}
