package Calendar;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;

public class Database {
    private String filePath = "eventsHashMap.txt";

    // Constructor
    public Database() {

        // Initialize the database file if it doesn't exist
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the events from the txt file
    public HashMap<String, Event> getEvents(String date) {
        HashMap<String, Event> events = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Continue reading until the end
            while ((line = reader.readLine()) != null) {
                Event e = parseEvent(line);
                if (e != null && e.getDateToString().equals(date)) {
                    events.put(e.getID() + "", e); // Using event ID as key
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    // Method used to check if a date has one or more event
    public boolean hasEvent(String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Continue reading until the end
            while ((line = reader.readLine()) != null) {
                Event e = parseEvent(line);
                if (e != null && e.getDateToString().equals(date)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method used to create a new event
    public void createEvent(Event e) {
        e.setID(generateUniqueID()); // Set unique ID for the new event
        HashMap<Integer, Event> events = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Continue reading until the end
            while ((line = reader.readLine()) != null) {
                Event currentEvent = parseEvent(line);
                if (currentEvent != null) {
                    events.put(currentEvent.getID(), currentEvent);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // To record the time taken to create a new event
        long startTime, endTime, totalTime;
        startTime = System.nanoTime();

        // Puts a new key-value pair into the hashmap to represent the new event
        events.put(e.getID(), e);

        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Adding event: " + totalTime / 1000000.0 + " ms");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event ev : events.values()) {
                writer.write(eventToString(ev));
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method used to change the information of a selected event
    public void updateEvent(Event e) {
        HashMap<Integer, Event> events = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Continue reading until the end
            while ((line = reader.readLine()) != null) {
                Event currentEvent = parseEvent(line);
                if (currentEvent != null) {
                    events.put(currentEvent.getID(), currentEvent);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // To record the time taken to modify a selected event
        long startTime, endTime, totalTime;
        startTime = System.nanoTime();

        // Simply put the same key with the updated value to replace the old key-value pair
        if (events.containsKey(e.getID())) {
            events.put(e.getID(), e);
        }

        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Updating event: " + totalTime / 1000000.0 + " ms");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event ev : events.values()) {
                writer.write(eventToString(ev));
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method used to delete a selected event
    public void deleteEvent(int ID) {
        HashMap<Integer, Event> events = new  HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Continue reading until the end
            while ((line = reader.readLine()) != null) {
                Event currentEvent = parseEvent(line);
                if (currentEvent != null) {
                    events.put(currentEvent.getID(), currentEvent);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // To record the time taken to delete a selected event
        long startTime, endTime, totalTime;
        startTime = System.nanoTime();

        // Simply remove the key-value pair by using the hashmap key
        if (events.containsKey(ID)) {
            events.remove(ID);
        }

        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Deleting event: " + totalTime / 1000000.0 + " ms");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event ev : events.values()) {
                writer.write(eventToString(ev));
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method used to set a new ID for every event added, and allows delete function to delete only 1 event
    private int generateUniqueID() {
        int maxID = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Continue reading until the end
            while ((line = reader.readLine()) != null) {
                Event e = parseEvent(line);

                // Gets the latest added event ID
                if (e != null && e.getID() > maxID) {
                    maxID = e.getID();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Then increases the ID by 1 to make it unique
        return maxID + 1;
    }

    // Parses the event from a simple string into the event object that can be used to manipulate the information of the object
    private Event parseEvent(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) return null;
        Event e = new Event();
        e.setID(Integer.parseInt(parts[0]));                    // For the ID
        e.setTitle(parts[1]);                                   // For the title
        e.setDescription(parts[2]);                             // For the description
        e.setDateTimeFromString(parts[3] + " | " + parts[4]);   // For the time
        return e;
    }

    // Converts the object into a string to store in the txt file
    private String eventToString(Event e) {
        // Convert event to string format: ID|Title|Description|Date|Time
        return e.getID() + "|" + e.getTitle() + "|" + e.getDescription() + "|" + e.getDateToString() + "|" + e.getTimeToString();
    }

    // Method used to automate adding of events for the purpose of testing different data structures
    public void addMassData() {
        String filePath = "eventsHashMap.txt";
        
        // Count the number of lines in the file
        long lineCount = 0;
        try {
            lineCount = Files.lines(Paths.get(filePath)).count();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // If the number of lines is less than this integer, add the datas
        if (lineCount < 1) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                for (int i = 1; i < 20001; i++) {

                    // The date can be anytime
                    String data = i + "|test|test|12-06-2024|12:00";
                    writer.write(data);
                    writer.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
