package rec.planner.model.serializer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends TypeAdapter<LocalDate> {

    DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(JsonWriter jsonWriter, LocalDate date) throws IOException {
        jsonWriter.value(date.format(DATA_FORMAT));
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        return LocalDate.parse(jsonReader.nextString(),DATA_FORMAT);
    }
}
