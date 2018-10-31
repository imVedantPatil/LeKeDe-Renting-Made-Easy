package com.example.vedant.lekede;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_email extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_email,edit_pass;
    private Button login;
    private ProgressDialog pd_login;
    private TextView forgot;
    private String forgot_email;
    FirebaseAuth mAuth;
   boolean reg_flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_email=findViewById(R.id.login_email);
        edit_pass=findViewById(R.id.login_password);
        login=findViewById(R.id.login_button);
        forgot=findViewById(R.id.forgot_pass);
        pd_login=new ProgressDialog(this);
        pd_login.setTitle("Please Wait");
        pd_login.setMessage("Log You In");
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(this);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_email=edit_email.getText().toString().trim();
                mAuth=FirebaseAuth.getInstance();
                mAuth.sendPasswordResetEmail(forgot_email)
                        .addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login_email.this,"Reset Password Email Send",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Login_email.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    @Override
    public void onClick(View v)
    {
        if(v==login)
        {
            userLogin();
        }
    }
    private void userLogin()
    {
        final String email = edit_email.getText().toString().trim();
        final String password = edit_pass.getText().toString().trim();
        boolean flag_email = validate_details_email(email);
        boolean flag_password = validate_details_password(password);
        if (flag_email == false && flag_password == false) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        pd_login.show();
                                        Bundle bundle=getIntent().getExtras();
                                        if(bundle!=null) {
                                            reg_flag = bundle.getBoolean("registerFlag");
                                            boolean verified_flag = user.isEmailVerified();
                                            if (verified_flag) {
                                                if (!reg_flag) {
                                                    Toast.makeText(Login_email.this, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Login_email.this, MainPage.class);
                                                    intent.putExtra("email", email);
                                                    startActivity(intent);
                                                    pd_login.dismiss();
                                                } else {
                                                    Toast.makeText(Login_email.this, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Login_email.this, Datastore.class);
                                                    intent.putExtra("email", email);
                                                    startActivity(intent);
                                                    pd_login.dismiss();
                                                }
                                            } else {
                                                if (reg_flag) {
                                                    Toast.makeText(Login_email.this, "Verify The Email First", Toast.LENGTH_SHORT).show();
                                                    mAuth.signOut();
                                                    pd_login.dismiss();
                                                } else {
                                                    Toast.makeText(Login_email.this, "Not User?Register Please", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(new Intent(Login_email.this, Register.class));
                                                }
                                            }
                                        }
                                        else {
                                            Toast.makeText(Login_email.this, "Not User?Register Please", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(Login_email.this, "Not User?Register Please", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(Login_email.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.w("hi",e.getMessage());
                            }
                        }
                    });
        }
    }
    private boolean validate_details_password(String password)
    {
        if(TextUtils.isEmpty(password))
        {
            edit_pass.setError("Please Enter the password");
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validate_details_email(String email)
    {
        if(TextUtils.isEmpty(email))
        {
            edit_email.setError("Please Enter the email");
            return true;
        }
        else
        {
            return false;
        }
    }
}
