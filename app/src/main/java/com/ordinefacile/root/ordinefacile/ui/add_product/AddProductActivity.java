package com.ordinefacile.root.ordinefacile.ui.add_product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ordinefacile.root.ordinefacile.R;
import com.ordinefacile.root.ordinefacile.ui.my_order.MyOrderActivity;
import com.ordinefacile.root.ordinefacile.utils.ParseImage;

public class AddProductActivity extends AppCompatActivity  implements  AddProductView {

    private String quantity;
    private String name;
    private String price;
    private String metric;
    private String description;
    private String urlImage;

    private TextView txt_quantity;
    private TextView txt_name;
    private TextView txt_price;
    private TextView txt_metric;
    private TextView txt_description;

    private Button btn_add;

    private ImageView img_urlImage;

    private ParseImage parseImage;

    AddProductPresenter addProductPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parseImage = new ParseImage(getApplicationContext());
        addProductPresenter = new AddProductPresenter(this,getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.menu);

        Intent intent = getIntent();
        quantity = intent.getStringExtra("categoryDetailQuantity");
        name = intent.getStringExtra("categoryDetailName");
        price = intent.getStringExtra("categoryDetailPrice");
        metric = intent.getStringExtra("categoryDetailMetric");
        description = intent.getStringExtra("categoryDetailDescription");
        urlImage = intent.getStringExtra("categoryDetailUrlImg");

        txt_quantity = (TextView)findViewById(R.id.textView_quantity);
        txt_name = (TextView)findViewById(R.id.textView_tittle);
        txt_price = (TextView)findViewById(R.id.textView_price);
        txt_metric = (TextView)findViewById(R.id.textView_metric);
        txt_description = (TextView)findViewById(R.id.textView_description);
        img_urlImage = (ImageView)findViewById(R.id.imageView_addproduct);
        btn_add = (Button)findViewById(R.id.button_addTo_Cart);

        txt_quantity.setText(quantity);
        txt_name.setText(name);
        txt_price.setText(price);
        txt_metric.setText(metric);
        txt_description.setText(description);
        parseImage.parseimage(urlImage,img_urlImage);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addProductPresenter.update(quantity, name,price,metric,description,urlImage);

                Intent intent = new Intent(getApplicationContext(), MyOrderActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_call_service) {

            addProductPresenter.checknumber();
        }
        if (id == R.id.action_my_order) {
               Intent intent = new Intent(getApplicationContext(),MyOrderActivity.class);
               startActivity(intent);
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void callNumber(String numberCall) {

        Intent call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numberCall, null));
        startActivity(call);

    }

    @Override
    public void callNumberIncorrect() {

        Toast.makeText(getApplicationContext(),R.string.numberIncorrect,Toast.LENGTH_LONG).show();
    }
}
