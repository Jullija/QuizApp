package pl.edu.agh.to2.example.retrofit.service;

import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.model.Form;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface FormAPIService {

    @PATCH("/forms/{id}")
    Call<Form> updateAward(@Path("id") int formID, @Body Award award);
}
