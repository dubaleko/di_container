package exception;

public class ToManyConstructorsException extends Exception {
    public ToManyConstructorsException(){
        super("There are several constructors with @Inject annotation in the class");
    }
}
