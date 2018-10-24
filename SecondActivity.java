package com.dit.thecampusculinaries;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import common.Globals;
import utility.PrefManager;

public class SecondActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        inflateActionBar();


        firebaseAuth=FirebaseAuth.getInstance();
    }
    private void Logout(){
        new PrefManager(SecondActivity.this).removeSharedPreferences();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void Danny (View v){
        startActivity(new Intent(SecondActivity.this,Danny.class));
    }
    public void SweetSpot(View v){
        startActivity(new Intent(SecondActivity.this,SweetSpot.class));
    }
    public void Iceberg(View v){
        startActivity(new Intent(SecondActivity.this,IceBerg.class));
    }


    private void inflateActionBar() {
        final ActionBar abar = getSupportActionBar();
        abar.show();
        abar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B71C1C")));
        ;//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_titletext_layout_back, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Choose Culinary");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        Toolbar parent = (Toolbar) viewActionBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

}
