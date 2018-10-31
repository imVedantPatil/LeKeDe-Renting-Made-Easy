package com.example.vedant.lekede;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    public EditText email_id,pass;
    Button register;
    ProgressDialog pd_register;
    UserInformation userInfo;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    public String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();


        email_id=findViewById(R.id.edit_email_id);
        pass=findViewById(R.id.edit_password);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        pd_register=new ProgressDialog(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v == register)
        {
            registerUser();
        }
    }

    boolean isEmailValid(CharSequence email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void registerUser()
    {
        email=email_id.getText().toString().trim();
        password=pass.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) )//|| TextUtils.isEmpty(adhar_number) || TextUtils.isEmpty(nm) || TextUtils.isEmpty(phone_number))
        {
            Toast.makeText(this,"please enter the valid details",Toast.LENGTH_SHORT).show();

        }
        pd_register.setMessage("please wait");
        pd_register.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Register.this,"REGISTERED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                            pd_register.dismiss();

                            FirebaseUser user=mAuth.getCurrentUser();
                            if(user!=null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Register.this, "Verification Email send", Toast.LENGTH_LONG).show();
                                                    finish();
                                                    boolean register_flag=true;
                                                    Intent intent=new Intent(Register.this,Login_email.class);
                                                    intent.putExtra("registerFlag",register_flag);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(Register.this, "NO SUCH EMAIL-ID..SORRY", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        }
                        else
                        {
                            Toast.makeText(Register.this,"Failed=" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Register.this,"PLEASE TRY AGAIN LATER",Toast.LENGTH_SHORT).show();
                            pd_register.dismiss();
                        }
                    }
                });

    }

}
