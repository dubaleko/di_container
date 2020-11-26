package exception;

public class ConstructorNotFoundException extends Exception {
    public ConstructorNotFoundException() {
        super("Default constructor not found");
    }
}
