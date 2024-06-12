package Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class EventSearcher {
    public EventSearcher(LocalDate date, Database database, Events eventsPanel) {
        
        JFrame frame = new JFrame("Search Events");
        frame.setSize(700, 350);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        mainPanel.setBackground(Color.white);

        JPanel center = new JPanel(new GridLayout(3, 2, 20, 20));
        center.setBackground(Color.white);

        JLabel l1 = new JLabel("Keyword");
        l1.setFont(new Font("Helvetica", Font.PLAIN, 20));
        l1.setHorizontalAlignment(JLabel.CENTER);
        center.add(l1);

        JTextField keyword = new JTextField();
        keyword.setFont(new Font("Helvetica", Font.PLAIN, 20));
        keyword.setHorizontalAlignment(JLabel.CENTER);
        center.add(keyword);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(1, 2, 20, 20));
        bottom.setBackground(null);

        JButton search = new JButton("Search");
        search.setFont(new Font("Helvetica", Font.PLAIN, 20));
        search.setBackground(Color.decode("#00d1e8"));
        search.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.add(search);

        LinkedList<Double> times = new LinkedList<>();
        search.addActionListener(e -> {
            for (int i = 1; i < 21; i++) {
                long startTime, endTime, totalTime;

                String searchText = keyword.getText().toLowerCase();
                if (searchText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Keyword cannot be empty");
                    return;
                }

                LinkedList<Event> events = database.getEvents(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                LinkedList<Event> filteredEvents = new LinkedList<>();
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

                eventsPanel.updateEventList(filteredEvents);
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
