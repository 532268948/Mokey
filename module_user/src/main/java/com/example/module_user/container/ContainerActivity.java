package com.example.module_user.container;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.module_user.R;
import com.example.module_user.ui.login.LoginActivity;

/**
 * @author tianhuaye
 */
public class ContainerActivity extends AppCompatActivity {

    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        mLoginBtn = findViewById(R.id.login);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContainerActivity.this, LoginActivity.class));
            }
        });
    }
}
