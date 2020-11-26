package exception;

public class BindingNotFoundException extends Exception {
    public BindingNotFoundException() {
        super("Binding cannot be found for some argument");
    }
}
