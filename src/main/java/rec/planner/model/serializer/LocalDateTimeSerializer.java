package rec.planner.model.serializer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends TypeAdapter<LocalDateTime> {


    DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime dateTime) throws IOException {
        jsonWriter.value(dateTime.format(DATA_FORMAT));
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        System.out.println(jsonReader.nextString());
        return LocalDateTime.parse(jsonReader.nextString(),DATA_FORMAT);
    }
}
