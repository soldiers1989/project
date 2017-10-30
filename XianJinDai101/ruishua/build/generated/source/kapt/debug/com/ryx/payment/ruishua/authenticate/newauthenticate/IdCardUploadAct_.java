//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.3.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.ryx.payment.ruishua.authenticate.newauthenticate;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.ryx.payment.ruishua.R;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.builder.PostActivityStarter;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class IdCardUploadAct_
    extends IdCardUploadAct
    implements HasViews, OnViewChangedListener
{
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(R.layout.activity_id_card_upload);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static IdCardUploadAct_.IntentBuilder_ intent(Context context) {
        return new IdCardUploadAct_.IntentBuilder_(context);
    }

    public static IdCardUploadAct_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new IdCardUploadAct_.IntentBuilder_(fragment);
    }

    public static IdCardUploadAct_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new IdCardUploadAct_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        this.img_front_idcard = ((ImageView) hasViews.findViewById(R.id.img_front_idcard));
        this.img_back_idcard = ((ImageView) hasViews.findViewById(R.id.img_back_idcard));
        View view_btn_next = hasViews.findViewById(R.id.btn_next);

        if (view_btn_next!= null) {
            view_btn_next.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    IdCardUploadAct_.this.BtnNextClick();
                }
            }
            );
        }
        if (this.img_front_idcard!= null) {
            this.img_front_idcard.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    IdCardUploadAct_.this.imgFrontIdcardClick();
                }
            }
            );
        }
        if (this.img_back_idcard!= null) {
            this.img_back_idcard.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    IdCardUploadAct_.this.imgBackIdcard();
                }
            }
            );
        }
        initView();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<IdCardUploadAct_.IntentBuilder_>
    {
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, IdCardUploadAct_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), IdCardUploadAct_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), IdCardUploadAct_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public PostActivityStarter startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                if (fragment_!= null) {
                    if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
                        fragment_.startActivityForResult(intent, requestCode, lastOptions);
                    } else {
                        fragment_.startActivityForResult(intent, requestCode);
                    }
                } else {
                    if (context instanceof Activity) {
                        Activity activity = ((Activity) context);
                        ActivityCompat.startActivityForResult(activity, intent, requestCode, lastOptions);
                    } else {
                        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
                            context.startActivity(intent, lastOptions);
                        } else {
                            context.startActivity(intent);
                        }
                    }
                }
            }
            return new PostActivityStarter(context);
        }
    }
}