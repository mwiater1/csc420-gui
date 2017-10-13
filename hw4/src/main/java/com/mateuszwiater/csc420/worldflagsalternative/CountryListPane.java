package com.mateuszwiater.csc420.worldflagsalternative;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
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

        // Set up the scroll pane.
        setViewportView(countries);
        setBorder(BorderFactory.createTitledBorder("World Countries"));
    }
}
