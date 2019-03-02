package com.example.msi.week3demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
 * <p>创建时间：2019/2/28  13:53<p>
 * <p>更改时间：2019/2/28  13:53<p>
 * <p>版本号：1<p>
 */
public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ViewHolder> {
    private Context mContext;
    private List<FindShoppingCartBean.ResultBean> mList;

    public ShopCartAdapter(Context mContext, List<FindShoppingCartBean.ResultBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shooping, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.checkboxItem.setChecked(mList.get(i).isCk());
        viewHolder.shopImages.setImageURI(mList.get(i).getPic());
        viewHolder.shopContext.setText(mList.get(i).getCommodityName());
        viewHolder.shopPrice.setText("￥" + mList.get(i).getPrice());
        viewHolder.shopnum.setText(mList.get(i).getCount() + "");
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnclicked!=null){
                    itemOnclicked.delete(i,AllPrice());
                }
            }
        });
        viewHolder.shopnumAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnclicked!=null){
                    int i1=mList.get(i).getCount();
                    mList.get(i).setCount(i1+1);
                    itemOnclicked.jia(i,AllPrice());
                }
            }
        });
        viewHolder.shopnumSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemOnclicked!=null){
                    if(mList.get(i).getCount()==0){
                        Toast.makeText(mContext, "数量最低为0", Toast.LENGTH_SHORT).show();
                    }else{
                        int i1 = mList.get(i).getCount();
                        mList.get(i).setCount(i1-1);
                        itemOnclicked.jian(i,AllPrice());
                    }
                }
            }
        });
        viewHolder.checkboxItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemOnclicked!=null){
                    mList.get(i).setCk(!mList.get(i).isCk());
                    itemOnclicked.ck(i,AllPrice(),Allchecked());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox_item)
        CheckBox checkboxItem;
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
        @BindView(R.id.btn_delete)
        Button btnDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //接口回调  定义相关的方法
    public interface ItemOnclicked {
        void delete(int i, double price);

        void jia(int i, double price);

        void jian(int i, double price);

        void ck(int i, double price, boolean Allcheck);
    }

    private ItemOnclicked itemOnclicked;

    public void getIntance(ItemOnclicked itemOnclicked) {
        this.itemOnclicked = itemOnclicked;
    }

    /**
     * 得到所选中的总价格
     * @return
     */
    private double AllPrice(){
        double price=0;
        for (int i = 0; i <mList.size(); i++) {
            if(mList.get(i).isCk()){
                price+=mList.get(i).getPrice()*mList.get(i).getCount();
            }
        }
        return price;
    }

    /**
     * 判断是否所有的子条目都选中
     */
    private boolean Allchecked(){
        for (int i = 0; i <mList.size(); i++) {
            if(!mList.get(i).isCk()){
                return false;
            }
        }
        return true;
    }
}
