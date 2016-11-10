package com.mateuszwiater.csc420.worldflagsalternative;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;

public class SelectionPanel extends JPanel {

    SelectionPanel(final FlagPanel flagPanel, final List<Flag> flags) {
        // Set panel parameters
        setLayout(new MigLayout("","[grow,fill]","[grow,fill][]"));

        // Add the components to the panel.
//        add(new JLabel("World Countries", SwingConstants.CENTER),"wrap");
        add(new CountryListPane(flags),"wrap");
        add(new FlagTextPanel(flagPanel, flags));
    }
}
