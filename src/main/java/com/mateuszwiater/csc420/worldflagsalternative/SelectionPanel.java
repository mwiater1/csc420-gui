package com.mateuszwiater.csc420.worldflagsalternative;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import java.util.List;

/**
 * The panel that contains the text input box and country list.
 */
class SelectionPanel extends JPanel {

    /**
     * The constructor.
     *
     * @param flagPanel the panel which displays the flag.
     * @param flags the list of flags to populate the list with.
     */
    SelectionPanel(final FlagPanel flagPanel, final List<Flag> flags) {
        // Set panel layout
        setLayout(new MigLayout("","[grow,fill]","[grow,fill][]"));

        // Add the components to the panel.
        add(new CountryListPane(flags),"wrap");
        add(new FlagTextPanel(flagPanel, flags));
    }
}
