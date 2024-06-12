package Calendar;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private String filePath = "eventsHashMap.txt";

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

    public HashMap<String, Event> getEvents(String date) {
        HashMap<String, Event> events = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
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
        ArrayList<Event> events = new ArrayList<>();

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
        ArrayList<Event> events = new ArrayList<>();

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
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getID() == e.getID()) {
                events.set(i, e);
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
        ArrayList<Event> events = new ArrayList<>();

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
        events.removeIf(event -> event.getID() == ID);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Deleting event: " + totalTime / 1000000.0 + " ms");

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
