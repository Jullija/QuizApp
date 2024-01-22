package pl.edu.agh.to2.example.retrofit.service;

import pl.edu.agh.to2.example.model.Chest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ChestAPIService {
    @GET("/chests")
    Call<List<Chest>> getAllChests();

    @POST("/chests/chest")
    Call<Chest> addChest(@Body Chest chest);
}
