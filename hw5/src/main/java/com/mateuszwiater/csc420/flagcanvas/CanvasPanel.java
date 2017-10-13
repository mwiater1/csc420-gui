package com.mateuszwiater.csc420.flagcanvas;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

class CanvasPanel extends JPanel {
    private final BoundedRangeModel verticalModel, horizontalModel;
    private final JPanel canvas;
    private Flag flag;

    /**
     * Constructor.
     */
    CanvasPanel() {
        JSlider north, south, east, west;
        setLayout(new MigLayout("","[][grow,fill][]","[][grow, fill][]"));

        // Create the canvas
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final int y = verticalModel.getValue();
                final int x = horizontalModel.getValue();
                if(flag == null) {
                    g.drawLine(x, y - 5, x, y + 5);
                    g.drawLine(x - 5, y, x + 5, y);
                } else {
                    int height = flag.getCountryFlag().getHeight();
                    int width = flag.getCountryFlag().getWidth();
                    g.drawImage(flag.getCountryFlag(), x - (width / 2), y - (height / 2), null);
                }
            }
        };

        // Set the transfer handler to accept Flag objects
        canvas.setTransferHandler(new TransferHandler() {
            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    setFlag((Flag) support.getTransferable().getTransferData(Flag.getDataFlavor()));
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(Flag.getDataFlavor());
            }
        });

        // Add a resize listener.
        canvas.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                verticalModel.setMaximum(e.getComponent().getHeight());
                horizontalModel.setMaximum(e.getComponent().getWidth());
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        // Set the background color of the canvas.
        canvas.setBackground(Color.GRAY);

        // Create the JSliders
        north = new JSlider();
        south = new JSlider();
        east = new JSlider(JSlider.VERTICAL);
        west = new JSlider(JSlider.VERTICAL);
        east.setInverted(true);
        west.setInverted(true);

        // Set the JSlider models
        horizontalModel = north.getModel();
        verticalModel = east.getModel();
        west.setModel(verticalModel);
        south.setModel(horizontalModel);

        // Set the model change listener
        horizontalModel.addChangeListener(c -> canvas.repaint());
        verticalModel.addChangeListener(c -> canvas.repaint());

        // Create the container for the canvas
        final JPanel canvasContainer = new JPanel(new MigLayout("","[grow,fill]","[grow,fill]"));
        canvasContainer.add(canvas);

        // Add the canvas and sliders to the Canvas Panel
        add(canvasContainer, "cell 1 1");
        add(north, "cell 1 0");
        add(west, "cell 0 1");
        add(east, "cell 2 1");
        add(south, "cell 1 2");
    }

    /**
     * Sets the flag to be shown on the canvas.
     *
     * @param flag the flag to be shown on the canvas.
     */
    private void setFlag(final Flag flag) {
        this.flag = flag;
        ((JFrame)SwingUtilities.getRoot(this)).setIconImage(flag.getCountryFlag());
        canvas.repaint();
    }
}
