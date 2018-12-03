/**
 * Splash Activity
 * @authors: Jeffrey Kao & Michael Tseng
 * Shows the app name, a description, and buttons for logging in and signing up
 */

package csx060.uga.edu.theweeklyburn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Create the Splash activity
 */
public class SplashActivity extends AppCompatActivity {

    //Initialize the global variables
    private Button signupButton;
    private Button loginButton;

    /**
     * Create the views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(new SignUpOnClickListener());
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new LoginOnClickListener());
    }

    /**
     * Listens for when the signup button is clicked.
     * When the button is clicked, open the Signup Activity
     */
    private class SignUpOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), SignUpActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    /**
     * Listens for when the login button is clicked.
     * When the button is clicked, open the login Activity
     */
    private class LoginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity( intent );
        }
    }

}
