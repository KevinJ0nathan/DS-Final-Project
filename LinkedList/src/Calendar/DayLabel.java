package Calendar;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;

public class DayLabel extends JLabel {
    public DayLabel(String text, Color background, Color foreground, boolean btn){
        setText(text);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("Helvetica", Font.PLAIN, 20));
        setOpaque(true);
        setBackground(background);
        setForeground(foreground);
        if (btn) setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
