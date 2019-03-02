package com.example.msi.week3demo.utils;

import java.util.Map;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/3/2  8:53<p>
 * <p>更改时间：2019/3/2  8:53<p>
 * <p>版本号：1<p>
 */
public interface MvPInterface {
    interface MyView<T>{
       void success(T data);
       void error(T error);
    }

    interface MyCallback<T>{
        void setData(T data);
        void setError(T error);
    }

    interface IMoudel{
        void get(String url, Map<String,String>headmap,Map<String,String>bodymap,Class aClass,MyCallback myCallback);
        void post(String url, Map<String,String>headmap,Map<String,String>bodymap,Class aClass,MyCallback myCallback);
        void put(String url, Map<String,String>headmap,Map<String,String>bodymap,Class aClass,MyCallback myCallback);

    }

    interface IPresenter{
        void getData(String url,Map<String,String>headmap,Map<String,String>bodymap,Class aClass);
        void postData(String url, Map<String,String>headmap,Map<String,String>bodymap,Class aClass);
        void putData(String url, Map<String,String>headmap,Map<String,String>bodymap,Class aClass);
    }
}
