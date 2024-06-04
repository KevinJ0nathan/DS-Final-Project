package Calendar;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calendar");
        frame.setSize(900,500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(1,2,0,0));

        LocalDate date = LocalDate.now();

        mainPanel.add(new Calendar(date.getYear(),date.getMonthValue(), date, mainPanel));
        mainPanel.add(new Events());

        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
    }
}