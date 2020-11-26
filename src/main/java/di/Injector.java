package di;

import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.ToManyConstructorsException;

import java.lang.reflect.InvocationTargetException;

public interface Injector {
    <T> Provider<T> getProvider(Class<T> type) throws IllegalAccessException, InstantiationException,
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException;
    <T> void bind(Class<T> intf, Class<? extends T> impl) throws ToManyConstructorsException,
            BindingNotFoundException, ConstructorNotFoundException;
    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) throws ToManyConstructorsException,
            ConstructorNotFoundException, BindingNotFoundException;
}

