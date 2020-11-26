import di.Injector;
import di.InjectorImpl;
import di.Provider;
import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.ToManyConstructorsException;
import org.junit.Test;
import service.*;

import java.lang.reflect.InvocationTargetException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertSame;

public class TestClass {
    @Test
    public void testExistingBinding() throws ToManyConstructorsException, ConstructorNotFoundException,
            BindingNotFoundException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Injector injector = new InjectorImpl();
        injector.bind(ServiceA.class, ServiceAImpl.class);
        injector.bindSingleton(ServiceB.class, ServiceBImpl.class);
        Provider<ServiceB> serviceBProvider = injector.getProvider(ServiceB.class);
        Provider<ServiceB> serviceBProvider1 = injector.getProvider(ServiceB.class);

        assertNotNull(serviceBProvider);
        assertNotNull(serviceBProvider.getInstance());
        assertSame(serviceBProvider,serviceBProvider1);
    }

    @Test
    public void testNullBinding() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Injector injector = new InjectorImpl();
        Provider<ServiceB> serviceBProvider = injector.getProvider(ServiceB.class);
        assertSame(serviceBProvider,null);
    }

    @Test
    public void testBindingNotFoundException() {
        try {
            Injector injector = new InjectorImpl();
            injector.bind(ServiceB.class,ServiceBImpl.class);
        }
        catch (BindingNotFoundException | ToManyConstructorsException | ConstructorNotFoundException e) {
            assertSame(e.getClass(),BindingNotFoundException.class);
        }

    }

    @Test
    public void testTooManyConstructorsException() {
        try {
            Injector injector = new InjectorImpl();
            injector.bind(ServiceC.class,ServiceCImpl.class);
        }
        catch (BindingNotFoundException | ToManyConstructorsException | ConstructorNotFoundException e) {
            assertSame(e.getClass(), ToManyConstructorsException.class);
        }
    }

    @Test
    public void testConstructorNotFoundException() {
        try {
            Injector injector = new InjectorImpl();
            injector.bind(ServiceA.class,ServiceAImpl.class);
            injector.bind(ServiceD.class,ServiceDImpl.class);
        }
        catch (BindingNotFoundException | ToManyConstructorsException | ConstructorNotFoundException e) {
            assertSame(e.getClass(), ConstructorNotFoundException.class);
        }
    }
}
