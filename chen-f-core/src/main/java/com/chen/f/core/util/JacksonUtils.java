package com.chen.f.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

/**
 * Jackson工具类
 *
 * @author chen
 * @since 2018/6/21 17:16.
 */
public class JacksonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());

    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 对象转成json字符串
     *
     * @param object 需要转换的对象
     * @return String json字符串
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * json字符串转成对象
     *
     * @param jsonString json字符串
     * @return JsonNode
     */
    public static JsonNode parse(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * json字符串转成对象
     *
     * @param jsonString json字符串
     * @param clazz      需要转换的对象class
     * @param <T>        要转换的对象
     * @return T 要转换的对象
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            final JsonNode jsonNode = objectMapper.readTree("");
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转成对象
     *
     * @param jsonString    json字符串
     * @param typeReference 泛型抽象类用于获得完整的泛型类型信息
     * @param <T>           要转换的对象
     * @return T 要转换的对象
     */
    public static <T> T parseObject(String jsonString, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转成列表对象
     *
     * @param jsonString json字符串
     * @param clazz      需要转换的列表泛型class
     * @param <T>        要转换的列表泛型对象
     * @return List<T> 要转换的对象
     */
    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return objectMapper.readValue(jsonString, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}


