package com.mateuszwiater.csc420.worldflagsalternative;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * The panel that contains the input text box for flags.
 */
class FlagTextPanel extends JPanel {
    private final List<Flag> flags;
    private final FlagPanel flagPanel;
    private final JTextField textField;

    /**
     * The constructor.
     *
     * @param flagPanel the panel which displays the flag.
     * @param flags the list of supported flags.
     */
    FlagTextPanel(final FlagPanel flagPanel, final List<Flag> flags) {
        this.flags     = flags;
        this.flagPanel = flagPanel;
        this.textField = new JTextField();

        // Setup the text field
        textField.setTransferHandler(new FlagTransferHandler());
        textField.getDocument().addDocumentListener(new ChangeListener());

        // Setup the panel
        setLayout(new MigLayout("","[grow,fill]","[grow,fill]"));
        setBorder(BorderFactory.createTitledBorder("Current Country"));

        // Add components to the panel
        add(textField);
    }

    /**
     * The custom document listener for the text field. It fires off whenever the text changes in any way.
     */
    private class ChangeListener implements DocumentListener {

        @Override
        public void insertUpdate(final DocumentEvent e) {
            onChange();
        }

        @Override
        public void removeUpdate(final DocumentEvent e) {
            onChange();
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            onChange();
        }

        /**
         * Sets the flag on the flag panel to one which matches the input text. Otherwise it displays text on the flag panel.
         */
        private void onChange() {
            final Optional<Flag> flag = flags.stream().filter(f -> f.toString().equalsIgnoreCase(textField.getText())).findFirst();
            if(flag.isPresent()) {
                flagPanel.setFlag(flag.get().getCountryFlag());
            } else {
                flagPanel.setText("\"" + textField.getText() + "\" is not a valid country.");
            }
        }
    }

    /**
     * The custom transfer handler which clears the text field before importing anything.
     */
    private class FlagTransferHandler extends TransferHandler {
        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            try {
                textField.setText((String) support.getTransferable().getTransferData(DataFlavor.stringFlavor));
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }
    }
}
