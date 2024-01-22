package pl.edu.agh.to2.example.retrofit.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.example.exception.DatabaseResponseFailure;
import pl.edu.agh.to2.example.model.Chest;
import pl.edu.agh.to2.example.retrofit.service.ChestAPIService;
import retrofit2.Response;

import java.util.List;

public class ChestClient {
    private final ChestAPIService chestAPIService;

    public ChestClient(RetrofitClient retrofitClient) {
        chestAPIService = retrofitClient.getRetrofit().create(ChestAPIService.class);
    }

    public ObservableList<Chest> getAllChests() throws Exception {
        Response<List<Chest>> response = this.chestAPIService.getAllChests().execute();

        if (response.isSuccessful()){
            return FXCollections.observableArrayList(response.body());
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    public Chest addChest(Chest chest) throws Exception {
        Response<Chest> response = chestAPIService.addChest(chest).execute();

        if (response.isSuccessful()) {
            return response.body();
        }

        throw new DatabaseResponseFailure(response.code(), response.message());
    }
}