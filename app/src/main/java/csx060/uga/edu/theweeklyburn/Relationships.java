package csx060.uga.edu.theweeklyburn;

public class Relationships {

    private String relationship;
    private String uid;

    public Relationships() {

    }

    public Relationships(String relationship, String uid) {

        this.relationship = relationship;
        this.uid = uid;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
