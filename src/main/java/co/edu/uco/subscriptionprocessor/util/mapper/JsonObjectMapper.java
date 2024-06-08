package co.edu.uco.subscriptionprocessor.util.mapper;

import java.util.List;
import java.util.Optional;

public interface JsonObjectMapper {

    Optional<String> execute(Object objet);

    <T> Optional<T> execute(String json, Class<T> targetClass);

    Optional<String> executeGson(Object object);

    <T> Optional<List<T>> executeList(String message, Class<T> clazz);
}
