package com.example.dip.quizapp;

import java.util.Comparator;

class SortbyMarks implements Comparator<resultformat>
{
    // Used for sorting in ascending order of
    // marks
    public int compare(resultformat a, resultformat b)
    {
        return Integer.parseInt(a.getMarks()) - Integer.parseInt(b.getMarks());
    }
}
