package ru.t1.java.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JsonParser {

    public static <T> List<T> parseJsonData(String fileName, Class<T[]> clazz) {
        ObjectMapper mapper = new ObjectMapper();

        T[] data;
        try {
            data = mapper.readValue(new File("src/main/resources/entityDataSets/" + fileName + ".json"), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Данные {} обработаны", fileName);

        return Arrays.asList(data);
    }
}
