package com.example.vedant.lekede;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Product_category_tv extends AppCompatActivity
{
    private Spinner category,company,duration;
    private EditText rent,deposit,timespan,description;
    public String categoryText,companyText,rentText,depositText,timespanText,descriptionText,dayText,uri,name,email,phone_number;
    private Snackbar error;
    private FirebaseAuth mAuth;
    protected DatabaseReference rootref;
    protected DatabaseReference childref;
    protected FirebaseUser user;
    protected Button postad;
    ImageView product;
    protected void onStart()
    {
        super.onStart();
        product=findViewById(R.id.product_image);
        uri=getIntent().getExtras().getString("uri");
        Context c=this.getApplicationContext();
        Picasso.with(c).load(uri).fit().into(product);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_tv);
        mAuth=FirebaseAuth.getInstance();
        category=findViewById(R.id.select_category);
        ArrayAdapter<CharSequence> category_adaptor=ArrayAdapter.createFromResource(this,R.array.sub_category_tv,android.R.layout.simple_spinner_item);
        category_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(category_adaptor);

        company=findViewById(R.id.select_company);

        String[] setItem=new String[]{"Nothing To select"};
        ArrayAdapter<String> mo_acc=new ArrayAdapter<String>(Product_category_tv.this,android.R.layout.simple_spinner_dropdown_item,setItem);
        company.setAdapter(mo_acc);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedText=category.getSelectedItem().toString().trim();
                checkSelected(selectedText);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(Product_category_tv.this, "Please Select the company", Toast.LENGTH_SHORT).show();
            }
        });

        duration=findViewById(R.id.duration);
        ArrayAdapter<CharSequence> duration_adapter=ArrayAdapter.createFromResource(this,R.array.timespan,android.R.layout.simple_spinner_item);
        duration_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;
        duration.setAdapter(duration_adapter);

        rent=findViewById(R.id.get_rent_ammount);
        deposit=findViewById(R.id.get_deposit);
        timespan=findViewById(R.id.get_timespan);
        description=findViewById(R.id.description);


        postad=findViewById(R.id.post_add);
        postad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        rootref=FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("User-Details");
        user=mAuth.getCurrentUser();
        childref.child(user.getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user_info=dataSnapshot.getValue(UserInformation.class);
                try {
                    name=user_info.getName();
                    email=user_info.getEmail();
                    phone_number=user_info.getPhone();
                    Toast.makeText(Product_category_tv.this, "User Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(Product_category_tv.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(Product_category_tv.this, "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData()
    {
        categoryText=category.getSelectedItem().toString().trim();
        companyText=company.getSelectedItem().toString().trim();
        rentText=rent.getText().toString().trim();
        depositText=deposit.getText().toString().trim();
        timespanText=timespan.getText().toString().trim();
        dayText=duration.getSelectedItem().toString().trim();
        uri=getIntent().getExtras().getString("uri");
        descriptionText=description.getText().toString().trim();

        if((!checkEmptyCategory(categoryText)) && (!checkEmptyCompany(companyText)) && (!checkEmptyRent(rentText)) && (!checkEmptyDeposit(depositText)) && (descriptionText.isEmpty()) && (!checkEmptyTimespan(timespanText,dayText)))
        {
            String durationText=timespanText.concat(dayText);
            ProductInfoCar product=new ProductInfoCar(categoryText,companyText,rentText,depositText,durationText,null,uri,name,phone_number,email);
            user=mAuth.getCurrentUser();
            rootref= FirebaseDatabase.getInstance().getReference();
            childref=rootref.child("Product-Details");
            childref.child("TV_Appliances").child(user.getUid()).setValue(product);
            finish();
            startActivity(new Intent(Product_category_tv.this,Addpost_finish.class));

        }
        else if((!checkEmptyCategory(categoryText)) && (!checkEmptyCompany(companyText)) && (!checkEmptyRent(rentText)) && (!checkEmptyDeposit(depositText)) && (!descriptionText.isEmpty()) && (!checkEmptyTimespan(timespanText,dayText)))
        {
            String durationText=timespanText.concat(dayText);
            UserInformation product=new UserInformation(categoryText,companyText,rentText,depositText,durationText,descriptionText,uri,name,phone_number,email);
            user=mAuth.getCurrentUser();
            rootref= FirebaseDatabase.getInstance().getReference();
            childref=rootref.child("Product-Details");
            childref.child("TV_Appliances").child(user.getUid()).setValue(product);
            finish();
            startActivity(new Intent(Product_category_tv.this,Addpost_finish.class));

        }
        else
        {
            Toast.makeText(this, "Error 403", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkEmptyCategory(String categoryText)
    {
        if(categoryText.equals("Click Me to select"))
        {
            error= Snackbar.make(findViewById(android.R.id.content),"Please select the sub category",Snackbar.LENGTH_SHORT);
            error.show();
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean checkEmptyCompany(String categoryText)
    {
        if(categoryText.equals("Click Me to select"))
        {
            error=Snackbar.make(findViewById(android.R.id.content),"Please select the company",Snackbar.LENGTH_SHORT);
            error.show();
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

    private void checkSelected(String selectedText)
    {
        if(selectedText.equals("Click Me to select"))
        {
            Toast.makeText(this, "Please Select The Company", Toast.LENGTH_SHORT).show();
        }
        if(selectedText.equals("Televisions"))
        {
            ArrayAdapter<CharSequence> mobile_adaptor=ArrayAdapter.createFromResource(this,R.array.company_tv,android.R.layout.simple_spinner_item);
            mobile_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            company.setAdapter(mobile_adaptor);
        }
        if(selectedText.equals("Washing Manchines"))
        {
            ArrayAdapter<CharSequence> computer_adaptor=ArrayAdapter.createFromResource(this,R.array.company_washing,android.R.layout.simple_spinner_item);
            computer_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            company.setAdapter(computer_adaptor);
        }
        if(selectedText.equals("Microwave Ovens"))
        {
            ArrayAdapter<CharSequence> computer_adaptor=ArrayAdapter.createFromResource(this,R.array.company_microwave_owens,android.R.layout.simple_spinner_item);
            computer_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            company.setAdapter(computer_adaptor);
        }
        if(selectedText.equals("Water Purifiers"))
        {
            ArrayAdapter<CharSequence> computer_adaptor=ArrayAdapter.createFromResource(this,R.array.company_water,android.R.layout.simple_spinner_item);
            computer_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            company.setAdapter(computer_adaptor);
        }
        if(selectedText.equals("Air Conditioners"))
        {
            ArrayAdapter<CharSequence> computer_adaptor=ArrayAdapter.createFromResource(this,R.array.company_air,android.R.layout.simple_spinner_item);
            computer_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            company.setAdapter(computer_adaptor);
        }
        if(selectedText.equals("Chimneys") || selectedText.equals("Dish Washers") || selectedText.equals("Others") || selectedText.equals("Kitchen Appliances") || selectedText.equals("Small Home Appliances"))
        {
            String[] setItem=new String[]{"Nothing To select"};
            ArrayAdapter<String> mo_acc=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,setItem);
            company.setAdapter(mo_acc);
        }
    }

}
