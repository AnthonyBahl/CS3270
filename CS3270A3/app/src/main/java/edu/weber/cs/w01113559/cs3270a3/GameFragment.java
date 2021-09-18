package edu.weber.cs.w01113559.cs3270a3;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    /**
     * Holds the main View that is this fragment.
     */
    private View root;
    /**
     * Text view to display the result of the current game.
     */
    private TextView tv_game_results;
    /**
     * Text view to display the choice that the phone made.
     */
    private TextView tv_phone_choice;
    /**
     * "Rock" Button.
     */
    private Button btn_rock;
    /**
     * "Paper" Button.
     */
    private Button btn_paper;
    /**
     * "Scissors" Button.
     */
    private Button btn_scissors;
    /**
     * onButtonListener on the Activities end that will allow us to communicate with it.
     */
    private onButtonListener mCallback;

    /**
     * Interface that will require the activity to implement the fragButtonPressed function.
     */
    public interface onButtonListener{
        /**
         * Updates the historical game results.
         * @param result 1-Player Win, 2-Phone Win, 3-Tie Game
         */
        void fragButtonPressed(int result);
    }

    /**
     * Required empty public constructor.
     */
    public GameFragment() {
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
        return root = inflater.inflate(R.layout.fragment_game, container, false);
    }

    /**
     * Happens after onActivityCreated().
     */
    @Override
    public void onStart() {
        super.onStart();

        // Find the text views that we created in XML
        tv_game_results = root.findViewById(R.id.game_result);
        tv_phone_choice = root.findViewById(R.id.phone_result);

        // Find the buttons that we created in XML
        btn_rock = root.findViewById(R.id.btn_rock);
        btn_paper = root.findViewById(R.id.btn_paper);
        btn_scissors = root.findViewById(R.id.btn_scissors);

        // Create Listener for rock button
        btn_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonColors(btn_rock);
                int game_results = determine_winner(1, phone_turn());
                update_result_text(game_results);
                mCallback.fragButtonPressed(game_results);
            }
        });

        // Create Listener for paper button
        btn_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonColors(btn_paper);
                int game_results = determine_winner(2, phone_turn());
                update_result_text(game_results);
                mCallback.fragButtonPressed(game_results);
            }
        });

        // Create Listener for scissors button
        btn_scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonColors(btn_scissors);
                int game_results = determine_winner(3, phone_turn());
                update_result_text(game_results);
                mCallback.fragButtonPressed(game_results);
            }
        });
    }

    /**
     * Very first thing that happens in the lifecycle of the fragment. Does not get repeated after onDestroyView().
     * @param activity The main activity for the app.
     */
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            // Make sure activity has the interface requirements
            mCallback = (onButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " Must implement onButtonListener");
        }
    }

    /**
     * Automates the phones turn by generating a random decision.
     * @return 1-Rock, 2-Paper, 3-Scissors
     */
    private int phone_turn() {

        // Create Random Number Generator
        Random rand = new Random();

        // Generate Phone's Choice
        int phone_decision = rand.nextInt(3) + 1;

        // Update Phone Choice Text
        update_phone_choice_text(phone_decision);

        // Return Phone Choice
        return phone_decision;

    }

    /**
     * Determines who the game winner is.
     * @param player_choice 1-Rock, 2-Paper, 3-Scissors
     * @param phone_choice 1-Rock, 2-Paper, 3-Scissors
     * @return 1-Player Win, 2-Phone Win, 3-Tie Game
     */
    private int determine_winner(int player_choice, int phone_choice) {

        switch (player_choice) {
            case 1: // Player Chooses Rock
                switch (phone_choice) {
                    case 1: // Phone Chooses Rock
                        return 3;
                    case 2: // Phone Chooses Paper
                        return 2;
                    default: // Phone Chooses Scissors
                        return 1;
                }
            case 2: // Player Chooses Paper
                switch (phone_choice) {
                    case 1: // Phone Chooses Rock
                        return 1;
                    case 2: // Phone Chooses Paper
                        return 3;
                    default: // Phone Chooses Scissors
                        return 2;
                }
            default: // Player Chooses Scissors
                switch (phone_choice) {
                    case 1: // Phone Chooses Rock
                        return 2;
                    case 2: // Phone Chooses Paper
                        return 1;
                    default: // Phone Chooses Scissors
                        return 3;
                }
        }
    }

    /**
     * Updates the game results text.
     * @param game_results 1-Player Win, 2-Phone Win, 3-Tie Game
     */
    private void update_result_text(int game_results) {
        switch (game_results) {
            case 1:
                tv_game_results.setText(getString(R.string.player_win));
                break;
            case 2:
                tv_game_results.setText(getString(R.string.phone_win));
                break;
            default:
                tv_game_results.setText(getString(R.string.tie_game));
                break;
        }
    }

    /**
     * Updates the "Phone Pick is" results
     * @param phone_choice 1-Rock, 2-Paper, 3-Scissors
     */
    private void update_phone_choice_text(int phone_choice) {
        switch (phone_choice) {
            case 1:
                tv_phone_choice.setText(getString(R.string.rock));
                break;
            case 2:
                tv_phone_choice.setText(getString(R.string.paper));
                break;
            default:
                tv_phone_choice.setText(getString(R.string.scissors));
                break;
        }
    }

    /**
     * Applies selected color to the button passed in, and resets the color of all other buttons.
     * @param selectedButton The button to turn the selected color.
     */
    private void setButtonColors(Button selectedButton) {

        // Reset all button colors to standard grey
        btn_rock.setBackgroundColor(getResources().getColor(R.color.button_grey));
        btn_paper.setBackgroundColor(getResources().getColor(R.color.button_grey));
        btn_scissors.setBackgroundColor(getResources().getColor(R.color.button_grey));

        // Set the color of the selected button
        selectedButton.setBackgroundColor(getResources().getColor(R.color.button_selected));
    }
}