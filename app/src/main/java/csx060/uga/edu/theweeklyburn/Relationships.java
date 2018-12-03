/**
 * Relationships class
 * @authors: Jeffrey Kao & Michael Tseng
 * Creates a Relationships object
 */

package csx060.uga.edu.theweeklyburn;

public class Relationships {

    private String relationship; //type of relationship with another user (i.e. "friend")
    private String uid; //UID of the friend

    /**
     * Empty constructor
     */
    public Relationships() {

    }

    /**
     * Constructor for Relationships
     * @param relationship
     * @param uid
     */
    public Relationships(String relationship, String uid) {

        this.relationship = relationship;
        this.uid = uid;
    }

    /**
     * Gets the relationship string
     * @return
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Sets the relationship string
     * @param relationship
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * Gets the UID of the friend
     * @return
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the UID of the friend
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }
}
