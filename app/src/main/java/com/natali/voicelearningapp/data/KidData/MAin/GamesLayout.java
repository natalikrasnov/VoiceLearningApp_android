package com.natali.voicelearningapp.data.KidData.MAin;

import android.content.Context;
import android.content.Intent;

import com.natali.voicelearningapp.KidApp.DialogViewHearAndRecordFromEN;
import com.natali.voicelearningapp.KidApp.DialogViewHearAndRecordFromHE;
import com.natali.voicelearningapp.KidApp.DialogViewSeeAndRecordInEN;
import com.natali.voicelearningapp.resiverAndServices.ServiceForLooperGetApps;

import java.util.Random;

public class GamesLayout {

    private static Intent[] allIntents;

    public static void setAllLayouts(Intent[] intents) {
        GamesLayout.allIntents = intents;
    }

    public GamesLayout(Context context) {
       setAllLayouts(new Intent[]{
                new Intent(context, DialogViewSeeAndRecordInEN.class),
                new Intent(context, DialogViewHearAndRecordFromEN.class),
                new Intent(context, DialogViewHearAndRecordFromHE.class)});

       for(Intent intent : getAllIntents()){
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
       }
    }

    public static Intent[] getAllIntents() {
        return allIntents;
    }

    public static Intent getRandomIntent(){
        if(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonName().equals(WordsBank.lessons[1]))
            return getAllIntents()[0];
        return getAllIntents()[new Random().nextInt(getAllIntents().length)];

    }
}
