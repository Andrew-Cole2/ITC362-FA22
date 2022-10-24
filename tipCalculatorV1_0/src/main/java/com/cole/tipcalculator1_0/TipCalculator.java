package com.cole.tipcalculator1_0;

public class TipCalculator {
    private float tip;
    private float bill;
    private float guestCount;

    public TipCalculator(float newTip, float newBill, float newGuestCount) {
        this.tip = newTip;
        this.bill = newBill;
        this.guestCount = newGuestCount;
    }

    public float getTip() {
        return tip;
    }

    public float getBill() {
        return bill;
    }

    public float getGuestCount() {
        return guestCount;
    }

    public void setTip(float newTip) {
        if ( newTip > 0 )
            tip = newTip;
    }

    public void setBill(float newBill) {
        if ( newBill > 0 )
            bill = newBill;
    }

    public void setGuestCount(float newGuestCount) {
        if ( newGuestCount > 0) {
            guestCount = newGuestCount;
        }
    }

    public float tipAmount() {
        return bill * tip;
    }

    public float guestTipAmount() {
        return tipAmount() / guestCount;
    }

    float totalAmount() {
        return bill + tipAmount();
    }
}
