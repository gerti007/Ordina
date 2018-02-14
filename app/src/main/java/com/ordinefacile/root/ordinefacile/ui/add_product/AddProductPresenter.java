package com.ordinefacile.root.ordinefacile.ui.add_product;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.ordinefacile.root.ordinefacile.data.db.DatabaseHelper;
import com.ordinefacile.root.ordinefacile.data.db.DatabaseOperationsImp;
import com.ordinefacile.root.ordinefacile.data.db.Orders;
import com.ordinefacile.root.ordinefacile.data.network.ApiHelper;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eljo on 2/11/2018.
 */

public class AddProductPresenter {


    AddProductActivity addProductActivity;
    Context context;
    RuntimeExceptionDao<Orders, Integer> userDao;

    ApiHelper apiHelper;
    DatabaseOperationsImp dbOperations;
    Gson gson = new Gson();
    Orders orders;
    DatabaseHelper databaseHelper;

    public AddProductPresenter(AddProductActivity addProductActivity, Context context) {
        this.addProductActivity = addProductActivity;
        this.context = context;
        orders = new Orders();
        dbOperations = new DatabaseOperationsImp(context);
        databaseHelper = new DatabaseHelper(context);
        userDao = databaseHelper.getRuntimeExceptionDao(Orders.class);
    }

    public void inserData(String quantity, String name, String price,
                          String metric, String description, String urlImage) {

        orders.setmQuantity(quantity);
        orders.setmName(name);
        orders.setmPrice(price);
        orders.setmMetric(metric);
        orders.setmDescriptions(description);
        orders.setmUrl_Image(urlImage);
        orders.setmUserOrder("USER");

      String emri = orders.toString();

        dbOperations.create(orders).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Orders> () {
                    @Override
                    public void onCompleted() {
                        Log.d("", "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("", "");
                    }

                    @Override
                    public void onNext(Orders orders) {
                      //  Log.d("", "");
                    //    for (int i = 0; i < orders.size(); i++) {
                     //       String ss = orders.get(i).getmName();
                     //   }
                    }

                });

    }

    public boolean update() {
        if(checkIfExist() == true){
            UpdateBuilder<Orders, Integer> updateBuilder = userDao.updateBuilder();
            try {
                updateBuilder.where().eq("name", orders.getmName());
                updateBuilder.updateColumnValue("quantity" , orders.getmQuantity());
                updateBuilder.update();
                return true;
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                return false;

            }
        }
        return false;
    }
    public boolean checkIfExist() {
        List<Orders> results = null;
        try {
            results = userDao.queryBuilder().where().eq("name",orders.getmName()).query();

            String DDJD=orders.getmName().toString();
            String DDwJD=orders.getmName().toString();

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

    public boolean checkIfExdist() {
        List<Orders> results = null;
        try {
            results = userDao.queryBuilder().where().eq("name","Nicholaus Simonis").query();
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
}
