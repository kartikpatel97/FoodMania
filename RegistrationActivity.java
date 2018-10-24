package com.dit.thecampusculinaries;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import common.Globals;
import common.Methods;

public class RegistrationActivity extends AppCompatActivity {
    private EditText userName, userPassword, userEmail;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        hideActionBar();
        pDialog = new ProgressDialog(RegistrationActivity.this);
        userName = findViewById(R.id.etUserName);
        userPassword = findViewById(R.id.etUserPassword);
        userEmail = findViewById(R.id.etUserEmail);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private Boolean validate() {
        Boolean result = false;

        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please Enter All the Details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    public void btn_login(View view) {
        finish();
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
    }

    public void btn_register(View v) {
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing...");
        pDialog.show();
        if (validate()) {
            String user_email = userEmail.getText().toString().trim();
            String user_password = userPassword.getText().toString().trim();

            (firebaseAuth.createUserWithEmailAndPassword(user_email, user_password))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                createUserInDB();
                               /* if(createUserInDB()) {
                                    sendEmailVerification();
                                }else{
                                    try {
                                        firebaseAuth.getCurrentUser().delete();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }*/
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void sendEmailVerification() {
        final FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!isFinishing()) {
                        pDialog.dismiss();
                    }
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "SuccessFully Registered, Verification Mail has been sent!", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Verification Mail hasn't been Sent", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void createUserInDB() {
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference(Globals.DB_USERS);
            final Methods methods=new Methods();
            mDatabase.child(methods.getFirebaseNodeforEmail(userEmail.getText().toString())).child(Globals.NONE_NAME).setValue(userName.getText().toString())

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDatabase.child(methods.getFirebaseNodeforEmail(userEmail.getText().toString())).child(Globals.NODE_USERTYPE).setValue("normal")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            sendEmailVerification();
                                        }
                                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegistrationActivity.this, "Error Creating User. Please Try Again!", Toast.LENGTH_LONG).show();
                                    try {
                                        firebaseAuth.getCurrentUser().delete();
                                    }catch (Exception e1){
                                        e1.printStackTrace();
                                    }
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrationActivity.this, "Error Creating User. Please Try Again!", Toast.LENGTH_LONG).show();
                            try {
                                firebaseAuth.getCurrentUser().delete();
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void hideActionBar() {
        final ActionBar abar = getSupportActionBar();
        assert abar != null;
        abar.hide();

    }



}