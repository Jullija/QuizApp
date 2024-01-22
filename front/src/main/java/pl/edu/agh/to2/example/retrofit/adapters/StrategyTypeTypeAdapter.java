package pl.edu.agh.to2.example.retrofit.adapters;

import com.google.gson.*;
import pl.edu.agh.to2.example.model.StrategyType;

import java.lang.reflect.Type;

public class StrategyTypeTypeAdapter implements JsonSerializer<StrategyType>, JsonDeserializer<StrategyType> {

    @Override
    public JsonElement serialize(StrategyType src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        System.out.println(jsonObject);
        jsonObject.addProperty("name", src.getName());

        return jsonObject;
    }

    @Override
    public StrategyType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        String name = json.getAsString();

        return new StrategyType(name);
    }
}
