package com.example.vedant.lekede;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;


public class Datastore extends AppCompatActivity implements View.OnClickListener
{
    private ProgressDialog pd,pd1;
    private FirebaseAuth mAuth;
    private static final int PICK_IMAGE_REQUEST=70;
    private EditText adharno,name,phoneno,set_email;
    String adhar_number,phone_number,nm,email,MoBoNo;
    Button map;
    DatabaseReference databaseReference,details,rootUri,childUri,emailref;
    private StorageReference mStorageRef;
    ImageView profile_image;
    private FirebaseUser user;
    ProgressDialog verify_no;
    Boolean image_selected=false;

    @Override
    protected void onStart() {
        Toast.makeText(this, "Please tap on profile image to select", Toast.LENGTH_LONG).show();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datastore);
        profile_image=findViewById(R.id.profile_image);
        mAuth=FirebaseAuth.getInstance();

        map=findViewById(R.id.add_map);
        if(mAuth==null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        databaseReference= FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        adharno=findViewById(R.id.adhar_no);
        name=findViewById(R.id.edit_name);
        phoneno=findViewById(R.id.Phone_No);
        pd=new ProgressDialog(this);
        pd1=new ProgressDialog(this);
        map.setOnClickListener(this);


        profile_image.setOnClickListener(this);

        email=getIntent().getExtras().getString("email");
        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        set_email=findViewById(R.id.register_email);
        set_email.setText(email);
        set_email.setFocusable(false);
        set_email.setCursorVisible(false);
        set_email.setClickable(false);
        set_email.setLongClickable(false);
        set_email.setSelected(false);
        set_email.setKeyListener(null);

        verify_no=new ProgressDialog(this);
        verify_no.setTitle("Verifying Your Number");
        verify_no.setMessage("Please Wait.....");

        rootUri=FirebaseDatabase.getInstance().getReference();
        childUri=rootUri.child("Uri_profile");
    }

    @Override
    public void onClick(View v)
    {
        if(v==map)
        {

            adhar_number=adharno.getText().toString().trim();
            nm=name.getText().toString().trim();
            phone_number=phoneno.getText().toString().trim();
            boolean flag_adhar_number=validate_adhar_number(adhar_number);
            boolean flag_name=validate_name(nm);
            boolean flag_phone_number=validate_phone_number(phone_number);
            boolean flag_image=validate_image();
            String country_code="+91";
            MoBoNo=country_code.concat(phone_number);
            if(!flag_adhar_number && !flag_name && !flag_phone_number && flag_image)
            {
                verify_no.show();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        MoBoNo,
                        60,
                        TimeUnit.SECONDS,
                        Datastore.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                            {
                                Toast.makeText(Datastore.this, "Verification Successfull", Toast.LENGTH_SHORT).show();
                                UserInformation userInfo = new UserInformation(email,nm, adhar_number, MoBoNo);
                                FirebaseUser user = mAuth.getCurrentUser();
                                details = databaseReference.child("User-Details");
                                details.child(user.getUid()).setValue(userInfo);

                                emailref=FirebaseDatabase.getInstance().getReference();
                                emailref.child("User-email").setValue(user.getEmail());

                                verify_no.dismiss();
                                finish();
                             //   startActivity(new Intent(Datastore.this, MapsActivity.class));
                                startActivity(new Intent(Datastore.this,MainPage.class));
                            }
                            @Override
                            public void onVerificationFailed(FirebaseException e)
                            {
                                Toast.makeText(Datastore.this, "Verification Unsuccessfull"+e, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                super.onCodeSent(s, forceResendingToken);
                                Toast.makeText(Datastore.this, "OTP Send", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "Please Enter The Details", Toast.LENGTH_SHORT).show();
            }
        }
        if(v==profile_image)
        {
            profile_image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(intent,PICK_IMAGE_REQUEST);
                    pd1.setMessage("UPLOADING IMAGE.....");
                    image_selected=true;
                }
            });
        }
    }
    private boolean validate_phone_number(String phone_number)
    {
        if(TextUtils.isEmpty(phone_number))
        {
            phoneno.setError("Please Enter the Phone Number");
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean validate_image()
    {
        if(image_selected)
        {
            return true;
        }
        else
        {
            Toast.makeText(this, "Please select the image", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please tap on profile image to select", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean validate_name(String nm)
    {
        if(TextUtils.isEmpty(nm))
        {
            name.setError("Please Enter the name");
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validate_adhar_number(String adhar_number)
    {
        if(TextUtils.isEmpty(adhar_number) || adhar_number.length()>12 || adhar_number.length()<=11)
        {
            adharno.setError("Please Enter the Valid Adhar Number");
            return true;
        }
        else
        {
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK)
            {
                Uri uri=data.getData();
                profile_image.setImageURI(uri);
                pd1.show();
                if (uri == null)
                {
                    Toast.makeText(this, "Please select the image", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    user=mAuth.getCurrentUser();
                    StorageReference filepath = mStorageRef.child("profile_images").child(user.getEmail()).child("profile");
                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            try
                            {
                                String download=taskSnapshot.getDownloadUrl().toString().trim();
                                UserInformation userInfo=new UserInformation(download);
                                childUri.child(user.getUid()).setValue(userInfo);
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(Datastore.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            Toast.makeText(Datastore.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                            pd1.dismiss();
                            //startActivity(new Intent(Datastore.this,MainPage.class));
                        }
                    });
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,""+e,Toast.LENGTH_LONG).show();
        }
    }
}
