package Calendar;

import java.awt.Color;
import java.awt.GridLayout;

import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calendar-HashMap");

        // Setting the frame
        frame.setSize(900,500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Gridlayout: first column for calendar, second for events
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // Gets the current date 
        LocalDate date = LocalDate.now();

        // Instantiating the database to access the txt file
        Database database = new Database();

        // Adding the calendar and events object to the main panel
        mainPanel.add(new Calendar(date.getYear(), date.getMonthValue(), date, mainPanel, database));
        mainPanel.add(new Events(date, database, mainPanel));

        // Adding the main panel to the set frame application window
        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
    }
}