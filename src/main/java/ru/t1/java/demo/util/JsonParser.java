package ru.t1.java.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonParser {

    public static <T> List<T> parseJsonData(String fileName, Class<T[]> clazz) {
        ObjectMapper mapper = new ObjectMapper();

        T[] data = null;
        try {
            data = mapper.readValue(new File("src/main/resources/entityDataSets/" + fileName + ".json"), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Arrays.asList(data);
    }
}
