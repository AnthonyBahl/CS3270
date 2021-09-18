package edu.weber.cs.w01113559.cs3270a3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    /**
     * Holds the main View that is this fragment.
     */
    private View root;

    // Counters
    /**
     * Counts the number of games that have been played.
     */
    private int games_played_count = 0;
    /**
     * Counts the number of games that the player has won.
     */
    private int player_win_count = 0;
    /**
     * Counts the number of games that the phone has won.
     */
    private int phone_win_count = 0;
    /**
     * Counts the number of games that have ended in a draw.
     */
    private int tie_game_count = 0;

    // Text Views
    /**
     * Text view to output how many games have been played.
     */
    private TextView tv_games_played;
    /**
     * Text view to output how many games the player has won.
     */
    private TextView tv_player_wins;
    /**
     * Text view to output how many games the phone has won.
     */
    private TextView tv_phone_wins;
    /**
     * Text view to output how many games have ended in a draw.
     */
    private TextView tv_tie_games;

    /**
     * Required empty public constructor.
     */
    public ResultsFragment() {
    }

    /**
     * Happens right after onCreate(), and is where we return to after onDestroyView().
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the view for this fragment, saved to the root variable.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_results, container, false);
    }

    /**
     * Happens after onActivityCreated().
     */
    @Override
    public void onStart() {
        super.onStart();

        // Find the text views that we created in XML
        tv_games_played = root.findViewById(R.id.games_played_output);
        tv_player_wins = root.findViewById(R.id.my_win_count_output);
        tv_phone_wins = root.findViewById(R.id.phone_win_count_output);
        tv_tie_games = root.findViewById(R.id.tie_games_count_output);

        // Find buttons that we created in the XML
        Button btn_Reset_counts = root.findViewById(R.id.btn_reset_counts);

        btn_Reset_counts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reset all count values
                games_played_count = 0;
                player_win_count = 0;
                phone_win_count = 0;
                tie_game_count = 0;

                // Update the text fields
                updateResultText();

                // Create and show message that the counts have been reset.
                Toast toast = Toast.makeText(getActivity(), R.string.counts_reset, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    /**
     * Updates the results field based on the outcome of the game.
     * @param result 1-Player Win, 2-Phone Win, 3-Tie Game
     */
    public void updateResults(int result) {
        switch (result){
            case 1:
                player_win_count++;
                games_played_count++;
                break;
            case 2:
                phone_win_count++;
                games_played_count++;
                break;
            case 3:
                tie_game_count++;
                games_played_count++;
                break;
        }

        updateResultText();
    }

    /**
     * Updates the text to reflect the counter values.
     */
    private void updateResultText() {
        tv_games_played.setText(String.format(Locale.getDefault(), "%d", games_played_count));
        tv_player_wins.setText(String.format(Locale.getDefault(), "%d", player_win_count));
        tv_phone_wins.setText(String.format(Locale.getDefault(), "%d", phone_win_count));
        tv_tie_games.setText(String.format(Locale.getDefault(), "%d", tie_game_count));
    }
}