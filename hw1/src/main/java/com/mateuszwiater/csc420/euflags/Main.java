package com.mateuszwiater.csc420.euflags;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * The main class of the eu-flags project.
 */
public class Main extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final String TITLE = "European Flags";

    private final JLabel flagImage;
    private final JPanel flagPanel;
    private final Map<String, ImageIcon> flags;

    /**
     * The main method. It starts the GUI.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }

    /**
     * Constructor.
     */
    private Main() {
        flags = loadFlags();
        flagImage = new JLabel();
        flagPanel = new JPanel();
        init();
    }

    /**
     * Sets up the various properties of the GUI.
     */
    private void init() {
        setTitle(TITLE);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        flagPanel.setLayout(new GridBagLayout());
        flagPanel.add(flagImage);

        JComboBox countrySelector = new JComboBox<>(flags.keySet().toArray());
        countrySelector.addActionListener(new ComboBoxAction());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        add(flagPanel, c);
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(countrySelector, c);
    }

    /**
     * Loads all of the flags into memory.
     *
     * @return a map of the flags.
     */
    private Map<String, ImageIcon> loadFlags() {
        final Map<String, ImageIcon> flags = new TreeMap<>();
        FileUtils.listFiles(FileUtils.toFile(this.getClass().getClassLoader().getResource("eu-flags")), null, false).forEach(f -> {
            try {
                flags.put(FilenameUtils.getBaseName(f.getName()), new ImageIcon(ImageIO.read(f)));
            } catch (IOException e) {
                logger.error("Something broke while reading the images in.", e);
            }
        });
        return flags;
    }

    /**
     * Changes the currently displayed flag to the one of
     * the specified country.
     *
     * @param countryName country name that exists in the flags map.
     */
    private void setFlag(final String countryName) {
        final Image flag = flags.get(countryName).getImage();
        int width = flagPanel.getWidth();
        int height = (flag.getHeight(null) * width) / flag.getWidth(null);

        if(height > flagPanel.getHeight()) {
            height = flagPanel.getHeight();
            width = (flag.getWidth(null) * height) / flag.getHeight(null);
        }

        flagImage.setIcon(new ImageIcon(flag.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        logger.info("Loaded " + countryName + "'s flag. Flag Size: " + width + "x" + height);
    }

    /**
     * Class that handles the ComboBox events.
     */
    private class ComboBoxAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String selection = (String) ((JComboBox)e.getSource()).getSelectedItem();
            setFlag(selection);
        }
    }
}