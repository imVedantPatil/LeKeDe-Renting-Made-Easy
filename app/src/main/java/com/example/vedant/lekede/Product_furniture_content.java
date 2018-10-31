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

public class Product_furniture_content extends AppCompatActivity {

    protected String rent_price,deposit_price,timespan,description,user_name,phone_no,email_id,uri;
    protected TextView rent,deposit,duration,descript,name;
    private Button call,email;
    protected ImageView product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_furniture_content);

        rent_price=getIntent().getExtras().getString("rent_price");
        deposit_price=getIntent().getExtras().getString("deposit_price");
        timespan=getIntent().getExtras().getString("timespan");
        description=getIntent().getExtras().getString("description");
        user_name=getIntent().getExtras().getString("user_name");
        phone_no=getIntent().getExtras().getString("phone");
        email_id=getIntent().getExtras().getString("email");
        uri=getIntent().getExtras().getString("uri");

        rent=findViewById(R.id.content_fur_rent);
        deposit=findViewById(R.id.content_fur_deposit);
        duration=findViewById(R.id.content_fur_duration);
        descript=findViewById(R.id.content_fur_description);
        name=findViewById(R.id.content_fur_user_name);
        call=findViewById(R.id.call);
        email=findViewById(R.id.email);
        product=findViewById(R.id.content_furniture_image);

        rent.setText(rent_price);
        deposit.setText(deposit_price);
        duration.setText(timespan);
        if(description==null)
        {
            descript.setText("Description not given by owner");
        }
        else {
            descript.setText(description);
        }
        name.setText(user_name);

        Picasso.with(getApplicationContext()).load(uri).fit().into(product);
        call.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Product_furniture_content.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Product_furniture_content.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    Toast.makeText(Product_furniture_content.this, "Please again click on call button", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent call_intent=new Intent(Intent.ACTION_CALL);
                    call_intent.setData(Uri.parse("tel:"+phone_no));
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
                            .setData(Uri.parse(email_id))
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