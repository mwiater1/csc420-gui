package com.mateuszwiater.csc420.worldflagsalternative;

import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;

/**
 * the panel that displays the flags.
 */
class FlagPanel extends JPanel {
    private final JLabel flag;

    /**
     * The constructor.
     *
     * @param minSize the smallest dimensions the flag panel.
     */
    FlagPanel(final Dimension minSize) {
        // Set panel parameters
        setMinimumSize(minSize);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new MigLayout("insets 0","[grow,fill]","[grow,fill]"));

        // Create the flag label and add it to the panel
        flag = new JLabel("", SwingConstants.CENTER);
        add(flag);
    }

    /**
     * Sets the flag of this panel. Removes any text.
     *
     * @param flag the flag to set.
     */
    void setFlag(final ImageIcon flag) {
        this.flag.setText("");
        this.flag.setIcon(flag);
        SwingUtilities.getWindowAncestor(this).setIconImage(flag.getImage());
    }

    /**
     * Sets the text of this panel. Removes any flag.
     *
     * @param string the text to set.
     */
    void setText(final String string) {
        this.flag.setIcon(null);
        this.flag.setText(string);
    }
}
