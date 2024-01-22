package pl.edu.agh.to2.example.retrofit.adapters;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.model.Chest;

import java.lang.reflect.Type;

public class AwardTypeAdapter implements JsonSerializer<Award>, JsonDeserializer<Award> {

    @Override
    public JsonElement serialize(Award src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("description", src.getDescription());
        jsonObject.add("chest", context.serialize(src.getChestsProperty()));
        return jsonObject;
    }

    @Override
    public Award deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.isJsonNull()){
            return new Award( -100000,"-", "...", FXCollections.observableArrayList());
        }else{
            int id = jsonObject.get("id").getAsInt();
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();

            JsonArray formsJsonArray = jsonObject.getAsJsonArray("chest");
            Chest[] chestsArray = context.deserialize(formsJsonArray, Chest[].class);
            ObservableList<Chest> chests = FXCollections.observableArrayList();
            chests.addAll(chestsArray);
            return new Award(id, name, description, chests);
        }




    }
}
