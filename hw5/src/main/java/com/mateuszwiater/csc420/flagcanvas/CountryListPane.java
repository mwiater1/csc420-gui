package com.mateuszwiater.csc420.flagcanvas;

import javax.swing.*;
import java.awt.datatransfer.Transferable;
import java.util.List;

/**
 * This panel houses the country list.
 */
class CountryListPane extends JScrollPane {

    /**
     * The constructor.
     *
     * @param flags the list of flags to use to populate the list.
     */
    CountryListPane(final List<Flag> flags) {
        // Set up the JList
        final JList countries = new JList<>(flags.toArray());
        countries.setSelectedIndex(0);
        countries.setDragEnabled(true);
        countries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        countries.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return false;
            }
            @Override
            public int getSourceActions(JComponent c) {
                return LINK;
            }
            @Override
            protected Transferable createTransferable(JComponent c) {
                return (Flag) ((JList) c).getSelectedValue();
            }
        });

        // Set up the scroll pane.
        setViewportView(countries);
        setBorder(BorderFactory.createTitledBorder("World Countries"));
    }
}
