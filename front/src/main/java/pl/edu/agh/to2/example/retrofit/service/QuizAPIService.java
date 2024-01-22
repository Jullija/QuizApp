package pl.edu.agh.to2.example.retrofit.service;

import okhttp3.MultipartBody;
import pl.edu.agh.to2.example.model.Chart.Question;
import pl.edu.agh.to2.example.model.Quiz;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface QuizAPIService {
    @GET("/quizzes")
    Call<List<Quiz>> getAllQuizzes();

    @PUT("/quizzes/updatedQuiz/{id}")
    Call<Quiz> updateQuizStrategy(@Path("id") int id, @Body int strategyId);

    @Multipart
    @POST("/quizzes/inputFile")
    Call<Quiz> uploadFile(@Part MultipartBody.Part file);

    @GET("/quizzes/exportQuiz/{id}")
    Call<Quiz> exportQuiz(@Path("id") int id);

    @GET("/quizzes/statistics/{id}")
    Call<List<Question>> getQuestions(@Path("id") int id);
}
