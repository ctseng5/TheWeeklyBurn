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

    public Workout() {

    }

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

    public float getRun() {
        return run;
    }

    public void setRun(float run) {
        this.run = run;
    }

    public int getPlank() {
        return plank;
    }

    public void setPlank(int plank) {
        this.plank = plank;
    }

    public int getPushups() {
        return pushups;
    }

    public void setPushups(int pushups) {
        this.pushups = pushups;
    }

    public int getPullups() {
        return pullups;
    }

    public void setPullups(int pullups) {
        this.pullups = pullups;
    }

    public int getSitups() {
        return situps;
    }

    public void setSitups(int situps) {
        this.situps = situps;
    }

    public int getSquats() {
        return squats;
    }

    public void setSquats(int squats) {
        this.squats = squats;
    }

    public int getTricepDips() {
        return tricepDips;
    }

    public void setTricepDips(int tricepDips) {
        this.tricepDips = tricepDips;
    }

    public int getJumpingJacks() {
        return jumpingJacks;
    }

    public void setJumpingJacks(int jumpingJacks) {
        this.jumpingJacks = jumpingJacks;
    }

    public int getLunges() {
        return lunges;
    }

    public void setLunges(int lunges) {
        this.lunges = lunges;
    }
}
