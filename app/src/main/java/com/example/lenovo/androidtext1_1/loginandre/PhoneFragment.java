package com.example.lenovo.androidtext1_1.loginandre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.androidtext1_1.R;
import com.example.lenovo.androidtext1_1.connectNet.ConnectZc;
import com.example.lenovo.androidtext1_1.userData.User;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@SuppressLint("ValidFragment")
public class PhoneFragment extends Fragment {
    private Button mCode;
    private EditText mCodeText;
    private String code="";
    private TextView mName,mPass,mSure;
    private boolean zc;
    private Context mContext;
    private Button mZc;
    private CheckBox mAgree;
    private boolean isTrue;
    Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if(result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean)data;
                    if(smart) {
                        Toast.makeText(getContext(),"该手机号已经注册过，请重新输入",
                                Toast.LENGTH_LONG).show();
                        mName.requestFocus();
                        return;
                    }
                }
            }
            if(result==SMSSDK.RESULT_COMPLETE)
            {

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    isTrue=true;
                }
            }
            else
            {
            }

        }
    };
    private EventHandler eh;

    @SuppressLint("ValidFragment")
    public PhoneFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_phone, container, false);
        mCode=view.findViewById(R.id.btn_getCode);
        mCodeText=view.findViewById(R.id.et_code);
        mName=view.findViewById(R.id.et_userName);
        mPass=view.findViewById(R.id.et_userPass);
        mSure=view.findViewById(R.id.et_surePass);
        mAgree=view.findViewById(R.id.cb_agree);
        mZc=view.findViewById(R.id.btn_zc);
        mZc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mPass.getText())||TextUtils.isEmpty(mName.getText()))
                {
                    Toast.makeText(getContext(),"格式错误！",Toast.LENGTH_SHORT).show();
                }else if(!mPass.getText().toString().trim().equals(mSure.getText().toString().trim())){
                    Toast.makeText(getContext(),"密码与确认密码不一致！",Toast.LENGTH_SHORT).show();
                }else if(!mAgree.isChecked())
                {
                    Toast.makeText(getContext(),"请同意协议！",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mCodeText.getText()))
                {
                    Toast.makeText(getContext(),"请获取验证码！"+mCodeText.getText().toString(),Toast.LENGTH_SHORT).show();
                }else{
                    zc();

                }
            }
        });
        mCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                SMSSDK.getVerificationCode("86",mName.getText().toString() );

            }
        });
        return view;
    }

    private void zc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user=new User();

                user.setName(mName.getText().toString().trim());
                user.setPass(mPass.getText().toString().trim());
                zc = ConnectZc.getData(user);
                getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                SMSSDK.submitVerificationCode("86", mName.getText().toString(), mCode.getText().toString());
                                if(zc&&isTrue)
                                {
                                    Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                                    mPass.setText("");
                                    mName.setText("");
                                    mSure.setText("");
                                    mCodeText.setText("");
                                }else
                                {
                                    Toast.makeText(getContext(),"用户名已存在！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        }).start();
    }

    public void  getCode() {
        eh = new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);

            }

        };
        SMSSDK.registerEventHandler(eh);
    }
//    private void onStop() {
//        super.onStop();
//        //用完回调要注销掉，否则可能会出现内存泄露
//        SMSSDK.unregisterEventHandler(eh);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
