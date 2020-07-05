package akif.myappcompany.gameconnect3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    // 0: yellow, 1: red
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int yellowWins = 0;
    int redWins = 0;
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };
    
    int activePlayer = 0;

    int drawCount = 0;

    int clicked = 0;

    boolean gameActive = true;

    public void dropIn(View view)
    {
        ImageView counter = (ImageView) view;

        int tappedCounter =    Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive )
        {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            clicked++;

            for (int[] winningPosition : winningPositions)
            {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2)
                {
                    //Someone has won!

                    gameActive = false;

                    String winner;
                    if (activePlayer == 1)
                    {
                        winner = "Yellow";
                        TextView yellowScore = findViewById(R.id.yellowTextView);
                        yellowWins++;
                        yellowScore.setText(String.format("Yellow: %s", String.valueOf(yellowWins)));
                    }
                    else {
                        winner = "Red";
                        TextView redScore = findViewById(R.id.redTextView);
                        redWins++;
                        redScore.setText(String.format("Red: %s", String.valueOf(redWins)));
                    }

                    //Toast.makeText(this, winner + " has won!", Toast.LENGTH_LONG).show();

                    TextView winnerTextView = findViewById(R.id.winnerTextView);

                    winnerTextView.setText(String.format("%s has won!", winner));

                    winnerTextView.setVisibility(View.VISIBLE);

                    return;
                }
            }
            if(clicked == 9 )
            {
                drawCount++;

                TextView winnerTextView = findViewById(R.id.winnerTextView);

                winnerTextView.setText(R.string.draw);

                winnerTextView.setVisibility(View.VISIBLE);

                TextView drawTextView = findViewById(R.id.drawTextView);

                drawTextView.setText(String.format("Draw : %s", String.valueOf(drawCount)));
            }

        }


    }

    public void playAgain(View view)
    {

        TextView winnerTextView = findViewById(R.id.winnerTextView);

        winnerTextView.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount() ; i++)
        {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);
        }

        Arrays.fill(gameState, 2);
        activePlayer = 0;

        gameActive = true;

        clicked = 0;
    }

    public void newGame(View view)
    {
        redWins = 0;
        yellowWins = 0;
        drawCount = 0;
        TextView yellowScore = findViewById(R.id.yellowTextView);
        yellowScore.setText(String.format("Yellow: %s", String.valueOf(yellowWins)));

        TextView redScore = findViewById(R.id.redTextView);
        redScore.setText(String.format("Red: %s", String.valueOf(redWins)));

        TextView drawTextView = findViewById(R.id.drawTextView);
        drawTextView.setText(R.string.drawIni);

        playAgain(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}
