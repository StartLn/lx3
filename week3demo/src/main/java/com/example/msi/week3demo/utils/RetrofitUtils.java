package com.example.msi.week3demo.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/3/2  8:52<p>
 * <p>更改时间：2019/3/2  8:52<p>
 * <p>版本号：1<p>
 */
public class RetrofitUtils {

    private final MyApiServer myApiServer;

    private RetrofitUtils() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .connectTimeout(20,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(MyServerApi.BASE_URL)
                .client(okHttpClient)
                .build();
        myApiServer = retrofit.create(MyApiServer.class);
    }

    public static RetrofitUtils getInterface(){
        return RetrofitHoulder.retrofit;
    }

    private static class RetrofitHoulder{
        private static final RetrofitUtils retrofit=new RetrofitUtils();
    }

    public RetrofitUtils get(String url, Map<String,String>headmap,Map<String,String>bodymap){
        myApiServer.get(url,headmap,bodymap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return RetrofitUtils.getInterface();
    }

    public RetrofitUtils post(String url, Map<String,String>headmap,Map<String,String>bodymap){
        myApiServer.post(url,headmap,bodymap)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(observer);
        return RetrofitUtils.getInterface();
    }
    public RetrofitUtils put(String url, Map<String,String>headmap,Map<String,String>bodymap){
        myApiServer.put(url,headmap,bodymap)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(observer);
        return RetrofitUtils.getInterface();
    }

    private Observer observer=new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (okhttpListener!=null){
                okhttpListener.Error(e.getMessage());
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            if (okhttpListener!=null){
                try {
                    okhttpListener.Success(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public interface OkhttpListener{
        void Success(String jsonStr);
        void Error(String error);
    }

    private OkhttpListener okhttpListener;

    public void setOkhttpListener(OkhttpListener okhttpListener){
        this.okhttpListener=okhttpListener;
    }
}
