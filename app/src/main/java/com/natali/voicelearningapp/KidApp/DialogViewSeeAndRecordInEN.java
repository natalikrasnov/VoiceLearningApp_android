package com.natali.voicelearningapp.KidApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.KidData.MAin.WordsBank;
import com.natali.voicelearningapp.data.KidData.Numbers;
import com.natali.voicelearningapp.resiverAndServices.ServiceForLooperGetApps;

import java.util.ArrayList;

@SuppressLint("Registered")
public class DialogViewSeeAndRecordInEN extends BaseGame {

    ImageView imageOfNumber;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_see_and_record_in_en);

        choosenWordInEnglish = WordsBank.getMeTheRightWordInEnglish();
        choosenWordInHebrew = WordsBank.giveMeTranslate(choosenWordInEnglish);

        view = findViewById(R.id.dialog);
        textView = findViewById(R.id.textForDialog);
        imageOfNumber = findViewById(R.id.imageView);

        imageOfNumber.setImageResource( WordsBank.getMeTheRightBackground() );

        initTextToSpeach(null);

        imageOfNumber.setOnClickListener(view -> speechREcordingInput("en-US"));

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
                    if(result.contains(choosenWordInEnglish)||result.contains(choosenWordInEnglish.toLowerCase())||result.contains(""+ Numbers.getMeThisNumber(choosenWordInEnglish))){
                        ServiceForLooperGetApps.getCurentPerson().addOnePoint();
                        textView.setText("יאיי צדקת!! ");
                        FirebaseData.setNewEvent("בתרגיל של לראות את המילה ולומר אותה באנגלית הילד/ה צדק!!! במילה: "+choosenWordInEnglish);
                    }
                    else{
                        textView.setText("התשובה הנכונה היא: \n"+choosenWordInEnglish);
                        initTextToSpeach(choosenWordInEnglish);
                        view.setOnClickListener(v -> talk(choosenWordInEnglish));
                        FirebaseData.setNewEvent("בתרגיל של לראות את המילה ולומר אותה באנגלית הילד/ה טעה!!! במילה: "+choosenWordInEnglish);
                    }
                    endWithIt();
                }

                break;
            }
        }
    }
}
