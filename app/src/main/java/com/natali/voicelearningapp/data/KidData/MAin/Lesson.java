package com.natali.voicelearningapp.data.KidData.MAin;

import android.content.Context;

import com.natali.voicelearningapp.data.QueryPreferences;

public class Lesson {

    private int countOfRepeatingCurrentLesson ;
    private int lessonNumber;
    private String lessonName;
    private Context context;

    public Lesson(String lessonName, Context context){
        this.context = context;
        this.lessonName = lessonName;
        this.lessonNumber = getLessonNumber();
        this.countOfRepeatingCurrentLesson = getCountOfRepeatingCurrentLesson();
    }

    private Lesson(int countOfRepeatingCurrentLesson, int lessonNumber, String lessonName) {
        this.countOfRepeatingCurrentLesson = countOfRepeatingCurrentLesson;
        this.lessonNumber = lessonNumber;
        this.lessonName = lessonName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public int getCountOfRepeatingCurrentLesson() {
        if (lessonName.equals(WordsBank.lessons[0]))
            return QueryPreferences.getPrefPrsonCountOfRepeatingLessonLetters(context);
        else return QueryPreferences.getPrefPrsonCountOfRepeatingLessonNumbers(context);
    }


    public void didThisMision(){
        setCountOfRepeatingCurrentLesson(getCountOfRepeatingCurrentLesson()+1);
    }

    public void nextLesson(){
        setLessonNumber(getLessonNumber()+1);
        if(lessonName.equals(WordsBank.lessons[0]))
            if(getLessonNumber()>=26) {
                setLessonNumber(0);
                return;
            }
        else if(getLessonNumber()>10) {
                setLessonNumber(0);
                return;
            }
    }

    public void setCountOfRepeatingCurrentLesson(int countOfRepeatingCurrentLesson) {
        this.countOfRepeatingCurrentLesson = countOfRepeatingCurrentLesson;
        if (lessonName.equals(WordsBank.lessons[0]))
             QueryPreferences.setPrefPrsonCountOfRepeatingLessonLetters(context,countOfRepeatingCurrentLesson);
        else  QueryPreferences.setPrefPrsonCountOfRepeatingLessonNumbers(context,countOfRepeatingCurrentLesson);
    }

    public int getLessonNumber() {
        if (lessonName.equals(WordsBank.lessons[0]))
          return QueryPreferences.getPrefPersonNumberOfLessonLetters(context);
        else return QueryPreferences.getPrefPersonNumberOfLessonNumber(context);

    }

    private void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
        if (lessonName.equals(WordsBank.lessons[0]))
              QueryPreferences.setPrefPersonNumberOfLessonLetters(context,lessonNumber);
        else QueryPreferences.setPrefPersonNumberOfLessonNumbers(context,lessonNumber);
    }

    public void save(){
        setLessonNumber(getLessonNumber());
        setCountOfRepeatingCurrentLesson(getCountOfRepeatingCurrentLesson());
    }
}
