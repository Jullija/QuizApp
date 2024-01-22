package pl.edu.agh.to2.example.retrofit.adapters;


import com.google.gson.*;
import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.model.Form;

import java.lang.reflect.Type;

public class FormTypeAdapter implements JsonSerializer<Form>, JsonDeserializer<Form> {

    @Override
    public JsonElement serialize(Form src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("nick", src.getNick());
        jsonObject.addProperty("points", src.getPoints());
        jsonObject.addProperty("timestamp", src.getTime());
        jsonObject.addProperty("endTime", src.getEndTime());
        jsonObject.add("award", context.serialize(src.getAward()));
        return jsonObject;
    }

    @Override
    public Form deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String nick = jsonObject.get("nick").getAsString();
        float points = jsonObject.get("points").getAsFloat();
        int time = jsonObject.get("timestamp").getAsInt();
        String endTime = jsonObject.get("endTime").getAsString();
        Award award = context.deserialize(jsonObject.get("award"), Award.class);

        return new Form(id, nick, points, time, endTime, award);
    }
}
