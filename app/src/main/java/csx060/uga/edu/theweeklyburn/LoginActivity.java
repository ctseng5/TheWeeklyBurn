/**
 * Login Activity
 * @authors: Jeffrey Kao & Michael Tseng
 * Allows for a user who has already signed up to log into the application
 */

package csx060.uga.edu.theweeklyburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Create the LoginActivity
 */
public class LoginActivity extends AppCompatActivity {

    //Initialize global variables
    private FirebaseAuth mAuth;
    private EditText emailField;
    private EditText passwordField;
    private Button signInButton;
    private Button signupButton;
    private Button resetPassword;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Create the views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailField = (EditText) findViewById(R.id.emailField);
        emailField.addTextChangedListener(new LoginListener());
        passwordField = (EditText) findViewById(R.id.passwordField);
        passwordField.addTextChangedListener(new LoginListener());
        signInButton = (Button) findViewById(R.id.signinButton);
        signInButton.setEnabled(false);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new SignUpButtonListener());

        resetPassword = findViewById(R.id.resetPassword);
        resetPassword.setOnClickListener( new ResetPasswordListener());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                }
            }
        };
    }

    /**
     * Detects if the user is logged in
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Determines if both the email and password fields have been filled out.
     * If not, disable the login button
     */
    private class LoginListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                signInButton.setEnabled(false);
            }
            else {
                signInButton.setEnabled(true);
            }
        }
    }

    /**
     * Gets the credentials from the text fields and attempts to log the user in.
     */
    private void startSignIn() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        signInButton.setEnabled(true);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Email or Password is incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * On click button listener to open Sign Up activity if user clicks on this button
     */
    private class SignUpButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), SignUpActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    /**
     * On click button listener to open up Reset Password activity if user clicks that
     * they forgot their password.
     */
    private class ResetPasswordListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ResetPasswordActivity.class);
            view.getContext().startActivity( intent );
        }
    }

}
