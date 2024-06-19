package Calendar;

import javax.swing.JFrame;
import javax.swing .JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventSearcher {
    public EventSearcher(LocalDate date, Database database, Events eventsPanel) {

        // Number of times the linear search is executed
        int NUMBER_OF_SEARCHES = 10;

        // Sets the frame for the pop-up window
        JFrame frame = new JFrame("Search Events");
        frame.setSize(700, 350);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        // Panel to add objects later
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        mainPanel.setBackground(Color.white);

        // Panel for the label and the user input
        JPanel center = new JPanel(new GridLayout(3, 1, 20, 20));
        center.setBackground(Color.white);

        // The search label
        JLabel l1 = new JLabel("Search for keyword");
        l1.setFont(new Font("Helvetica", Font.PLAIN, 20));
        l1.setHorizontalAlignment(JLabel.CENTER);
        center.add(l1);

        // To recieve keyword from user input
        JTextField keyword = new JTextField();
        keyword.setFont(new Font("Helvetica", Font.PLAIN, 20));
        keyword.setHorizontalAlignment(JLabel.CENTER);
        center.add(keyword);

        mainPanel.add(center, BorderLayout.CENTER);

        // Panel for the search button
        JPanel bottom = new JPanel(new GridLayout(1, 1, 20, 20));
        bottom.setBackground(null);

        // Creating the search button
        JButton search = new JButton("Search");
        search.setFont(new Font("Helvetica", Font.PLAIN, 20));
        search.setBackground(Color.decode("#00d1e8"));
        search.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.add(search);

        // List to store the time taken to search a keyword, 50 times
        ArrayList<Double> times = new ArrayList<>();
        search.addActionListener(e -> {
            for (int i = 1; i < NUMBER_OF_SEARCHES + 1; i++) {
                long startTime, endTime, totalTime;

                String searchText = keyword.getText().toLowerCase();
                if (searchText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Keyword cannot be empty");
                    return;
                }

                ArrayList<Event> events = database.getEvents(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                ArrayList<Event> filteredEvents = new ArrayList<>();
                startTime = System.nanoTime();
                for (Event event : events) {
                    if (event.getTitle().toLowerCase().contains(searchText)) {
                        filteredEvents.add(event);
                    }
                }
                endTime = System.nanoTime();
                totalTime = endTime - startTime;
                times.add(totalTime / 1000000.0);
                System.out.println(i + ". Linear search time: " + (totalTime / 1000000.0) + " ms");

                eventsPanel.populateEventList(filteredEvents);
            }

            double sum = 0;
            for (Double time : times) {
                sum += time;
            }
            System.out.println("Average search time: " + (sum / times.size()) + " ms");

            frame.dispose();
        });

        mainPanel.add(bottom, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
    }
}
