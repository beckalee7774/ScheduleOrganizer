package model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public List<Event> events;

    public Schedule() {
        events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event newEvent) {
        boolean add_to_schedule = true;
        for(Event event : events) {
            if(event.check_events_clash(newEvent)) {
                System.out.println("events clashed, could not add to schedule");
                add_to_schedule = false;
            }
            if(event.event_description.equals(newEvent.event_description)) {
                System.out.println("you can not enter two events with the same name, did not add to schedule");
                add_to_schedule = false;
            }
        }
        if(add_to_schedule) {
            addInOrder(newEvent);
            System.out.println("successfully added event");
        }
    }

    public void addInOrder(Event newEvent) {
        boolean was_added = false;
        for(int i = 0; i < events.size(); i++) {
            if(newEvent.isBefore(events.get(i)) && !was_added) {
                events.add(i, newEvent);
                was_added = true;
            }
        }
        if(!was_added) {
            events.add(newEvent);
        }
    }

    public void removeEvent(String event_description) {
        boolean removed_event = false;
        Event eventToRemove = null;
        for(Event event : events) {
            if(event.event_description.equals(event_description)) {
                eventToRemove = event;
                removed_event = true;
            }
        }
        if(eventToRemove != null) {
            events.remove(eventToRemove);
        }
        if(removed_event) {
            System.out.println("successfully removed event");
        } else {
            System.out.println("could not find event, nothing was removed");
        }
    }

    public void print() {
        for(Event event : events) {
            event.print();
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("events", eventsToJson(events));
        return json;
    }

    private JSONArray eventsToJson(List<Event> events) {
        JSONArray jsonArray = new JSONArray();
        for(Event event : events) {
            jsonArray.put(event.toJson());
        }
        return jsonArray;
    }
}
