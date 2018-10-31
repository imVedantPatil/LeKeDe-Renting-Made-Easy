package com.example.vedant.lekede;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login_OTP extends AppCompatActivity {

    private EditText phone,otp;
    private Button generate;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private String MoBoNo,code;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        phone=findViewById(R.id.edit_phone);
        otp=findViewById(R.id.edit_otp);
        generate=findViewById(R.id.generate_otp);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String country_code="+91";
                String mno=phone.getText().toString();
                MoBoNo=country_code.concat(mno);
                Toast.makeText(Login_OTP.this, ""+MoBoNo, Toast.LENGTH_SHORT).show();
                if(MoBoNo.isEmpty())
                {
                    Toast.makeText(Login_OTP.this, "Please Enter The Mobile Number", Toast.LENGTH_SHORT).show();

                }
                verifyPhoneNo();
            }
        });

        mAuth=FirebaseAuth.getInstance();
    }
    private void verifyPhoneNo()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                MoBoNo,
                60,
                TimeUnit.SECONDS,
                Login_OTP.this,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
        {

            Toast.makeText(Login_OTP.this, "Verification Successfull", Toast.LENGTH_SHORT).show();
            code=phoneAuthCredential.getSmsCode();
            if(code.isEmpty())
            {
                Toast.makeText(Login_OTP.this, "Error", Toast.LENGTH_SHORT).show();
                code=otp.getText().toString().trim();
                verifyVerificationcode(code);
            }
            else
            {
                verifyVerificationcode(code);
                otp.setText(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e)
        {
            Toast.makeText(Login_OTP.this, "Verification Unsuccessfull"+e, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(Login_OTP.this, "OTP Send", Toast.LENGTH_SHORT).show();
            mVerificationId = s;
        }
    };

    private void verifyVerificationcode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            Toast.makeText(Login_OTP.this, ""+isNew, Toast.LENGTH_SHORT).show();
                            if(isNew)
                            {
                                Toast.makeText(Login_OTP.this, "New user", Toast.LENGTH_SHORT).show();

                                generate.setClickable(false);
                            }
                            else {
                                Toast.makeText(Login_OTP.this, "Log In Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Login_OTP.this, MainPage.class));
                            }
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(Login_OTP.this, "Error during authentication", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
