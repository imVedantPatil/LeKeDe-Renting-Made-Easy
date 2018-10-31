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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Product_content_fashion extends AppCompatActivity {

    protected String sub_category, rent_price, deposit_ammount, duration, description, image_uri,user_nm,mono,emailadd,subText;
    protected ImageView product_image;
    public Button call,email;
    protected EditText rent,deposit,timespan,descript,name;
    TextView subcat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_content_fashion);

        subcat=findViewById(R.id.subcat);
        rent=findViewById(R.id.content_fashion_rent);
        deposit=findViewById(R.id.content_fashion_deposit);
        timespan=findViewById(R.id.content_fashion_duration);
        descript=findViewById(R.id.content_fashion_description);
        name=findViewById(R.id.content_fashion_user_name);
        call=findViewById(R.id.call);
        email=findViewById(R.id.email);

        product_image=findViewById(R.id.content_fashion_image);

        sub_category=getIntent().getExtras().getString("subcat");
        rent_price=getIntent().getExtras().getString("rent_price");
        deposit_ammount=getIntent().getExtras().getString("deposit_price");
        duration=getIntent().getExtras().getString("timespan");
        description=getIntent().getExtras().getString("description");
        image_uri=getIntent().getExtras().getString("uri");
        user_nm=getIntent().getExtras().getString("user_name");
        mono=getIntent().getExtras().getString("phone");
        emailadd=getIntent().getExtras().getString("email");

       subText=sub_category.concat(" Decription");
        subcat.setText(subText);
        rent.setText(rent_price);
        deposit.setText(deposit_ammount);
        timespan.setText(duration);
        descript.setText(description);
        name.setText(user_nm);
        Picasso.with(getApplicationContext()).load(image_uri).fit().into(product_image);

        call.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Product_content_fashion.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Product_content_fashion.this,new String[]{Manifest.permission.CALL_PHONE}, 1);
                    Toast.makeText(Product_content_fashion.this, "Please again click on call button", Toast.LENGTH_SHORT).show();
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
