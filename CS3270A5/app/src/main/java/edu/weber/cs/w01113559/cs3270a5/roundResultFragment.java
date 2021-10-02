package edu.weber.cs.w01113559.cs3270a5;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class roundResultFragment extends DialogFragment {

    private int iResult;
    private dialogInterface mCallback;

    interface dialogInterface{
        /**
         * Sets the change so far to 0
         */
        void resetChangeSoFar();
    }

    /**
     * Fragment Constructor
     * @param result int: 1- Time ran out, 2- Went over on change, 3- Successfully made change.
     */
    public roundResultFragment(int result) {
        iResult = result;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Create a builder for the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        switch (iResult){
            case 1: // Time ran out
                builder.setTitle(R.string.times_up)
                        .setMessage(R.string._try_again);
                break;
            case 2: // Went over on Change
                builder.setTitle(R.string.too_much_change)
                        .setMessage(R.string._try_again);
                break;
            case 3: // Successfully made change.
                builder.setTitle(R.string.you_did_it)
                        .setMessage(R.string.go_again);
                break;
        }

        builder.setNeutralButton(R.string._ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCallback.resetChangeSoFar();
            }
        });


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        super.onAttach(activity);

        // Verify that the activity is implementing the timerActions interface.
        try {

            mCallback = (dialogInterface) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException( activity.toString() + " must implement the dialogInterface interface.");

        }
    }
}