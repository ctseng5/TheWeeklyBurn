package csx060.uga.edu.theweeklyburn;

public class BadgeRecord {

    private int runBadges;
    private int plankBadges;
    private int pushupBadges;
    private int pullupBadges;
    private int situpBadges;
    private int squatBadges;
    private int tricepBadges;
    private int jumpingBadges;
    private int lungeBadges;


    public BadgeRecord() {

    }

    public BadgeRecord(int runBadges, int plankBadges, int pushupBadges, int pullupBadges, int situpBadges, int squatBadges, int tricepBadges, int jumpingBadges, int lungeBadges) {
        this.runBadges = runBadges;
        this.plankBadges = plankBadges;
        this.pushupBadges = pushupBadges;
        this.pullupBadges = pullupBadges;
        this.situpBadges = situpBadges;
        this.squatBadges = squatBadges;
        this.tricepBadges = tricepBadges;
        this.jumpingBadges = jumpingBadges;
        this.lungeBadges = lungeBadges;
    }

    public int getRunBadges() {
        return runBadges;
    }

    public void setRunBadges(int runBadges) {
        this.runBadges = runBadges;
    }

    public int getPlankBadges() {
        return plankBadges;
    }

    public void setPlankBadges(int plankBadges) {
        this.plankBadges = plankBadges;
    }

    public int getPushupBadges() {
        return pushupBadges;
    }

    public void setPushupBadges(int pushupBadges) {
        this.pushupBadges = pushupBadges;
    }

    public int getPullupBadges() {
        return pullupBadges;
    }

    public void setPullupBadges(int pullupBadges) {
        this.pullupBadges = pullupBadges;
    }

    public int getSitupBadges() {
        return situpBadges;
    }

    public void setSitupBadges(int situpBadges) {
        this.situpBadges = situpBadges;
    }

    public int getSquatBadges() {
        return squatBadges;
    }

    public void setSquatBadges(int squatBadges) {
        this.squatBadges = squatBadges;
    }

    public int getTricepBadges() {
        return tricepBadges;
    }

    public void setTricepBadges(int tricepBadges) {
        this.tricepBadges = tricepBadges;
    }

    public int getJumpingBadges() {
        return jumpingBadges;
    }

    public void setJumpingBadges(int jumpingBadges) {
        this.jumpingBadges = jumpingBadges;
    }

    public int getLungeBadges() {
        return lungeBadges;
    }

    public void setLungeBadges(int lungeBadges) {
        this.lungeBadges = lungeBadges;
    }
}
