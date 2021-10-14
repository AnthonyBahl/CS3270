package edu.weber.cs.w01113559.fullscreendialogdemo;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FullScreenDialogFragment extends DialogFragment {

    public FullScreenDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Fullscreendialogdemo_Dialog_FullScreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireDialog().getWindow().setWindowAnimations(R.style.Theme_Fullscreendialogdemo_DialogAnimation);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // the close icon is the navagation that we set in our xml
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dismiss();
            }
        });

        toolbar.setTitle(R.string.fullscreen_title);    // Not necessary since we already did it, but we could have done it that way.

        toolbar.inflateMenu(R.menu.fullscreen_dialog);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        // Do Something.
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().setWindowAnimations(R.style.Theme_Fullscreendialogdemo_DialogAnimation);

        return dialog;
    }
}