package Calendar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class EventEditor {
    public EventEditor(Event e, Database database, JPanel parent) {
        int year = e.getDate().getYear();
        int month = e.getDate().getMonthValue();

        // Sets the frame for the pop-up window
        JFrame frame = new JFrame("Calendar");
        frame.setSize(700, 350);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        // Main panel, objects will be added into this panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        mainPanel.setBackground(Color.white);

        // This panel will display the title, time and description
        JPanel center = new JPanel(new GridLayout(3, 2, 20, 20));
        center.setBackground(Color.white);

        // Label for event title
        JLabel l1 = new JLabel("Title");
        l1.setFont(new Font("Helvetica", Font.PLAIN, 20));
        l1.setHorizontalAlignment(JLabel.CENTER);
        center.add(l1);

        // To recieve user title input
        JTextField title = new JTextField();
        title.setFont(new Font("Helvetica", Font.PLAIN, 20));
        title.setHorizontalAlignment(JLabel.CENTER);
        center.add(title);

        // Label for event time
        JLabel l2 = new JLabel("Time");
        l2.setFont(new Font("Helvetica", Font.PLAIN, 20));
        l2.setHorizontalAlignment(JLabel.CENTER);
        center.add(l2);

        // To recieve user time input
        JTextField time = new JTextField();
        time.setFont(new Font("Helvetica", Font.PLAIN, 20));
        time.setHorizontalAlignment(JLabel.CENTER);
        center.add(time);

        // Label for event description
        JLabel l3 = new JLabel("Description");
        l3.setFont(new Font("Helvetica", Font.PLAIN, 20));
        l3.setHorizontalAlignment(JLabel.CENTER);
        center.add(l3);

        // To recieve user  description input
        JTextField description = new JTextField();
        description.setFont(new Font("Helvetica", Font.PLAIN, 20));
        description.setHorizontalAlignment(JLabel.CENTER);
        center.add(description);

        // Then the event details are added to the center of the editor panel
        mainPanel.add(center, BorderLayout.CENTER);

        // This is to confirm user input, either delete or save the event
        JPanel bottom = new JPanel(new GridLayout(1, 2, 20, 20));
        bottom.setBackground(null);

        // Creating the delete button (To remove a selected event)
        JButton delete = new JButton("Delete");
        delete.setFont(new Font("Helvetica", Font.PLAIN, 20));
        delete.setBackground(Color.decode("#00d1e8"));
        delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.add(delete);

        // Creating the save button (to save an existing event or add a new one)
        JButton save = new JButton("Save");
        save.setFont(new Font("Helvetica", Font.PLAIN, 20));
        save.setBackground(Color.decode("#00d1e8"));
        save.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.add(save);

        // Used in both update and create, this sets the default input to the user's current time
        time.setText(e.getTimeToString());

        // This checks if the event has a title (used to decide if either an existing event is selected or a new one is to be made)
        if (e.getTitle() != null) { // This is for modifying an event

            // Upon opening the event, the information displayed is kept the same
            title.setText(e.getTitle());
            description.setText(e.getDescription());

            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {

                    // Error handling
                    if (title.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Title cannot be empty");
                        return;
                    }

                    if (description.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Description cannot be empty");
                        return;
                    }

                    // Sets the user input as the new attribute to be updated in the event object
                    e.setTitle(title.getText());
                    e.setDescription(description.getText());
                    try {
                        e.setTime(time.getText());
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Check time format HH:mm");
                        return;
                    }

                    // Update event in database
                    database.updateEvent(e);

                    // Refreshing main view (calendar & events)
                    parent.removeAll();
                    parent.add(new Calendar(year, month, e.getDate(), parent, database));
                    parent.add(new Events(e.getDate(), database, parent));
                    parent.revalidate();
                    frame.dispose();
                }
            });

            // Adding on click response to the delete button
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {

                    // Delete event from database
                    database.deleteEvent(e.getID());

                    // Refreshing main view (calendar & events)
                    parent.removeAll();
                    parent.add(new Calendar(year, month, e.getDate(), parent, database));
                    parent.add(new Events(e.getDate(), database, parent));
                    parent.revalidate();
                    frame.dispose();
                }
            });
        } else { // This is for adding a new event
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {

                    // Same logic applies here
                    if (title.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Title cannot be empty");
                        return;
                    }
                    if (description.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Description cannot be empty");
                        return;
                    }
                    e.setTitle(title.getText());
                    e.setDescription(description.getText());
                    try {
                        e.setTime(time.getText());
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Check time format HH:mm");
                        return;
                    }

                    // Create new event in database
                    database.createEvent(e);

                    // Refreshing main view (calendar & events)
                    parent.removeAll();
                    parent.add(new Calendar(year, month, e.getDate(), parent, database));
                    parent.add(new Events(e.getDate(), database, parent));
                    parent.revalidate();
                    frame.dispose();
                }
            });

            // The delete button is set hidden
            delete.setVisible(false);
        }

        mainPanel.add(bottom, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
    }
}
