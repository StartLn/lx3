package com.example.msi.week3demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.msi.week3demo.R;
import com.example.msi.week3demo.bean.FindShoppingCartBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/3/1  14:29<p>
 * <p>更改时间：2019/3/1  14:29<p>
 * <p>版本号：1<p>
 */
public class SubmitOrderAdapter extends RecyclerView.Adapter<SubmitOrderAdapter.ViewHolder> {
    private Context context;
    private List<FindShoppingCartBean.ResultBean> list;

    public SubmitOrderAdapter(Context context, List<FindShoppingCartBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_submit_order, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.shopImages.setImageURI(list.get(i).getPic());
        viewHolder.shopContext.setText(list.get(i).getCommodityName());
        viewHolder.shopPrice.setText("￥"+list.get(i).getPrice());
        viewHolder.shopnum.setText(list.get(i).getCount()+"");
        viewHolder.shopnumAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemOnclicked!=null){
                    int count = list.get(i).getCount();
                    list.get(i).setCount(count+1);
                    itemOnclicked.jia(i,getAllCount(),getAllPrice());
                }
            }
        });
        viewHolder.shopnumSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(i).getCount()>1){
                    int count = list.get(i).getCount();
                    list.get(i).setCount(count-1);
                    itemOnclicked.jian(i,getAllCount(),getAllPrice());
                }else{
                    Toast.makeText(context, "已经是最少的数量了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_images)
        SimpleDraweeView shopImages;
        @BindView(R.id.shop_context)
        TextView shopContext;
        @BindView(R.id.shop_price)
        TextView shopPrice;
        @BindView(R.id.shopnum_add)
        TextView shopnumAdd;
        @BindView(R.id.shopnum)
        TextView shopnum;
        @BindView(R.id.shopnum_subtract)
        TextView shopnumSubtract;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ItemOnclicked{
        void jia(int i, int count, double price);
        void jian(int i, int count, double price);
    }
    private ItemOnclicked itemOnclicked;

    public void getIntance(ItemOnclicked itemOnclicked){
        this.itemOnclicked=itemOnclicked;
    }

    /**
     * 计算总的价格
     */
    public double getAllPrice(){
        double price =0;
        for (int i = 0; i <list.size(); i++) {
            price+=list.get(i).getPrice()*list.get(i).getCount();
        }
        return price;
    }

    /**
     * 计算总的数量
     */
    public int getAllCount(){
        int Count =0;
        for (int i = 0; i <list.size(); i++) {
            Count+=list.get(i).getCount();
        }
        return Count;
    }
}
