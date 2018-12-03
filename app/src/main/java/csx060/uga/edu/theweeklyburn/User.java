package csx060.uga.edu.theweeklyburn;

public class User {

    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String uid;
    public int profilePicNum;

    public User(String firstName, String lastName, String email, String phoneNumber, String uid, int
                profilePicNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
        this.profilePicNum = profilePicNum;
    }

    public User () {
        firstName = "";
        lastName = "";
        email = "";
        phoneNumber = "";
        uid = "";
        profilePicNum = 0;
    }

    public User(String uid){
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getProfilePicNum() {
        return profilePicNum;
    }

    public void setProfilePicNum(int profilePicNum) {
        this.profilePicNum = profilePicNum;
    }
}
