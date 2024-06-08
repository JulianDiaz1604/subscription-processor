package co.edu.uco.subscriptionprocessor.util.courier;

public interface MessageSender<T> {
    void execute(T message, String idMessage);
}
