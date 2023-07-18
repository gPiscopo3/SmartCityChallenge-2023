package rec.planner.model.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import rec.planner.model.MTUArray;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MTUArraySerializer<T> extends TypeAdapter<MTUArray<T>> {

    private Gson gson;
    private Class<T> tClass;

    public MTUArraySerializer(Class<T> tClass) {
        this.tClass = tClass;
        gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();
    }

    public MTUArraySerializer(Class<T> tClass, TypeAdapter<T> adapter) {
        this.tClass = tClass;
        gson = new GsonBuilder().registerTypeAdapter(tClass, adapter).registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();
    }



    @Override
    public void write(JsonWriter jsonWriter, MTUArray array) throws IOException {
        jsonWriter.value(gson.toJson(array));
    }

    @Override
    public MTUArray<T> read(JsonReader jsonReader) throws IOException {
        Wrapper<T> wrapper = gson.fromJson(jsonReader.nextString(), Wrapper.class);
        return MTUArray.ofValues(wrapper.values);
    }

    private static class Wrapper<T>{

        List<T> values;

        public Wrapper() {
        }
    }

}
