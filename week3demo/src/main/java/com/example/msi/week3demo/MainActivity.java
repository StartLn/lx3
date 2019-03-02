package com.example.msi.week3demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.msi.week3demo.fragement.CircleFragment;
import com.example.msi.week3demo.fragement.FirstFragment;
import com.example.msi.week3demo.fragement.OrderFromFragment;
import com.example.msi.week3demo.fragement.PersontenFragment;
import com.example.msi.week3demo.fragement.ShopCartsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_fragment)
    FrameLayout mainFragment;
    @BindView(R.id.btn_radioOne)
    RadioButton btnRadioOne;
    @BindView(R.id.btn_radioTwo)
    RadioButton btnRadioTwo;
    @BindView(R.id.btn_radioThree)
    RadioButton btnRadioThree;
    @BindView(R.id.btn_radioFour)
    RadioButton btnRadioFour;
    @BindView(R.id.btn_radioFive)
    RadioButton btnRadioFive;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private FirstFragment firstFragment;
    private CircleFragment circleFragment;
    private ShopCartsFragment shopCartFragment;
    private OrderFromFragment orderFromFragment;
    private PersontenFragment persontenFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        initData();
        manager.beginTransaction().replace(R.id.main_fragment,firstFragment).commit();
        setListener();
    }

    private void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_radioOne:
                        manager.beginTransaction().replace(R.id.main_fragment,firstFragment).commit();
                        break;
                    case R.id.btn_radioTwo:
                        manager.beginTransaction().replace(R.id.main_fragment,circleFragment).commit();
                        break;
                    case R.id.btn_radioThree:
                        manager.beginTransaction().replace(R.id.main_fragment,shopCartFragment).commit();
                        break;
                    case R.id.btn_radioFour:
                        manager.beginTransaction().replace(R.id.main_fragment,orderFromFragment).commit();
                        break;
                    case R.id.btn_radioFive:
                        manager.beginTransaction().replace(R.id.main_fragment,persontenFragment).commit();
                        break;
                }
            }
        });
    }

    private void initData() {
        firstFragment = new FirstFragment();
        circleFragment = new CircleFragment();
        shopCartFragment = new ShopCartsFragment();
        orderFromFragment = new OrderFromFragment();
        persontenFragment = new PersontenFragment();
    }


}