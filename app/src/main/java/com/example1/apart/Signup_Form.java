package com.example1.apart;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Thread.sleep;


public class Signup_Form extends AppCompatActivity {
    EditText txtName,txtEmail, txtVehicle_type, txtVehicle_number, txtPassword, txtConfirm_Password;
    EditText editTextEmail;
    String editTextEmail1;
    String editTextSubject;
    String editTextMessage;
    Button btn_register;
    DatabaseReference databaseReference;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    int maxid=0;


    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);
        getSupportActionBar().setTitle("Signup Form");

        txtName = (EditText) findViewById(R.id.Name);
        txtEmail = (EditText) findViewById(R.id.Email);
        txtVehicle_type = (EditText) findViewById(R.id.Vehicle_type);
        txtVehicle_number = (EditText) findViewById(R.id.Vehicle_Number);
        txtPassword = (EditText) findViewById(R.id.Password);
        //txtConfirm_Password = (EditText) findViewById(R.id.Confirm_Password);
        btn_register = (Button) findViewById(R.id.Register);
        ref=FirebaseDatabase.getInstance().getReference().child("APART/USER_DETAILS");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = (int) dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = txtName.getText().toString();
                final String Email = txtEmail.getText().toString();
                final String Vehicle_type = txtVehicle_type.getText().toString();
                final String Vehicle_Number = txtVehicle_number.getText().toString();
                String Password = txtPassword.getText().toString();
                String email1=Email;
                sendEmail(email1);





                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(Signup_Form.this, "Please enter name", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(Signup_Form.this, "Please enter email", Toast.LENGTH_SHORT).show();

                }

                if (TextUtils.isEmpty(Vehicle_type)) {
                    Toast.makeText(Signup_Form.this, "Please enter type of the vehicle", Toast.LENGTH_SHORT).show();

                }

                if (TextUtils.isEmpty(Vehicle_Number)) {
                    Toast.makeText(Signup_Form.this, "Please enter vehicle number", Toast.LENGTH_SHORT).show();

                }

                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Signup_Form.this, "Please enter password", Toast.LENGTH_SHORT).show();

                }

                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(Signup_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    send information = new send(
                                            Name,
                                            Email,
                                            Vehicle_type,
                                            Vehicle_Number
                                    );

                                    ref.child(String.valueOf(maxid+1)).child("/user details")

                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {


                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(Signup_Form.this, "Registration complete", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                   ref.child(String.valueOf(maxid+1)).child("/ID")
                                            .setValue(maxid+1001);
                                }

                            };



                        });


            }


            protected void sendEmail(String email1){

                int neww=maxid+1001;
                Log.i("Send Email", "sameerafatima333@gmail.com");
                    String [] TO ={email1};
                String maxid455 = new Integer(neww).toString();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
                emailIntent.putExtra(Intent.EXTRA_TEXT,maxid455);
                Toast.makeText(Signup_Form.this,"done sending",Toast.LENGTH_SHORT).show();
                try{
                    startActivity(Intent.createChooser(emailIntent,"Send mail...."));
                    sleep(1000);
                    // finish();
                    Log.i("Finished sending mail","");

                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(Signup_Form.this,"There is no email client installed",Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });


    }

}

