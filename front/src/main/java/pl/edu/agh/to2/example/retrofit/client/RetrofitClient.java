package pl.edu.agh.to2.example.retrofit.client;

import com.google.gson.GsonBuilder;
import pl.edu.agh.to2.example.model.*;
import pl.edu.agh.to2.example.model.Chart.Answer;
import pl.edu.agh.to2.example.model.Chart.Question;
import pl.edu.agh.to2.example.retrofit.adapters.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapter(Chest.class, new ChestTypeAdapter())
                            .registerTypeAdapter(Award.class, new AwardTypeAdapter())
                            .registerTypeAdapter(Form.class, new FormTypeAdapter())
                            .registerTypeAdapter(Quiz.class, new QuizTypeAdapter())
                            .registerTypeAdapter(Strategy.class, new StrategyTypeAdapter())
                            .registerTypeAdapter(StrategyType.class, new StrategyTypeTypeAdapter())
                            .registerTypeAdapter(Question.class, new QuestionTypeAdapter())
                            .registerTypeAdapter(Answer.class, new AnswerTypeAdapter())
                            .create()))
            .build();
    private final FormClient formClient = new FormClient(this);
    private final QuizClient quizClient = new QuizClient(this);
    private final ChestClient chestClient = new ChestClient(this);
    private final AwardClient awardClient = new AwardClient(this);
    private final StrategyClient strategyClient = new StrategyClient(this);

    public RetrofitClient() {
    }

    protected Retrofit getRetrofit() {
        return retrofit;
    }

    public FormClient getFormClient() {
        return formClient;
    }

    public QuizClient getQuizClient() {
        return quizClient;
    }

    public ChestClient getChestClient() {
        return chestClient;
    }

    public AwardClient getAwardClient() {
        return awardClient;
    }

    public StrategyClient getStrategyClient() {
        return strategyClient;
    }
}
