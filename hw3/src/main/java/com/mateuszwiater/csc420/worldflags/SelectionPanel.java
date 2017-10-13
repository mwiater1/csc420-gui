package com.mateuszwiater.csc420.worldflags;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.util.List;

public class SelectionPanel extends JPanel {

    SelectionPanel(final FlagPanel flagPanel, final List<Flag> flags) {
        // Set panel parameters
        setLayout(new MigLayout("","[grow,fill]","[][grow,fill][]"));

        // Create the country list and show button
        final JLabel topLabel = new JLabel("World Countries", SwingConstants.CENTER);
        final JList countryList = new JList<>(flags.toArray());
        countryList.setSelectedIndex(0);
        final JButton showButton = new JButton("Show Flag");

        // Set the button to change the flag based on what is selected in the country list when clicked
        showButton.addActionListener( l -> flagPanel.setFlag(((Flag)countryList.getSelectedValue()).getCountryFlag()));

        // Add the country list and button to the panel
        add(topLabel,"wrap");
        add(new JScrollPane(countryList),"wrap");
        add(showButton);
    }
}
