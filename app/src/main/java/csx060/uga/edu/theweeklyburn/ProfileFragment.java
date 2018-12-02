package csx060.uga.edu.theweeklyburn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

    private RecyclerView friendsList;
    private RecyclerView badgeList;
    private DatabaseReference friendsDatabase;
    private DatabaseReference badgeDatabase;
    private FirebaseRecyclerOptions<User> optionsFriends;
    private FirebaseRecyclerAdapter<User, ProfileFragment.UserViewHolder> adapter;

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

        friendsDatabase = database.getReference().child("Relationships").child(auth.getUid());
        friendsList = (RecyclerView) view.findViewById(R.id.friendsList);
        friendsList.setHasFixedSize(true);
        friendsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        badgeDatabase = database.getReference().child("Badges").child(auth.getUid());
        badgeList = (RecyclerView) view.findViewById(R.id.badgeList);
        badgeList.setHasFixedSize(true);
        badgeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        DatabaseReference userRef = ref.child("users");
        getUserInfo(userRef);

        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userPhone = view.findViewById(R.id.userPhone);

        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        optionsFriends = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(friendsDatabase, User.class)
                .build();

        adapter = createAdapterFriends(optionsFriends);
        friendsList.setAdapter(adapter);
        adapter.startListening();
    }

    public FirebaseRecyclerAdapter<User, ProfileFragment.UserViewHolder> createAdapterFriends(FirebaseRecyclerOptions<User> options){
        FirebaseRecyclerAdapter<User, ProfileFragment.UserViewHolder> createdadapter = new FirebaseRecyclerAdapter<User, ProfileFragment.UserViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull ProfileFragment.UserViewHolder holder, int position, @NonNull User friends) {
                final String otherUid = friends.getUid();
                ProfileFragment.UserViewHolder viewHolder = holder;
                viewHolder.setFirstName(friends.getFirstName());
                viewHolder.setLastName(friends.getLastName());

                viewHolder.viewProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button viewProfile = (Button) v;
                        if (viewProfile.getText().toString().equalsIgnoreCase("add")) {


                        }
                    }
                });
            }

            @NonNull
            @Override
            public ProfileFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_friend, viewGroup, false);

                return new ProfileFragment.UserViewHolder(userView);
            }
        };
        return createdadapter;
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

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView friendFname;
        TextView friendLname;
//        ImageView userImage;
        Button viewProfile;

        public UserViewHolder(View itemView){
            super(itemView);

            friendFname = itemView.findViewById(R.id.friendFname);
            friendLname = itemView.findViewById(R.id.friendLname);
//            userImage = itemView.findViewById(R.id.imageView);
            viewProfile = itemView.findViewById(R.id.viewProfile);
        }

        public void setFirstName(String firstName){
            friendFname.setText(firstName);
        }

        public void setLastName(String lastName){
            friendLname.setText(lastName);
        }

//        public void setUserPhone(String phone){
//            String phoneText = "Phone: " + phone;
//            userPhone.setText(phoneText);
//        }
//
//        public void setUserImage(int image){
//            userImage.setImageResource(image);
//        }
    }

}
