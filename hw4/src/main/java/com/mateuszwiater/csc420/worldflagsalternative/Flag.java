package com.mateuszwiater.csc420.worldflagsalternative;

import javax.swing.ImageIcon;

/**
 * Contains a country name and the associated flag.
 */
class Flag {
    private final String countryName;
    private final ImageIcon countryFlag;

    /**
     * The constructor.
     *
     * @param countryName the name of the country.
     * @param countryFlag the flag of the country.
     */
    Flag(final String countryName, final ImageIcon countryFlag) {
        this.countryName = countryName;
        this.countryFlag = countryFlag;
    }

    /**
     * Returns the country flag.
     *
     * @return the country flag.
     */
    ImageIcon getCountryFlag() {
        return countryFlag;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
