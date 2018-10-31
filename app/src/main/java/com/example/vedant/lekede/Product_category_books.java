package com.example.vedant.lekede;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Product_category_books extends AppCompatActivity {

    private Spinner duration;
    private EditText rent,deposit,timespan,description,title;
    public String rentText,depositText,timespanText,descriptionText,dayText,bookTitle,uri,name,email,phone_number;
    private FirebaseAuth mAuth;
    public DatabaseReference rootref,childref;
    private FirebaseUser user;
    private ImageView product;
    public Button postad;
    DatabaseReference root_ref,child_ref;

    private StorageReference imgrootref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_books);
        duration=findViewById(R.id.duration);
        ArrayAdapter<CharSequence> duration_adapter=ArrayAdapter.createFromResource(this,R.array.timespan,android.R.layout.simple_spinner_item);
        duration_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        duration.setAdapter(duration_adapter);

        mAuth=FirebaseAuth.getInstance();
        rent=findViewById(R.id.get_rent_ammount);
        deposit=findViewById(R.id.get_deposit);
        timespan=findViewById(R.id.get_timespan);
        description=findViewById(R.id.description);
        title=findViewById(R.id.get_book);
       // getData();

        postad=findViewById(R.id.post_add);

        uri=getIntent().getExtras().getString("uri");
        postad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        product=findViewById(R.id.product_image);
        uri=getIntent().getExtras().getString("uri");
        Context c=Product_category_books.this.getApplicationContext();
        Picasso.with(c).load(uri).fit().into(product);

        root_ref=FirebaseDatabase.getInstance().getReference();
        child_ref=root_ref.child("User-Details");
        user=mAuth.getCurrentUser();
        child_ref.child(user.getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user_info=dataSnapshot.getValue(UserInformation.class);
                try {
                    name=user_info.getName();
                    email=user_info.getEmail();
                    phone_number=user_info.getPhone();
                    Toast.makeText(Product_category_books.this, "User Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(Product_category_books.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(Product_category_books.this, "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getData()
    {

        bookTitle=title.getText().toString().trim();
        rentText=rent.getText().toString().trim();
        depositText=deposit.getText().toString().trim();
        timespanText=timespan.getText().toString().trim();
        dayText=duration.getSelectedItem().toString().trim();

        descriptionText=description.getText().toString().trim();

        if(!checkEmptyBookTitle(bookTitle) && !checkEmptyRent(rentText) && !checkEmptyDeposit(depositText) && !checkEmptyTimespan(timespanText,dayText) && descriptionText.isEmpty())
        {
            String durationText=timespanText.concat(dayText);
            ProductInfoBook product=new ProductInfoBook(bookTitle,rentText,depositText,durationText,null,uri,name,phone_number,email);
            user=mAuth.getCurrentUser();
            rootref= FirebaseDatabase.getInstance().getReference();
            childref=rootref.child("Product-Details");
            childref.child("Book").child(user.getUid()).setValue(product);
          //  childref.child("Book").setValue(product);
            finish();
            startActivity(new Intent(Product_category_books.this,Addpost_finish.class));
        }
        else if(!checkEmptyBookTitle(bookTitle) && !checkEmptyRent(rentText) && !checkEmptyDeposit(depositText) && !checkEmptyTimespan(timespanText,dayText) && !descriptionText.isEmpty())
        {
            String durationText=timespanText.concat(dayText);
            ProductInfoBook product=new ProductInfoBook(bookTitle,rentText,depositText,durationText,descriptionText,uri,name,phone_number,email);
            user=mAuth.getCurrentUser();
            rootref= FirebaseDatabase.getInstance().getReference();
            childref=rootref.child("Product-Details");
            childref.child("Book").child(user.getUid()).setValue(product);
            finish();
            startActivity(new Intent(Product_category_books.this,Addpost_finish.class));
        }
        else
        {
            Toast.makeText(this, "Error 403", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkEmptyBookTitle(String bookTitle)
    {
        if (bookTitle.isEmpty())
        {
            title.setError("Please enter the book title");
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean checkEmptyRent(String rentText)
    {
        if (rentText.isEmpty())
        {
            rent.setError("Please Enter the rent price");
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean checkEmptyDeposit(String depositText)
    {
        if (depositText.isEmpty())
        {
            deposit.setError("Please Enter the deposit ammount");
            return true;
        }
        else
        {
            return false;
        }
     }
    private boolean checkEmptyTimespan(String timespanText,String dayText)
    {
        if (timespanText.isEmpty() || dayText.isEmpty())
        {
            timespan.setError("Please enter the valid duration");
            return true;
        }
        else
        {
            return false;
        }
    }
}
