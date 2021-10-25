package persistence;

import model.Event;
import model.Schedule;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    private Schedule parseGame(JSONObject jsonObject) {
        Schedule schedule = new Schedule();
        addEvents(schedule, jsonObject);
        return schedule;
    }

    private void addEvents(Schedule schedule, JSONObject jsonObject) {
        JSONArray events = jsonObject.getJSONArray("events");
        for(Object json : events) {
            addEventToEvents(json, schedule.getEvents());
        }
    }

    private void addEventToEvents(Object obj, List<Event> events) {
        JSONObject json = (JSONObject) obj;
        int start_day = json.getInt("start day");
        int start_hour = json.getInt("start hour");
        int start_minute = json.getInt("start minute");
        int end_day = json.getInt("end day");
        int end_hour = json.getInt("end hour");
        int end_minute = json.getInt("end minute");
        String event_description = json.getString("event description");
        Event event = new Event(event_description, start_day, start_hour, start_minute,
                end_day, end_hour, end_minute);
        events.add(event);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }
}
