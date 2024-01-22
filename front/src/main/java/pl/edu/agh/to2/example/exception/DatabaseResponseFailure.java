package pl.edu.agh.to2.example.exception;

public class DatabaseResponseFailure extends Exception {
    public DatabaseResponseFailure(int code, String message) {
        super(code + " " + message);
    }
}
