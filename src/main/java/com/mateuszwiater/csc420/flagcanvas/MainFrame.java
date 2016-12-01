package com.mateuszwiater.csc420.flagcanvas;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The main window of the program.
 */
class MainFrame extends JFrame {

    /**
     * The constructor.
     *
     * @param minSize the smallest dimensions the flag panel can become.
     * @param flags the list of supported flags.
     */
    MainFrame(final Dimension minSize, final List<Flag> flags) {
        // Set the window parameters
        setTitle("Flag Canvas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new MigLayout("","[grow,fill][]","[grow,fill]"));

        // Create the flag and selection panels
        final CanvasPanel canvasPanel = new CanvasPanel();
        final CountryListPane countryListPane = new CountryListPane(flags);

        // Add the components to the window
        add(canvasPanel);
        add(countryListPane);

        // Set the windows minimum size
        pack();
        setMinimumSize(getPreferredSize());
    }
}
