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
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button signOutButton;
    private FirebaseAuth auth;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();
        signOutButton = view.findViewById(R.id.signOutButton);
//        signOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Successfully signed out", Toast.LENGTH_LONG).show();
//                auth.signOut();
//                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
//
//                getActivity().finish();
//            }
//        });
        signOutButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        auth.signOut();
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        Toast.makeText(getActivity(), "Successfully signed out", Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

}
