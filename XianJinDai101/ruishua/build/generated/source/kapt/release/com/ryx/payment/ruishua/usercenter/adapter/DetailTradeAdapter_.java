//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.usercenter.adapter;

import android.content.Context;

public final class DetailTradeAdapter_
    extends DetailTradeAdapter
{
    private Context context_;

    private DetailTradeAdapter_(Context context) {
        context_ = context;
        init_();
    }

    public static DetailTradeAdapter_ getInstance_(Context context) {
        return new DetailTradeAdapter_(context);
    }

    private void init_() {
        this.context = context_;
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }
}