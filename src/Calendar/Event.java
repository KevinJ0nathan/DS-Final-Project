package Calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private int ID;
    private String title;
    private String description;
    private LocalDateTime dateTime;

    public Event(int ID, String title, String description, LocalDateTime dateTime){
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    public Event(LocalDate date){
        dateTime = LocalDateTime.of(date, LocalTime.now())
    }

    public Event(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTimeToString(){
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm"));
    }

    public String getDateToString(){
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getTimeToString(){
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setDateTimeFromString(String dateTime){
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm"));
    }

    public void setTime(String time){
        this.dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }

    public LocalDate getDate(){
        return dateTime.toLocalDate();
    }

}
