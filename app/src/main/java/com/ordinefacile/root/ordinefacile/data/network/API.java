package com.ordinefacile.root.ordinefacile.data.network;
import com.ordinefacile.root.ordinefacile.data.network.model.MenuDishes;
import com.ordinefacile.root.ordinefacile.data.network.model.Store;
import com.ordinefacile.root.ordinefacile.data.network.model.StoreCategories;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by root on 1/20/18.
 */

public interface API {

    @GET("qr_scan/{qrcode}")
    Observable<Store> getStoreDetails(@Path("qrcode") String qrCode);

    @GET("categories/store/{id}")
    Observable<StoreCategories> getStoreCategories(@Path("id") int id);

    @GET("products/category/{id}")
    Observable<MenuDishes> getMenuDishes(@Path("id") int id);

}
