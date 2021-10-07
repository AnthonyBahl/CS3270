package edu.weber.cs.w01113559.mypracticecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class displayFragment extends Fragment {

    private View root;
    private TextView equationDisplay;
    private TextView resultPreviewDisplay;
    private SharedPreferences prefs;
    private String currentDisplay;
    private char[] cOperators = { '%', '÷', '×', '−', '+' };

    public displayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_display, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Instantiate Variables
        prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        equationDisplay = root.findViewById(R.id.userInputDisplay);
        resultPreviewDisplay = root.findViewById(R.id.resultPreviewDisplay);

        // Populate Display with Saved Preferences
        String sSavedDisplay = prefs.getString("display", "0");
        updateDisplay(sSavedDisplay);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save Current display to the shared preferences
        prefs.edit()
                .putString("display", currentDisplay)
                .apply();
    }

    /**
     * Updates the display then calls updateResultsDisplay.
     * @param sDisplay String: string of equation.
     */
    private void updateDisplay(String sDisplay) {
        sDisplay = handleLeadingZeroes(sDisplay);   // Remove Leading Zeroes if appropriate
        currentDisplay = sDisplay;
        equationDisplay.setText(sDisplay);
        updateResultPreview();
    }

    /**
     * Removes the last character of the current display.
     */
    private void backspace() {
        updateDisplay(currentDisplay.substring(0,currentDisplay.length()-1));
    }

    /**
     * Updates the results preview text view
     */
    private void updateResultPreview() {
        if (currentDisplay == "0") {
            resultPreviewDisplay.setText("");   // Erase contents of result preview
        } else {
            // ToDo: Solve the current equation and display result
        }
    }

    /**
     * Removes any inappropriate leading zeroes
     * @param value
     * @return
     */
    private String handleLeadingZeroes(String value) {

        if (value.length() >= 2) {
            // Remove leading zeroes
            while (value.charAt(0) == '0' && value.charAt(1) != '.') {
                value = value.substring(1);
            }
        }
        return value;
    }

    /**
     * Finds the first instance of any of the characters in the cArr array.
     * @param cArr char[]: array of characters to search for.
     * @param mString String: string to search through.
     * @return int: index of first instance of one of the characters in cArr. If none are found, returns -1.
     */
    private int findFirst(char[] cArr, String mString) {

        // Loop Through String
        for (int i = 0; i < mString.length(); i++) {

            char c = mString.charAt(i);

            // Check each character in the array
            for (int j = 0; j < cArr.length; j++) {
                if (c == cArr[j]) { return i; }
            }

        }

        return -1;
    }

    /**
     * Finds the last instance of any of the characters in the cArr array.
     * @param cArr char[]: array of characters to search for.
     * @param mString String: string to search through.
     * @return int: index of last instance of one of the characters in cArr. If none are found, returns -1.
     */
    private int findLast(char[] cArr, String mString) {

        // Loop Through String Backwards
        for (int i = mString.length()-1; i >= 0; i--) {

            char c = mString.charAt(i);

            // Check each character in the array
            for (int j = 0; j < cArr.length; j++) {
                if (c == cArr[j]) { return i; }
            }

        }

        return -1;
    }

    /**
     * Retrieves the current number (taking operators into account)
     * @return Current nubmer being worked on.
     */
    private String getCurrentNumber() {

        int lastOperator = findLast(cOperators, currentDisplay);

        if (lastOperator == -1) { return currentDisplay; }  // No operators yet

        return currentDisplay.substring(lastOperator + 1);
    }

    /**
     * Processes input from the user and adds it to the display.
     * @param tag String: Tag of the button pressed by the user.
     */
    public void processInput(String tag){
        switch (tag) {

            // Numbers
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                updateDisplay(currentDisplay.concat(tag));
                break;

            // Decimal
            case ".":
                if (getCurrentNumber().indexOf('.') == -1) { updateDisplay(currentDisplay.concat(tag)); }   // Make sure that current number doesn't already have a decimal.
                break;

            // Sign
            case "change_sign":
                // Find Start of Number
                char[] cArr = new char[cOperators.length + 2];
                cArr[0] = '(';
                cArr[1] = ')';
                for (int i = 0; i < cOperators.length; i++) {
                    cArr[i+2] = cOperators[i];
                }
                int lastSymbol = findLast(cArr, currentDisplay);

                // Change Sign
                if (lastSymbol == -1) {
                    if (currentDisplay.charAt(0) != '-') {
                        updateDisplay("-".concat(currentDisplay));
                    } else {
                        updateDisplay(currentDisplay.substring(1));
                    }
                } else {
                    if (currentDisplay.charAt(lastSymbol+1) != '-') {   // Insert negative
                        String startOfString = currentDisplay.substring(0,lastSymbol);
                        String endOfString = currentDisplay.substring(lastSymbol+1);
                        updateDisplay(startOfString + "-" + endOfString);
                    } else {    // Remove Negative
                        String startOfString = currentDisplay.substring(0,lastSymbol);
                        String endOfString = currentDisplay.substring(lastSymbol+2);
                        updateDisplay(startOfString + endOfString);
                    }
                }


                break;

            // Operators
            case "%":
            case "÷":
            case "×":
            case "−":
            case "+":
                if (Character.isDigit(currentDisplay.charAt(currentDisplay.length()-1))) { updateDisplay(currentDisplay.concat(tag)); }   // Make sure that the last character in the string is a digit and not another operator or a decimal
                break;

            // Parenthesis
            case "parenthesis":
                if ( currentDisplay.lastIndexOf('(') > currentDisplay.lastIndexOf(')') ) { // There is an open parentheses

                    if (currentDisplay.charAt(currentDisplay.length() - 1) == '(') {

                        backspace();    // Remove the unnecessary open parenthesis (nothing was placed between opening parenthesis)

                    } else {

                        updateDisplay(currentDisplay.concat(")"));  // Close parentheses

                    }
                } else {

                    updateDisplay(currentDisplay.concat("("));

                }
                break;

            // Clear
            case "clear":
                updateDisplay("0");
                break;

            // Back Space
            case "backspace":
                backspace();
                break;
        }
    }
}