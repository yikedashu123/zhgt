package com.example.lenovo.androidtext1_1.loginandre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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

import java.util.Random;

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
                    mPass.setText("");
                    mName.setText("");
                    mSure.setText("");
                    mCodeText.setText("");
                }
            }
        });
        mCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                for(int i=0;i<4;i++)
                {
                    code+=random.nextInt(10);
                }
                mCodeText.setText(code);
                code="";
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
                                if(zc)
                                {
                                    Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
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
}
