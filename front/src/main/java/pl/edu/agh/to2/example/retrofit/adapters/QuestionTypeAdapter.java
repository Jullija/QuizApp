package pl.edu.agh.to2.example.retrofit.adapters;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.example.model.Chart.Answer;
import pl.edu.agh.to2.example.model.Chart.Question;
import pl.edu.agh.to2.example.model.Form;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class QuestionTypeAdapter implements JsonSerializer<Question>, JsonDeserializer<Question> {

    @Override
    public JsonElement serialize(Question src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        return jsonObject;
    }

    @Override
    public Question deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        String title = jsonObject.get("title").getAsString();
        float successRate = jsonObject.get("successRate").getAsFloat();

        JsonArray answersJsonArray = jsonObject.getAsJsonArray("answerList");
        Answer[] answers = context.deserialize(answersJsonArray, Answer[].class);

        return new Question(title, successRate, List.of(answers));
    }
}