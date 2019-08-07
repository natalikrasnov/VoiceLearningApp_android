package com.natali.voicelearningapp.data.KidData;

import com.natali.voicelearningapp.R;

public class Letters {

    private static String[] letters = new String[]{"A", "B" ,"C" ,"D" , "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W","X","Y", "Z"};

    private static int[] imagesForBackground = new int[]{R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g,
            R.drawable.h,
            R.drawable.i,
            R.drawable.j,
            R.drawable.k,
            R.drawable.l,
            R.drawable.m,
            R.drawable.n,
            R.drawable.o,
            R.drawable.p,
            R.drawable.q,
            R.drawable.r,
            R.drawable.s,
            R.drawable.t,
            R.drawable.u,
            R.drawable.v,
            R.drawable.w,
            R.drawable.x,
            R.drawable.y,
            R.drawable.z};

    public static int[] getImagesForBackground() {
        return imagesForBackground;
    }

    public static int getImageForBackground(int index){
        return getImagesForBackground()[index];
    }

    public static String[] getLetters() {
        return letters;
    }

    public static String getMeWord(int lessonNumber){
        return getLetters()[lessonNumber];

    }
}
