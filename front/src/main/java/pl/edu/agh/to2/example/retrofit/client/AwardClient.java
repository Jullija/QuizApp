package pl.edu.agh.to2.example.retrofit.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.example.exception.DatabaseResponseFailure;
import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.retrofit.service.AwardAPIService;
import retrofit2.Response;

import java.util.List;

public class AwardClient {
    private final AwardAPIService awardAPIService;

    public AwardClient(RetrofitClient retrofitClient) {
        awardAPIService = retrofitClient.getRetrofit().create(AwardAPIService.class);
    }

    public ObservableList<Award> getAllAwards() throws Exception {
        Response<List<Award>> response = this.awardAPIService.getAllAwards().execute();

        if (response.isSuccessful()){
            return FXCollections.observableArrayList(response.body());
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    public Award addAward(Award award) throws Exception {
        Response<Award> response = awardAPIService.addAward(award).execute();

        if (response.isSuccessful()) {
            return response.body();
        }

        //System.out.println(response.raw());
        throw new DatabaseResponseFailure(response.code(), response.message());
    }
}