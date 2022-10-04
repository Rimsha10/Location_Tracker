package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class ForgotPassActivity extends AppCompatActivity {
    private EditText fgname,fgpass;
    private Button fgbtn;
    private ProgressDialog pgDialog;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        fgbtn=(Button) (findViewById(R.id.confirm_btn));
        fgname=(EditText) (findViewById(R.id.lguser));
        fgpass=(EditText) (findViewById(R.id.lgPassword));
        pgDialog=new ProgressDialog(this);
        pgDialog.setMessage("Please wait..");
        DatabaseHelper database=new DatabaseHelper(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        fgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgDialog.show();
                boolean username_check=database.checkUsername(fgname.getText().toString());
                boolean fg_check=database.forgotPass(fgname.getText().toString(),fgpass.getText().toString());
                awesomeValidation.addValidation(ForgotPassActivity.this,R.id.lguser, RegexTemplate.NOT_EMPTY,R.string.empty_name);
                awesomeValidation.addValidation(ForgotPassActivity.this,R.id.lgPassword,RegexTemplate.NOT_EMPTY,R.string.empty_pass);
                awesomeValidation.addValidation(ForgotPassActivity.this,R.id.lgPassword,".{6,}",R.string.invalid_pass);
                if(awesomeValidation.validate()) {
                    if (username_check) {
                        pgDialog.dismiss();
                        if(database.forgotPass(fgname.getText().toString(),fgpass.getText().toString())){
                        Toast.makeText(ForgotPassActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));}
                        else{
                            pgDialog.hide();
                            Toast.makeText(ForgotPassActivity.this, "Error occurred while updating password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pgDialog.hide();
                        Toast.makeText(ForgotPassActivity.this, "Username doesnot exist", Toast.LENGTH_SHORT).show();
                    }
                }else{pgDialog.hide();
                }
            }
        });
    }
}

