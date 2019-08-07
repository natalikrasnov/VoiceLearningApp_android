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

public class DialogViewHearAndRecordFromEN extends BaseGame {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_hear_and_record_from_en);

        choosenWordInEnglish = WordsBank.getMeTheRightWordInEnglish();
        choosenWordInHebrew = WordsBank.giveMeTranslate(choosenWordInEnglish);

        view = findViewById(R.id.dialog);
        textView = findViewById(R.id.textForDialog);

        initTextToSpeach(choosenWordInEnglish);

        view.setOnClickListener(v -> talk(choosenWordInEnglish));
        findViewById(R.id.image).setOnClickListener(view -> speechREcordingInput("he-IL"));

        talk(choosenWordInEnglish);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RECORDING_CODE_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.contains(choosenWordInHebrew)||result.contains(""+ Numbers.getMeThisNumber(choosenWordInEnglish))){
                        ServiceForLooperGetApps.getCurentPerson().addOnePoint();
                        textView.setText("יאיי צדקת!! ");
                        FirebaseData.setNewEvent("בתרגיל של שמיעת המילה באנגלית ולומר אותה בעברית הילד/ה צדק!!! במילה: "+choosenWordInEnglish);

                    }
                    else{
                        textView.setText("התשובה הנכונה היא: \n"+choosenWordInHebrew);
                        initTextToSpeach( Numbers.getWordsInEnglishLatters().get(choosenWordInEnglish));
                        FirebaseData.setNewEvent("בתרגיל של שמיעת המילה באנגלית ולומר אותה בעברית הילד/ה טעה!!! במילה: "+choosenWordInEnglish);
                    }
                    endWithIt();
                }
                break;
            }
        }
    }
}
