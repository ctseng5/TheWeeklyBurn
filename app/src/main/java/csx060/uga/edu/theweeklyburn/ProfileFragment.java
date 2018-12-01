package csx060.uga.edu.theweeklyburn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private Button signOutButton;
    private FirebaseAuth auth;

    private String name = "";
    private String email = "";
    private String phone = "";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Information");

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();

        DatabaseReference userRef = ref.child("users");
        getUserInfo(userRef);

//        if (user != null) {
//            name = user.get();
//            email = user.getEmail();
//            phone = user.getPhoneNumber();
//
//        } else {
//            // No user is signed in.
//        }

        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userPhone = view.findViewById(R.id.userPhone);

        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        auth.signOut();
//        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        Intent loginScreen = new Intent(getActivity(), LoginActivity.class);

        Toast.makeText(getActivity(), "Successfully signed out", Toast.LENGTH_LONG).show();
        getActivity().finish();
        loginScreen.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(loginScreen);
    }

    public void getUserInfo(DatabaseReference userRef) {
        userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //Workout prevWorkout = dataSnapshot.getValue(Workout.class);
                if(user != null) {
                    name = user.getFirstName() + " " + user.getLastName();
                    email = user.getEmail();
                    phone = user.getPhoneNumber();
                }

                userName.setText(name);
                userEmail.setText(email);
                userPhone.setText(phone);
                //Toast.makeText(getActivity(), prevPlank, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
