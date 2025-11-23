package view;

import javax.swing.*;

public class LabelTextPanel extends JPanel{
    LabelTextPanel(JLabel label, JComponent component) {
        this.add(label);
        this.add(component);
    }
}
