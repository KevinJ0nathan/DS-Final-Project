package Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class Events extends JPanel {
    public Events(LocalDate date, Database database, JPanel mainPanel){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ArrayList<Event> events = database.getEvents(dateFormatter.format(date));

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));

        int rows = 4;
        if (events.size() > 4) rows = events.size();

        JPanel list = new JPanel(new GridLayout(rows, 1, 10, 10));
        list.setBackground(Color.white);

        JScrollPane sp = new JScrollPane(list);

        for (int i = 0; i < events.size(); i++) {
            final int j = i;
            JPanel event = new JPanel(new GridLayout(2, 1));
            event.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(10, 10, 10, 10),
                            BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode("#00d1e8"))));
            event.setBackground(Color.decode("#f0f0f0"));
            event.setCursor(new Cursor(Cursor.HAND_CURSOR));
            event.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {
                    new EventEditor(events.get(j), database, mainPanel);
                }
            });

            JLabel title = new JLabel(events.get(i).getTitle());
            title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
            title.setFont(new Font("Helvetica", Font.PLAIN, 18));
            title.setForeground(Color.black);
            event.add(title);

            JLabel time = new JLabel(events.get(i).getDateTimeToString());
            time.setBorder(BorderFactory.createEmptyBorder(0, 15, 4, 15));
            time.setFont(new Font("Helvetica", Font.PLAIN, 14));
            time.setForeground(Color.DARK_GRAY);
            event.add(time);

            list.add(event);
        }

        add(sp, BorderLayout.CENTER);

        JButton newEvent = new JButton("New");
        newEvent.setFont(new Font("Helvetica", Font.PLAIN, 20));
        newEvent.setBackground(Color.decode("#00d1e8"));
        newEvent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EventEditor(new Event(date), database, mainPanel);
            }
        });
        add(newEvent, BorderLayout.SOUTH);

        JButton searchEvent = new JButton("Search");
        searchEvent.setFont(new Font("Helvetica", Font.PLAIN, 20));
        searchEvent.setBackground(Color.decode("#00d1e8"));
        searchEvent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EventSearcher(date, database, mainPanel);
            }
        });
        add(searchEvent, BorderLayout.NORTH);
    }
}
