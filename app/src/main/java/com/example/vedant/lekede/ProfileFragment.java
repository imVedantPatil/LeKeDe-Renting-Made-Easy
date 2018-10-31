package com.example.vedant.lekede;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ProfileFragment extends Fragment
{
    private GoogleSignInClient mGoogleSignInClient;
    public StorageReference mStorageRef;
    public EditText email_id,phone_no;
    public ImageView option,profile;
    public Button location,logout,save;
    public View rootView;
    private ProgressDialog pd1;
    private static final int PICK_IMAGE_REQUEST=70;
    private FirebaseAuth mAuth;
    private TextView edit,user_name;
    private FirebaseUser user;
    DatabaseReference root_ref,child_ref,profile_root,profile_child,childUri,rootUri;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        //initiating component
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        user_name=rootView.findViewById(R.id.user_profile_name);
        email_id=(EditText) rootView.findViewById(R.id.Email_id);
        phone_no=(EditText) rootView.findViewById(R.id.PhoneNo);
        profile=rootView.findViewById(R.id.profile_image_user);
        //retriving data from firebase


        root_ref=FirebaseDatabase.getInstance().getReference();
        child_ref=root_ref.child("User-Details");
        child_ref.child(user.getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user_info=dataSnapshot.getValue(UserInformation.class);
                try {
                    user_name.setText(user_info.getName());
                    email_id.setText(user_info.getEmail());
                    phone_no.setText(user_info.getPhone());
                    Toast.makeText(getActivity(), "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "Error!!!!1,Data Unccessfully Fetched"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        user=mAuth.getCurrentUser();
        profile_root=FirebaseDatabase.getInstance().getReference();
        profile_child=profile_root.child("Uri_profile");
        profile_child.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    UserInformation user_uri = dataSnapshot.getValue(UserInformation.class);
                    if(user_uri==null) {
                        Toast.makeText(getActivity(), "Profile image not found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String uri = user_uri.getUri();
                        Toast.makeText(getActivity(), "" + uri, Toast.LENGTH_LONG).show();
                        Context c = getActivity().getApplicationContext();
                        Picasso.with(c).load(uri).fit().into(profile);
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        location=(Button)rootView.findViewById(R.id.set_location);
        logout=(Button)rootView.findViewById(R.id.log_out_account);
        location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    startActivity(new Intent(getActivity(),MapsActivity.class));
            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference();
        pd1=new ProgressDialog(getActivity());

        edit=rootView.findViewById(R.id.edit_details);
        edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {

                Toast.makeText(getActivity(), "Double tap on photo to change it", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),"Please Enter The Details",Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater)
                        getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.pop_window, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                save=popupView.findViewById(R.id.save_details);
                save.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        email_id=popupView.findViewById(R.id.edit_emailid);
                        String email=email_id.getText().toString();
                        Toast.makeText(getActivity(), email+"", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
                // dismiss the popup window when touched

            }
        });

        profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
                pd1.setMessage("UPLOADING IMAGE.....");
            }
        });

        email_id.setText("vedant.patil@viit.ac.in");
        phone_no.setText("0123456789");
        email_id.setFocusable(false);
        email_id.setCursorVisible(false);
        email_id.setClickable(false);
        email_id.setLongClickable(false);
        email_id.setSelected(false);
        email_id.setKeyListener(null);

        phone_no.setFocusable(false);
        phone_no.setCursorVisible(false);
        phone_no.setClickable(false);
        phone_no.setLongClickable(false);
        phone_no.setSelected(false);
        phone_no.setKeyListener(null);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
               mGoogleSignInClient= GoogleSignIn.getClient(getActivity(),gso);

                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getActivity(),"LOGED OUT SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(),MainActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"ERROR.....",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        option=(ImageView) rootView.findViewById(R.id.option_menu);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                PopupMenu popup = new PopupMenu(getActivity(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.menu_help:

                                return true;

                            case R.id.menu_feedack:

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.inflate(R.menu.item_menu);
                popup.show();
            }
        });

        return rootView;

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK)
            {
                Uri uri=data.getData();
                profile.setImageURI(uri);
                pd1.show();
                if (uri == null)
                {
                    Toast.makeText(getActivity(), "Please select the image", Toast.LENGTH_SHORT).show();
                } else
                {
                    user=mAuth.getCurrentUser();
                    StorageReference filepath = mStorageRef.child("profile_images").child(user.getEmail()).child("profile");
                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            try
                            {
                                rootUri=FirebaseDatabase.getInstance().getReference();
                                childUri=rootUri.child("Uri_profile");
                                String download=taskSnapshot.getDownloadUrl().toString().trim();
                                UserInformation userInfo=new UserInformation(download);
                                childUri.child(user.getUid()).setValue(userInfo);
                                Toast.makeText(getActivity(), "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                                pd1.dismiss();
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(),""+e,Toast.LENGTH_LONG).show();
        }
    }


}
