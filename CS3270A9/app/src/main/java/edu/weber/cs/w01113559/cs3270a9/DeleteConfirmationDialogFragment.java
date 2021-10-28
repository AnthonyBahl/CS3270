package edu.weber.cs.w01113559.cs3270a9;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeleteConfirmationDialogFragment extends DialogFragment {

    private deleteDialogInterface mCallback;

    interface deleteDialogInterface{
        void confirmDelete();
    }

    public DeleteConfirmationDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (deleteDialogInterface) requireActivity().getSupportFragmentManager().findFragmentByTag("courseEditFrag");
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " needs to implement deleteDialogInterface.");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage(R.string.delete_dialog_message)
                .setTitle(R.string.are_you_sure)
                .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.confirmDelete();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}