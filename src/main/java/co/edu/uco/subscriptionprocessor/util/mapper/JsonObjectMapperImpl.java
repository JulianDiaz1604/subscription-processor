package co.edu.uco.subscriptionprocessor.util.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class JsonObjectMapperImpl implements JsonObjectMapper {

    private final Gson gson;

    public JsonObjectMapperImpl() {
        this.gson = new Gson();
    }

    @Override
    public Optional<String> execute(Object objet) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.ofNullable(mapper.writeValueAsString(objet));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public <T> Optional<T> execute(String json, Class<T> targetClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.ofNullable(mapper.readValue(json, targetClass));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> executeGson(Object object) {
        try {
            Gson gson = new GsonBuilder().serializeNulls()
                    .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                        @Override
                        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        }
                    }).create();
            String json = gson.toJson(object);
            return Optional.ofNullable(json);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public <T> Optional<List<T>> executeList(String jsonString, Class<T> clazz) {
        try {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return Optional.of(gson.fromJson(jsonString, listType));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
