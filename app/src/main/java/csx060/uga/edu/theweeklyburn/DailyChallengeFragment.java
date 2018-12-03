package csx060.uga.edu.theweeklyburn;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyChallengeFragment extends Fragment {

    private TextView dayOfTheWeek;
    private TextView activity1;
    private TextView activity2;
    private TextView activity3;
    private TextView activity4;
    private EditText quant1;
    private EditText quant2;
    private EditText quant3;
    private EditText quant4;
    private Button submit;
    private TextView countdown;

    float prevRun = 0;
    int prevPlank = 0;
    int prevPushups = 0;
    int prevPullups = 0;
    int prevSitups = 0;
    int prevSquats = 0;
    int prevTricepDips = 0;
    int prevJumpingJacks = 0;
    int prevLunges = 0;

    long diff;
    long oldLong;
    long newLong;

    private FirebaseAuth auth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Workouts");

    /**
     *
     */
    public DailyChallengeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get Firebase Auth instance
        auth = FirebaseAuth.getInstance();

        //Get Firebase previous workout values
        Calendar calendar = Calendar.getInstance();
        DatabaseReference dateRef = ref.child("Year" + calendar.get(Calendar.YEAR) +
                "Week" + calendar.get(Calendar.WEEK_OF_YEAR));
        getPrevValues(dateRef);

        //Setup views
        View view = inflater.inflate(R.layout.fragment_daily_challenge, container, false);

        dayOfTheWeek = view.findViewById(R.id.day);
        activity1 = view.findViewById(R.id.activity1);
        activity2 = view.findViewById(R.id.activity2);
        activity3 = view.findViewById(R.id.activity3);
        activity4 = view.findViewById(R.id.activity4);
        quant1 = view.findViewById(R.id.quant1);
        quant2 = view.findViewById(R.id.quant2);
        quant3 = view.findViewById(R.id.quant3);
        quant4 = view.findViewById(R.id.quant4);
        submit = view.findViewById(R.id.submitWorkout);
        countdown = view.findViewById(R.id.countdown);
        quant1.addTextChangedListener(new TextChangeListener());
        quant2.addTextChangedListener(new TextChangeListener());
        quant3.addTextChangedListener(new TextChangeListener());
        quant4.addTextChangedListener(new TextChangeListener());

        submit.setOnClickListener(new SubmitWorkoutClickListener());

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyy, HH:mm");
        //oldLong = System.currentTimeMillis();
        String oldTime = getCurrentTime();
        String newTime = getTomorrowsDate();
        Date oldDate, newDate;
        try {
            oldDate = formatter.parse(oldTime);
            newDate = formatter.parse(newTime);
            oldLong = oldDate.getTime();
            newLong = newDate.getTime();
            diff = newLong - oldLong;
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        MyCount counter = new MyCount(10000, 1000);
        MyCount counter = new MyCount(diff, 1000);
        counter.start();

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                dayOfTheWeek.setText("Sunday");
                activity1.setText("Run/Jog");
                activity2.setText("Plank");
                activity3.setText("Push-Ups");
                activity4.setText("Sit-Ups");
                break;
            case Calendar.MONDAY:
                dayOfTheWeek.setText("Monday");
                activity1.setText("Run/Jog");
                activity2.setText("Plank");
                activity3.setText("Pull-Ups");
                activity4.setText("Squats");
                break;
            case Calendar.TUESDAY:
                dayOfTheWeek.setText("Tuesday");
                activity1.setText("Run/Jog");
                activity2.setText("Plank");
                activity3.setText("Tricep Dips");
                activity4.setText("Jumping Jacks");
                break;
            case Calendar.WEDNESDAY:
                dayOfTheWeek.setText("Wednesday");
                activity1.setText("Run/Jog");
                activity2.setText("Plank");
                activity3.setText("Push-Ups");
                activity4.setText("Lunges");
                break;
            case Calendar.THURSDAY:
                dayOfTheWeek.setText("Thursday");
                activity1.setText("Run/Jog");
                activity2.setText("Plank");
                activity3.setText("Pull-Ups");
                activity4.setText("Sit-Ups");
                break;
            case Calendar.FRIDAY:
                dayOfTheWeek.setText("Friday");
                activity1.setText("Run/Jog");
                activity2.setText("Plank");
                activity3.setText("Tricep Dips");
                activity4.setText("Lunges");
                break;
            case Calendar.SATURDAY:
                dayOfTheWeek.setText("Saturday");
                activity1.setText("Run/Jog");
                activity2.setText("Plank");
                activity3.setText("Jumping Jacks");
                activity4.setText("Squats");
                break;
        }

        return view;
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy, HH:mm");
        return dateFormat.format(today);
    }

    private String getTomorrowsDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy");
        return dateFormat.format(tomorrow) + ", 00:00";
    }

    public class MyCount extends CountDownTimer {
        MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            getActivity().recreate();
            Toast.makeText(getActivity(), "It's a new day with a new Daily Challenge!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = (TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)) + ":")
                    + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)) + ":"
                    + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            countdown.setText(/*context.getString(R.string.ends_in) + " " +*/ hms);
        }
    }

    public class TextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkFields();
        }

        @Override
        public void afterTextChanged(Editable editable) {        }
    }

    public void checkFields() {
        if (quant1.getText().toString().isEmpty() && quant2.getText().toString().isEmpty() &&
                quant3.getText().toString().isEmpty() && quant4.getText().toString().isEmpty()) {
            submit.setEnabled(false);
        } else {
            submit.setEnabled(true);
        }
    }

    public class SubmitWorkoutClickListener implements View.OnClickListener {

//        float prevRun = 0;
//        int prevPlank = 0;
//        int prevPushups = 0;
//        int prevPullups = 0;
//        int prevSitups = 0;
//        int prevSquats = 0;
//        int prevTricepDips = 0;
//        int prevJumpingJacks = 0;
//        int prevLunges = 0;

//        float prevRun ;
//        int prevPlank ;
//        int prevPushups ;
//        int prevPullups ;
//        int prevSitups ;
//        int prevSquats ;
//        int prevTricepDips ;
//        int prevJumpingJacks ;
//        int prevLunges ;

        @Override
        public void onClick(View view) {
            float run;
            int plank;
            int act1;
            int act2;

            Calendar calendar = Calendar.getInstance();
            DatabaseReference dateRef = ref.child("Year" + calendar.get(Calendar.YEAR) +
                    "Week" + calendar.get(Calendar.WEEK_OF_YEAR));

            getPrevValues(dateRef);

            //Get run value
            String runText = quant1.getText().toString();
            if (runText.equals("")) {
                run = 0;
            }
            else {
                run = Float.valueOf(runText);
            }

            //Get plank value
            String plankString = quant2.getText().toString();
            if (plankString.equals("")) {
                plank = 0;
            }
            else {
                plank = Integer.parseInt(plankString);
            }

            //Get act1 value
            String act1Text = quant3.getText().toString();
            if (act1Text.equals("")) {
                act1 = 0;
            }
            else {
                act1 = Integer.parseInt(act1Text);
            }

            //Get act2 value
            String act2Text = quant4.getText().toString();
            if (act2Text.equals("")) {
                act2 = 0;
            }
            else {
                act2 = Integer.parseInt(act2Text);
            }

            //Write to the database


            int day = calendar.get(Calendar.DAY_OF_WEEK);

            //Toast.makeText(getActivity(), prevPushups, Toast.LENGTH_LONG).show();
            switch (day) {
                case Calendar.SUNDAY:
                    dateRef.child(auth.getUid()).setValue(new Workout(prevRun+run, prevPlank+plank,
                            prevPushups+act1, 0, prevSitups+act2, 0, 0, 0, 0));
                    break;
                case Calendar.MONDAY:
                    dateRef.child(auth.getUid()).setValue(new Workout(prevRun+run, prevPlank+plank,
                            0, prevPullups+act1, 0, prevSquats+act2, 0, 0, 0));
                    break;
                case Calendar.TUESDAY:
                    dateRef.child(auth.getUid()).setValue(new Workout(prevRun+run, prevPlank+plank,
                            0, 0, 0, 0, prevTricepDips+act1, prevJumpingJacks+act2, 0));
                    break;
                case Calendar.WEDNESDAY:
                    dateRef.child(auth.getUid()).setValue(new Workout(prevRun+run, prevPlank+plank,
                            prevPushups+act1, 0, 0, 0, 0, 0, prevLunges+act2));
                    break;
                case Calendar.THURSDAY:
                    dateRef.child(auth.getUid()).setValue(new Workout(prevRun+run, prevPlank+plank,
                            0, prevPullups+act1, prevSitups+act1, 0, 0, 0, 0));
                    break;
                case Calendar.FRIDAY:
                    dateRef.child(auth.getUid()).setValue(new Workout(prevRun+run, prevPlank+plank,
                            0, 0, 0, 0, prevTricepDips+act1, 0, prevLunges+act2));
                    break;
                case Calendar.SATURDAY:
                    dateRef.child(auth.getUid()).setValue(new Workout(prevRun+run, prevPlank+plank,
                            0, 0, 0, prevSquats+act2, 0, prevJumpingJacks+act1, 0));
                    break;
            }

            resetEditText();
            Toast.makeText(getActivity(), "Your workout has been recorded!", Toast.LENGTH_LONG).show();
//            Toast.makeText(getActivity(), "Your workout has been recorded!" +
//                                                "\nRun: " + run +
//                                                "\nPlank: " + plank +
//                                                "\nAct1: " + act1 +
//                                                "\nAct2: " + act2, Toast.LENGTH_LONG).show();
        }
    }

    public void getPrevValues(DatabaseReference dateRef) {
        dateRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Workout prevWorkout = dataSnapshot.getValue(Workout.class);
                if(prevWorkout != null) {
                    prevRun = prevWorkout.getRun();
                    prevPlank = prevWorkout.getPlank();
                    prevPushups = prevWorkout.getPushups();
                    prevPullups = prevWorkout.getPullups();
                    prevSitups = prevWorkout.getSitups();
                    prevSquats = prevWorkout.getSquats();
                    prevTricepDips = prevWorkout.getTricepDips();
                    prevJumpingJacks = prevWorkout.getJumpingJacks();
                    prevLunges = prevWorkout.getLunges();
                }
                //Toast.makeText(getActivity(), prevPlank, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void resetEditText() {
        quant1.setText("");
        quant2.setText("");
        quant3.setText("");
        quant4.setText("");
    }

}
