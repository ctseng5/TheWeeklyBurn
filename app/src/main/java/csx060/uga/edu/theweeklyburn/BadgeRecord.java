/**
 * Badge Record class
 * @authors: Jeffrey Kao & Michael Tseng
 * Creates a BadgeRecord object
 */

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

    /**
     * Empty constructor
     */
    public BadgeRecord() {

    }

    /**
     * Constructor for a BadgeRecord
     * @param runBadges
     * @param plankBadges
     * @param pushupBadges
     * @param pullupBadges
     * @param situpBadges
     * @param squatBadges
     * @param tricepBadges
     * @param jumpingBadges
     * @param lungeBadges
     */
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

    /**
     * Get # of run badges
     * @return
     */
    public int getRunBadges() {
        return runBadges;
    }

    /**
     * Set # of run badges
     * @param runBadges
     */
    public void setRunBadges(int runBadges) {
        this.runBadges = runBadges;
    }

    /**
     * Get # of plank badges
     * @return
     */
    public int getPlankBadges() {
        return plankBadges;
    }

    /**
     * set  # of plank badges
     * @param plankBadges
     */
    public void setPlankBadges(int plankBadges) {
        this.plankBadges = plankBadges;
    }

    /**
     * get  # of pushup badges
     * @return
     */
    public int getPushupBadges() {
        return pushupBadges;
    }

    /**
     * set # of pushup badges
     * @param pushupBadges
     */
    public void setPushupBadges(int pushupBadges) {
        this.pushupBadges = pushupBadges;
    }

    /**
     * get # of pullup badges
     * @return
     */
    public int getPullupBadges() {
        return pullupBadges;
    }

    /**
     * set  # of pullup badges
     * @param pullupBadges
     */
    public void setPullupBadges(int pullupBadges) {
        this.pullupBadges = pullupBadges;
    }

    /**
     * get # of situp badges
     * @return
     */
    public int getSitupBadges() {
        return situpBadges;
    }

    /**
     * set # of situp badges
     * @param situpBadges
     */
    public void setSitupBadges(int situpBadges) {
        this.situpBadges = situpBadges;
    }

    /**
     * get # of squat badges
     * @return
     */
    public int getSquatBadges() {
        return squatBadges;
    }

    /**
     * set # of squat badges
     * @param squatBadges
     */
    public void setSquatBadges(int squatBadges) {
        this.squatBadges = squatBadges;
    }

    /**
     * get # of tricep badges
     * @return
     */
    public int getTricepBadges() {
        return tricepBadges;
    }

    /**
     * set # of tricep badges
     * @param tricepBadges
     */
    public void setTricepBadges(int tricepBadges) {
        this.tricepBadges = tricepBadges;
    }

    /**
     * get # of jumping badges
     * @return
     */
    public int getJumpingBadges() {
        return jumpingBadges;
    }

    /**
     * set  # of  jumping badges
     * @param jumpingBadges
     */
    public void setJumpingBadges(int jumpingBadges) {
        this.jumpingBadges = jumpingBadges;
    }

    /**
     * get # of lunge badges
     * @return
     */
    public int getLungeBadges() {
        return lungeBadges;
    }

    /**
     * set # of lunge badges
     * @param lungeBadges
     */
    public void setLungeBadges(int lungeBadges) {
        this.lungeBadges = lungeBadges;
    }
}
