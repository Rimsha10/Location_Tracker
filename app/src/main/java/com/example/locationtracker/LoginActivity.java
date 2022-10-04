package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class LoginActivity extends AppCompatActivity {
    private EditText lgname,lgpass;
    private Button lgbtn;
    private ProgressDialog pgDialog;
    private TextView fpass;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lgbtn=(Button) (findViewById(R.id.lgbutton));
        lgname=(EditText) (findViewById(R.id.lguser));
        lgpass=(EditText) (findViewById(R.id.lgPassword));
        fpass=(TextView)(findViewById(R.id.lg_fgpass));
        pgDialog=new ProgressDialog(this);
        pgDialog.setMessage("Please wait..");
        DatabaseHelper database=new DatabaseHelper(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPassActivity.class));
            }
        });

        lgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgDialog.show();
                Cursor lg_check=database.checkUser(lgname.getText().toString(),lgpass.getText().toString());
                awesomeValidation.addValidation(LoginActivity.this,R.id.lguser, RegexTemplate.NOT_EMPTY,R.string.empty_name);
                awesomeValidation.addValidation(LoginActivity.this,R.id.lgPassword,".{6,}",R.string.invalid_pass);
                if(awesomeValidation.validate()) {
                    if (lg_check.getCount()!=-1) {
                        pgDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, Home_Activity.class));
                    } else {
                        pgDialog.hide();
                        Toast.makeText(LoginActivity.this, "Error occurred while signing in. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }else{pgDialog.hide();
                }
           }
        });
    }
}
