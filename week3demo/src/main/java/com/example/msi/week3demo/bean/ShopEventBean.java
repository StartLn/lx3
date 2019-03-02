package com.example.msi.week3demo.bean;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${adai}<p>
 * <p>创建时间：2019/1/10 11:37<p>
 * <p>更改时间：2019/1/10 11:37<p>
 * <p>版本号：1<p>
 */
public class ShopEventBean {
    private List<FindShoppingCartBean.ResultBean> list;

    public ShopEventBean(List<FindShoppingCartBean.ResultBean> list) {
        this.list = list;
    }

    public List<FindShoppingCartBean.ResultBean> getList() {
        return list;
    }

    public void setList(List<FindShoppingCartBean.ResultBean> list) {
        this.list = list;
    }
}
