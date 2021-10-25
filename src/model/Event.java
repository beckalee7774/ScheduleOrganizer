package model;

import org.json.JSONObject;

import java.sql.Time;

public class Event {
    public int start_day; //0-6
    public int start_hour; //0-23
    public int start_minute; //0-59
    public int end_day;
    public int end_hour;
    public int end_minute;
    String event_description;

    //Constructs an event
    public Event(String e,int s_day, int s_hour, int s_minute, int e_day, int e_hour, int e_minute) {
        event_description = e;
        start_day = s_day;
        start_hour = s_hour;
        start_minute = s_minute;
        end_day = e_day;
        end_hour = e_hour;
        end_minute = e_minute;
    }

    public boolean check_events_clash(Event event2) {
        if(start_day < event2.start_day) {
            return eventEndIsAfterEvent2Start(event2);
        } else if(start_day == event2.start_day) {
            return sameStartDay(event2);
        } else { // start_day > event2.start_day
            return event2endIsAfterEventStart(event2);
        }
    }

    private boolean event2endIsAfterEventStart(Event event2) {
        if(event2.end_day > start_day) {
            return true;
        } else if(event2.end_day == start_day) {
            if(event2.end_hour > start_hour) {
                return true;
            } else if(event2.end_hour == start_hour) {
                if(event2.end_minute > start_minute) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean eventEndIsAfterEvent2Start(Event event2) {
        if(end_day > event2.start_day) {
            return true;
        } else if(end_day == event2.start_day) {
            if(end_hour > event2.start_hour) {
                return true;
            } else if(end_hour == event2.start_hour) {
                if(end_minute > event2.start_minute) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean sameStartDay(Event event2) {
        if(start_hour < event2.start_hour) {
            return eventEndIsAfterEvent2Start(event2);
        } else if(start_hour == event2.start_hour) {
            return SameStartDayHour(event2);
        } else { //start_hour > event2.start_hour
            return event2endIsAfterEventStart(event2);
        }
    }

    private boolean SameStartDayHour(Event event2) {
        if(start_minute < event2.start_minute) {
            return eventEndIsAfterEvent2Start(event2);
        } else if(start_minute == event2.start_minute) {
            return true;
        } else { //start_minute > event2.start_minute
            return event2endIsAfterEventStart(event2);
        }
    }

    public boolean isBefore(Event newEvent) {
        if(start_day < newEvent.start_day) {
            return true;
        } else if(start_day == newEvent.start_day) {
            return isBeforeSameDay(newEvent);
        } else { //start_day > newEvent.start_day
            return false;
        }
    }

    private boolean isBeforeSameDay(Event newEvent) {
        if(start_hour < newEvent.start_hour) {
            return true;
        } else if(start_hour == newEvent.start_hour) {
            return isBeforeSameDayHour(newEvent);
        } else { //start_hour > newEvent.start_hour
            return false;
        }
    }

    private boolean isBeforeSameDayHour(Event newEvent) {
        return start_minute < newEvent.start_minute;
    }

    public void print() {
        String s_day = "";
        String e_day = "";
        switch(start_day) {
            case 0 : s_day = "sunday";
                break;
            case 1 : s_day = "monday";
                break;
            case 2 : s_day = "tuesday";
                break;
            case 3 : s_day = "wednesday";
                break;
            case 4 : s_day = "thursday";
                break;
            case 5 : s_day = "friday";
                break;
            case 6 : s_day = "saturday";
                break;

        }
        switch(end_day) {
            case 0 : e_day = "sunday";
                break;
            case 1 : e_day = "monday";
                break;
            case 2 : e_day = "tuesday";
                break;
            case 3 : e_day = "wednesday";
                break;
            case 4 : e_day = "thursday";
                break;
            case 5 : e_day = "friday";
                break;
            case 6 : e_day = "saturday";
                break;

        }
        String s_extraZero = "";
        String e_extraZero = "";
        if(start_minute < 10) {
            s_extraZero = "0";
        }
        if(end_minute < 10) {
            e_extraZero = "0";
        }
        System.out.println(event_description + " " + s_day +" " + start_hour + ":" + s_extraZero +
                start_minute + " - " + e_day +" " + end_hour + ":" + e_extraZero + end_minute);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("start day", start_day);
        json.put("start hour", start_hour);
        json.put("start minute", start_minute);
        json.put("end day", end_day);
        json.put("end hour", end_hour);
        json.put("end minute", end_minute);
        json.put("event description", event_description);
        return json;
    }
}
