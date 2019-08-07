package com.natali.voicelearningapp.KidApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;

import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.KidData.MAin.WordsBank;
import com.natali.voicelearningapp.data.KidData.Numbers;
import com.natali.voicelearningapp.resiverAndServices.ServiceForLooperGetApps;

import java.util.ArrayList;

@SuppressLint("Registered")
public class DialogViewHearAndRecordFromHE extends BaseGame {
    String choosenWordInHebrewInEnglishLatters;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_hear_and_record_from_he);

        choosenWordInEnglish = WordsBank.getMeTheRightWordInEnglish();
        choosenWordInHebrew = WordsBank.giveMeTranslate(choosenWordInEnglish);
        choosenWordInHebrewInEnglishLatters = WordsBank.giveMeTranslateInHebrewButInEnglishLetters(choosenWordInEnglish);

        view = findViewById(R.id.dialog);
        textView = findViewById(R.id.textForDialog);

        initTextToSpeach(choosenWordInHebrewInEnglishLatters);

        view.setOnClickListener(v -> talk(choosenWordInHebrewInEnglishLatters));
        findViewById(R.id.image).setOnClickListener(view -> speechREcordingInput("en-US"));

        talk(choosenWordInHebrewInEnglishLatters);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RECORDING_CODE_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.contains(choosenWordInEnglish)||result.contains(choosenWordInEnglish.toLowerCase())||result.contains(""+ Numbers.getMeThisNumber(choosenWordInEnglish))){
                        ServiceForLooperGetApps.getCurentPerson().addOnePoint();
                        textView.setText("יאיי צדקת!!");
                        FirebaseData.setNewEvent("בתרגיל של שמיעת המילה בעברית ולומר אותה באנגלית הילד/ה צדק!!! במילה: "+choosenWordInEnglish);

                    }
                    else{
                        textView.setText("התשובה הנכונה היא: \n"+choosenWordInEnglish);
                        initTextToSpeach( choosenWordInEnglish);
                        FirebaseData.setNewEvent("בתרגיל של שמיעת המילה בעברית ולומר אותה באנגלית הילד/ה טעה!!! במילה: "+choosenWordInEnglish);

                    }
                    endWithIt();
                }

                break;
            }
        }
    }
}
