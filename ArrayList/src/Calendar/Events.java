package Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ArrayList<Event> events = database.getEvents(dateFormatter.format(date));

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));

        list = new JPanel(new GridLayout(events.size() + 2, 1, 10, 10));
        list.setBackground(Color.white);
        JScrollPane sp = new JScrollPane(list);
        populateEventList(events);
        add(sp, BorderLayout.CENTER);

        JButton newEvent = createButton("New", e -> new EventEditor(new Event(date), database, mainPanel));
        add(newEvent, BorderLayout.SOUTH);

        JButton searchEvent = createButton("Search", e -> new EventSearcher(date, database, this));
        add(searchEvent, BorderLayout.NORTH);
    }

    private void populateEventList(ArrayList<Event> events) {
        list.removeAll();
        for (Event event : events) {
            JPanel eventPanel = createEventPanel(event);
            list.add(eventPanel);
        }
        
        revalidate();
        repaint();
    }

    private JPanel createEventPanel(Event event) {
        JPanel eventPanel = new JPanel(new GridLayout(2, 1));
        eventPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode("#00d1e8"))
        ));
        eventPanel.setBackground(Color.decode("#f0f0f0"));
        eventPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eventPanel.addMouseListener(new EventMouseListener(event, database, mainPanel));

        JLabel title = createLabel(event.getTitle(), 18);
        eventPanel.add(title);

        JLabel time = createLabel(event.getDateTimeToString(), 14);
        eventPanel.add(time);

        return eventPanel;
    }

    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        label.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        label.setForeground(Color.black);
        return label;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Helvetica", Font.PLAIN, 20));
        button.setBackground(Color.decode("#00d1e8"));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(actionListener);
        return button;
    }

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

    public void updateEventList(ArrayList<Event> events) {
        populateEventList(events);
    }
}
