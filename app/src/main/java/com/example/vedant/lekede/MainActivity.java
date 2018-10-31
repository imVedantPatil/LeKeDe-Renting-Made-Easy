package com.example.vedant.lekede;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;
    private Button login_email,login_otp;
    private TextView tv1;
    private ProgressDialog pd;
    private GoogleSignInClient mGoogleSignInClient;
    //private SignInButton google;
    Button google;
    private String gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        login_email=(Button) findViewById(R.id.login_with_email);
        login_email.setOnClickListener(this);
        login_otp=(Button)findViewById(R.id.login_OTP);
        login_otp.setOnClickListener(this);

        tv1=(TextView) findViewById(R.id.new_account);
        tv1.setOnClickListener(this);

        google=findViewById(R.id.google_sign_in);
        google.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        pd=new ProgressDialog(this);
    }
    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                gmail=account.getEmail();
                firebaseAuthWithGoogle(account);
            }
            catch (ApiException e)
            {

            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        pd.setMessage("please wait");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            pd.show();
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            Toast.makeText(MainActivity.this, ""+isNew, Toast.LENGTH_SHORT).show();
                            if(isNew) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "New User.....", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,Datastore.class);
                                intent.putExtra("email",user.getEmail());
                                startActivity(intent);
                                pd.dismiss();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "LOGIN SUCCESSFULLY WITH GOOGLE", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, MainPage.class));
                                pd.dismiss();
                            }

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "PLEASE TRY AGAIN LATER", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v == google) {
            signIn();
        }
        if (v == tv1) {
            Toast.makeText(this, "PLEASE ENTER THE DETAILS", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Register.class));
        }
        if (v == login_email) {
            Toast.makeText(this, "PLEASE ENTER THE DETAILS", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login_email.class));
        }
        if (v == login_otp) {
            Toast.makeText(this, "Please Enter The Phone Number", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Login_OTP.class));
        }
    }
}
