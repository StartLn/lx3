package com.example.msi.week3demo.presenter;

import com.example.msi.week3demo.moudel.ImoudelImpl;
import com.example.msi.week3demo.utils.MvPInterface;

import java.util.Map;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/3/2  9:19<p>
 * <p>更改时间：2019/3/2  9:19<p>
 * <p>版本号：1<p>
 */
public class IpresenterImpl implements MvPInterface.IPresenter {

    private MvPInterface.MyView myView;
    private final ImoudelImpl imoudel;

    public IpresenterImpl(MvPInterface.MyView myView) {
        this.myView = myView;
        imoudel = new ImoudelImpl();
    }

    @Override
    public void getData(String url, Map<String, String> headmap, Map<String, String> bodymap, Class aClass) {
        imoudel.get(url, headmap, bodymap, aClass, new MvPInterface.MyCallback() {
            @Override
            public void setData(Object data) {
                myView.success(data);
            }

            @Override
            public void setError(Object error) {
                myView.error(error);
            }
        });
    }

    @Override
    public void postData(String url, Map<String, String> headmap, Map<String, String> bodymap, Class aClass) {
        imoudel.post(url, headmap, bodymap, aClass, new MvPInterface.MyCallback() {
            @Override
            public void setData(Object data) {
                myView.success(data);
            }

            @Override
            public void setError(Object error) {
                myView.error(error);
            }
        });
    }

    @Override
    public void putData(String url, Map<String, String> headmap, Map<String, String> bodymap, Class aClass) {
        imoudel.put(url, headmap, bodymap, aClass, new MvPInterface.MyCallback() {
            @Override
            public void setData(Object data) {
                myView.success(data);
            }

            @Override
            public void setError(Object error) {
                myView.error(error);
            }
        });
    }
}
