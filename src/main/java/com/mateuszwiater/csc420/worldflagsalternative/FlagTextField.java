package com.mateuszwiater.csc420.worldflagsalternative;


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

public class FlagTextField extends JTextField {
    private final List<Flag> flags;
    private final FlagPanel flagPanel;

    public FlagTextField(final FlagPanel flagPanel, final List<Flag> flags) {
        this.flags = flags;
        this.flagPanel = flagPanel;
//        this.setDropMode(DropMode);
        this.setTransferHandler(new FlagTransferHandler());
        System.out.println(this.getDropMode());
        this.getDocument().addDocumentListener(new ChangeListener());
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
            final Optional<Flag> flag = flags.stream().filter(f -> f.toString().equalsIgnoreCase(getText())).findFirst();
            if(flag.isPresent()) {
                flagPanel.setFlag(flag.get().getCountryFlag());
            } else {
                flagPanel.setText("\"" + getText() + "\" is not a valid country.");
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
                setText((String) support.getTransferable().getTransferData(DataFlavor.stringFlavor));
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean (TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }
    }
}
