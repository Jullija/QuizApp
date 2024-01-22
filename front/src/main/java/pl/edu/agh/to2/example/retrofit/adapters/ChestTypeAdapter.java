package pl.edu.agh.to2.example.retrofit.adapters;

import com.google.gson.*;
import pl.edu.agh.to2.example.model.Chest;

import java.lang.reflect.Type;

public class ChestTypeAdapter implements JsonSerializer<Chest>, JsonDeserializer<Chest> {

    @Override
    public JsonElement serialize(Chest src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        return jsonObject;
    }

    @Override
    public Chest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();

        return new Chest(id, name);
    }
}