package Calendar;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class Events extends JPanel {
    private JPanel list;
    private Database database;
    private LocalDate date;
    private JPanel mainPanel;

    public Events(LocalDate date, Database database, JPanel mainPanel) {
        this.database = database;
        this.date = date;
        this.mainPanel = mainPanel;

        // The events are temporarily stored in the arraylist, it is filtered according to the date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ArrayList<Event> events = database.getEvents(dateFormatter.format(date));

        // Setting the display for the events
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));

        // Defines the size of the events container
        list = new JPanel(new GridLayout(events.size() + 2, 1, 10, 10));
        list.setBackground(Color.white);
        JScrollPane sp = new JScrollPane(list);
        populateEventList(events);
        add(sp, BorderLayout.CENTER);

        // Adds the button to create new event
        JButton newEvent = createButton("New", e -> new EventEditor(new Event(date), database, mainPanel));
        add(newEvent, BorderLayout.SOUTH);

        // Adds the button to search event according to keyword(s)
        JButton searchEvent = createButton("Search", e -> new EventSearcher(date, database, this));
        add(searchEvent, BorderLayout.NORTH);
    }

    // Method to update the list of events
    public void populateEventList(ArrayList<Event> events) {
        list.removeAll();
        for (Event event : events) {
            JPanel eventPanel = createEventPanel(event);
            list.add(eventPanel);
        }
        
        revalidate();
        repaint();
    }

    // Method to create the events panel to be able to display each event and its details
    private JPanel createEventPanel(Event event) {

        // First row for title, second for description, third for time
        JPanel eventPanel = new JPanel(new GridLayout(3, 1));
        eventPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode("#00d1e8"))
        ));
        eventPanel.setBackground(Color.decode("#f0f0f0"));
        eventPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eventPanel.addMouseListener(new EventMouseListener(event, database, mainPanel));

        // Displays the title
        JLabel title = createLabel(event.getTitle(), 18);
        eventPanel.add(title);

        // Displays the description
        JLabel description = createLabel(event.getDescription(), 14);
        eventPanel.add(description);

        // Displays the time
        JLabel time = createLabel(event.getDateTimeToString(), 12);
        eventPanel.add(time);

        return eventPanel;
    }

    // Method to set the label to display the event details
    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        label.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        label.setForeground(Color.black);
        return label;
    }

    // Method to create buttons for searching and adding an event
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Helvetica", Font.PLAIN, 20));
        button.setBackground(Color.decode("#00d1e8"));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(actionListener);
        return button;
    }

    // Overriding the mousadaptor class to open the editor window
    private static class EventMouseListener extends MouseAdapter {
        private final Event event;
        private final Database database;
        private final JPanel mainPanel;

        public EventMouseListener(Event event, Database database, JPanel mainPanel) {
            this.event = event;
            this.database = database;
            this.mainPanel = mainPanel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            new EventEditor(event, database, mainPanel);
        }
    }
}
