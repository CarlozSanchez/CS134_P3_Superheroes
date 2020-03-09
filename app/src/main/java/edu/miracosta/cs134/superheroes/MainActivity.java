package edu.miracosta.cs134.superheroes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.miracosta.cs134.superheroes.model.JSONLoader;
import edu.miracosta.cs134.superheroes.model.Superhero;


public class MainActivity extends AppCompatActivity {

    // Constants
    public static final int QUESTIONS_IN_QUIZ = 10;
    public static final String FILE_NAME = "FileName";
    public static final String SUPERPOWER = "Superpower";
    public static final String ONE_THING = "OneThing";

    // Fields
    private int mCorrectGuesses;
    private int mTotalGuesses;
    private Superhero mCorrectHero;

    // Resources
    private List<Superhero> mAllSuperheroesList;
    private List<Superhero> mQuizSuperheroesList;
    private AssetManager am;
    private InputStream inStream;
    private SecureRandom rng;
    private Handler handler;

    // GUI Components
    private TextView mQuestionNumberTextView;
    private ImageView mSuperheroImageView;
    private TextView mAnswerTextView;
    private Button[] mButtons = new Button[4];


    private int mChoices = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiage resources
        mQuizSuperheroesList = new ArrayList<>(QUESTIONS_IN_QUIZ);
        rng = new SecureRandom();
        handler = new Handler();

        // References the GUI components
        mQuestionNumberTextView = findViewById(R.id.questionNumberTextView);
        mSuperheroImageView = findViewById(R.id.superheroImageView);
        mAnswerTextView = findViewById(R.id.answerTextView);
        mButtons[0] = findViewById(R.id.button1);
        mButtons[1] = findViewById(R.id.button2);
        mButtons[2] = findViewById(R.id.button3);
        mButtons[3] = findViewById(R.id.button4);

        // Update question text
        mQuestionNumberTextView.setText(getString(R.string.question, 1, QUESTIONS_IN_QUIZ));

        // Try to load superheroes for quiz from JSON loader
        try {
            mAllSuperheroesList = JSONLoader.loadJSONFromAsset(this);
        }catch(IOException e)
        {
            Log.e("Superhero Main", e.getMessage());
        }

//        for (Superhero superhero:mAllSuperheroesList){
//            Log.i("superheroes", superhero.toString());
//        }

        resetQuiz();
    }

    public void resetQuiz()
    {
        mCorrectGuesses = 0;
        mTotalGuesses = 0;

        mQuizSuperheroesList.clear();

        Superhero randomHero;

        while(mQuizSuperheroesList.size() < QUESTIONS_IN_QUIZ)
        {
            randomHero = mAllSuperheroesList.get(rng.nextInt(mAllSuperheroesList.size()));

            mQuizSuperheroesList.add(randomHero);
        }

        loadNextHero();
    }

    public void loadNextHero()
    {
        mCorrectHero = mQuizSuperheroesList.remove( 0);
        mAnswerTextView.setText("");
        mQuestionNumberTextView.setText(getString(R.string.question,
                QUESTIONS_IN_QUIZ - mQuizSuperheroesList.size(), QUESTIONS_IN_QUIZ));

        am = getAssets();
        try{
             inStream = am.open(mCorrectHero.getFileName());
             Drawable image = Drawable.createFromStream(inStream, mCorrectHero.getName());
             mSuperheroImageView.setImageDrawable(image);
        }
        catch(IOException e)
        {
            Log.e("MainActivity", "Unable to load image from " + mCorrectHero.getFileName());
        }
        Collections.shuffle(mAllSuperheroesList);

        for (int i = 0; i < mChoices; i++) {
            mButtons[i].setEnabled(true);
            mButtons[i].setText(mAllSuperheroesList.get(i).getName());
        }

        int correctButton = rng.nextInt(mChoices);
        mButtons[correctButton].setText(mCorrectHero.getName());
    }

    public void makeGuess(View view){
        Button clickedButton = (Button)view;

        String guess = clickedButton.getText().toString();
        mTotalGuesses++;

        if(guess.equals(mCorrectHero.getName()))
        {
            mCorrectGuesses++;
            // disable all buttons
            for (int i = 0; i < mButtons.length; i++) {
                mButtons[i].setEnabled(false);
            }

            mAnswerTextView.setTextColor(getResources().getColor(R.color.correct_answer));
            mAnswerTextView.setText(mCorrectHero.getName());

            if(mCorrectGuesses < QUESTIONS_IN_QUIZ)
            {
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        loadNextHero();
                    }
                }, 2000);
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.results, mTotalGuesses,
                        (double) mCorrectGuesses/mTotalGuesses * 100));

                builder.setPositiveButton("Reset Quiz", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        resetQuiz();
                    }
                });

                builder.setCancelable(false);
                builder.create();
                builder.show();
            }
        }
        else
        {
            clickedButton.setEnabled(false);
        }

    }
}
