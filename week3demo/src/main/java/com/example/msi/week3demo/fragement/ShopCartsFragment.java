package com.example.msi.week3demo.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msi.week3demo.R;
import com.example.msi.week3demo.SubmitActivity;
import com.example.msi.week3demo.adapter.ShopCartAdapter;
import com.example.msi.week3demo.bean.FindShoppingCartBean;
import com.example.msi.week3demo.bean.LoginBean;
import com.example.msi.week3demo.bean.ShopEventBean;
import com.example.msi.week3demo.presenter.IpresenterImpl;
import com.example.msi.week3demo.utils.MvPInterface;
import com.example.msi.week3demo.utils.MyServerApi;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCartsFragment<T> extends Fragment implements MvPInterface.MyView<T> {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.check_all)
    CheckBox checkAll;
    @BindView(R.id.pricesum)
    TextView pricesum;
    @BindView(R.id.btn_close)
    Button btnClose;
    Unbinder unbinder;
    private IpresenterImpl iPresenter;

    private List<FindShoppingCartBean.ResultBean> cartBeans = new ArrayList<>();
    private ShopCartAdapter shopCartAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_carts, container, false);
        unbinder = ButterKnife.bind(this, view);
        iPresenter = new IpresenterImpl(this);
        cartBeans.clear();
        //开始请求数据
        startRequest();
        init();
        seiListener();
        return view;
    }

    private void seiListener() {
        shopCartAdapter.getIntance(new ShopCartAdapter.ItemOnclicked() {
            @Override
            public void delete(int i, double price) {
                cartBeans.remove(i);
                pricesum.setText(price + "元");
                shopCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void jia(int i, double price) {
                pricesum.setText(price + "元");
                shopCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void jian(int i, double price) {
                pricesum.setText(price + "元");
                shopCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void ck(int i, double price, boolean Allcheck) {
                cartBeans.get(i).setCk(cartBeans.get(i).isCk());
                checkAll.setChecked(Allcheck);
                pricesum.setText(price + "元");
                shopCartAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        shopCartAdapter = new ShopCartAdapter(getActivity(), cartBeans);
        recyclerView.setAdapter(shopCartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void startRequest() {
        Map<String, String> headermap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13852501387");
        map.put("pwd", "123456");
        iPresenter.postData(MyServerApi.BASE_LOGIN,headermap,map,LoginBean.class);

    }


    @OnClick({R.id.check_all, R.id.btn_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_all:
                //全选，反选
                for (int i = 0; i < cartBeans.size(); i++) {
                    cartBeans.get(i).setCk(checkAll.isChecked());
                }
                double price = 0;
                for (int i = 0; i < cartBeans.size(); i++) {
                    if (cartBeans.get(i).isCk()) {
                        price += cartBeans.get(i).getPrice() * cartBeans.get(i).getCount();
                    }
                }
                pricesum.setText(price + "元");
                shopCartAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_close:
                //提交订单
                Intent intent = new Intent(getContext(), SubmitActivity.class);

                List<FindShoppingCartBean.ResultBean> oklist = new ArrayList<>();
                for (int i = 0; i < cartBeans.size(); i++) {
                    if (cartBeans.get(i).isCk()) {
                        oklist.add(cartBeans.get(i));
                    }
                }
                if (oklist.size() != 0) {
                    EventBus.getDefault().postSticky(new ShopEventBean(oklist));
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "请选择购物车中的物品", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //成功
    @Override
    public void success(T data) {
        if (data instanceof LoginBean){
            LoginBean loginBean= (LoginBean) data;
            if (loginBean.getStatus().equals("0000")){
                Toast.makeText(getActivity(),loginBean.getMessage(),Toast.LENGTH_SHORT).show();
                Map<String, String> headermaps = new HashMap<>();
                Map<String, String> maps = new HashMap<>();
                headermaps.put("userId", loginBean.getResult().getUserId()+"");
                headermaps.put("sessionId", loginBean.getResult().getSessionId());
                iPresenter.getData(MyServerApi.BASE_SHOPCART, headermaps, maps, FindShoppingCartBean.class);
            }else{
                Toast.makeText(getActivity(),loginBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }else if (data instanceof FindShoppingCartBean) {
            FindShoppingCartBean bean = (FindShoppingCartBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();
                cartBeans.addAll(bean.getResult());
                shopCartAdapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        shopCartAdapter.notifyDataSetChanged();
        double price = 0;
        for (int i = 0; i < cartBeans.size(); i++) {
            if (cartBeans.get(i).isCk()) {
                price += cartBeans.get(i).getPrice() * cartBeans.get(i).getCount();
            }
        }
        pricesum.setText(price + "元");
    }

    //失败
    @Override
    public void error(T error) {
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (iPresenter != null) {
            iPresenter = null;
        }

    }
}
