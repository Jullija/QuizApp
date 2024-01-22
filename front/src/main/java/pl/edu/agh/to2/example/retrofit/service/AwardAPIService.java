package pl.edu.agh.to2.example.retrofit.service;

import pl.edu.agh.to2.example.model.Award;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface AwardAPIService {
    @GET("/awards")
    Call<List<Award>> getAllAwards();

    @POST("awards")
    Call<Award> addAward(@Body Award award);
}
