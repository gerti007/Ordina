package com.ordinefacile.root.ordinefacile.ui.my_order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ordinefacile.root.ordinefacile.data.db.DatabaseHelper;
import com.ordinefacile.root.ordinefacile.R;
import com.ordinefacile.root.ordinefacile.data.db.order.Orders;
import com.ordinefacile.root.ordinefacile.ui.order_history.OrderHistoryActivity;
import com.ordinefacile.root.ordinefacile.utils.BaseActivity;
import com.ordinefacile.root.ordinefacile.utils.ParseImage;

import net.idik.lib.slimadapter.SlimAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.ordinefacile.root.ordinefacile.R2.color.myorder_yellow;

public class MyOrderActivity extends BaseActivity implements MyOrderView {

  private static final String TAG = "My Debug";
  private RecyclerView mRecyclerView;
  private MyOrderAdapter adapter;

  MyOrderPresenter myOrderPresenter;
  DatabaseHelper dbHelper;
  ParseImage parseImage;
  SlimAdapter slimAdapter;
  MaterialDialog progressDialog;
  MaterialDialog alertDialog;

  ArrayList<Orders> muylis = new ArrayList<Orders>();

  Button fab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_order);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setTitle(R.string.menu_myorder);

    EventBus bus = EventBus.getDefault();
    mRecyclerView = (RecyclerView) findViewById(R.id.recycle_myorder);
    dbHelper = new DatabaseHelper(getApplicationContext());

    myOrderPresenter = new MyOrderPresenter(getApplicationContext(), this);
    myOrderPresenter.getListProducts();

    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mRecyclerView.setPadding(25, 25, 25, 25);

    slimAdapter = SlimAdapter.create();
    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
    itemAnimator.setAddDuration(1000);
    itemAnimator.setRemoveDuration(1000);
    mRecyclerView.setItemAnimator(itemAnimator);

    myOrderPresenter.checkHideShowFloating();

    fab = (Button) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        myOrderPresenter.getListProductsSendJson();

       // myOrderPresenter.dropDb();
      }
    });

  }

  @Override
  public void listAdapter(List<Orders> feedItemList) {

    muylis = (ArrayList<Orders>) feedItemList;
    adapter = new MyOrderAdapter(MyOrderActivity.this, feedItemList, this);
    mRecyclerView.setAdapter(adapter);
    System.out.println(feedItemList.size());
    Log.d(TAG, feedItemList.toString());
    adapter.notifyDataSetChanged();

  }

  @Override
  public void idProduct(int int_product) {
    myOrderPresenter.delete(int_product);

  }

  @Override
  public void tokenExpired() {
    progressDialog.dismiss();

  }

  @SuppressLint("ResourceAsColor")
  @Override
  public void sentErrorInternet() {

    progressDialog.dismiss();
    alertDialog = new MaterialDialog.Builder(MyOrderActivity.this)
            .title(R.string.error)
            .content(R.string.not_successfully_internet)
            .cancelable(false)
            .positiveColor(myorder_yellow)
            .positiveText(R.string.try_again)
            .show();

  }

  @SuppressLint("ResourceAsColor")
  @Override
  public void sentError() {
    progressDialog.dismiss();
    alertDialog = new MaterialDialog.Builder(MyOrderActivity.this)
            .title(R.string.error)
            .content(R.string.not_successfully)
            .cancelable(false)
            .positiveColor(myorder_yellow)
            .positiveText(R.string.try_again)
            .show();

  }

  @Override
  public void goToMyOrderHistory() {
    Intent intent = new Intent(this, OrderHistoryActivity.class);
    startActivity(intent);
    finish();

  }

  @Override
  public void dismissDialog() {
    alertDialog.dismiss();

  }

  @Override
  public void checkProduct() {

    Toast.makeText(getApplicationContext(),R.string.no_product_in_cart,Toast.LENGTH_LONG).show();
  }

  @Override
  public void showSendOrder() {

    progressDialog = new MaterialDialog.Builder(MyOrderActivity.this)
            .title(R.string.loading)
            .content(R.string.sending_order)
            .progress(true, 0)
            .cancelable(false)
            .widgetColorRes(myorder_yellow)
            .progressIndeterminateStyle(false)
            .show();

  }

  @Override
  public void showCompleteOrder() {
    progressDialog.dismiss();

  }

  @Override
  public void showFloating() {
   // fab.show();


    fab.setVisibility(View.VISIBLE);

  }

  @Override
  public void hideFloating() {
    //fab.hide();
    fab.setVisibility(View.GONE);

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

  @Override
  protected void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);

  }

  @Override
  protected void onResume() {
    super.onResume();
    myOrderPresenter.dismissDialog(alertDialog);

    EventBus.getDefault().register(this);
  }

  @Subscribe
  public void onEvent(Eventlist event) {

    progressDialog.dismiss();
    myOrderPresenter.getListProductsSendJson();

  }

  public void onDestroy() {
    super.onDestroy();
    //   progressDialog.dismiss();
  }
}