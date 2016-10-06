package com.mateuszwiater.csc420.circlecanvas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Map;
import java.util.TreeMap;


/**
 * The main class of the circle-canvas project.
 */
public class Main extends JFrame implements ComponentListener {
    private static final String TITLE = "Circle Canvas";
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int CIRCLE_RADIUS = 100;
    private final JPanel canvas;
    private final JButton button;
    private final JComboBox comboBox;
    private final Map<String, Color> colors;
    private final JSlider leftSlider, bottomSlider;
    private boolean shown;

    /**
     * The Main method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        final Main main = new Main();
        SwingUtilities.invokeLater(() -> main.setVisible(true));
    }

    /**
     * Constructor.
     */
    private Main() {
        init();
        shown = false;
        colors = initColors();
        canvas = initCanvas();
        button = initButton();
        comboBox = initComboBox();
        leftSlider = initLeftSlider();
        bottomSlider = initBottomSlider();
    }

    /**
     * Initializes the JFrame.
     */
    private void init() {
        setTitle(TITLE);
        addComponentListener(this);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Initializes the JSlider on the left.
     *
     * @return and initialized JSlider.
     */
    private JSlider initLeftSlider() {
        final JSlider leftSlider = new JSlider(JSlider.VERTICAL);
        leftSlider.setMinimum(0);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.VERTICAL;
        leftSlider.setInverted(true);
        leftSlider.addChangeListener(changeEvent -> canvas.repaint());
        add(leftSlider, constraints);

        return leftSlider;
    }

    /**
     * Initializes the JSlider on the bottom.
     *
     * @return and initialized JSlider.
     */
    private JSlider initBottomSlider() {
        final JSlider bottomSlider = new JSlider(JSlider.HORIZONTAL);
        bottomSlider.setMinimum(0);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(bottomSlider, constraints);
        bottomSlider.addChangeListener(e -> canvas.repaint());

        return bottomSlider;
    }

    /**
     * Initializes the Show/Hide button.
     *
     * @return an initialized button.
     */
    private JButton initButton() {
        final JButton button = new JButton("SHOW");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        button.addActionListener(a -> {
            if(button.getText().equals("SHOW")) {
                button.setText("HIDE");
                comboBox.setEnabled(false);
                leftSlider.setEnabled(false);
                bottomSlider.setEnabled(false);
            } else {
                button.setText("SHOW");
                comboBox.setEnabled(true);
                leftSlider.setEnabled(true);
                bottomSlider.setEnabled(true);
            }

            canvas.repaint();
        });
        add(button, constraints);

        return button;
    }

    /**
     * Initializes the color ComboBox.
     *
     * @return an initialized ComboBox.
     */
    private JComboBox initComboBox() {
        final JComboBox comboBox = new JComboBox<>(colors.keySet().toArray());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        add(comboBox, constraints);

        return comboBox;
    }

    /**
     * Initializes the colors map.
     *
     * @return and initialized colors map.
     */
    private Map<String, Color> initColors() {
        final Map<String, Color> colors = new TreeMap<>();
        colors.put("BLACK", Color.BLACK);
        colors.put("GREEN", Color.GREEN);
        colors.put("RED", Color.RED);
        colors.put("ORANGE", Color.ORANGE);
        colors.put("YELLOW", Color.YELLOW);

        return colors;
    }

    /**
     * Initializes the JPanel which serves as the canvas.
     *
     * @return and initialized JPanel.
     */
    private JPanel initCanvas() {
        final JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final int y = leftSlider.getValue();
                final int x = bottomSlider.getValue();
                final String buttonText = button.getText();

                if(buttonText.equals("SHOW")) {
                    g.drawLine(x, y - 5, x, y + 5);
                    g.drawLine(x - 5, y, x + 5, y);
                } else if(buttonText.equals("HIDE")) {
                    final int offset = CIRCLE_RADIUS / 2;
                    g.setColor(colors.get(comboBox.getSelectedItem()));
                    g.fillOval(x - offset, y - offset, CIRCLE_RADIUS, CIRCLE_RADIUS);
                }
            }
        };
        canvas.setBackground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        add(canvas, constraints);

        return canvas;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(shown) {
            leftSlider.setMaximum(canvas.getHeight());
            bottomSlider.setMaximum(canvas.getWidth());
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {
        leftSlider.setMaximum(canvas.getHeight());
        leftSlider.setValue(leftSlider.getMaximum()/2);
        bottomSlider.setMaximum(canvas.getWidth());
        bottomSlider.setValue(bottomSlider.getMaximum()/2);
        shown = true;
    }

    @Override
    public void componentHidden(ComponentEvent e) {}
}
