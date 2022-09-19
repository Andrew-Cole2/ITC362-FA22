package com.cole.burgercaloriecounter;

public class CalorieCalculator {
    static final int PATTY_ONE = 100; // Beef
    static final int PATTY_TWO = 170; // Lamb
    static final int PATTY_THREE = 150; // Ostrich
    static final int CHEESE_ONE = 90; //Asiago
    static final int CHEESE_TWO = 120; //Creme Fraiche
    static final int EXTRA = 115; // Prosciutto

    private int pattyCal;
    private int cheeseCal;
    private int extraCal;
    private int sauceCal;

    public CalorieCalculator() {
        pattyCal = PATTY_ONE;
        cheeseCal = CHEESE_ONE;
        extraCal = 0;
        sauceCal = 0;
    }

    public void setPattyCalories(int calories){
        pattyCal = calories;
    }

    public void setCheeseCalories( int calories){
        cheeseCal = calories;
    }
    public void setProsciuttoCalories (int calories){
        extraCal = calories;
    }
    public void clearProsciuttoCalories (){
        extraCal = 0;
    }
    public void setSauceCalories(int calories){
        sauceCal = calories;
    }

    public int getTotalCalories() {
        return pattyCal + cheeseCal + extraCal + sauceCal;
    }
}
