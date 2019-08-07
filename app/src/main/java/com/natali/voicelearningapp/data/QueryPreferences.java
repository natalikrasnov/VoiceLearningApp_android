package com.natali.voicelearningapp.data;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {
    private static final String PREF_IS_THIS_IS_A_KID = "is_this_is_kid";
    public static boolean getIsThisAKid(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_IS_THIS_IS_A_KID, true);
    }

    public static void setISThisAKid(Context context, Boolean isKid) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_THIS_IS_A_KID, isKid)
                .apply();
    }


    private static final String PREF_PERSON_POINTS = "person_points";
    private static final String PREF_PERSON_COUNT_OF_REPEATING_LESSON_NUMBERS = "person_countOfRepeatingLesson_numbers";
    private static final String PREF_PERSON_COUNT_OF_REPEATING_LESSON_LETTERS = "person_countOfRepeatingLesson_letters";

    private static final String PREF_PERSON_NUMBER_OF_LESSON_NUMBERS = "person_numberOfLesson_numbers";
    private static final String PREF_PERSON_NUMBER_OF_LESSON_LETTERS = "person_numberOfLesson_letters";



    public static int getPerson_points(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_PERSON_POINTS, 0);
    }

    public static void setPrefPersonPoints(Context context, int points) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_PERSON_POINTS, points)
                .apply();
    }

    public static int getPrefPrsonCountOfRepeatingLessonLetters(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_PERSON_COUNT_OF_REPEATING_LESSON_LETTERS, 0);
    }

    public static void setPrefPrsonCountOfRepeatingLessonLetters(Context context, int countOFRepeat) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_PERSON_COUNT_OF_REPEATING_LESSON_LETTERS, countOFRepeat)
                .apply();
    }
    public static int getPrefPrsonCountOfRepeatingLessonNumbers(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_PERSON_COUNT_OF_REPEATING_LESSON_NUMBERS, 0);
    }

    public static void setPrefPrsonCountOfRepeatingLessonNumbers(Context context, int countOFRepeat) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_PERSON_COUNT_OF_REPEATING_LESSON_NUMBERS, countOFRepeat)
                .apply();
    }
    public static int getPrefPersonNumberOfLessonNumber(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_PERSON_NUMBER_OF_LESSON_NUMBERS, 0);
    }

    public static void setPrefPersonNumberOfLessonNumbers(Context context, int lesson) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_PERSON_NUMBER_OF_LESSON_NUMBERS, lesson)
                .apply();
    }
    public static int getPrefPersonNumberOfLessonLetters(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_PERSON_NUMBER_OF_LESSON_LETTERS, 0);
    }

    public static void setPrefPersonNumberOfLessonLetters(Context context, int lesson) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_PERSON_NUMBER_OF_LESSON_LETTERS, lesson)
                .apply();
    }
}
