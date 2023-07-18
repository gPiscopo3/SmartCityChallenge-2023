package rec.planner.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;
import rec.planner.model.MTUArray;
import rec.planner.model.serializer.LocalDateSerializer;
import rec.planner.model.serializer.MTUArraySerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MongoDocumentConverter {

    private static final Gson gson = new GsonBuilder().
            registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateSerializer())
            .create();


    public static  <T> T fromDocument(Document document, Class<T> tClass) throws RuntimeException{
        return gson.fromJson(document.toJson(), tClass);
    }

    public static <T> Document toDocument(T object) throws RuntimeException{
        return Document.parse(gson.toJson(object));
    }


}
