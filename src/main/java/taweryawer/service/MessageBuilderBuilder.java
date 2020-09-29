package taweryawer.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageBuilderBuilder<E> {

    private HashMap<String, Object> headers = new HashMap<>();

    public MessageBuilderBuilder addHeader(String key, Object o) {
        headers.put(key, o);
        return this;
    }

    public Message<E> build(E event) {
        MessageBuilder<E> messageBuilder = MessageBuilder.withPayload(event);
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            messageBuilder.setHeader(entry.getKey(), entry.getValue());
        }
        headers.clear();
        return messageBuilder.build();
    }
}
