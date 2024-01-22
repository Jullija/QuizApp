package pl.edu.agh.to2.example.retrofit.adapters;

import com.google.gson.*;
import pl.edu.agh.to2.example.model.Chart.Answer;
import pl.edu.agh.to2.example.model.Chest;

import java.lang.reflect.Type;

public class AnswerTypeAdapter implements JsonSerializer<Answer>, JsonDeserializer<Answer> {

    @Override
    public JsonElement serialize(Answer src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        return jsonObject;
    }

    @Override
    public Answer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        String title = jsonObject.get("title").getAsString();
        float successRate = jsonObject.get("successRate").getAsFloat();

        return new Answer(title, successRate);
    }
}