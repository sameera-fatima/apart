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
    //EditText textTo,textMessage;
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

        //Initializing the views

        // editTextEmail = (EditText) findViewById(R.id.Email);



        //textTo = (EditText) findViewById(R.id.Email);
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

        //databaseReference = FirebaseDatabase.getInstance().getReference("send");
        firebaseAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = txtName.getText().toString();
                final String Email = txtEmail.getText().toString();
                final String Vehicle_type = txtVehicle_type.getText().toString();
                final String Vehicle_Number = txtVehicle_number.getText().toString();
                String Password = txtPassword.getText().toString();
               // String ConfirmPassword = txtConfirm_Password.getText().toString();
                // String maxid1 = Integer.toString(maxid);
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
                /*long textMessage = maxid+1000;
                String to = textTo.getText().toString();
                long subject = textMessage;
                Intent emaiil = new Intent(Intent.ACTION_SEND);
                emaiil.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                emaiil.putExtra(Intent.EXTRA_TEXT, subject );
                emaiil.setType("messagee");
                startActivity(Intent.createChooser(emaiil,"Choose an email client"));*/



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
                                    //int count = 0;

                                    //FirebaseDatabase.getInstance().getReference("/APART/USERS")
                                    //   DatabaseReference mCounter = mdatabaseReference.child("counter");
                                    //.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("/user details")
                                    //.child(maxid)
                                    ref.child(String.valueOf(maxid+1)).child("/user details")
                                            //.child("count++)
                                            //mCounter.child(mCounter.setValue(++count));




                                            // .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {


                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(Signup_Form.this, "Registration complete", Toast.LENGTH_SHORT).show();
                                            //Intent startActivity = new Intent(Signup_Form.this,MapsActivity.class);
                                           // startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                                        }
                                    });

                                   ref.child(String.valueOf(maxid+1)).child("/ID")
                                            .setValue(maxid+1001);




                                }
                                /*private FusedLocationClient fusedLocationClient;

                                LocationManager locationManager;
                                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                                Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

                                public void onLocationChanged(Location location) {
                                    double longitude=location.getLongitude();
                                    double latitude=location.getLatitude();

                                    location_helper update = new location_helper(
                                            longitude,
                                            latitude
                                    );



                                // ...
                            }*/
                            };



                        });


            }

           /* private void sendEmail() {
                //Getting content for email
               // String email = editTextEmail.getText().toString().trim();
                String email = editTextEmail1;
                String subject = editTextSubject;
                String message =editTextMessage ;

                //Creating SendMail object
                SendMail sm = new SendMail(this, email, subject, message);

                //Executing sendmail to send email
                sm.execute();
            }*/

            protected void sendEmail(String email1){

                int neww=maxid+1001;
                int maxid45=1;
                Log.i("Send Email", "sameerafatima333@gmail.com");
                    String [] TO ={email1};
                //String[] maxid2 = {Integer.toString(maxid45)};
                String maxid455 = new Integer(neww).toString();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
                String maxid1 = "Hello";
                int maxid3 = 1;
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

   /* public void btn_MapsActivity(View view) {

        //Intent act = new Intent(this, MapsActivity.class);
        //startActivity(act);
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
    }*/
}




/*import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

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
    EditText txtName,txtEmail, txtVehicle_type, txtVehicle_number, txtPassword;
    EditText editTextEmail;
    String editTextEmail1;
    String editTextSubject;
    String editTextMessage;
    //Intent intent;
    //EditText textTo,textMessage;
    Button btn_register;
    DatabaseReference databaseReference;
    DatabaseReference ref;
    //DatabaseReference reff;
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

        btn_register = (Button) findViewById(R.id.Register);
        ref=FirebaseDatabase.getInstance().getReference().child("APART/USERS");
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
                String  email1 = Email;
                sendEmail1(email1);







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
                                    String  email1 = Email;

                                   sendEmail1(email1);
                                    Toast.makeText(Signup_Form.this, "Email sent", Toast.LENGTH_SHORT).show();



                                    send information = new send(
                                            Name,
                                            Email,
                                            Vehicle_type,
                                            Vehicle_Number
                                    );

                                   // String email2 = Email;
                                    //int count = 0;

                                    //FirebaseDatabase.getInstance().getReference("/APART/USERS")
                                    //   DatabaseReference mCounter = mdatabaseReference.child("counter");
                                    //.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("/user details")
                                    //.child(maxid)
                                   // ref=FirebaseDatabase.getInstance().getReference().child("APART/USERS");
                                    ref.child(String.valueOf(maxid+1)).child("/user details")
                                            //.child("count++)
                                            //mCounter.child(mCounter.setValue(++count));




                                            // .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {



                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {





                                            Toast.makeText(Signup_Form.this, "Registration complete", Toast.LENGTH_SHORT).show();

                                            //Intent startActivity = new Intent(Signup_Form.this,MapsActivity.class);


                                        }

                                    });


                                   // sendEmail(Email);






                                }






                                /*private FusedLocationClient fusedLocationClient;

                                LocationManager locationManager;
                                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                                Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

                                public void onLocationChanged(Location location) {
                                    double longitude=location.getLongitude();
                                    double latitude=location.getLatitude();

                                    location_helper update = new location_helper(
                                            longitude,
                                            latitude
                                    );



                                // ...
                            }*/
                           // };




                        //});






           // }

            //reff= FirebaseDatabase.getInstance().getReference().child("APART").child("USERS").child("1").child("user details");

           /* private void sendEmail() {
                //Getting content for email
               // String email = editTextEmail.getText().toString().trim();
                String email = editTextEmail1;
                String subject = editTextSubject;
                String message =editTextMessage ;

                //Creating SendMail object
                SendMail sm = new SendMail(this, email, subject, message);

                //Executing sendmail to send email
                sm.execute();
            }*/

           /* public void sendEmail1(String email1){

                int neww=maxid+1001;

                Log.i("Send Email", "sameerafatima333@gmail.com");
                //String []  TO = {"sameerafatima4136@gmail.com"};
                String [] TO ={email1};
                //String[] maxid2 = {Integer.toString(maxid45)};
                String maxid455 = new Integer(neww).toString();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);

                emailIntent.putExtra(Intent.EXTRA_TEXT,maxid455);
                Toast.makeText(Signup_Form.this,"done sending",Toast.LENGTH_SHORT).show();
                try{
                    startActivity(Intent.createChooser(emailIntent,"Send mail...."));
                    sleep(500);
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
*/






   /* public void sendEmail() {
        reff.child(String.valueOf(11)).child("/user details");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String email11 = dataSnapshot.child("Email").getValue().toString();

                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String(email11));

                intent.putExtra(Intent.EXTRA_TEXT, new String("sameera"));
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select email sending app"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

   /* public void btn_MapsActivity(View view) {

        //Intent act = new Intent(this, MapsActivity.class);
        //startActivity(act);
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
    }*/
//}


