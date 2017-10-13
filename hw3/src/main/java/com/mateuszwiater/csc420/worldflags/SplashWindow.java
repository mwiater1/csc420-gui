package com.mateuszwiater.csc420.worldflags;

import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SplashWindow extends JWindow {
    private static final Dimension SIZE = new Dimension(300,300);


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SplashWindow().setVisible(true));
    }

    private SplashWindow() {
        // Set window parameters
        setSize(SIZE);
        setLocationRelativeTo(null);
        setLayout(new MigLayout("","[grow,fill]","[grow,fill]"));

        // Create the label and progress bar
        final JLabel countryName = new JLabel("", SwingConstants.CENTER);
        countryName.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getName(), Font.PLAIN, 24));
        final JLabel topLabel = new JLabel("LOADING", SwingConstants.CENTER);
        topLabel.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getName(), Font.PLAIN, 60));
        final JProgressBar progressBar = new JProgressBar();

        // Add the components to the window
        add(topLabel,"wrap");
        add(progressBar,"wrap");
        add(countryName);

        // Load the flags using a worker
        final FlagLoader flagLoader = new FlagLoader(this, progressBar, countryName);
        flagLoader.execute();
    }

    private class FlagLoader extends SwingWorker<Pair<Dimension, List<Flag>>, String> {
        private final List<Flag> flags;
        private final JLabel countryLabel;
        private final SplashWindow splash;

        FlagLoader(final SplashWindow splash, final JProgressBar progressBar, final JLabel countryLabel) {
            this.splash = splash;
            this.flags = new ArrayList<>();
            this.countryLabel = countryLabel;
            addPropertyChangeListener(e -> {
                if(e.getPropertyName().equalsIgnoreCase("progress")) {
                    progressBar.setValue((int) e.getNewValue());
                }
            });
        }

        @Override
        protected Pair<Dimension, List<Flag>> doInBackground() throws Exception {
            final Collection<File> flagFiles = FileUtils.listFiles(
                    FileUtils.toFile(this.getClass().getClassLoader().getResource("flags")), null, false
            );

            final int amountOfFlags = flagFiles.size();
            int maxWidth = 0, maxHeight = 0;

            for (final File f : flagFiles) {
                try {
                    final String countryName = new Locale("" ,FilenameUtils.getBaseName(f.getName())).getDisplayCountry();
                    final ImageIcon flag = new ImageIcon(ImageIO.read(f));
                    maxWidth = maxWidth < flag.getIconWidth() ? flag.getIconWidth() : maxWidth;
                    maxHeight = maxHeight < flag.getIconHeight() ? flag.getIconHeight() : maxHeight;
                    flags.add(new Flag(countryName, flag));
                    publish(countryName);
                    setProgress((int)((flags.size() * 100.0f) / amountOfFlags));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return new Pair<>(new Dimension(maxWidth, maxHeight), flags);
        }

        @Override
        protected void process(List<String> chunks) {
            chunks.forEach(countryLabel::setText);
        }

        @Override
        protected void done() {
            try {
                final Pair<Dimension, List<Flag>> flags = get();
                final MainFrame mainFrame = new MainFrame(flags.getKey(), flags.getValue());
                splash.setVisible(false);
                mainFrame.setVisible(true);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
