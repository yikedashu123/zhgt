package com.example.lenovo.androidtext1_1.loginandre;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.androidtext1_1.R;

import java.util.Random;

public class PhoneFragment extends Fragment {
    private Button mCode;
    private EditText mCodeText;
    private String code="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_phone, container, false);
        mCode=view.findViewById(R.id.btn_getCode);
        mCodeText=view.findViewById(R.id.et_code);
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
}
