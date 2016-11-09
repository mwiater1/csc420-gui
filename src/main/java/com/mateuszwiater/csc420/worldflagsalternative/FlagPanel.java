package com.mateuszwiater.csc420.worldflagsalternative;

import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;

public class FlagPanel extends JPanel {
    private final JLabel flag;

    FlagPanel(final Dimension minSize) {
        // Set panel parameters
        setMinimumSize(minSize);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new MigLayout("insets 0","[grow,fill]","[grow,fill]"));

        // Create the flag label and add it to the panel
        flag = new JLabel("", SwingConstants.CENTER);
        add(flag);
    }

    void setFlag(final ImageIcon flag) {
        this.flag.setText("");
        this.flag.setIcon(flag);
    }

    void setText(final String string) {
        this.flag.setIcon(null);
        this.flag.setText(string);
    }
}
