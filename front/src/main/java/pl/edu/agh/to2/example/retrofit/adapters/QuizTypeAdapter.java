package pl.edu.agh.to2.example.retrofit.adapters;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.example.model.Form;
import pl.edu.agh.to2.example.model.Quiz;
import pl.edu.agh.to2.example.model.Strategy;

import java.lang.reflect.Type;

public class QuizTypeAdapter implements JsonSerializer<Quiz>, JsonDeserializer<Quiz> {

    @Override
    public JsonElement serialize(Quiz src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        jsonObject.add("forms", context.serialize(src.getFormsProperty()));
        jsonObject.add("strategyID", context.serialize(src.getStrategy()));
        return jsonObject;
    }

    @Override
    public Quiz deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("quizName").getAsString();
        JsonArray formsJsonArray = jsonObject.getAsJsonArray("forms");
        Form[] formsArray = context.deserialize(formsJsonArray, Form[].class);
        ObservableList<Form> forms = FXCollections.observableArrayList();
        forms.addAll(formsArray);

        Strategy strategy = context.deserialize(jsonObject.get("strategyID"), Strategy.class);

        return new Quiz(id, name, strategy, forms);
    }
}

