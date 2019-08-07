package com.natali.voicelearningapp.data.KidData;
import com.natali.voicelearningapp.R;

import java.util.Hashtable;
import java.util.Random;

public class Numbers {

    private static Hashtable<String, String> words = new Hashtable<String, String>() {};
    private static Hashtable<String, String> wordsInEnglishLatters = new Hashtable<String, String>() {};

    private static int[] imagesForBackground = new int[]{R.drawable.zero,
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.eight,
            R.drawable.nine};

    static {
        addWord("zero","אפס");
        addWord("one","אחד");
        addWord("two","שתים");
        addWord("three","שלוש");
        addWord("four","ארבע");
        addWord("five","חמש");
        addWord("six","שש");
        addWord("seven","שבע");
        addWord("eight","שמונה");
        addWord("nine","תישע");

        wordsInEnglishLatters.put("zero","efes");
        wordsInEnglishLatters.put("one","ahad");
        wordsInEnglishLatters.put("two","shtauyem");
        wordsInEnglishLatters.put("three","shaloesh");
        wordsInEnglishLatters.put("four","arba");
        wordsInEnglishLatters.put("five","hamesh");
        wordsInEnglishLatters.put("six","shesh");
        wordsInEnglishLatters.put("seven","shaeva");
        wordsInEnglishLatters.put("eight","shmonae");
        wordsInEnglishLatters.put("nine","teysha");

    }

    public static Hashtable<String, String> getWordsInEnglishLatters() {
        return wordsInEnglishLatters;
    }

    public static void addWord(String newWordInEnglish, String newWordInHebrew){
        words.put(newWordInEnglish, newWordInHebrew);
    }

    public static Hashtable<String, String> getWords() {
        return words;
    }

    public static String getRandomWordInHebrew(){
        int random = new Random().nextInt(getWords().size());
        int count = 0;
       for (String key : getWords().keySet()){
           if(random==count)
             return key;
           count++;
        }
        return "";
    }

    public static int getMeThisNumber(String numberInEnglish){
            switch (numberInEnglish){
                case"zero":
                        return 0;
                case "one":
                        return 1;
                case "two":
                        return 2;
                case"three":
                        return 3;
                case"four":
                        return 4;
                case"five":
                        return 5;
                case"six":
                        return 6;
                case"seven":
                        return 7;
                case"eight":
                        return 8;
                case"nine":
                        return 9;
            }
        return 11;
    }


    public static String getMeThisNumberInEnglish(int number){
            switch (number){
                case 0:
                    return "zero";
                case 1:
                    return "one";
                case 2:
                    return "two";
                case 3:
                    return "three";
                case 4:
                    return "four";
                case 5:
                    return "five";
                case 6:
                    return "six";
                case 7:
                    return "seven";
                case 8:
                    return "eight";
                case 9:
                    return "nine";
            }
        return "";
    }

    public static int[] getDrawbleImage(){
       return imagesForBackground;
    }

    public static int getBackground(int index){
        return getDrawbleImage()[index];
    }

}
