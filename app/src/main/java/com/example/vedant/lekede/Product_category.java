package com.example.vedant.lekede;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Product_category extends AppCompatActivity implements View.OnClickListener{

    private Button mobile,tv,furniture,sports,fashion,car,book,other;
    private Snackbar snackbar;
    byte[] bitmap_image;
    StorageReference imgrootref;
    FirebaseUser current_user;
    FirebaseAuth mAuth;
    String uri;
    private ProgressDialog pd_upload;
    private FloatingActionButton fab_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        mAuth=FirebaseAuth.getInstance();
        current_user=mAuth.getCurrentUser();

        mobile=findViewById(R.id.mobile_computer);
        tv=findViewById(R.id.tv_electronics);
        furniture=findViewById(R.id.home_furniture);
        sports=findViewById(R.id.Sports);
        fashion=findViewById(R.id.fashion);
        car=findViewById(R.id.car_industrial);
        book=findViewById(R.id.books);
        other=findViewById(R.id.other_item);


        mobile.setOnClickListener(this);
        tv.setOnClickListener(this);
        furniture.setOnClickListener(this);
        sports.setOnClickListener(this);
        fashion.setOnClickListener(this);
        car.setOnClickListener(this);
        book.setOnClickListener(this);
        other.setOnClickListener(this);


        snackbar = Snackbar.make(findViewById(android.R.id.content), "Add the product details", Snackbar.LENGTH_LONG);
        bitmap_image=getIntent().getExtras().getByteArray("bitmap_image");

        pd_upload=new ProgressDialog(this);
        pd_upload.setTitle("Please Wait.....");
        pd_upload.setMessage("Uploading product image.....");

        fab_cancel=findViewById(R.id.cancel_post_category);

        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alert=new android.app.AlertDialog.Builder(getApplicationContext());
                alert.setCancelable(false);
                alert.setTitle("Do you wan't cancel");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Product_category.this,MainPage.class));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        snackbar.setText("Please countinue the process");
                        snackbar.show();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public void onClick(View v)
    {

        if(v==mobile)
        {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("Mobile").child("product");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_mobile.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==tv)
        {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("TV_Appliances").child("product");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_tv.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==furniture) {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("Furniture").child("product");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_furniture.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==sports)
        {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("Sports").child("product");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_sports.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==fashion)
        {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("Fashion").child("product");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_fashion.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==car)
        {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("Car").child("product_car");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_car.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==book)
        {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("Book").child("product_book");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_books.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==other)
        {
            snackbar.show();
            pd_upload.show();
            byte[] bitmap_data=getIntent().getExtras().getByteArray("bitmap_image");
            imgrootref= FirebaseStorage.getInstance().getReference();
            StorageReference filepath = imgrootref.child("product_images").child(current_user.getUid()).child("Other").child("product");
            filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try
                    {
                        uri=taskSnapshot.getDownloadUrl().toString().trim();
                        pd_upload.dismiss();
                        finish();
                        Intent intent=new Intent(Product_category.this,Product_category_other.class);
                        intent.putExtra("uri",uri);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Product_category.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Product_category.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
