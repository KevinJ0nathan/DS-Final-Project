package Calendar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JTextField;

public class EventSearcher {
    public EventSearcher(LocalDate date, Database database, JPanel parent) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ArrayList<Event> events = database.getEvents(dateFormatter.format(date));

        JFrame frame = new JFrame("Calendar");
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

        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (keyword.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Keyword cannot be empty");
                    return;
                }
                String titleSearch = keyword.getText().toLowerCase();
                System.out.println("button clicked");
                for (int i = 0; i < events.size(); i++) {
                    if (events.get(i).getTitle().toLowerCase().contains(titleSearch)) {
                        System.out.println(events.get(i).getTitle());
                    }
                    return;
                }

            }
        });

        mainPanel.add(bottom, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
    }
}
