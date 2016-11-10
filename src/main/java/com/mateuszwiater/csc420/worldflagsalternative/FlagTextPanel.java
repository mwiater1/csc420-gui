package com.mateuszwiater.csc420.worldflagsalternative;


import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FlagTextPanel extends JPanel {
    private final List<Flag> flags;
    private final FlagPanel flagPanel;
    private final JTextField textField;

    public FlagTextPanel(final FlagPanel flagPanel, final List<Flag> flags) {
        this.flags = flags;
        this.flagPanel = flagPanel;
        this.textField = new JTextField();
        textField.setTransferHandler(new FlagTransferHandler());
        textField.getDocument().addDocumentListener(new ChangeListener());
        setLayout(new MigLayout("","[grow,fill]","[grow,fill]"));
        setBorder(BorderFactory.createTitledBorder("Current Country"));
        add(textField);
    }

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

        private void onChange() {
            final Optional<Flag> flag = flags.stream().filter(f -> f.toString().equalsIgnoreCase(textField.getText())).findFirst();
            if(flag.isPresent()) {
                flagPanel.setFlag(flag.get().getCountryFlag());
            } else {
                flagPanel.setText("\"" + textField.getText() + "\" is not a valid country.");
            }
        }
    }

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
