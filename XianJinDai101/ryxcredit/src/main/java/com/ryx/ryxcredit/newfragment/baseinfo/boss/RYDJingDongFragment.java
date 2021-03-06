package com.ryx.ryxcredit.newfragment.baseinfo.boss;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.livedetect.utils.LogUtil;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.ryxcredit.R;
import com.ryx.ryxcredit.beans.ReqAction;
import com.ryx.ryxcredit.services.ICallback;
import com.ryx.ryxcredit.services.UICallBack;
import com.ryx.ryxcredit.utils.CLogUtil;
import com.ryx.ryxcredit.utils.CPreferenceUtil;
import com.ryx.ryxcredit.utils.HttpUtil;
import com.ryx.ryxcredit.widget.RyxCreditLoadDialog;
import com.ryx.ryxcredit.xjd.CommonH5Activity;
import com.ryx.ryxcredit.xjd.RYDBossBaseInfoActivity;
import com.ryx.ryxcredit.xjd.bean.JdStatus.JdStatusRequest;
import com.ryx.ryxcredit.xjd.bean.JdStatus.JdStatusResponse;
import com.ryx.ryxcredit.xjd.bean.JdSubmmit.JdSubmitRequest;
import com.ryx.ryxcredit.xjd.bean.JdSubmmit.JdSubmitResponse;
import com.ryx.ryxcredit.xjd.bean.JdTelCode.JdTelCodeRequest;
import com.ryx.ryxcredit.xjd.bean.JdTelCode.JdTelCodeResponse;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import static com.ryx.ryxcredit.R.id.c_btn_sendmsg;

/**
 * A simple {@link Fragment} subclass.
 */
public class RYDJingDongFragment extends Fragment implements View.OnClickListener {
    private RYDBossBaseInfoActivity baseInfoActivity;
    private RYDBossBaseInfoActivity callback;
    private CPreferenceUtil preferenceUtil;
    private View rootView;
    private AutoLinearLayout ll_sitchooseone_jingdong_account;
    private EditText et_sitchooseone_jingdong_account;
    private AutoLinearLayout ll_sitchooseone_jingdong_password;
    private EditText et_sitchooseone_jingdong_password;
    private CheckBox cb_sitchooseone_jingdong_agreement;
    private Button bt_sitchooseone_jingdong_next;
    private int cb_sitchooseone_jingdong_agreement_num;
    private TextView tv_sitchooseone_creditcardbill_agreement;
    private String sitchooseone_jingdong_accountEt;
    private String sitchooseone_jingdong_passwordEt;
    private Timer mtimer;
    private String jdStatusResulte;
    private BottomSheetDialog mBottomSheetDialog;
    private Timer smsTimer;
    private int secondsRremaining;
    private  int frequency =0;
    private TextView tvPhoneno;
    private String authorise_info_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseInfoActivity = (RYDBossBaseInfoActivity) getActivity();
        callback = (RYDBossBaseInfoActivity) getActivity();
        preferenceUtil = CPreferenceUtil.getInstance(getActivity().getApplication());
        rootView = inflater.inflate(R.layout.ryd_fragment_jing_dong, container, false);
        initView();
        authorise_info_url = ((RYDBossBaseInfoActivity) getActivity()).getAuthorise_info_url();
        cb_sitchooseone_jingdong_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_sitchooseone_jingdong_agreement.isChecked()) {
                    cb_sitchooseone_jingdong_agreement_num =0;
                }else{
                    cb_sitchooseone_jingdong_agreement_num =1;
                }
            }
        });
        return rootView;
    }

    /*
    * 初始化控件
    * */
    private void initView() {
        ll_sitchooseone_jingdong_account = (AutoLinearLayout) rootView.findViewById(R.id.xjd_ll_sitchooseone_jingdong_account);
        et_sitchooseone_jingdong_account = (EditText) rootView.findViewById(R.id.xjd_et_sitchooseone_jingdong_account);
        ll_sitchooseone_jingdong_password = (AutoLinearLayout) rootView.findViewById(R.id.xjd_ll_sitchooseone_jingdong_password);
        et_sitchooseone_jingdong_password = (EditText) rootView.findViewById(R.id.xjd_et_sitchooseone_jingdong_password);
        cb_sitchooseone_jingdong_agreement = (CheckBox) rootView.findViewById(R.id.xjd_cb_sitchooseone_jingdong_agreement);
        bt_sitchooseone_jingdong_next = (Button) rootView.findViewById(R.id.xjd_bt_sitchooseone_jingdong_next);
        tv_sitchooseone_creditcardbill_agreement = (TextView) rootView.findViewById(R.id.xjd_tv_sitchooseone_creditcardbill_agreement);
        tv_sitchooseone_creditcardbill_agreement.setOnClickListener(this);
        cb_sitchooseone_jingdong_agreement.setOnClickListener(this);
        bt_sitchooseone_jingdong_next.setOnClickListener(this);
    }
    /*
    * 检测内容是否输入完整
    * */
    private boolean checkInput() {
        sitchooseone_jingdong_accountEt = et_sitchooseone_jingdong_account.getText().toString().trim();
        if (TextUtils.isEmpty(sitchooseone_jingdong_accountEt)) {
            CLogUtil.showToast(getActivity(), "京东账号不能为空!");
            return false;
        }
        sitchooseone_jingdong_passwordEt = et_sitchooseone_jingdong_password.getText().toString().trim();
        if (TextUtils.isEmpty(sitchooseone_jingdong_passwordEt)) {
            CLogUtil.showToast(getActivity(), "京东密码不能为空!");
            return false;
        }
        if (cb_sitchooseone_jingdong_agreement_num ==1) {
            CLogUtil.showToast(getActivity(), "请同意授权信息使用协议!");
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.xjd_bt_sitchooseone_jingdong_next){
            if (checkInput()){
                submitData();
            }
        }else if(id==R.id.xjd_cb_sitchooseone_jingdong_agreement){
            cb_sitchooseone_jingdong_agreement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cb_sitchooseone_jingdong_agreement.isChecked()) {
                        cb_sitchooseone_jingdong_agreement_num =0;
                    }else{
                        cb_sitchooseone_jingdong_agreement_num =1;
                    }
                }
            });
        }else if(id==R.id.xjd_tv_sitchooseone_creditcardbill_agreement){
            //跳转协议
            Intent intent = new Intent(baseInfoActivity, CommonH5Activity.class);
            intent.putExtra("url_address",authorise_info_url);
            intent.putExtra("title","授权信息使用协议");
            startActivity(intent);
            CLogUtil.showLog("协议");
        }
    }
    /*
    * 提交账号密码
    * */
    private void submitData() {
        JdSubmitRequest jdSubmitRequest = new JdSubmitRequest();
        jdSubmitRequest.setLogin_name(sitchooseone_jingdong_accountEt);
        jdSubmitRequest.setLogin_password(sitchooseone_jingdong_passwordEt);
        HttpUtil.getInstance(getActivity()).httpsPost(jdSubmitRequest, ReqAction.CREDIT_JDSUBMIT, JdSubmitResponse.class, new ICallback<JdSubmitResponse>() {
            @Override
            public void success(JdSubmitResponse jdSubmitResponse) {
                int jdSubmitCode = jdSubmitResponse.getCode();
                if (jdSubmitCode==5031) {
                    baseInfoActivity.showMaintainDialog();
                } else {
                    RyxCreditLoadDialog.getInstance(getActivity()).setMessage("授权需要1-2分钟，请不要退出!");
                    RyxCreditLoadDialog.getInstance(getActivity()).show();
                    doResearch();
                }
            }

            @Override
            public void failture(String tips) {
                CLogUtil.showToast(getActivity(), tips);
                RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
            }
        },new UICallBack() {
            @Override
            public void complete() {

            }
        });
    }
    /*
    * 轮循
    * */
    private void doResearch() {
        mtimer = new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                JingdongStatus();
            }
        }, 10000, 10000);
    }
    /*
    * 状态接口
    * */
    private void JingdongStatus() {
        final JdStatusRequest jdStatusRequest = new JdStatusRequest();
        HttpUtil.getInstance(getActivity()).httpsPost(jdStatusRequest, ReqAction.CREDIT_JDSTATUS, JdStatusResponse.class, new ICallback<JdStatusResponse>() {
            @Override
            public void success(JdStatusResponse jdStatusResponse) {
   //             RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
                jdStatusResulte = jdStatusResponse.getResult();
                int jdStatusCode = jdStatusResponse.getCode();
                if (jdStatusCode==5031) {
                    baseInfoActivity.showMaintainDialog();
                } else {
                    switch (jdStatusResulte) {
                        case "I":
                            CLogUtil.showToast(getActivity(), "信息校验中!");
                            if (frequency == 12) {
                                CLogUtil.showToast(getActivity(), "输入超时，请重新输入");
                          //      RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
                                mtimer.cancel();
                            } else {
                                frequency++;
                            }
                            break;
                        case "S":
                            /*callback.setFaceCollectInfo();
                            mtimer.cancel();*/
                            if (mtimer != null) {
                                mtimer.cancel();
                                mtimer = null;
                           //     RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
                                if(mtimer == null){
                                    callback.setFaceCollectInfo();
                                }
                            }
                            break;
                        case "F":
                            CLogUtil.showToast(getActivity(), "用户名或密码错误，请重新输入!");
                            mtimer.cancel();
                            break;
                        case "D":
                            CLogUtil.showToast(getActivity(), "提交失败，请重新输入提交!");
                            mtimer.cancel();
                            break;
                        case "E":
                            showBottomSheet(sitchooseone_jingdong_accountEt);
                            mtimer.cancel();
                            break;
                        case "N":
                            CLogUtil.showToast(getActivity(), "验证码错误，请重新获取输入!");
                            mtimer.cancel();
                            break;
                        case "P":
                            /*callback.setFaceCollectInfo();
                            mtimer.cancel();*/
                            if (mtimer != null) {
                                mtimer.cancel();
                                mtimer = null;
                              //  RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
                                if(mtimer == null){
                                    callback.setFaceCollectInfo();
                                }
                            }
                            break;
                        default:

                            break;
                    }
                }
            }

            @Override
            public void failture(String tips) {
                RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
                CLogUtil.showToast(getActivity(), tips);
                mtimer.cancel();
            }
        },new UICallBack() {
            @Override
            public void complete() {

            }
        });

    }

    private TextView tvVertifyCode;//"重新发送按钮"

    public void showBottomSheet(final String phoneNo) {
        //如果底部输入验证码对话框正在显示，则不重复打开
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            if (isAdded()) {
                setVertifyText();
            }
            return;
        }
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.Material_App_BottomSheetDialog);
        View boottomView = LayoutInflater.from(getContext()).inflate(R.layout.c_call_records_sms_check, null);
        tvPhoneno = (TextView) boottomView.findViewById(R.id.c_tv_phoneno);
        tvVertifyCode = (TextView) boottomView.findViewById(c_btn_sendmsg);
        setVertifyText();
        tvVertifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doSubmit();
                sendVertifyCode();
            }
        });
        final EditText etPhonecode = (EditText) boottomView.findViewById(R.id.c_et_phonecode);
        TextView tvPhoneno = (TextView) boottomView.findViewById(R.id.c_tv_phoneno);
     //   tvPhoneno.setText(phoneNo);
        Button buttonSure = (Button) boottomView.findViewById(R.id.c_sure_borrow_btn);
        buttonSure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (TextUtils.isEmpty(etPhonecode.getText().toString().trim())) {
                    CLogUtil.showToast(getContext(), "请填写验证码!");
                    return;
                }
                mBottomSheetDialog.dismiss();

                doSubmit();
            }
        });
        mBottomSheetDialog.contentView(boottomView).show();
        sendVertifyCode();
    }
    /*
    * 验证码正确与否
    * */
    private void doSubmit() {
        RyxCreditLoadDialog.getInstance(getContext()).setMessage("授权需要1-2分钟，请不要退出!");
        RyxCreditLoadDialog.getInstance(getContext()).show();
        JdTelCodeRequest jdTelCodeRequest = new JdTelCodeRequest();
        if (tvPhoneno.getText().toString().trim()!=null) {
            jdTelCodeRequest.setCode(tvPhoneno.getText().toString().trim());
        }
        HttpUtil.getInstance(getActivity()).httpsPost(jdTelCodeRequest, ReqAction.CREDIT_JDSUBMIT, JdTelCodeResponse.class, new ICallback<JdTelCodeResponse>() {
            @Override
            public void success(JdTelCodeResponse jdTelCodeResponse) {
                RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
                int jdTelCodeCode = jdTelCodeResponse.getCode();
                if (jdTelCodeCode==5031) {
                    baseInfoActivity.showMaintainDialog();
                } else {
                    callback.setFaceCollectInfo();
                }
            }

            @Override
            public void failture(String tips) {
                RyxCreditLoadDialog.getInstance(getActivity()).dismiss();
                CLogUtil.showToast(getActivity(), tips);

            }
        },new UICallBack() {
            @Override
            public void complete() {

            }
        });
    }

    //设置“发送验证码”文字样式
    private void setVertifyText() {
        if (smsTimer == null) {
            tvVertifyCode.setText(getResources().getString(R.string.c_common_send_verify_code));
            tvVertifyCode.setClickable(true);
            tvVertifyCode.setTextColor(Color.parseColor("#1db7f0"));
        } else {
            tvVertifyCode.setTextColor(getResources().getColor(R.color.text_a));
            tvVertifyCode.setText(getResources().getString(R.string.resend)
                    + "(" + secondsRremaining + ")");
            tvVertifyCode.setClickable(false);
        }
    }

    private void sendVertifyCode() {
        if (smsTimer != null) {
            return;
        }
        smsTimer = new Timer();
        startCountdown();
    }
    /**
     * 开始倒计时60秒
     */
    public void startCountdown() {
        secondsRremaining = 60;
        TimerTask task = new TimerTask() {

            public void run() {
                Message msg = Message.obtain();
                msg.what = secondsRremaining--;
                int i = msg.what;
                LogUtil.i("我是i",i+"");
                timeHandler.sendMessage(msg);
            }
        };
        smsTimer.schedule(task, 1000, 1000);
    }

    Handler timeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what > 0) {
                if (isAdded()) {
                    tvVertifyCode.setTextColor(getResources().getColor(R.color.text_a));
                    tvVertifyCode.setText(getResources().getString(R.string.resend)
                            + "(" + msg.what + ")");
                    tvVertifyCode.setClickable(false);
                }
            } else {
                smsTimer.cancel();
                smsTimer = null;
                if (isAdded()) {
                    tvVertifyCode.setText(getResources().getString(
                            R.string.resend_verification_code));
                    tvVertifyCode.setClickable(true);
                    tvVertifyCode.setTextColor(Color.parseColor("#1db7f0"));
                }
            }
        }
    };


}
