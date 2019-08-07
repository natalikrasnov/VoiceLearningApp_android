package com.natali.voicelearningapp.KidApp;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

@SuppressLint("Registered")
public class BaseGame extends AppCompatActivity {


    final int RECORDING_CODE_INPUT = 50;
    TextToSpeech tts;
    TextView textView;
    String choosenWordInEnglish, choosenWordInHebrew;
    View view;

    void initTextToSpeach(String sayThis )     //-> Locale.en
    {
        tts = new TextToSpeech(this, status -> {
            // TODO Auto-generated method stub
            if(status == TextToSpeech.SUCCESS) {
                tts.speak(sayThis, TextToSpeech.QUEUE_FLUSH, null);
                int result = tts.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported");
                } else talk(sayThis);
            } else Log.e("error", "Initialization Failed!");
        });
    }

    void talk(String sayThisWord){
        tts.speak(sayThisWord, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speechREcordingInput(String languageForRecording)  //-> "he-IL"
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageForRecording );
        try {
            startActivityForResult(intent, RECORDING_CODE_INPUT);
        } catch (ActivityNotFoundException a) {
           // Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }


    void endWithIt(){
        Handler handler = new Handler();
        AsyncTask.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(this::finish);
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

}
