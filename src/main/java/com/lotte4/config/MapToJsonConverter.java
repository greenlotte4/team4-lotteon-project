package com.lotte4.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Converter
public class MapToJsonConverter implements AttributeConverter<Map<String, List<String>>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, List<String>> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }

    @Override
    public Map<String, List<String>> convertToEntityAttribute(String dbData) {
        try {
            // 변환 중 문제가 발생할 경우 단일 값을 리스트로 처리
            Map<String, Object> rawMap = objectMapper.readValue(dbData, new TypeReference<Map<String, Object>>() {});
            Map<String, List<String>> resultMap = new HashMap<>();

            rawMap.forEach((key, value) -> {
                if (value instanceof String) {
                    // 단일 값인 경우 리스트로 감쌉니다.
                    resultMap.put(key, Collections.singletonList((String) value));
                } else if (value instanceof List) {
                    resultMap.put(key, (List<String>) value);
                } else {
                    throw new IllegalArgumentException("Unexpected data type for key: " + key);
                }
            });

            return resultMap;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to map", e);
        }
    }

}
