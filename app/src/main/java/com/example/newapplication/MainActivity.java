package com.example.newapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AlertDialog alertDialog;
    private Difficulty tempDifficulty;

    private static final GuessNum guessNum = new GuessNum(Difficulty.EASY);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.attempt.setOnClickListener(this::onAttempt);

        createDialog();
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings);

        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            if(tempDifficulty != null)
                changeDifficulty(tempDifficulty);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            tempDifficulty = null;
        });

        String[] strings = new String[]{getResources().getString(R.string.easy),
                getResources().getString(R.string.medium),
                getResources().getString(R.string.hard)};

        final int[] chosen = {-1};

        builder.setSingleChoiceItems(strings, chosen[0], ((dialog, which) -> {
            chosen[0] = which;

            switch (which){
                case 0:
                    tempDifficulty = Difficulty.EASY;
                    break;
                case 1:
                    tempDifficulty = Difficulty.MEDIUM;
                    break;
                case 2:
                    tempDifficulty = Difficulty.HARD;
                    break;
            }
        }));

        alertDialog = builder.create();
    }

    public void onClear(View view){
        guessNum.restart();
        binding.hint.setText("");

        String str = binding.attemptsLeft.getText().toString();
        str = str.substring(0, str.lastIndexOf(":") + 2) + guessNum.getAttemptsLeft();
        binding.attemptsLeft.setText(str);

        binding.inputText.setText("");
    }

    private void onAttempt(View view){
        String input = binding.inputText.getText().toString();

        if(input.isEmpty()){
            binding.hint.setText(R.string.emptyField);
            return;
        }

        int userSuggestValue = Integer.parseInt(input);

        try{
            int compare = guessNum.compare(userSuggestValue);
            if(compare == 0) {
            onClear(view);
            Toast t = Toast.makeText(this.getApplicationContext(), R.string.congrats, Toast.LENGTH_LONG);
            t.show();
            return;
        }
        if(compare < 0)
            binding.hint.setText(R.string.greaterValue);
        else
            binding.hint.setText(R.string.lessValue);
        }
        catch (NoAttemptsException ex){
            binding.hint.setText(R.string.noAttempts);
        }

        String str = binding.attemptsLeft.getText().toString();
        str = str.substring(0, str.lastIndexOf(":") + 2) + guessNum.getAttemptsLeft();
        binding.attemptsLeft.setText(str);

    }

    public void onDigitButtonClick(View view){
        Button button = (Button) view;

        binding.inputText.setText(String.format("%s%s", binding.inputText.getText().toString(), button.getText().toString()));
    }

    public void onDelete(View view) {
        CharSequence charSequence = binding.inputText.getText();

        if(charSequence.length() > 0)
            binding.inputText.setText(charSequence.subSequence(0, charSequence.length() - 1));
    }

    public void changeDifficulty(Difficulty difficulty){
        guessNum.changeDifficulty(difficulty);
        binding.hint.setText("");


        String str = binding.attemptsLeft.getText().toString();
        str = str.substring(0, str.lastIndexOf(":") + 2) + guessNum.getAttemptsLeft();
        binding.attemptsLeft.setText(str);
    }

    public void onChangeDifficulty(View view) {
        alertDialog.show();
    }
}