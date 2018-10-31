package com.example.vedant.lekede;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerviewAdapterFurniture extends RecyclerView.Adapter<RecyclerviewAdapterFurniture.MyViewHolder>
{
    private Context mContext;
    private List<ProductInfoFurniture> mData;
    private Context context;

    public RecyclerviewAdapterFurniture(Context mContext, List<ProductInfoFurniture> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerviewAdapterFurniture.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflator=LayoutInflater.from(mContext);
        view=mInflator.inflate(R.layout.cardview_item_furniture,viewGroup,false);
        context=viewGroup.getContext();
        return new RecyclerviewAdapterFurniture.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerviewAdapterFurniture.MyViewHolder myViewHolder, final int i)
    {
        myViewHolder.productCompany.setText("₹".concat(mData.get(i).getRent_price()));
        Picasso.with(context).load(mData.get(i).getUri()).fit().into(myViewHolder.productimage);
        myViewHolder.cardFur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Product_furniture_content.class);
                intent.putExtra("rent_price",mData.get(i).getRent_price());
                intent.putExtra("deposit_price",mData.get(i).getDeposit_price());
                intent.putExtra("timespan",mData.get(i).getDurationTime());
                intent.putExtra("description",mData.get(i).getDescriptionText());
                intent.putExtra("user_name",mData.get(i).getUser_name());
                intent.putExtra("email",mData.get(i).getEmail_id());
                intent.putExtra("phone",mData.get(i).getPhone_no());
                intent.putExtra("uri",mData.get(i).getUri());
                mContext.startActivity(intent);
            }
        });
        myViewHolder.cardFur.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder delete = new AlertDialog.Builder(mContext);
                delete.setCancelable(false);
                delete.setTitle("Do you wan't to delete the post");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myViewHolder.childref.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                Toast.makeText(mContext, "Post deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                delete.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        DatabaseReference rootref,childref;
        ImageView productimage;
        TextView productCompany;
        CardView cardFur;
        FirebaseAuth mAuth;
        FirebaseUser user;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            productimage=itemView.findViewById(R.id.imageview_product_image);
            productCompany=itemView.findViewById(R.id.textview_product_name);
            cardFur=itemView.findViewById(R.id.cardview_id_furniture);
            mAuth=FirebaseAuth.getInstance();
            user=mAuth.getCurrentUser();
            rootref = FirebaseDatabase.getInstance().getReference();
            childref = rootref.child("Product-Details").child("Book").child(user.getUid());
        }
    }
}

