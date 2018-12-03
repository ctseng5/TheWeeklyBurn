/**
 * Workout class
 * @authors: Jeffrey Kao & Michael Tseng
 * Creates a Workout object
 */

package csx060.uga.edu.theweeklyburn;

public class Workout {

    private float run;
    private int plank;
    private int pushups;
    private int pullups;
    private int situps;
    private int squats;
    private int tricepDips;
    private int jumpingJacks;
    private int lunges;

    /**
     * Empty constructor for workout
     */
    public Workout() {

    }

    /**
     * Constructor for workout
     * @param run
     * @param plank
     * @param pushups
     * @param pullups
     * @param situps
     * @param squats
     * @param tricepDips
     * @param jumpingJacks
     * @param lunges
     */
    public Workout(float run, int plank, int pushups, int pullups, int situps, int squats, int tricepDips, int jumpingJacks, int lunges) {
        this.run = run;
        this.plank = plank;
        this.pushups = pushups;
        this.pullups = pullups;
        this.situps = situps;
        this.squats = squats;
        this.tricepDips = tricepDips;
        this.jumpingJacks = jumpingJacks;
        this.lunges = lunges;
    }

    /**
     * Get the miles run
     * @return
     */
    public float getRun() {
        return run;
    }

    /**
     * Set the miles run
     * @param run
     */
    public void setRun(float run) {
        this.run = run;
    }

    /**
     * Get the number of planks
     * @return
     */
    public int getPlank() {
        return plank;
    }

    /**
     * Set the number of planks
     * @param plank
     */
    public void setPlank(int plank) {
        this.plank = plank;
    }

    /**
     * Get the number of pushups
     * @return
     */
    public int getPushups() {
        return pushups;
    }

    /**
     * Set the number of pushups
     * @param pushups
     */
    public void setPushups(int pushups) {
        this.pushups = pushups;
    }

    /**
     * Get the number of pullups
     * @return
     */
    public int getPullups() {
        return pullups;
    }

    /**
     * Set the number of pullups
     * @param pullups
     */
    public void setPullups(int pullups) {
        this.pullups = pullups;
    }

    /**
     * Get the number of situps
     * @return
     */
    public int getSitups() {
        return situps;
    }

    /**
     * Set the number of situps
     * @param situps
     */
    public void setSitups(int situps) {
        this.situps = situps;
    }

    /**
     * Get the number of squats
     * @return
     */
    public int getSquats() {
        return squats;
    }

    /**
     * Set the number of squats
     * @param squats
     */
    public void setSquats(int squats) {
        this.squats = squats;
    }

    /**
     * Get the number of tricep dips
     * @return
     */
    public int getTricepDips() {
        return tricepDips;
    }

    /**
     * Set the number of tricep dips
     * @param tricepDips
     */
    public void setTricepDips(int tricepDips) {
        this.tricepDips = tricepDips;
    }

    /**
     * Get the number of jumping jacks
     * @return
     */
    public int getJumpingJacks() {
        return jumpingJacks;
    }

    /**
     * Set the number of jumping jacks
     * @param jumpingJacks
     */
    public void setJumpingJacks(int jumpingJacks) {
        this.jumpingJacks = jumpingJacks;
    }

    /**
     * Get the number of lunges
     * @return
     */
    public int getLunges() {
        return lunges;
    }

    /**
     * Set the number of lunges
     * @param lunges
     */
    public void setLunges(int lunges) {
        this.lunges = lunges;
    }
}
