package ui;

import model.Event;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.InvalidEntryException;
import utils.ExcelReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ScheduleOrganizerConsoleApp {

    private final Scanner input;
    private Schedule schedule;
    boolean keepGoing = true;
    int s_day;
    int s_hour;
    int s_minute;
    int e_day;
    int e_hour;
    int e_minute;
    String name;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/schedule.json";

    public ScheduleOrganizerConsoleApp() {
        input = new Scanner(System.in);
        schedule = new Schedule();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        System.out.println("enter 'e' to add an event, " +
                "enter 'q' to quit, enter 'l' to load a saved schedule, " +
                "enter ex to load the schedule from the excel file");
        String command = input.next();
        while(!command.equals("q") && !command.equals("e") && !command.equals("l") && !command.equals(("ex"))) {
            System.out.println("not valid, please enter 'q', 'e', 'l', or 'ex'");
            command = input.next();
        }
        keepGoing = !command.equals("q");
        while(keepGoing) {
            switch (command) {
                case "e":
                    try {
                        enterEvent();
                    } catch (InvalidEntryException e) {
                        System.out.println("entry was not valid, could not enter event");
                    }
                    break;
                case "r":
                    removeEvent();
                    break;
                case "s":
                    saveSchedule();
                    break;
                case "l":
                    loadSchedule();
                    break;
                case "ex":
                    ExcelReader excelReader = new ExcelReader(schedule);
                    try {
                        schedule = excelReader.readFromExcel();
                    } catch (IOException e) {
                        System.out.println("could not load excel file, error occurred");
                    }
                    break;
                default:
                    printSchedule();
                    break;
            }

            System.out.println("enter 'e' to add an event, enter 'q' to quit, enter 'r' to remove an event, " +
                    "enter 'p' to print your schedule, enter 's' to save a schedule, enter 'l' to load a schedule," +
                    "enter ex to load the schedule from the excel file");
            command = input.next();
            while(!command.equals("q") && !command.equals("e") && !command.equals("r") &&
            !command.equals("p") && !command.equals("s") && !command.equals("l")&& !command.equals(("ex"))) {
                System.out.println("not valid, please enter 'q', 'e', 'r' or p");
                command = input.next();
            }
            keepGoing = !command.equals("q");
        }
    }

    public void enterEvent() throws InvalidEntryException {
        System.out.println("enter the start day of the week that this event is for " +
                "(sunday - 0, monday - 1, tuesday - 2 etc");
        s_day = input.nextInt();
        checkDay(s_day);

        System.out.println("enter the start hour ");
        s_hour = input.nextInt();
        checkHour(s_hour);

        System.out.println("enter the start minute");
        s_minute = input.nextInt();
        checkMinute(s_minute);

        System.out.println("enter the end day of the week that this event is for " +
                "(sunday - 0, monday - 1, tuesday - 2 etc");
        e_day = input.nextInt();
        checkDay(e_day);

        System.out.println("enter the end hour  ");
        e_hour = input.nextInt();
        checkHour(e_hour);

        System.out.println("enter the end minute  ");
        e_minute = input.nextInt();
        checkMinute(e_minute);

        System.out.println("enter the name of the event");
        name = input.next();

        Event e = new Event(name, s_day, s_hour, s_minute, e_day, e_hour, e_minute);
        schedule.addEvent(e);
    }
    private void checkDay(int day) throws InvalidEntryException {
        if(day < 0 || day > 6) {
            throw new InvalidEntryException();
        }
    }

    private void checkHour(int hour) throws InvalidEntryException {
        if(hour < 0 || hour > 23) {
            throw new InvalidEntryException();
        }
    }

    private void checkMinute(int minute) throws InvalidEntryException {
        if(minute < 0 || minute > 59) {
            throw new InvalidEntryException();
        }
    }

    public void removeEvent() {
        System.out.println("enter the name of the event you want to remove");
        String toRemove = input.next();
        schedule.removeEvent(toRemove);
    }

    public void printSchedule() {
        schedule.print();
    }

    // MODIFIES: this
    // EFFECTS: saves Schedule to file
    private void saveSchedule() {
        try {
            jsonWriter.open();
            jsonWriter.writeFile(schedule);
            jsonWriter.close();
            System.out.println("This game was saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadSchedule() {
        try {
            schedule = jsonReader.read();
            System.out.println("Game was loaded" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
