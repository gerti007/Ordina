package com.ordinefacile.root.ordinefacile.ui.menu_detail;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.TableUtils;
import com.ordinefacile.root.ordinefacile.data.db.DatabaseHelper;
import com.ordinefacile.root.ordinefacile.data.db.order.OrdersOperationsImp;
import com.ordinefacile.root.ordinefacile.data.db.order.Orders;
import com.ordinefacile.root.ordinefacile.data.network.ApiHelper;
import com.ordinefacile.root.ordinefacile.data.network.AppApiHelper;
import com.ordinefacile.root.ordinefacile.data.network.model.CallService;
import com.ordinefacile.root.ordinefacile.data.network.model.MenuDishes;
import com.ordinefacile.root.ordinefacile.data.network.model.MenuDishesDatum;
import com.ordinefacile.root.ordinefacile.data.prefs.SaveData;


import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 1/22/2018.
 */

public class MenuDetailPresenter {

    MenuDetailActivity menuDetailActivity;
    ApiHelper apiHelper;
    Gson gson = new Gson();
    Context context;
    MenuDetailAdapter menuDetailAdapter;
    List<MenuDishesDatum> feedItemList;
    OrdersOperationsImp dbOperations;
    SaveData saveData;
    Orders orders;
    DatabaseHelper databaseHelper;
    RuntimeExceptionDao<Orders, Integer> userDao;

    public MenuDetailPresenter( Context context,MenuDetailActivity menuDetailActivity) {
        this.menuDetailActivity = menuDetailActivity;
        this.context = context ;
        apiHelper = new AppApiHelper();
        menuDetailAdapter =  new  MenuDetailAdapter(context,feedItemList,this);
        saveData = new SaveData(context);
        orders = new Orders();
        dbOperations = new OrdersOperationsImp(context);
        databaseHelper = new DatabaseHelper(context);
        userDao = databaseHelper.getRuntimeExceptionDao(Orders.class);

    }

    public void getCategoryId() {
        menuDetailActivity.getMenuDetails();
    }

    public void getMenuDishes(String categoryId) {
        apiHelper.getMenuDishes(Integer.parseInt(categoryId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MenuDishes>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Problem : ", e.getMessage());
                        menuDetailActivity.dissapearSwipeToRefresh();

                    }

                    @Override
                    public void onNext(MenuDishes menuDishes) {
                        feedItemList = new ArrayList<>();
                        Log.d("size : ", "" + menuDishes.getData().size());
                        if (menuDishes.getError() == false) {
                            for (int i = 0; i < menuDishes.getData().size(); i++) {
                                feedItemList.add(menuDishes.getData().get(i));
                            }
                            menuDetailActivity.getListDished(feedItemList);
                        }
                    }

                });
    }


    public void inserData(Float final_price, Float quantity, String name, Float price,String description,
                          String urlImage,String id_table,String id_product,String id_product_card){



        orders.setmFinalPrice(final_price);
        orders.setmQuantity(quantity);
        orders.setmName(name);
        orders.setmPrice(price);
      //  orders.setmMetric(metric);
        orders.setmDescriptions(description);
        orders.setmUrl_Image(urlImage);
        orders.setmIdTable(id_table);
        orders.setmIdProduct(id_product);
        orders.setmIdProductCart(id_product_card);

        dbOperations.create(orders).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer> () {
                    @Override
                    public void onCompleted() {
                        Log.d("", "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("", "");
                    }

                    @Override
                    public void onNext(Integer orders) {
                        checkHideShowFloating();

                        String SJS =  orders.toString();

                      //  addProductActivity.goToMyOrder();

                    }

                });

    }

    public boolean update(Float final_price, Float quantity, String name, Float price, String description,
                          String urlimage,String id_table,String id_product,String id_product_card) {
        //  if(checkIfExdist(name) == true){
        if(checkIfExdist(name) == true){
            UpdateBuilder<Orders, Integer> updateBuilder = userDao.updateBuilder();
            try {
                updateBuilder.where().eq("name",name);
                updateBuilder.updateColumnValue("quantity" ,quantity);
                updateBuilder.updateColumnValue("final_price" ,final_price);
                updateBuilder.update();


           //     addProductActivity.goToMyOrder();

                return true;
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                return false;

            }
        }else if (checkIfExdist(name) == false){
            inserData(final_price ,quantity,name,price,description,urlimage,id_table,id_product,id_product_card);

        }
        return false;
    }

    public boolean checkIfExdist(String p)
    {
        List<Orders> results = null;
        List<Orders> results1 = null;
        try {
            results = userDao.queryBuilder().where().eq("name",p).query();
          //  results1 = userDao.queryBuilder().where().eq("id_product_cart",p).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(results.size() == 0){
            return false;
        }else{
            return true;
        }
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

                        Log.d("", "");

                    }

                });

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
                            menuDetailActivity.showFloating();
                            String SJS = "SSS";

                        }else if (size4<1){

                            menuDetailActivity.hideFloating();
                            String SJHS = "SSS";

                        }
                    }
                });
    }


    public void callService() {

        try {
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("device_token", saveData.getTokenFcm());
            jsonObj.put("brand", Build.MANUFACTURER);
            jsonObj.put("model", Build.MODEL);

            String json_obj = jsonObj.toString();

            apiHelper.callService(saveData.getQrCode(),json_obj)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CallService>() {
                        @Override
                        public void onCompleted() {
                            Log.d("","");

                            menuDetailActivity.showSendingSms();

                        }

                        @Override
                        public void onError(Throwable e) {
                            menuDetailActivity.showErrorSending(e.toString());

                        }

                        @Override
                        public void onNext(CallService callService) {

                            String SJSJ = callService.getMessage().toString();
                            String SJuSJ = callService.getMessage().toString();

                        }

                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}