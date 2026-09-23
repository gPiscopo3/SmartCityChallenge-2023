package rec.planner.model.serializer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class BooleanToIntSerializer extends TypeAdapter<Boolean> {
    @Override
    public void write(JsonWriter jsonWriter, Boolean aBoolean) throws IOException {
        if(aBoolean)
            jsonWriter.value(1);
        else
            jsonWriter.value(0);
    }

    @Override
    public Boolean read(JsonReader jsonReader) throws IOException {
        return jsonReader.nextInt() == 1;
    }
}
