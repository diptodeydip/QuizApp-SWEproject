package com.example.dip.quizapp;

import java.util.Comparator;

class Sortbyreg implements Comparator<resultformat>
{
    // Used for sorting in ascending order of
    // reg number
    public int compare(resultformat a, resultformat b)
    {
        return Integer.parseInt(a.getReg()) - Integer.parseInt(b.getReg());
    }
}
