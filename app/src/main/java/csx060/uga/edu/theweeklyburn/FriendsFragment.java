package csx060.uga.edu.theweeklyburn;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private RecyclerView userList;
    private DatabaseReference userDatabase;
    private DatabaseReference relationshipDatabase;
    private FirebaseRecyclerOptions<User> options;
    private FirebaseRecyclerAdapter<User, UserViewHolder> adapter;
    private SearchView searchView;
    private String currentUserEmail;

    DatabaseReference RelationRef = database.getReference("Relationships");

    public FriendsFragment() {
        // Required empty public constructor
    }

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
        relationshipDatabase = RelationRef.child("users");

        userList = (RecyclerView) view.findViewById(R.id.userList);
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView = view.findViewById(R.id.searchView);

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

    public void firebaseSearch(String searchText, String orderByChild){
        Query firebaseSearchQuery = userDatabase.orderByChild(orderByChild).startAt(searchText).endAt(searchText + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(firebaseSearchQuery, User.class)
                .build();

        adapter = createAdapter(options);
        userList.setAdapter(adapter);
        adapter.startListening();
    }

    public FirebaseRecyclerAdapter<User, UserViewHolder> createAdapter(FirebaseRecyclerOptions<User> options){
        FirebaseRecyclerAdapter<User, UserViewHolder> createdadapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User users) {
                final String fullName = users.getFirstName() + " " + users.getLastName();
                final String otherUid = users.getUid();
                UserViewHolder viewHolder = holder;
                viewHolder.setUserName(fullName);
                viewHolder.setUserPhone(users.getPhoneNumber());
                viewHolder.setUserImage(R.drawable.icon);

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

        public void setUserName(String fullName){
            String nameText = "Name: " + fullName;
            userName.setText(nameText);
        }

        public void setUserPhone(String phone){
            String phoneText = "Phone: " + phone;
            userPhone.setText(phoneText);
        }

        public void setUserImage(int image){
            userImage.setImageResource(image);
        }
    }

    public void addNewFriend(String friendName, String friendUID) {
        RelationRef.child(auth.getUid()).child(friendUID).setValue(new Relationships("friend"));
        RelationRef.child(friendUID).child(auth.getUid()).setValue(new Relationships("friend"));
        Toast.makeText(getActivity(), friendName + " was added as your friend", Toast.LENGTH_LONG).show();
    }

    public void removeFriend(String friendName, String friendUID) {
        RelationRef.child(auth.getUid()).child(friendUID).removeValue();
        RelationRef.child(friendUID).child(auth.getUid()).removeValue();
        Toast.makeText(getActivity(), friendName + " was removed as your friend", Toast.LENGTH_LONG).show();
    }

}
