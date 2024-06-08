package co.edu.uco.subscriptionprocessor.util.courier;

import co.edu.uco.subscriptionprocessor.util.mapper.JsonObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MessageUtilsImpl implements MessageUtils {

    private final JsonObjectMapper jsonObjectMapper;

    public MessageUtilsImpl(JsonObjectMapper jsonObjectMapper) {
        this.jsonObjectMapper = jsonObjectMapper;
    }

    @Override
    public MessageProperties generateMessageProperties(String idMessageSender ) {
        return MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("messageId", idMessageSender)
                .build();
    }

    @Override
    public Optional<Message> getMessageBody(Object message, MessageProperties messageProperties) {
        Optional<String> messageText = jsonObjectMapper.executeGson(message);

        return messageText.map(msg -> MessageBuilder
                .withBody(msg.getBytes())
                .andProperties(messageProperties)
                .build());

    }

    @Override
    public <T> Optional<T> getObjectFromMessage(String message, Class<T> clazz) {
        return jsonObjectMapper.execute(message, clazz);
    }

    @Override
    public <T> Optional<List<T>> getListFromMessage(String message, Class<T> clazz) {
        return jsonObjectMapper.executeList(message, clazz);
    }

}
