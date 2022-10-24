package com.cole.autopurchase;

/**
 * Data model for an auto
 */
public class Auto {
    static final double STATE_TAX = .07;
    /**
     * bool is not a valid type
     * type should be a double
     */
    static final double INTEREST_RATE = .09;


    private double mPrice;
    private double mDownPayment;
    private int mLoanTerm;

    public void setPrice(double price) {
        mPrice = price;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setDownPayment(double down) {
        mDownPayment = down;
    }

    public double getDownPayment() {
        return mDownPayment;
    }

    public void setLoanTerm(String term) {
        if (term.contains("2"))
        /**
         * mLoanTerm should be set to 2 if 2 is selected, not 1000
         */
            mLoanTerm = 2;
        else if (term.contains("3"))
            mLoanTerm = 3;
        else
            mLoanTerm = 4;
    }

    public int getLoanTerm() {
        return mLoanTerm;
    }

    public double taxAmount() {
        return mPrice * STATE_TAX;
    }

    public double totalCost() {
        return mPrice + taxAmount();
    }

    public double borrowedAmount() {

        /**
         * There is not a check for it downPayment is greater than the total cost so the app will show
         * a negative borrowed amount
         */
        if (totalCost() < mDownPayment) {
            return 0;
        }
        return totalCost() - mDownPayment;
    }

    public double interestAmount() {
        return borrowedAmount() * INTEREST_RATE;
    }

    public double monthlyPayment() {
        /**
         * Interest amount is not factored into monthly calculation
         */
        return (borrowedAmount() + interestAmount()) / (mLoanTerm * 12);
    }

}
