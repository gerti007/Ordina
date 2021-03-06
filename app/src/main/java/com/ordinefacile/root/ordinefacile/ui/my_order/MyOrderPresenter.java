package com.ordinefacile.root.ordinefacile.ui.my_order;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import com.ordinefacile.root.ordinefacile.R;
import com.ordinefacile.root.ordinefacile.data.db.DatabaseHelper;
import com.ordinefacile.root.ordinefacile.data.db.order.OrdersOperationsImp;
import com.ordinefacile.root.ordinefacile.data.db.order.Orders;
import com.ordinefacile.root.ordinefacile.data.network.ApiHelper;
import com.ordinefacile.root.ordinefacile.data.network.AppApiHelper;
import com.ordinefacile.root.ordinefacile.data.network.model.SendOrderModel;
import com.ordinefacile.root.ordinefacile.data.prefs.SaveData;
import com.ordinefacile.root.ordinefacile.ui.code_scan.CodeOrScanActivity;
import com.ordinefacile.root.ordinefacile.ui.scan.ScannerActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eljo on 2/1/2018.
 */

public class MyOrderPresenter {

  Context context;
  MyOrderActivity myOrderActivity;
  ApiHelper apiHelper;
  OrdersOperationsImp dbOperations;
  Orders orders;
  DatabaseHelper databaseHelper;
  RuntimeExceptionDao<Orders, Integer> userDao;
  List<Orders> feedItemList;
  String json_obj22;
  EventBus bus = EventBus.getDefault();
  SaveData saveData;
  JSONObject jsonObject;

  public MyOrderPresenter(Context context, MyOrderActivity myOrderActivity) {
    this.context = context;
    this.myOrderActivity = myOrderActivity;
    apiHelper = new AppApiHelper();
    dbOperations = new OrdersOperationsImp(context);
    orders = new Orders();
    databaseHelper = new DatabaseHelper(context);
    userDao = databaseHelper.getRuntimeExceptionDao(Orders.class);
    saveData = new SaveData(context);
  }

  public void getListProducts() {
    dbOperations.read().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Orders>>() {
              @Override
              public void onCompleted() {
                Log.d("", "");

              }

              @Override
              public void onError(Throwable e) {
                Log.d("", "");
              }

              @Override
              public void onNext(List<Orders> orders) {
                Log.d("", "");

                try {
                  JSONObject jsonObj = new JSONObject();
                  JSONArray jsonArr = new JSONArray();
                  boolean boolean_delivery_status = Boolean.parseBoolean(saveData.getDeliveryStatus());
                  System.out.print(boolean_delivery_status);
                  System.out.print(boolean_delivery_status);
                  jsonObj.put("delivery", boolean_delivery_status);
                  jsonObj.put("entity_id", saveData.getEntity());
                  for (int i = 0; i < orders.size(); i++) {
                    JSONObject pnObj = new JSONObject();
                    pnObj.put("mDescriptions", orders.get(i).getmDescriptions());
                    pnObj.put("mFinalPrice", orders.get(i).getmFinalPrice());
                    pnObj.put("mId", orders.get(i).getmId());
                    pnObj.put("mIdProduct", orders.get(i).getmIdProduct());
                    pnObj.put("mIdTable", orders.get(i).getmIdProduct());
                    pnObj.put("mMetric", orders.get(i).getmMetric());
                    pnObj.put("mName", orders.get(i).getmName());
                    pnObj.put("mPrice", orders.get(i).getmPrice());
                    pnObj.put("mQuantity", orders.get(i).getmQuantity());
                    pnObj.put("mUrl_Image", orders.get(i).getmUrl_Image());
                    jsonArr.put(pnObj);
                    jsonObj.put("order_items", jsonArr);
                  }
                  String json_array = jsonArr.toString();
                  String json_obj = jsonObj.toString();
                  JSONObject jsonAdd = new JSONObject();
                  jsonAdd.put("device_token", saveData.getTokenFcm());
                  jsonAdd.put("brand", Build.MANUFACTURER);
                  jsonAdd.put("model", Build.MODEL);
                  jsonObj.put("device", jsonAdd);
                  String json_array2 = jsonArr.toString();
                  String json_obj2 = jsonObj.toString();
                  json_obj22 = jsonObj.toString();
                  jsonObject = jsonObj;

                } catch (JSONException e) {
                  e.printStackTrace();
                }

                feedItemList = new ArrayList<Orders>();
                for (int i = 0; i < orders.size(); i++) {

                  String ss = orders.get(i).getmName();
                  feedItemList.add(orders.get(i));

                }
                myOrderActivity.listAdapter(feedItemList);
              }
            });
  }

  public void getListProductsSendJson() {
    dbOperations.read().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Orders>>() {
              @Override
              public void onCompleted() {
                Log.d("", "");

              }

              @Override
              public void onError(Throwable e) {
                Log.d("", "");
              }

              @Override
              public void onNext(List<Orders> orders) {
                Log.d("", "");

                if (orders.size()==0){
                  myOrderActivity.checkProduct();

                }else if (orders.size()>0){


                  try {
                    JSONObject jsonObj = new JSONObject();
                    JSONArray jsonArr = new JSONArray();
                    boolean boolean_delivery_status = Boolean.parseBoolean(saveData.getDeliveryStatus());
                    System.out.print(boolean_delivery_status);
                    System.out.print(boolean_delivery_status);
                    jsonObj.put("delivery", boolean_delivery_status);
                    jsonObj.put("entity_id", saveData.getEntity());
                    for (int i = 0; i < orders.size(); i++) {
                      JSONObject pnObj = new JSONObject();
                      pnObj.put("mDescriptions", orders.get(i).getmDescriptions());
                      pnObj.put("mFinalPrice", orders.get(i).getmFinalPrice());
                      pnObj.put("mId", orders.get(i).getmId());
                      pnObj.put("mIdProduct", orders.get(i).getmIdProduct());
                      pnObj.put("mIdTable", orders.get(i).getmIdProduct());
                      pnObj.put("mMetric", orders.get(i).getmMetric());
                      pnObj.put("mName", orders.get(i).getmName());
                      pnObj.put("mPrice", orders.get(i).getmPrice());
                      pnObj.put("mQuantity", orders.get(i).getmQuantity());
                      pnObj.put("mUrl_Image", orders.get(i).getmUrl_Image());
                      jsonArr.put(pnObj);
                      jsonObj.put("order_items", jsonArr);
                    }

                    String json_array = jsonArr.toString();
                    String json_obj = jsonObj.toString();
                    JSONObject jsonAdd = new JSONObject();
                    jsonAdd.put("device_token", saveData.getTokenFcm());
                    jsonAdd.put("brand", Build.MANUFACTURER);
                    jsonAdd.put("model", Build.MODEL);
                    jsonObj.put("device", jsonAdd);
                    String json_array2 = jsonArr.toString();
                    String json_obj2 = jsonObj.toString();
                    json_obj22 = jsonObj.toString();
                    sendJson(json_obj22);
                    jsonObject = jsonObj;

                  } catch (JSONException e) {
                    e.printStackTrace();
                  }

                  feedItemList = new ArrayList<Orders>();
                  for (int i = 0; i < orders.size(); i++) {

                    String ss = orders.get(i).getmName();
                    feedItemList.add(orders.get(i));

                  }

                  myOrderActivity.listAdapter(feedItemList);
                }

              }
            });
  }

  public void delete(int id) {

    dbOperations.delete2(id).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<DeleteBuilder<Orders, Integer>>() {
              @Override
              public void onCompleted() {
                Log.d("", "");
              }

              @Override
              public void onError(Throwable e) {
                Log.d("", "");

              }

              @Override
              public void onNext(DeleteBuilder<Orders, Integer> deleteBuilder) {
                  checkHideShowFloating();
                  getListProducts();

                Log.d("", "");

              }

            });

  }

  public void sendJson(String jsonObject2) {

    apiHelper.sendJson(jsonObject2)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<SendOrderModel>() {
              @Override
              public void onCompleted() {
                Log.d("","");

                myOrderActivity.showCompleteOrder();
              }

              @Override
              public void onError(Throwable e) {
                Log.d("Problem : ", e.getMessage());if (e.getMessage().equalsIgnoreCase("Failed to connect to /165.227.201.28:80")){

                  myOrderActivity.sentErrorInternet();
                }else {

                  myOrderActivity.sentError();
                }

              }

              @Override
              public void onNext(SendOrderModel myorder) {

                myOrderActivity.showSendOrder();

                if (myorder.getError().toString().equalsIgnoreCase("false")) {

                //  myOrderActivity.deleteDatabase("ordinafacile.db");

                    deleteall(3);

              //  dropDb();

                //    dbOperations.dropDb();

                 //   userDao.dropTable(ConnectionSource, DatabaseTableConfig, boolean ignoreErrors)



                  feedItemList.clear();
                  myOrderActivity.listAdapter(feedItemList);

                  myOrderActivity.goToMyOrderHistory();

                }else if (myorder.getError().toString().equalsIgnoreCase("true")){

                  myOrderActivity.tokenExpired();
                  myOrderActivity.sentError();

                }
              }

            });
  }
  public void dismissDialog(MaterialDialog dialog) {
    if(dialog != null){
      myOrderActivity.dismissDialog();
    }
  }

    public void checkHideShowFloating() {
        dbOperations.read().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Orders>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("", "");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("", "");
                    }

                    @Override
                    public void onNext(List<Orders> orders) {
                        Log.d("", "");

                        int size4 =  orders.size();
                        int size2 =  orders.size();

                        if (size4>0){
                            myOrderActivity.showFloating();
                            String SJS = "SSS";

                        }else if (size4<1){

                            myOrderActivity.hideFloating();
                            String SJHS = "SSS";

                        }
                    }
                });
    }


    public void deleteall(int id) {

        dbOperations.deleteall(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeleteBuilder<Orders, Integer>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("", "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("", "");
                    }

                    @Override
                    public void onNext(DeleteBuilder<Orders, Integer> deleteBuilder) {

                        /*
                        if (saveData.getNumberCharacter().equalsIgnoreCase("5")){


                           saveData.clearPin();
                           Intent i = new Intent(context, CodeOrScanActivity.class);
                           i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            Toast.makeText(context, R.string.call_send,Toast.LENGTH_LONG).show();


                            context.startActivity(i);


                        }

*/
                        checkHideShowFloating();

                        Log.d("", "");

                    }

                });

    }

}