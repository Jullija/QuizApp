package pl.edu.agh.to2.example.retrofit.client;

import pl.edu.agh.to2.example.exception.DatabaseResponseFailure;
import pl.edu.agh.to2.example.model.Chest;
import pl.edu.agh.to2.example.model.Choice;
import pl.edu.agh.to2.example.model.Strategy;
import pl.edu.agh.to2.example.model.StrategyType;
import pl.edu.agh.to2.example.retrofit.service.StrategyApiService;
import retrofit2.Response;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StrategyClient {
    private final StrategyApiService strategyApiService;

    public StrategyClient(RetrofitClient retrofitClient) {
        strategyApiService = retrofitClient.getRetrofit().create(StrategyApiService.class);
    }

    public List<Strategy> getStrategies() throws Exception {
        Response<List<Strategy>> response = this.strategyApiService.getAllStrategies().execute();

        if (response.isSuccessful()){
            // TODO: remove default strategy
            return response.body();
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    public List<StrategyType> getAllStrategyTypes() throws Exception {
        Response<List<StrategyType>> response = this.strategyApiService.getAllStrategyTypes().execute();

        if (response.isSuccessful()){
            return response.body();
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    public Strategy addStrategy(List<Choice> choices, StrategyType strategyType) throws Exception {
        //String parameters = getParametersString(choices);

        Map<Chest, Integer> map = new HashMap<>();

        for(Choice choice : choices){
            map.put(choice.getChest(), (int) choice.getNumber());
        }

        System.out.println(map);


        Response<Strategy> response = this.strategyApiService.addStrategy(strategyType.getName(), map).execute();

        if (response.isSuccessful()){
            return response.body();
        }
        throw new DatabaseResponseFailure(response.code(), response.message());
    }

    private String getParametersString(List<Choice> choices) {
//        int n = choices.size();
//
//        String res = "{";
//        for(int i=0; i<n; i++){
//            Choice choice = choices.get(i);
//            System.out.println(choice);
//            int chestId = choice.getChest().getId();
//            int number = (int) choice.getNumber();
//
//            System.out.println(chestId + " " + number);
//
//            res += chestId;
//            res += ":";
//            res += "\"" + number + "\"";
//
//            if(i != n-1){
//                res += ", ";
//            }
//        }
//        res += "}";
//
//        System.out.println(res);
//
//        return res;

        Map<String, Integer> map = new LinkedHashMap<>();

        for(Choice choice : choices){
            int chestId = choice.getChest().getId();
            int number = (int) choice.getNumber();
            map.put(String.valueOf(chestId), number);
        }

        StringBuilder sb = new StringBuilder("{");
        String prefix = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            sb.append(prefix);
            prefix = ",";
            sb.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
        }
        sb.append("}");

        return sb.toString();
    }
}
