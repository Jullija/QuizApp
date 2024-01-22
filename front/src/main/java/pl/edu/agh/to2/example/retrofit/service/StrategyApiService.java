package pl.edu.agh.to2.example.retrofit.service;

import pl.edu.agh.to2.example.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface StrategyApiService {
    @GET("/strategies")
    Call<List<Strategy>> getAllStrategies();

    @GET("strategies/types")
    Call<List<StrategyType>> getAllStrategyTypes();

    @POST("strategies/{strategyTypeName}")
    Call<Strategy> addStrategy(@Path("strategyTypeName") String strategyTypeName, @Body Map<Chest, Integer> parameters);
}
