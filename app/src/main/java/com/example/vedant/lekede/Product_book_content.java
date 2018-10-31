package com.example.vedant.lekede;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Product_book_content extends AppCompatActivity {

    protected String book_name, rent_price, deposit_ammount, duration, description, image_uri,user_nm,mono,emailadd;
    protected ImageView product_image;
    protected TextView setbookname,setbookrent,setbookdeposit,setbookduration,setbookdescription,user_name;
    private Button call,email;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_book_content);

        product_image=findViewById(R.id.content_book_image);
        setbookname=findViewById(R.id.content_book_name);
        setbookdeposit=findViewById(R.id.content_book_deposit);
        setbookdescription=findViewById(R.id.content_book_description);
        setbookrent=findViewById(R.id.content_book_rent);
        setbookduration=findViewById(R.id.content_book_duration);
        call=findViewById(R.id.call);
        email=findViewById(R.id.email);
        user_name=findViewById(R.id.content_book_user_name);


        book_name=getIntent().getExtras().getString("title");
        rent_price=getIntent().getExtras().getString("rent_ammount");
        deposit_ammount=getIntent().getExtras().getString("deposit_ammount");
        duration=getIntent().getExtras().getString("duration");
        description=getIntent().getExtras().getString("discription");
        image_uri=getIntent().getExtras().getString("uri");
        user_nm=getIntent().getExtras().getString("user_name");
        mono=getIntent().getExtras().getString("phone_no");
        emailadd=getIntent().getExtras().getString("email");


        Picasso.with(getApplicationContext()).load(image_uri).fit().into(product_image);
        setbookduration.setText(duration);
        setbookrent.setText(rent_price);
        if(description==null)
        {
            setbookdescription.setText("Description is not given by owner");
        }
        else
        {
            setbookdescription.setText(description);
        }
        setbookdeposit.setText(deposit_ammount);
        setbookname.setText(book_name);
        user_name.setText(user_nm);
        call.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Product_book_content.this,Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Product_book_content.this,new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    Toast.makeText(Product_book_content.this, "Please again click on call button", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent call_intent=new Intent(Intent.ACTION_CALL);
                    call_intent.setData(Uri.parse("tel:"+mono));
                    startActivity(call_intent);
                }
            }
        });
        email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try {
                    Intent mailClient = new Intent(Intent.ACTION_SEND);
                    mailClient.setType("text/plain")
                            .setData(Uri.parse(emailadd))
                            .putExtra(Intent.EXTRA_SUBJECT, "About your add on LeKeDe")
                            .putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from LeKeDe app :-)")
                            .setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
                    startActivity(mailClient);
                }
                catch (Exception e)
                {
                    Log.w("hi",e.getMessage());
                }
            }
        });
    }
}
