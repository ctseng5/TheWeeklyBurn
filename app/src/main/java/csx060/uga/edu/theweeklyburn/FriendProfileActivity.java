/**
 * FriendProfile Activity
 * @authors: Jeffrey Kao & Michael Tseng
 * The activity that shows a user's friend
 */

package csx060.uga.edu.theweeklyburn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Creates the FriendProfileActivity
 */
public class FriendProfileActivity extends AppCompatActivity {

    //Initialize global variables
    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private Button signOutButton;
    private ImageView userImage;
    private FirebaseAuth auth;
    private String currentDisplayUser;

    private String name = "";
    private String email = "";
    private String phone = "";
    private String uid = "";
    private User friend;

    private TextView badge1;
    private TextView badge2;
    private TextView badge3;
    private TextView badge4;
    private TextView badge5;
    private TextView badge6;
    private TextView badge7;
    private TextView badge8;
    private TextView badge9;

    private String badge1Number;
    private String badge2Number;
    private String badge3Number;
    private String badge4Number;
    private String badge5Number;
    private String badge6Number;
    private String badge7Number;
    private String badge8Number;
    private String badge9Number;

    private RecyclerView friendsList;
//    private RecyclerView badgeList;
    private DatabaseReference friendsDatabase;
    private DatabaseReference badgeDatabase;
    private FirebaseRecyclerOptions<Relationships> optionsFriends;
    private FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder> adapter;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Information");

    /**
     * Creates the views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        auth = FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            uid = extras.getString("key");
        }

        friendsDatabase = database.getReference().child("Relationships").child(uid);
        friendsList = (RecyclerView) findViewById(R.id.friendsList);
        friendsList.setHasFixedSize(true);
        friendsList.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));

        badgeDatabase = database.getReference().child("Badges").child(uid);
//        badgeList = (RecyclerView) findViewById(R.id.badgeList);
//        badgeList.setHasFixedSize(true);
//        badgeList.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));

        DatabaseReference userRef = ref.child("users");
        getUserInfo(userRef);

        badge1 = findViewById(R.id.badge1);
        badge2 = findViewById(R.id.badge2);
        badge3 = findViewById(R.id.badge3);
        badge4 = findViewById(R.id.badge4);
        badge5 = findViewById(R.id.badge5);
        badge6 = findViewById(R.id.badge6);
        badge7 = findViewById(R.id.badge7);
        badge8 = findViewById(R.id.badge8);
        badge9 = findViewById(R.id.badge9);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        userImage = findViewById(R.id.imageView2);
    }

    /**
     * Sets the adapters
     */
    @Override
    public void onStart(){
        super.onStart();

        optionsFriends = new FirebaseRecyclerOptions.Builder<Relationships>()
                .setQuery(friendsDatabase, Relationships.class)
                .build();

        adapter = createAdapterFriends(optionsFriends);
        friendsList.setAdapter(adapter);
        adapter.startListening();

        getBadges(badgeDatabase);
    }

    /**
     * Creates a FirebaseRecyclerAdapter to hold friends list
     * @param optionsFriends
     * @return
     */
    public FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder> createAdapterFriends(FirebaseRecyclerOptions<Relationships> optionsFriends){
        FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder> createdadapter = new FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder>(optionsFriends){
            @Override
            protected void onBindViewHolder(@NonNull ProfileFragment.UserViewHolder holder, int position, @NonNull Relationships friendsList) {
                final String otherUid = friendsList.getUid();
                System.out.println("other UID: " + otherUid);

                ProfileFragment.UserViewHolder viewHolder = holder;
                //DatabaseReference friendRef = ref.child("user").child(otherUid);

                User friend = getFriendInfo(otherUid, viewHolder);
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

    /**
     * Fetches the user info from the DB
     * @param userRef
     */
    public void getUserInfo(DatabaseReference userRef) {
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String profileImage = "";

                //Workout prevWorkout = dataSnapshot.getValue(Workout.class);
                if(user != null) {
                    name = user.getFirstName() + " " + user.getLastName();
                    email = user.getEmail();
                    phone = user.getPhoneNumber();

                    //Sets the profile pic of the friend
                    switch(user.getProfilePicNum()){
                        case 0:
                            profileImage = "pro_pic_1";
                            break;
                        case 1:
                            profileImage = "pro_pic_2";
                            break;
                        case 2:
                            profileImage = "pro_pic_3";
                            break;
                        case 3:
                            profileImage = "pro_pic_4";
                            break;
                        case 4:
                            profileImage = "pro_pic_5";
                            break;
                        case 5:
                            profileImage = "pro_pic_6";
                            break;
                        case 6:
                            profileImage = "pro_pic_7";
                            break;
                        case 7:
                            profileImage = "pro_pic_8";
                            break;
                        case 8:
                            profileImage = "pro_pic_9";
                            break;
                    }
                }

                userName.setText(name);
                userEmail.setText("Email: " + email);
                userPhone.setText("Phone: " + phone);
                userImage.setImageResource(getResources().getIdentifier(profileImage, "drawable", getPackageName()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Creates UserViewHolder
     */
    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView friendFname;
        ImageView friendImage;

        public UserViewHolder(View itemView){
            super(itemView);

            friendFname = itemView.findViewById(R.id.friendFname);
            friendImage = itemView.findViewById(R.id.imageView3);
        }

        public void setFriendName(String fullName){
            friendFname.setText(fullName);
        }

        public void setFriendImage(int image){
            friendImage.setImageResource(image);
        }
    }

    /**
     * Gets the friend's info from the DB
     * @param friendUid
     * @param viewHolder
     * @return
     */
    public User getFriendInfo(String friendUid, final ProfileFragment.UserViewHolder viewHolder) {
        DatabaseReference userRef = ref.child("users").child(friendUid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friend = dataSnapshot.getValue(User.class);
                String profileFriendimage = "";
                String fullName = friend.getFirstName() + " " + friend.getLastName();

                switch(friend.getProfilePicNum()){
                    case 0:
                        profileFriendimage = "pro_pic_1";
                        break;
                    case 1:
                        profileFriendimage = "pro_pic_2";
                        break;
                    case 2:
                        profileFriendimage = "pro_pic_3";
                        break;
                    case 3:
                        profileFriendimage = "pro_pic_4";
                        break;
                    case 4:
                        profileFriendimage = "pro_pic_5";
                        break;
                    case 5:
                        profileFriendimage = "pro_pic_6";
                        break;
                    case 6:
                        profileFriendimage = "pro_pic_7";
                        break;
                    case 7:
                        profileFriendimage = "pro_pic_8";
                        break;
                    case 8:
                        profileFriendimage = "pro_pic_9";
                        break;
                }

                viewHolder.setFriendName(fullName);
                viewHolder.setFriendImage(getResources().getIdentifier(profileFriendimage, "drawable", getPackageName()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return friend;
    }

    /**
     * Retreives the number of badges for the user and displays them on the profile
     * @param badgeDatabase
     */
    public void getBadges(DatabaseReference badgeDatabase) {
        badgeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //If there is an entry for user, set the values to the numbers
                if(dataSnapshot.exists()) {
                    BadgeRecord badgeRecord = dataSnapshot.getValue(BadgeRecord.class);
                    badge1Number = Integer.toString(badgeRecord.getRunBadges());
                    badge2Number = Integer.toString(badgeRecord.getPlankBadges());
                    badge3Number = Integer.toString(badgeRecord.getPushupBadges());
                    badge4Number = Integer.toString(badgeRecord.getPullupBadges());
                    badge5Number = Integer.toString(badgeRecord.getSitupBadges());
                    badge6Number = Integer.toString(badgeRecord.getSquatBadges());
                    badge7Number = Integer.toString(badgeRecord.getTricepBadges());
                    badge8Number = Integer.toString(badgeRecord.getJumpingBadges());
                    badge9Number = Integer.toString(badgeRecord.getLungeBadges());


                    badge1.setText("Run Badges: " + badge1Number);
                    badge2.setText("Plank Badges: " + badge2Number);
                    badge3.setText("Pushup Badges: " + badge3Number);
                    badge4.setText("Pullup Badges: " + badge4Number);
                    badge5.setText("Situp Badges: " + badge5Number);
                    badge6.setText("Squat Badges: " + badge6Number);
                    badge7.setText("Tricep Dip Badges: " + badge7Number);
                    badge8.setText("Jumping Jacks Badges: " + badge8Number);
                    badge9.setText("Lunge Badges: " + badge9Number);
                }
                //If there is no entry for the user, set all values to 0.
                else {
                    badge1.setText("Run Badges: 0");
                    badge2.setText("Plank Badges: 0");
                    badge3.setText("Pushup Badges: 0");
                    badge4.setText("Pullup Badges: 0");
                    badge5.setText("Situp Badges: 0");
                    badge6.setText("Squat Badges: 0");
                    badge7.setText("Tricep Dip Badges: 0");
                    badge8.setText("Jumping Jacks Badges: 0");
                    badge9.setText("Lunge Badges: 0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
