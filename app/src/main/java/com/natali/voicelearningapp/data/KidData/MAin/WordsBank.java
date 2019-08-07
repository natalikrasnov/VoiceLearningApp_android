package com.natali.voicelearningapp.data.KidData.MAin;

import com.natali.voicelearningapp.data.KidData.Letters;
import com.natali.voicelearningapp.data.KidData.Numbers;
import com.natali.voicelearningapp.resiverAndServices.ServiceForLooperGetApps;

public class WordsBank {

    public static final String[] lessons = new String[]{"ספרות" , "אותיות"};

    public static String getMeTheRightWordInEnglish(){
        if (ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getCountOfRepeatingCurrentLesson() >= 2) {
            ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().setCountOfRepeatingCurrentLesson(0);
            ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().nextLesson();
        }
        if(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonName().equals(WordsBank.lessons[0])) {
            return Numbers.getMeThisNumberInEnglish(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonNumber());
        }else {
            return Letters.getMeWord(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonNumber());
        }
    }

    public static int getMeTheRightBackground(){
        if(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonName().equals(WordsBank.lessons[0])) {
            return Numbers.getBackground(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonNumber());
        }else{
            return Letters.getImageForBackground(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonNumber());
        }
    }

    public static String giveMeTranslate(String wordInEnglish){
        if(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonName().equals(WordsBank.lessons[0]))
            return Numbers.getWords().get(wordInEnglish);
       return null;
    }

    public static String giveMeTranslateInHebrewButInEnglishLetters(String wordInEnglish){
        if(ServiceForLooperGetApps.getCurentPerson().getCurrentLesson().getLessonName().equals(WordsBank.lessons[0]))
            return Numbers.getWordsInEnglishLatters().get(wordInEnglish);
        return null;
    }
}
