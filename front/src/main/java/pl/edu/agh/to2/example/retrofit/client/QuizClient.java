package pl.edu.agh.to2.example.retrofit.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.edu.agh.to2.example.exception.DatabaseResponseFailure;
import pl.edu.agh.to2.example.model.Chart.Question;
import pl.edu.agh.to2.example.model.Quiz;
import pl.edu.agh.to2.example.model.Strategy;
import pl.edu.agh.to2.example.retrofit.service.QuizAPIService;
import retrofit2.Response;

import java.io.File;
import java.util.List;

public class QuizClient {
    private final QuizAPIService quizAPIService;

    public QuizClient(RetrofitClient retrofitClient) {
        quizAPIService = retrofitClient.getRetrofit().create(QuizAPIService.class);
    }

    public ObservableList<Quiz> getAllQuizzes() throws Exception {
        Response<List<Quiz>> response = this.quizAPIService.getAllQuizzes().execute();

        if (response.isSuccessful()) {
            return FXCollections.observableArrayList(response.body());
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    public Quiz updateQuizStrategy(int quizId, Strategy strategy) throws Exception {
        Response<Quiz> response = this.quizAPIService.updateQuizStrategy(quizId, strategy.getId()).execute();

        if (response.isSuccessful()) {
            return this.quizAPIService.updateQuizStrategy(quizId, strategy.getId()).execute().body();
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    private Quiz uploadFile(File fileToUpload) throws Exception {
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileToUpload);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileToUpload.getName(), fileRequestBody);

        Response<Quiz> response = quizAPIService.uploadFile(filePart).execute();

        if (response.isSuccessful()) {
            return response.body();
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    public Quiz addFile(File file) throws Exception {
        return uploadFile(file);
    }

    public Quiz exportQuiz(int quizId) throws Exception{
        System.out.println("exporting quiz");

        Response<Quiz> response = this.quizAPIService.exportQuiz(quizId).execute();
        System.out.println(response.body());

        System.out.println("hejka");
        System.out.println(response.raw());

        if (response.isSuccessful()) {
            System.out.println("success");
            return response.body();
        }

        System.out.println("fail");
        System.out.println(response.raw());
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    public List<Question> getQuestions(int quizId) throws Exception{
        Response<List<Question>> response = this.quizAPIService.getQuestions(quizId).execute();

        if (response.isSuccessful()) {
            return response.body();
        }

        throw new DatabaseResponseFailure(response.code(), response.message());
    }

}