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

public class Product_car_content extends AppCompatActivity {

    protected String sub_category,company_name, rent_price, deposit_ammount, duration, description, image_uri,user_nm,mono,emailadd,subText;
    protected ImageView product_image;
    public Button call,email;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    protected EditText company,rent,deposit,timespan,descript,name;
    TextView subcat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_car_content);

        subcat=findViewById(R.id.sub);
        company=findViewById(R.id.content_car_name);
        rent=findViewById(R.id.content_car_rent);
        deposit=findViewById(R.id.content_car_deposit);
        timespan=findViewById(R.id.content_car_duration);
        descript=findViewById(R.id.content_car_description);
        name=findViewById(R.id.content_car_user_name);
        call=findViewById(R.id.call);
        email=findViewById(R.id.email);

        product_image=findViewById(R.id.content_carmobilo_image);

        sub_category=getIntent().getExtras().getString("sub_category");
        company_name=getIntent().getExtras().getString("company");
        rent_price=getIntent().getExtras().getString("rent_price");
        deposit_ammount=getIntent().getExtras().getString("deposit_price");
        duration=getIntent().getExtras().getString("timespan");
        description=getIntent().getExtras().getString("description");
        image_uri=getIntent().getExtras().getString("uri");
        user_nm=getIntent().getExtras().getString("user_name");
        mono=getIntent().getExtras().getString("phone_no");
        emailadd=getIntent().getExtras().getString("email");

        subText=sub_category.concat(" Decription");
        subcat.setText(subText);
        company.setText(company_name);
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
                if (ActivityCompat.checkSelfPermission(Product_car_content.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Product_car_content.this,new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    Toast.makeText(Product_car_content.this, "Please again click on call button", Toast.LENGTH_SHORT).show();
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
                    Intent mailClient = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto",emailadd,null));
                    mailClient.putExtra(Intent.EXTRA_SUBJECT, "About your add on LeKeDe")
                            .putExtra(Intent.EXTRA_TEXT, "hello,This is a message sent from LeKeDe app ");
                    startActivity(Intent.createChooser(mailClient,"complete action using....."));
                }
                catch (Exception e)
                {
                    Log.w("hi",e.getMessage());
                }
            }
        });

    }
}
