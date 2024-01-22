package pl.edu.agh.to2.example.retrofit.client;

import pl.edu.agh.to2.example.exception.DatabaseResponseFailure;
import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.model.Form;
import pl.edu.agh.to2.example.retrofit.service.FormAPIService;
import retrofit2.Response;

public class FormClient {
    private final FormAPIService formAPIService;

    public FormClient(RetrofitClient retrofitClient) {
        formAPIService = retrofitClient.getRetrofit().create(FormAPIService.class);
    }

    public Form updateAward(int formID, Award award) throws Exception {
        Response<Form> response = formAPIService.updateAward(formID, award).execute();

        if (response.isSuccessful()) {
            return response.body();
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }
}
