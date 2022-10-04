package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class RegisterActivity extends AppCompatActivity {
    private EditText regname,regemail,regpass;
    private Button regbtn;
    private ProgressDialog pgDialog;
    private TextView login;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regname=(EditText)  findViewById(R.id.regName);
        regemail=(EditText) findViewById(R.id.regEmail);
        regpass=(EditText) findViewById(R.id.regPassword);
        login=(TextView) findViewById(R.id.login);
        regbtn=(Button) findViewById(R.id.rg_button);
        pgDialog= new ProgressDialog(this);
        DatabaseHelper database=new DatabaseHelper(this);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgDialog.show();
                Cursor lg_check=database.checkUser(regname.getText().toString(),regpass.getText().toString());
                awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
                awesomeValidation.addValidation(RegisterActivity.this,R.id.regName, RegexTemplate.NOT_EMPTY,R.string.empty_name);
                awesomeValidation.addValidation(RegisterActivity.this,R.id.regEmail, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
                awesomeValidation.addValidation(RegisterActivity.this,R.id.regPassword,".{6,}",R.string.invalid_pass);
                if(awesomeValidation.validate()) {
                    if (lg_check.getCount()>0) {
                        pgDialog.hide();
                        Toast.makeText(getApplicationContext(), "Username already exists. Please try with another username.", Toast.LENGTH_SHORT).show();

                    } else if (database.registerUser(regname.getText().toString(),regemail.getText().toString(),regpass.getText().toString())) {
                        pgDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    } else {
                        pgDialog.hide();
                        Toast.makeText(getApplicationContext(), "Error occurred while registering. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }else{ pgDialog.hide();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }
}