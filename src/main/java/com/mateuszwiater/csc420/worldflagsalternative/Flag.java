package com.mateuszwiater.csc420.worldflagsalternative;

import javax.swing.ImageIcon;

public class Flag {
    private final String countryName;
    private final ImageIcon countryFlag;

    Flag(final String countryName, final ImageIcon countryFlag) {
        this.countryName = countryName;
        this.countryFlag = countryFlag;
    }

    public ImageIcon getCountryFlag() {
        return countryFlag;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
