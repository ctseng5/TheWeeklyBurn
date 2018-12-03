package csx060.uga.edu.theweeklyburn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private Button signOutButton;
    private ImageView userImage;
    private FirebaseAuth auth;

    private String name = "";
    private String email = "";
    private String phone = "";
    private User friend;
    private BadgeRecord badges;

    private TextView badge1;
    private TextView badge2;
    private TextView badge3;
    private TextView badge4;
    private TextView badge5;
    private TextView badge6;
    private TextView badge7;
    private TextView badge8;
    private TextView badge9;


    private RecyclerView friendsList;
//    private RecyclerView badgeList;
    private DatabaseReference friendsDatabase;
    private DatabaseReference badgeDatabase;
    private FirebaseRecyclerOptions<Relationships> optionsFriends;
    private FirebaseRecyclerOptions<BadgeRecord> optionsBadges;
    private FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder> adapter;
    private FirebaseRecyclerAdapter<BadgeRecord, ProfileFragment.UserViewHolder> adapterBadge;
    private int count = 1;

    private String badge1Number;
    private String badge2Number;
    private String badge3Number;
    private String badge4Number;
    private String badge5Number;
    private String badge6Number;
    private String badge7Number;
    private String badge8Number;
    private String badge9Number;


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
//        badgeList = (RecyclerView) view.findViewById(R.id.badgeList);
//        badgeList.setHasFixedSize(true);
//        badgeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        DatabaseReference userRef = ref.child("users");
        getUserInfo(userRef);


        badge1 = view.findViewById(R.id.badge1);
        badge2 = view.findViewById(R.id.badge2);
        badge3 = view.findViewById(R.id.badge3);
        badge4 = view.findViewById(R.id.badge4);
        badge5 = view.findViewById(R.id.badge5);
        badge6 = view.findViewById(R.id.badge6);
        badge7 = view.findViewById(R.id.badge7);
        badge8 = view.findViewById(R.id.badge8);
        badge9 = view.findViewById(R.id.badge9);



        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userPhone = view.findViewById(R.id.userPhone);
        userImage = view.findViewById(R.id.imageView2);

        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        optionsFriends = new FirebaseRecyclerOptions.Builder<Relationships>()
                .setQuery(friendsDatabase, Relationships.class)
                .build();
//
//        optionsBadges = new FirebaseRecyclerOptions.Builder<BadgeRecord>()
//                .setQuery(badgeDatabase, BadgeRecord.class)
//                .build();

//        adapterBadge = createAdapterBadge(optionsBadges);
        adapter = createAdapterFriends(optionsFriends);

        friendsList.setAdapter(adapter);
//        badgeList.setAdapter(adapterBadge);
        adapter.startListening();

        getBadges(badgeDatabase);
//        badge1.setText("hello");
//        badge2.setText("it's");
//        badge3.setText("me");
    }

    public FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder> createAdapterFriends(FirebaseRecyclerOptions<Relationships> optionsFriends){
        FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder> createdadapter = new FirebaseRecyclerAdapter<Relationships, ProfileFragment.UserViewHolder>(optionsFriends){
            @Override
            protected void onBindViewHolder(@NonNull ProfileFragment.UserViewHolder holder, int position, @NonNull Relationships friendsList) {
                final String otherUid = friendsList.getUid();
                System.out.println("other UID: " + otherUid);

                ProfileFragment.UserViewHolder viewHolder = holder;

                User friend = getFriendInfo(otherUid, viewHolder);

                viewHolder.friendImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent friendIntent = new Intent(getActivity(), FriendProfileActivity.class);
                        friendIntent.putExtra("key",otherUid);
                        startActivity(friendIntent);
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

    public FirebaseRecyclerAdapter<BadgeRecord, ProfileFragment.UserViewHolder> createAdapterBadge(FirebaseRecyclerOptions<BadgeRecord> optionsBadges){
        FirebaseRecyclerAdapter<BadgeRecord, ProfileFragment.UserViewHolder> createdadapter = new FirebaseRecyclerAdapter<BadgeRecord, ProfileFragment.UserViewHolder>(optionsBadges){
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull BadgeRecord badgeList) {
                ProfileFragment.UserViewHolder viewHolder = holder;
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getRunBadges()), 1);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getPlankBadges()),2);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getPushupBadges()),3);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getPullupBadges()),4);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getSitupBadges()),5);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getSquatBadges()),6);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getTricepBadges()),7);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getJumpingBadges()),8);
                viewHolder.setBadgeTotal(Integer.toString(badgeList.getLungeBadges()),9);

                for(int i = 1; i < 10; i++) {
                    String profileBadgeImage = "badge" + Integer.toString(i);
                    viewHolder.setBadgeImage(getResources().getIdentifier(profileBadgeImage, "drawable", getContext().getPackageName()), i);
                }
            }

            @NonNull
            @Override
            public ProfileFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_badge, viewGroup, false);

                return new ProfileFragment.UserViewHolder(userView);
            }
        };
        return createdadapter;
    }

    @Override
    public void onClick(View view) {
        auth.signOut();
        Intent loginScreen = new Intent(getActivity(), LoginActivity.class);

        Toast.makeText(getActivity(), "Successfully signed out", Toast.LENGTH_LONG).show();
        loginScreen.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        getActivity().finish();
        startActivity(loginScreen);
    }

    public void getUserInfo(DatabaseReference userRef) {
        userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String profileImage = "";

                if(user != null) {
                    name = user.getFirstName() + " " + user.getLastName();
                    email = user.getEmail();
                    phone = user.getPhoneNumber();

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
                userImage.setImageResource(getResources().getIdentifier(profileImage, "drawable", getContext().getPackageName()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView friendFname;
        ImageView friendImage;
        TextView userTrophyTotal;
        TextView userTrophyTotal2;
        TextView userTrophyTotal3;
        TextView userTrophyTotal4;
        TextView userTrophyTotal5;
        TextView userTrophyTotal6;
        TextView userTrophyTotal7;
        TextView userTrophyTotal8;
        TextView userTrophyTotal9;
        ImageView userBadgeImage;
        ImageView userBadgeImage2;
        ImageView userBadgeImage3;
        ImageView userBadgeImage4;
        ImageView userBadgeImage5;
        ImageView userBadgeImage6;
        ImageView userBadgeImage7;
        ImageView userBadgeImage8;
        ImageView userBadgeImage9;


        public UserViewHolder(View itemView){
            super(itemView);

            friendFname = itemView.findViewById(R.id.friendFname);
            friendImage = itemView.findViewById(R.id.imageView3);
            userTrophyTotal = itemView.findViewById(R.id.trophyTotal);
            userTrophyTotal2 = itemView.findViewById(R.id.trophyTotal2);
            userTrophyTotal3 = itemView.findViewById(R.id.trophyTotal3);
            userTrophyTotal4 = itemView.findViewById(R.id.trophyTotal4);
            userTrophyTotal5 = itemView.findViewById(R.id.trophyTotal5);
            userTrophyTotal6 = itemView.findViewById(R.id.trophyTotal6);
            userTrophyTotal7 = itemView.findViewById(R.id.trophyTotal7);
            userTrophyTotal8 = itemView.findViewById(R.id.trophyTotal8);
            userTrophyTotal9 = itemView.findViewById(R.id.trophyTotal9);
            userBadgeImage = itemView.findViewById(R.id.trophyImage);
            userBadgeImage2 = itemView.findViewById(R.id.trophyImage2);
            userBadgeImage3 = itemView.findViewById(R.id.trophyImage3);
            userBadgeImage4 = itemView.findViewById(R.id.trophyImage4);
            userBadgeImage5 = itemView.findViewById(R.id.trophyImage5);
            userBadgeImage6 = itemView.findViewById(R.id.trophyImage6);
            userBadgeImage7 = itemView.findViewById(R.id.trophyImage7);
            userBadgeImage8 = itemView.findViewById(R.id.trophyImage8);
            userBadgeImage9 = itemView.findViewById(R.id.trophyImage9);


        }

        public void setFriendName(String fullName){
            friendFname.setText(fullName);
        }

        public void setFriendImage(int image){
            friendImage.setImageResource(image);
        }

        public void setBadgeTotal(String total, int num){
            switch(num) {
                case 1:
                    userTrophyTotal.setText(total);
                    break;
                case 2:
                    userTrophyTotal2.setText(total);
                    break;
                case 3:
                    userTrophyTotal3.setText(total);
                    break;
                case 4:
                    userTrophyTotal4.setText(total);
                    break;
                case 5:
                    userTrophyTotal5.setText(total);
                    break;
                case 6:
                    userTrophyTotal6.setText(total);
                    break;
                case 7:
                    userTrophyTotal7.setText(total);
                    break;
                case 8:
                    userTrophyTotal8.setText(total);
                    break;
                case 9:
                    userTrophyTotal9.setText(total);
                    break;

            }
        }

        public void setBadgeImage(int image, int num){
            switch(num) {
                case 1:
                    userBadgeImage.setImageResource(image);
                    ;
                    break;
                case 2:
                    userBadgeImage2.setImageResource(image);
                    break;
                case 3:
                    userBadgeImage3.setImageResource(image);
                    break;
                case 4:
                    userBadgeImage4.setImageResource(image);
                    break;
                case 5:
                    userBadgeImage5.setImageResource(image);
                    break;
                case 6:
                    userBadgeImage6.setImageResource(image);
                    break;
                case 7:
                    userBadgeImage7.setImageResource(image);
                    break;
                case 8:
                    userBadgeImage8.setImageResource(image);
                    break;
                case 9:
                    userBadgeImage9.setImageResource(image);
                    break;
            }
        }
    }

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
                viewHolder.setFriendImage(getResources().getIdentifier(profileFriendimage, "drawable", getContext().getPackageName()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return friend;
    }

    public void getBadges(DatabaseReference badgeDatabase) {
        badgeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
