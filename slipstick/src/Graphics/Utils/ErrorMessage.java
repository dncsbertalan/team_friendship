package Graphics.Utils;

import Constants.GameConstants;
import GameManagers.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorMessage extends JDialog implements ActionListener {
    private JButton okButton;
    Shape shape;

    public ErrorMessage(JFrame parent, String message) {
        super(parent, "Error", Dialog.ModalityType.APPLICATION_MODAL);
        this.setUndecorated(true); // Hide title bar and borders
        this.setMinimumSize(new Dimension(GameConstants.MenuPanel2_ERROR_MESSAGE_WIDTH, GameConstants.MenuPanel2_ERROR_MESSAGE_HEIGHT));
        this.setPreferredSize(new Dimension(GameConstants.MenuPanel2_ERROR_MESSAGE_WIDTH, GameConstants.MenuPanel2_ERROR_MESSAGE_HEIGHT));

        shape = CustomShapeFactory.CreateCustomShape(getWidth(), getHeight());
        this.setShape(shape);

        JPanel contentPane = createPanel(shape);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel(message);
        label.setFont(GameConstants.MenuPanel2_ERROR_MESSAGE_FONT);

        okButton = new MenuButton(
                GameConstants.MenuPanel2_ERROR_MESSAGE_BUTTON,
                GameConstants.MenuPanel2_ERROR_MESSAGE_BUTTON_BACKGROUND_COLOR,
                GameConstants.MenuPanel2_ERROR_MESSAGE_BUTTON_BORDER_COLOR,
                GameConstants.MenuPanel2_ERROR_MESSAGE_BUTTON_BORDER_THICKNESS);
        okButton.setPreferredSize(new Dimension(80, 40));
        okButton.setFont(GameConstants.MenuPanel2_ERROR_MESSAGE_BUTTON_FONT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(label, gbc);

        gbc.gridy = 1;
        contentPane.add(okButton, gbc);

        this.setContentPane(contentPane);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(parent);

        okButton.addActionListener(this);
    }

    private JPanel createPanel(Shape s) {
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fill background
                g2.setColor(GameConstants.MenuPanel2_ERROR_MESSAGE_BACKGROUND_COLOR);
                g2.fill(s);

                // Draw border
                g2.setColor(GameConstants.MenuPanel2_ERROR_MESSAGE_BORDER_COLOR);
                g2.setStroke(new BasicStroke(GameConstants.MenuPanel2_ERROR_MESSAGE_BORDER_THICKNESS));
                g2.draw(s);

                g2.dispose();
            }
        };

        contentPane.setLayout(new GridBagLayout());
        return contentPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            dispose();
        }
    }
}
