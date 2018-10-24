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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import common.Globals;
import common.Methods;
import utility.PrefManager;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText pass;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabaseReference;
    private PrefManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideActionBar();
        prefs=new PrefManager(MainActivity.this);
        name = findViewById(R.id.etName);
        pass = findViewById(R.id.etPass);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void btn_Login(View i) {
        if (!pass.getText().toString().equalsIgnoreCase("")) {
            final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Proccessing...", true);

            (firebaseAuth.signInWithEmailAndPassword(name.getText().toString(), pass.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                // Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                checkEmailVerification();
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

    }


    public void btn_Register(View v) {
        finish();
        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
    }

    private void checkEmailVerification() {
        final FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if (emailFlag) {

            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Globals.DB_USERS)
                    .child(new Methods().getFirebaseNodeforEmail(firebaseUser.getEmail())).child(Globals.NONE_NAME);

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        prefs.createLogin(dataSnapshot.getValue().toString(),firebaseUser.getEmail());
                        //prefs.setLoggedIn(true);

                    } catch (Exception e) {
                        prefs.removeSharedPreferences();
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Some error occurred!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(this, "Verify your Email", Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Globals.DB_USERS)
                    .child(new Methods().getFirebaseNodeforEmail(firebaseUser.getEmail())).child(Globals.NODE_USERTYPE);

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        prefs.setLoggedIn(true);
                        if (dataSnapshot.getValue().toString().equalsIgnoreCase(Globals.NORMAL_USER)) {
                            prefs.setUserType("normal");
                            startActivity(new Intent(MainActivity.this, SecondActivity.class));
                            finish();
                        } else if (dataSnapshot.getValue().toString().equalsIgnoreCase(Globals.ADMIN_USER)) {
                            prefs.setUserType("admin");
                            startActivity(new Intent(MainActivity.this, AdminActivity.class));
                            finish();
                        }
                    } catch (Exception e) {
                        prefs.removeSharedPreferences();
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Some error occurred!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


    }

    public void ResetPassword(View v) {
        startActivity(new Intent(MainActivity.this, PasswordActivity.class));

    }

    private void hideActionBar() {
        final ActionBar abar = getSupportActionBar();
        assert abar != null;
        abar.hide();

    }

}



