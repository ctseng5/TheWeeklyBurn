package csx060.uga.edu.theweeklyburn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;
    private TextView emailInput;
    private TextView passwordInput;
    private TextView passwordConfirmation;
    private TextView firstName;
    private TextView lastName;
    private TextView phoneNum;
    private TextView passwordMessage;
    private FirebaseAuth auth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Information");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase Auth instance
        auth = FirebaseAuth.getInstance();

        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setEnabled(false);
        signUpButton.setOnClickListener(new SignUpOnClickListener());
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new LoginOnClickListener());
        emailInput = findViewById(R.id.emailField);
        passwordInput = findViewById(R.id.passwordField);
        passwordConfirmation = findViewById(R.id.confirmPasswordField);
        passwordConfirmation.addTextChangedListener(new PasswordListener());
        firstName = findViewById(R.id.fname);
        lastName = findViewById(R.id.lname);
        phoneNum = findViewById(R.id.phone);
        passwordMessage = findViewById(R.id.passwordMessage);


    }

    private class LoginOnClickListener implements View.OnClickListener {
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    private class PasswordListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(passwordInput.getText().toString().equals(passwordConfirmation.getText().toString())) {
                passwordMessage.setText("");
                signUpButton.setEnabled(true);
            }
            else {
                passwordMessage.setText("Passwords do not match");
                signUpButton.setEnabled(false);
            }
        }
    }

    private class SignUpOnClickListener implements View.OnClickListener {
        public void onClick(View view) {

            final String email = emailInput.getText().toString().trim();
            final String password = passwordInput.getText().toString().trim();
            final String fname = firstName.getText().toString().trim();
            final String lname = lastName.getText().toString().trim();
            final String phone = phoneNum.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(fname)) {
                Toast.makeText(getApplicationContext(), "Enter your first name!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(lname)) {
                Toast.makeText(getApplicationContext(), "Enter your last name!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(getApplicationContext(), "Enter your phone number!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            }

            //User newUser = new User(fname, lname, email, phone);

            auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(SignUpActivity.this, "Created Account:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DatabaseReference usersRef = ref.child("users");
                        usersRef.child(auth.getUid()).setValue(new User(fname, lname, email, phone, auth.getUid()));
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        }
    }
}
