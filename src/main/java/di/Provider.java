package di;

import java.lang.reflect.InvocationTargetException;

public interface Provider<T> {
    T getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException;
}

