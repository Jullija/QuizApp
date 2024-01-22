package pl.agh.edu.to.project.back.strategy.strategyTypes.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.agh.edu.to.project.back.chest.Chest;
import pl.agh.edu.to.project.back.chest.ChestRepository;

import javax.persistence.AttributeConverter;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StrategyParamsConverter implements AttributeConverter<Map<Chest, Integer>, String> {

    private List<Chest> chestList;
    private final ObjectMapper objectMapper;

    public StrategyParamsConverter(ChestRepository chestRepository) {
        this.objectMapper = new ObjectMapper();
        this.chestList = chestRepository.findAll();
    }

    public StrategyParamsConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(Map<Chest, Integer> attribute) {
        Map<Integer, Integer> parameters = new HashMap<>();
        for (Map.Entry<Chest, Integer> entry : attribute.entrySet()) {
            parameters.put(entry.getKey().getId(), entry.getValue());
        }
        try {
            return objectMapper.writeValueAsString(parameters);
        } catch (Exception e) {
            throw new IllegalStateException("Error converting map to JSON", e);
        }
    }

    @Override
    public LinkedHashMap<Chest, Integer> convertToEntityAttribute(String dbData) {
        try {
            LinkedHashMap<Integer, Integer> chestIdMap = objectMapper.readValue(dbData, new TypeReference<>() {
            });
            LinkedHashMap<Chest, Integer> chestMap = new LinkedHashMap<>();
            for (Map.Entry<Integer, Integer> entry : chestIdMap.entrySet()) {
                Optional<Chest> chest = chestList.stream()
                        .filter(c -> c.getId() == entry.getKey())
                        .findFirst();

                if (chest.isEmpty()) {
                    throw new IllegalStateException("Chest " + entry.getKey() + " not found");
                }

                chestMap.put(chest.get(), entry.getValue());
            }
            return chestMap;
        } catch (Exception e) {
            throw new IllegalStateException("Error converting parameters to object", e);
        }
    }
}
