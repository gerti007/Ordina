package com.ordinefacile.root.ordinefacile.ui.my_order;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ordinefacile.root.ordinefacile.R;
import com.ordinefacile.root.ordinefacile.data.db.order.Orders;

import com.ordinefacile.root.ordinefacile.ui.dialog.MaterialDialog;
import com.ordinefacile.root.ordinefacile.utils.ParseImage;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Eljo on 2/1/2018.
 */

public class MyOrderAdapter   extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>  {

    private static final String TAG = "My Debugg";
    private List<Orders> feedItemList;
    private Context context;
    private ParseImage parseimage;
    MaterialDialog materialDialog;
    MyOrderPresenter myOrderPresenter;
    MyOrderActivity myOrderActivity;

    EventBus bus = EventBus.getDefault();

    public MyOrderAdapter(Context context, List<Orders> feedItemList,MyOrderActivity myOrderActivity) {

        this.feedItemList = feedItemList;
        this.context = context;
        this.myOrderActivity = myOrderActivity;
        parseimage = new ParseImage(context);
        materialDialog = new MaterialDialog();

    }

    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.menu_my_order_adapter, parent, false);

        myOrderPresenter = new MyOrderPresenter(context,myOrderActivity);
        return new MyOrderAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyOrderAdapter.ViewHolder holder, int position) {




        final Orders feedItem = feedItemList.get(position);
        holder.txt_name.setText(feedItem.getmName());
        holder.txt_name.setText(feedItem.getmName());

        String final_price = String.valueOf(new DecimalFormat("##.##").format(feedItem.getmFinalPrice()));

        holder.txt_price.setText(final_price+" €");




            holder.txt_metric.setText("  "+feedItem.getmQuantity()+"  ");


        parseimage.parseimage(feedItem.getmUrl_Image().toString(),holder.imag_myorder_pick);

        holder.img_bacground.setBackgroundColor(position % 2 == 0 ? Color.parseColor("#00D26A"): Color.parseColor("#F29C20"));

        holder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                materialDialog.createDialog(context,feedItem.getmName(),String.valueOf(new DecimalFormat("##.##").format(feedItem.getmFinalPrice())+""));
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Eventlist event = new Eventlist();
                EventBus.getDefault().post(event);

                String myString = feedItem.getmIdProductCart();
                int int_product = Integer.parseInt(myString);

                myOrderActivity.idProduct(int_product);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_name;
        private TextView txt_price;
        private TextView txt_metric;
        private CircularImageView imag_myorder_pick;
        private ImageButton btn_info;
        private ImageView btn_delete;
        private ImageView img_bacground;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_name = (TextView) itemView.findViewById(R.id.textView_myorder_name);
            imag_myorder_pick = (CircularImageView) itemView.findViewById(R.id.circularImageView_myorder);
            txt_price = (TextView) itemView.findViewById(R.id.textView_myorder_price);
            txt_metric = (TextView) itemView.findViewById(R.id.textView_myorder_metric);
            btn_info = (ImageButton) itemView.findViewById(R.id.imageButton_myorder_info);
            img_bacground = (ImageView) itemView.findViewById(R.id.imageView_myorder);
            btn_delete = (ImageView) itemView.findViewById(R.id.imageButton_delete);
        }
    }

}

