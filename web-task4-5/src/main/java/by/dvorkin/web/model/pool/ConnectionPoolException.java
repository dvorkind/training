package by.dvorkin.web.model.pool;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
