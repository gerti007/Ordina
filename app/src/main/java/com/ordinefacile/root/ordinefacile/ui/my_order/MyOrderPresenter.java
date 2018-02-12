package com.ordinefacile.root.ordinefacile.ui.my_order;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ordinefacile.root.ordinefacile.data.db.DatabaseHelper;
import com.ordinefacile.root.ordinefacile.data.db.DatabaseOperationsImp;
import com.ordinefacile.root.ordinefacile.data.db.Orders;
import com.ordinefacile.root.ordinefacile.data.network.ApiHelper;
import com.ordinefacile.root.ordinefacile.data.network.AppApiHelper;
import com.ordinefacile.root.ordinefacile.data.network.model.MenuDishesDatum;

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
    DatabaseOperationsImp dbOperations;
    Orders orders;
    DatabaseHelper databaseHelper;

    List<Orders> feedItemList;

    public MyOrderPresenter(Context context, MyOrderActivity myOrderActivity) {
        this.context = context;
        this.myOrderActivity = myOrderActivity;
        apiHelper = new AppApiHelper();
        dbOperations = new DatabaseOperationsImp(context);
        orders = new Orders();
        databaseHelper = new DatabaseHelper(context);
    }



    public void getListProducts(){
        dbOperations.read().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Orders>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("","");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("","");
                    }

                    @Override
                    public void onNext(List<Orders> orders) {
                        Log.d("","");

                        feedItemList = new ArrayList<Orders>();
                        for (int i=0;i<orders.size();i++){

                            String ss = orders.get(i).getmName();

                            myOrderActivity.listAdapter(feedItemList);

                        }
                    }

                });
    }
}
