/**
 * Friends Fragment
 * @authors: Jeffrey Kao & Michael Tseng
 * Friends tab that is responsible for searching for and adding/removing friends
 */

package csx060.uga.edu.theweeklyburn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * Creates the Friends Fragment
 */
public class FriendsFragment extends Fragment {

    //Initialize global variables
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private RecyclerView userList;
    private DatabaseReference userDatabase;
    private DatabaseReference relationshipDatabase;
    private FirebaseRecyclerOptions<User> options;
    private FirebaseRecyclerAdapter<User, UserViewHolder> adapter;
    private SearchView searchView;
    private String currentUserEmail;
    private String selectedFriendUID = "";

    DatabaseReference RelationRef = database.getReference("Relationships");

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the views
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            currentUserEmail = user.getEmail();
        }

        userDatabase = database.getReference().child("Information").child("users");

        userList = (RecyclerView) view.findViewById(R.id.userList);
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView = view.findViewById(R.id.searchView);

        /**
         * Set the query based on what the user types into the search box
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String orderByChild = "firstName";

                if(query.matches("\\d+")){
                    orderByChild = "phoneNumber";
                }
                firebaseSearch(query, orderByChild);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String orderByChild = "firstName";

                if(newText.matches("\\d+")){
                    orderByChild = "phoneNumber";
                }
                firebaseSearch(newText, orderByChild);
                return false;
            }
        });

        return view;
    }

    /**
     * On start of the fragment, display all users
     */
    @Override
    public void onStart(){
        super.onStart();

        options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(userDatabase, User.class)
                .build();

        adapter = createAdapter(options);
        userList.setAdapter(adapter);
        adapter.startListening();
    }

    /**
     * after running the query, display only the relevant results
     * @param searchText
     * @param orderByChild
     */
    public void firebaseSearch(String searchText, String orderByChild){
        Query firebaseSearchQuery = userDatabase.orderByChild(orderByChild).startAt(searchText).endAt(searchText + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(firebaseSearchQuery, User.class)
                .build();

        adapter = createAdapter(options);
        userList.setAdapter(adapter);
        adapter.startListening();
    }

    /**
     * Creates a FirebaseRecyclerView to hold all the results
     * @param options
     * @return
     */
    public FirebaseRecyclerAdapter<User, UserViewHolder> createAdapter(FirebaseRecyclerOptions<User> options){
        FirebaseRecyclerAdapter<User, UserViewHolder> createdadapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User users) {
                final String fullName = users.getFirstName() + " " + users.getLastName();
                final String otherUid = users.getUid();
                final UserViewHolder viewHolder = holder;
                String profileImage = "";

                viewHolder.setUserName(fullName);
                viewHolder.setUserPhone(users.getPhoneNumber());

                //Sets users profile pictures based on their number
                switch(users.getProfilePicNum()){
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
                viewHolder.setUserImage(getResources().getIdentifier(profileImage, "drawable", getContext().getPackageName()));

                //Fetch the users from the DB
                RelationRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                selectedFriendUID = snapshot.getKey().toString();

                                //If the user is a friend, show "remove" instead of "add" button
                                if (otherUid.equalsIgnoreCase(selectedFriendUID)) {
                                    String addRemoveText = "Remove";
                                    viewHolder.addRemoveButton.setText(addRemoveText);

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //If friend is not the user, show the button, else, hide the button
                if(!users.getEmail().equalsIgnoreCase(currentUserEmail)) {
                    viewHolder.addRemoveButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button addRemoveButton = (Button) v;
                            if (addRemoveButton.getText().toString().equalsIgnoreCase("add")) {
                                addRemoveButton.setText("Remove");

                                addNewFriend(fullName, otherUid);
                            } else {
                                addRemoveButton.setText("Add");

                                removeFriend(fullName, otherUid);
                            }
                        }
                    });
                }
                else{
                    viewHolder.addRemoveButton.setVisibility(View.GONE);
                }
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_single, viewGroup, false);

                return new UserViewHolder(userView);
            }
        };
        return createdadapter;
    }

    /**
     * Creates the UserViewHolder to hold a user record in the results
     */
    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView userPhone;
        ImageView userImage;
        Button addRemoveButton;

        public UserViewHolder(View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.nameView);
            userPhone = itemView.findViewById(R.id.phoneView);
            userImage = itemView.findViewById(R.id.imageView);
            addRemoveButton = itemView.findViewById(R.id.addRemoveButton);
        }

        /**
         * set the user's name
         * @param fullName
         */
        public void setUserName(String fullName){
            String nameText = "Name: " + fullName;
            userName.setText(nameText);
        }

        /**
         * set the user's phone number
         * @param phone
         */
        public void setUserPhone(String phone){
            String phoneText = "Phone: " + phone;
            userPhone.setText(phoneText);
        }

        /**
         * set the user's picture
         * @param image
         */
        public void setUserImage(int image){
            userImage.setImageResource(image);
        }
    }

    /**
     * Adding a friend will add friend's UID to user's record, and add user's UID to friend's record
     * @param friendName
     * @param friendUID
     */
    public void addNewFriend(String friendName, String friendUID) {
        RelationRef.child(auth.getUid()).child(friendUID).setValue(new Relationships("friend", friendUID));
        RelationRef.child(friendUID).child(auth.getUid()).setValue(new Relationships("friend", auth.getUid()));
        Toast.makeText(getActivity(), friendName + " was added as your friend", Toast.LENGTH_LONG).show();
    }

    /**
     * Removing a friend will remove friend's UID from user's record and remove user's UID from friend's record
     * @param friendName
     * @param friendUID
     */
    public void removeFriend(String friendName, String friendUID) {
        RelationRef.child(auth.getUid()).child(friendUID).removeValue();
        RelationRef.child(friendUID).child(auth.getUid()).removeValue();
        Toast.makeText(getActivity(), friendName + " was removed as your friend", Toast.LENGTH_LONG).show();
    }

}
