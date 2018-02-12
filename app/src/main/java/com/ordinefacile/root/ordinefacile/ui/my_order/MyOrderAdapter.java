package com.ordinefacile.root.ordinefacile.ui.my_order;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ordinefacile.root.ordinefacile.R;
import com.ordinefacile.root.ordinefacile.data.network.model.MenuDishesDatum;

import com.ordinefacile.root.ordinefacile.ui.menu_detail.MenuDetailPresenter;
import com.ordinefacile.root.ordinefacile.utils.ParseImage;

import java.util.List;

/**
 * Created by Eljo on 2/1/2018.
 */

public class MyOrderAdapter   extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>  {

    private List<MenuDishesDatum> feedItemList;
    private Context context;
    private ParseImage parseimage;
    int score = 0 ;
    MenuDetailPresenter menuDetailPresenter;


    String urlImg;

    public MyOrderAdapter(Context context, List<MenuDishesDatum> feedItemList,  MenuDetailPresenter menuDetailPresenter) {

        this.feedItemList = feedItemList;
        this.context = context;
        parseimage = new ParseImage(context);
        this.menuDetailPresenter = menuDetailPresenter;

    }

    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.menu_my_order_adapter, parent, false);
        return new MyOrderAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyOrderAdapter.ViewHolder holder, int position) {

        final MenuDishesDatum feedItem = feedItemList.get(position);
        holder.txt_name.setText("  "+feedItem.getName()+"  ");






    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_name;


        public ViewHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.textView5);
           // imageviews = (ImageView) itemView.findViewById(R.id.imageView2);
          //  price = (TextView) itemView.findViewById(R.id.textView_price);
      //      metric = (TextView) itemView.findViewById(R.id.textView_metric);
       //     description = (TextView) itemView.findViewById(R.id.textView_description);
       //     btn_increment = (Button) itemView.findViewById(R.id.button_increment);
       //     btn_decrement = (Button) itemView.findViewById(R.id.button_decrement);
        //    txt_add = (TextView) itemView.findViewById(R.id.textView_add);

        }
    }
}

