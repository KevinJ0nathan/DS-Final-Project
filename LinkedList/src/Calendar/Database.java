package Calendar;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Database {
    private String filePath = "events.txt";

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

    public LinkedList<Event> getEvents(String date) {
        LinkedList<Event> events = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Event e = parseEvent(line);
                if (e != null && e.getDateToString().equals(date)) {
                    events.add(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean hasEvent(String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
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

    public void createEvent(Event e) {
        e.setID(generateUniqueID()); // Set unique ID for the new event
        LinkedList<Event> events = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Event currentEvent = parseEvent(line);
                if (currentEvent != null) {
                    events.add(currentEvent);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        long startTime, endTime, totalTime;
        startTime = System.nanoTime();
        events.add(e);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Adding event: " + totalTime / 1000000.0 + " ms");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event ev : events) {
                writer.write(eventToString(ev));
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void updateEvent(Event e) {
        LinkedList<Event> events = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Event currentEvent = parseEvent(line);
                if (currentEvent != null) {
                    events.add(currentEvent);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        long startTime, endTime, totalTime;
        startTime = System.nanoTime();
        for (Event event : events) {
            if (event.getID() == e.getID()) {
                events.remove(event);
                events.add(e);
                break;
            }
        }
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Updating event: " + totalTime / 1000000.0 + " ms");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event ev : events) {
                writer.write(eventToString(ev));
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteEvent(int ID) {
        LinkedList<Event> events = new LinkedList<>();
        
        // Step 1: Read all events from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Event currentEvent = parseEvent(line);
                if (currentEvent != null) {
                    events.add(currentEvent);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Step 2: Remove the event with the specified ID
        long startTime, endTime, totalTime;
        startTime = System.nanoTime();
        for (int i = events.size() - 1; i > 0; i--) {
            Event event = events.get(i);
            if (event.getID() == ID) {
                events.remove(event);
                break;
            }
        }
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Deleting event: " + totalTime / 1000000.0 + " ms");

        // Step 3: Write the remaining events back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event ev : events) {
                writer.write(eventToString(ev));
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Event parseEvent(String line) {
        // Example line format: ID|Title|Description|Date|Time
        String[] parts = line.split("\\|");
        if (parts.length != 5) return null;
        Event e = new Event();
        e.setID(Integer.parseInt(parts[0]));
        e.setTitle(parts[1]);
        e.setDescription(parts[2]);
        e.setDateTimeFromString(parts[3] + " | " + parts[4]);
        return e;
    }

    private String eventToString(Event e) {
        // Convert event to string format: ID|Title|Description|Date|Time
        return e.getID() + "|" + e.getTitle() + "|" + e.getDescription() + "|" + e.getDateToString() + "|" + e.getTimeToString();
    }

    private int generateUniqueID() {
        int maxID = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Event e = parseEvent(line);
                if (e != null && e.getID() > maxID) {
                    maxID = e.getID();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxID + 1;
    }
}
