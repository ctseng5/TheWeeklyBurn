package csx060.uga.edu.theweeklyburn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    private Button signupButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(new SignUpOnClickListener());
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new LoginOnClickListener());
    }

    private class SignUpOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), SignUpActivity.class);
            view.getContext().startActivity( intent );
        }
    }

        private class LoginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity( intent );
        }
    }

}
