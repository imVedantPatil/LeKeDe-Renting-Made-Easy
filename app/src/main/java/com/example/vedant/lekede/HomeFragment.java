package com.example.vedant.lekede;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment
{
    private ProgressDialog loading;
    FirebaseAuth mAuth;
    DatabaseReference rootref=null,childref=null;
    List<ProductInfoBook> list;
    List<ProductInfoCar> listMo;
    List<ProductInfoCar> listTv,listCar;
    List<ProductInfoFurniture> listFur,listOther;
    List<ProductInfoFashion> listFashion,listSport;
    private FirebaseUser user;
    String book_name, rent_price, deposit_ammount, duration, description,image_uri;
    private String company,uri;
    public View rootView;
    RecyclerView myrv;
    private Snackbar error;
    private ImageView book,car,fashion,furniture,other,sports,mobile,electronics;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView=inflater.inflate(R.layout.fragment_home,container,false);

        book=rootView.findViewById(R.id.select_card_book);
        car=rootView.findViewById(R.id.select_card_car);
        fashion=rootView.findViewById(R.id.select_card_fashion);
        furniture=rootView.findViewById(R.id.select_card_furniture);
        other=rootView.findViewById(R.id.select_card_other);
        sports=rootView.findViewById(R.id.select_card_sports);
        mobile=rootView.findViewById(R.id.select_card_mobile);
        electronics=rootView.findViewById(R.id.select_card_electronics);

        error=Snackbar.make(getActivity().findViewById(android.R.id.content),"Nothing to display",Snackbar.LENGTH_LONG);
        loading=new ProgressDialog(getActivity());
        loading.setTitle("Please wait.....");
        loading.setMessage("loading.....");
        loading.show();
        productBook();
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productBook();
            }
        });
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productCar();
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productFashion();
            }
        });
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productFur();
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productMobile();
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productOther();
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productSport();
            }
        });
        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                productTv();
            }
        });

        myrv=rootView.findViewById(R.id.recyclerview_id);
        return rootView;
    }
    public void setRecycleBook()
    {
        loading.dismiss();
        RecyclerviewAdapterBook myAdapter=new RecyclerviewAdapterBook(getContext(),list);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);
    }
    public void setRecycleMobile()
    {
        loading.dismiss();
        RecyclerviewAdapterMobile myAdapter=new RecyclerviewAdapterMobile(getContext(),listMo);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);
    }
    public void setRecycleTv()
    {
        loading.dismiss();
        RecyclerviewAdapterMobile myAdapter=new RecyclerviewAdapterMobile(getContext(),listTv);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);
    }
    public void setRecycleCar()
    {
        loading.dismiss();
        RecyclerviewAdapterMobile myAdapter=new RecyclerviewAdapterMobile(getContext(),listCar);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);
    }
    public void setRecycleFur()
    {
        loading.dismiss();
        RecyclerviewAdapterFurniture myAdapter=new RecyclerviewAdapterFurniture(getContext(),listFur);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myrv.setAdapter(myAdapter);
    }
    public void setRecycleOther()
    {
        if(listOther==null)
        {
            loading.dismiss();
        }
        else {
            loading.dismiss();
            RecyclerviewAdapterFurniture myAdapter = new RecyclerviewAdapterFurniture(getContext(), listOther);
            myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            myrv.setAdapter(myAdapter);
        }
    }
    public void setRecycleFashion()
    {
        loading.dismiss();
        RecycleviewAdapterFashion myAdapter=new RecycleviewAdapterFashion(getContext(),listFashion);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),1));
        myrv.setAdapter(myAdapter);
    }
    public void setRecycleSport()
    {

        loading.dismiss();
        RecycleviewAdapterFashion myAdapter=new RecycleviewAdapterFashion(getContext(),listSport);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(),1));
        myrv.setAdapter(myAdapter);
    }
    public void productBook()
    {
        list=new ArrayList<ProductInfoBook>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("Book");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoBook values = dataSnapshot1.getValue(ProductInfoBook.class);
                        ProductInfoBook set = new ProductInfoBook();
                        book_name=values.getBook_name();
                        image_uri=values.getImage_uri();
                        deposit_ammount=values.getDeposit_ammount();
                        description=values.getDescription();
                        rent_price=values.getRent_price();
                        duration=values.getDuration();
                        set.setBook_name(book_name);
                        set.setImage_uri(image_uri);
                        set.setDeposit_ammount(deposit_ammount);
                        set.setDescription(description);
                        set.setRent_price(rent_price);
                        set.setDuration(duration);
                        set.setEmail_id(values.getEmail_id());
                        set.setPhone_no(values.getPhone_no());
                        set.setUser_name(values.getUser_name());
                        list.add(set);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        setRecycleBook();

                    } catch (Exception e)
                    {
                        System.out.print(e.getMessage());
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void productMobile()
    {
        listMo=new ArrayList<ProductInfoCar>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("Mobile");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoCar values = dataSnapshot1.getValue(ProductInfoCar.class);
                        ProductInfoCar setMo = new ProductInfoCar();
                        company=values.getCompany();
                        uri=values.getUri();
                        setMo.setUri(uri);
                        setMo.setDeposit_price(values.getDeposit_price());
                        setMo.setDescriptionText(values.getDescriptionText());
                        setMo.setDurationTime(values.getDurationTime());
                        setMo.setRent_price(values.getRent_price());
                        setMo.setSub_category(values.getSub_category());
                        setMo.setEmail_id(values.getEmail_id());
                        setMo.setPhone_no(values.getPhone_no());
                        setMo.setUser_name(values.getUser_name());
                        setMo.setCompany(company);
                        listMo.add(setMo);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        setRecycleMobile();

                    } catch (Exception e) {
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void productCar()
    {
        listCar=new ArrayList<ProductInfoCar>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("Car");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoCar values = dataSnapshot1.getValue(ProductInfoCar.class);
                        ProductInfoCar setCar = new ProductInfoCar();
                        company=values.getCompany();
                        uri=values.getUri();
                        setCar.setUri(uri);
                        setCar.setDeposit_price(values.getDeposit_price());
                        setCar.setDescriptionText(values.getDescriptionText());
                        setCar.setDurationTime(values.getDurationTime());
                        setCar.setRent_price(values.getRent_price());
                        setCar.setSub_category(values.getSub_category());
                        setCar.setEmail_id(values.getEmail_id());
                        setCar.setPhone_no(values.getPhone_no());
                        setCar.setUser_name(values.getUser_name());
                        setCar.setCompany(company);
                        listCar.add(setCar);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        setRecycleCar();

                    } catch (Exception e) {
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void productTv()
    {
        listTv=new ArrayList<ProductInfoCar>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("TV_Appliances");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoCar values = dataSnapshot1.getValue(ProductInfoCar.class);
                        ProductInfoCar setTv = new ProductInfoCar();
                        company=values.getCompany();
                        uri=values.getUri();
                        setTv.setDeposit_price(values.getDeposit_price());
                        setTv.setDescriptionText(values.getDescriptionText());
                        setTv.setDurationTime(values.getDurationTime());
                        setTv.setRent_price(values.getRent_price());
                        setTv.setSub_category(values.getSub_category());
                        setTv.setEmail_id(values.getEmail_id());
                        setTv.setPhone_no(values.getPhone_no());
                        setTv.setUser_name(values.getUser_name());
                        setTv.setCompany(company);
                        setTv.setUri(uri);
                        listTv.add(setTv);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), ""+values.getUri(), Toast.LENGTH_SHORT).show();
                        setRecycleTv();

                    } catch (Exception e)
                    {
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void productFur()
    {
        listFur=new ArrayList<ProductInfoFurniture>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("Furniture");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoFurniture values = dataSnapshot1.getValue(ProductInfoFurniture.class);
                        ProductInfoFurniture setCar = new ProductInfoFurniture();
                        uri=values.getUri();
                        setCar.setUri(uri);
                        setCar.setDeposit_price(values.getDeposit_price());
                        setCar.setDescriptionText(values.getDescriptionText());
                        setCar.setDurationTime(values.getDurationTime());
                        setCar.setRent_price(values.getRent_price());
                        setCar.setUser_name(values.getUser_name());
                        setCar.setPhone_no(values.getPhone_no());
                        setCar.setEmail_id(values.getEmail_id());
                        listFur.add(setCar);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        setRecycleFur();

                    } catch (Exception e) {
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void productOther()
    {
        listOther=new ArrayList<ProductInfoFurniture>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("Other");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoFurniture values = dataSnapshot1.getValue(ProductInfoFurniture.class);
                        ProductInfoFurniture setCar = new ProductInfoFurniture();
                        uri=values.getUri();
                        setCar.setUri(uri);
                        setCar.setDeposit_price(values.getDeposit_price());
                        setCar.setDescriptionText(values.getDescriptionText());
                        setCar.setDurationTime(values.getDurationTime());
                        setCar.setRent_price(values.getRent_price());
                        setCar.setEmail_id(values.getEmail_id());
                        setCar.setPhone_no(values.getPhone_no());
                        setCar.setUser_name(values.getUser_name());
                        listOther.add(setCar);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        setRecycleOther();

                    } catch (Exception e) {
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void productFashion()
    {
        listFashion=new ArrayList<ProductInfoFashion>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("Fashion");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoFashion values = dataSnapshot1.getValue(ProductInfoFashion.class);
                        ProductInfoFashion setCar = new ProductInfoFashion();
                        uri=values.getUri();
                        setCar.setUri(uri);
                        setCar.setDeposit_price(values.getDeposit_price());
                        setCar.setDescription(values.getDescription());
                        setCar.setDurationTime(values.getDurationTime());
                        setCar.setRent_price(values.getRent_price());
                        setCar.setEmail(values.getEmail());
                        setCar.setUser_name(values.getUser_name());
                        setCar.setPhone_no(values.getPhone_no());
                        setCar.setSub_category(values.getSub_category());
                        listFashion.add(setCar);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        setRecycleFashion();

                    } catch (Exception e) {
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void productSport()
    {
        listSport=new ArrayList<ProductInfoFashion>();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        childref=rootref.child("Product-Details").child("Sports");
        childref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    try {
                        ProductInfoFashion values = dataSnapshot1.getValue(ProductInfoFashion.class);
                        ProductInfoFashion setCar = new ProductInfoFashion();
                        uri=values.getUri();
                        setCar.setUri(uri);
                        setCar.setDeposit_price(values.getDeposit_price());
                        setCar.setDescription(values.getDescription());
                        setCar.setDurationTime(values.getDurationTime());
                        setCar.setRent_price(values.getRent_price());
                        setCar.setEmail(values.getEmail());
                        setCar.setUser_name(values.getUser_name());
                        setCar.setPhone_no(values.getPhone_no());
                        setCar.setSub_category(values.getSub_category());
                        listSport.add(setCar);
                        Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        setRecycleSport();

                    } catch (Exception e) {
                        Log.w("hello", e.getMessage());
                        loading.dismiss();
                        error.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
