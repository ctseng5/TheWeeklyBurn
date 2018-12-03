/**
 * Reset Passwords activity
 * @authors: Jeffrey Kao & Michael Tseng
 * Allows the user to reset their password if they forget it.
 * Uses Firebase's built-in functionality and mail server
 */

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Creates the Reset Password activity
 */
public class ResetPasswordActivity extends AppCompatActivity {

    //Initialize global veriables
    private EditText emailField;
    private Button resetButton;
    private Button signinButton;

    /**
     * Creates the views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailField = (EditText) findViewById(R.id.emailField);
        emailField.addTextChangedListener(new EmailListener());

        resetButton = findViewById(R.id.resetButton);
        resetButton.setEnabled(false);
        resetButton.setOnClickListener(new ResetPasswordOnClickListener());

        signinButton = findViewById(R.id.signinButton);
        signinButton.setOnClickListener(new LoginOnClickListener ());

    }

    /**
     * Listens to see if text has been entered into the email field. If not, disable button
     */
    private class EmailListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String email = emailField.getText().toString();
            if(TextUtils.isEmpty(email)) {
                resetButton.setEnabled(false);
            }
            else {
                resetButton.setEnabled(true);
            }
        }
    }

    /**
     * If login button is clicked, direct user to Login Activity
     */
    private class LoginOnClickListener implements View.OnClickListener {
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    /**
     * If reset password button is clicked, get typed in email and
     * send user an email with instructions on resetting password.
     */
    private class ResetPasswordOnClickListener implements View.OnClickListener {
        public void onClick(View view) {
            String emailAddress = emailField.getText().toString();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            //Use Firebase auth to send email to the provided email address
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Reset Email sent! Check your email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
