package com.mateuszwiater.csc420.flagcanvas;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Contains a country name and the associated flag.
 */
class Flag implements Transferable {
    private final String countryName;
    private final BufferedImage countryFlag;
    private final static DataFlavor dataFlavor = new DataFlavor(Flag.class, "Flag Object");

    /**
     * The constructor.
     *
     * @param countryName the name of the country.
     * @param countryFlag the flag of the country.
     */
    Flag(final String countryName, final BufferedImage countryFlag) {
        this.countryName = countryName;
        this.countryFlag = countryFlag;
    }

    /**
     * Returns the country flag.
     *
     * @return the country flag.
     */
    BufferedImage getCountryFlag() {
        return countryFlag;
    }

    /**
     * Returns the flag Data Flavor.
     *
     * @return the flag Data Flavor.
     */
    static DataFlavor getDataFlavor() {
        return dataFlavor;
    }

    @Override
    public String toString() {
        return countryName;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] {dataFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.getRepresentationClass() == dataFlavor.getRepresentationClass();
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if(isDataFlavorSupported(flavor)) {
            return this;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
