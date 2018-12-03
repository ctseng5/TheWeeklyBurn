/**
 * User class
 * @authors: Jeffrey Kao & Michael Tseng
 * Creates a User object
 */

package csx060.uga.edu.theweeklyburn;

public class User {

    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String uid;
    public int profilePicNum; //random number to set their profile picture

    /**
     * Constructor for User
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param uid
     * @param profilePicNum
     */
    public User(String firstName, String lastName, String email, String phoneNumber, String uid, int
                profilePicNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
        this.profilePicNum = profilePicNum;
    }

    /**
     * Default constructor for user
     */
    public User () {
        firstName = "";
        lastName = "";
        email = "";
        phoneNumber = "";
        uid = "";
        profilePicNum = 0;
    }

    /**
     * Creates a user with a UID
     * @param uid
     */
    public User(String uid){
        this.uid = uid;
    }

    /**
     * Gets the first name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the UID
     * @return
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the UID
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets the profile picture number
     * @return
     */
    public int getProfilePicNum() {
        return profilePicNum;
    }

    /**
     * Sets the profile picture number
     * @param profilePicNum
     */
    public void setProfilePicNum(int profilePicNum) {
        this.profilePicNum = profilePicNum;
    }
}
