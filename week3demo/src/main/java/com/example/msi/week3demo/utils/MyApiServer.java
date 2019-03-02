package com.example.msi.week3demo.utils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/3/2  8:52<p>
 * <p>更改时间：2019/3/2  8:52<p>
 * <p>版本号：1<p>
 */
public interface MyApiServer {
    @GET
    Observable<ResponseBody>get(@Url String url, @HeaderMap Map<String,String>headmap, @QueryMap Map<String,String>bodymap);
    @POST
    Observable<ResponseBody>post(@Url String url, @HeaderMap Map<String,String>headmap, @QueryMap Map<String,String>bodymap);
    @PUT
    Observable<ResponseBody>put(@Url String url, @HeaderMap Map<String,String>headmap, @QueryMap Map<String,String>bodymap);
}
