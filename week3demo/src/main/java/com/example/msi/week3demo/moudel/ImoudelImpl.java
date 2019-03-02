package com.example.msi.week3demo.moudel;

import com.example.msi.week3demo.utils.MvPInterface;
import com.example.msi.week3demo.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.util.Map;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/3/2  9:18<p>
 * <p>更改时间：2019/3/2  9:18<p>
 * <p>版本号：1<p>
 */
public class ImoudelImpl implements MvPInterface.IMoudel {
    private MvPInterface.MyCallback myCallback;

    @Override
    public void get(String url, Map<String, String> headmap, Map<String, String> bodymap, final Class aClass, final MvPInterface.MyCallback myCallback) {
        this.myCallback=myCallback;
        RetrofitUtils.getInterface().get(url,headmap,bodymap).setOkhttpListener(new RetrofitUtils.OkhttpListener() {
            @Override
            public void Success(String jsonStr) {
                Gson gson = new Gson();
                Object fromJson = gson.fromJson(jsonStr, aClass);
                myCallback.setData(fromJson);
            }

            @Override
            public void Error(String error) {
                myCallback.setError(error);
            }
        });
    }

    @Override
    public void post(String url, Map<String, String> headmap, Map<String, String> bodymap, final Class aClass, final MvPInterface.MyCallback myCallback) {
        RetrofitUtils.getInterface().post(url,headmap,bodymap).setOkhttpListener(new RetrofitUtils.OkhttpListener() {
            @Override
            public void Success(String jsonStr) {
                Gson gson = new Gson();
                Object fromJson = gson.fromJson(jsonStr, aClass);
                myCallback.setData(fromJson);
            }

            @Override
            public void Error(String error) {
                myCallback.setError(error);
            }
        });
    }

    @Override
    public void put(String url, Map<String, String> headmap, Map<String, String> bodymap, final Class aClass, final MvPInterface.MyCallback myCallback) {
        RetrofitUtils.getInterface().put(url,headmap,bodymap).setOkhttpListener(new RetrofitUtils.OkhttpListener() {
            @Override
            public void Success(String jsonStr) {
                Gson gson = new Gson();
                Object fromJson = gson.fromJson(jsonStr, aClass);
                myCallback.setData(fromJson);
            }

            @Override
            public void Error(String error) {
                myCallback.setError(error);
            }
        });
    }
}
