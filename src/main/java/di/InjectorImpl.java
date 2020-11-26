package di;

import annotation.Inject;
import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.ToManyConstructorsException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectorImpl implements Injector {

    public static List<ServiceDescription> container;
    public static Map<String,Provider> singletonObjects;

    public InjectorImpl() {
        container = new ArrayList<>();
        singletonObjects = new HashMap<>();
    }

    public <T> Provider<T> getProvider(Class<T> type) {
        for (ServiceDescription description : container) {
            if (description.intf.equals(type)) {
                if (singletonObjects.containsKey(description.impl.getName())) {
                    return singletonObjects.get(description.impl.getName());
                }
                Provider<T> provider = new ProviderImpl(description);
                if (description.lifeTime == ServiceLifeTime.Singleton) {
                    singletonObjects.put(description.impl.getName(), provider);
                }
                return  provider;
            }
        }
        return null;
    }

    public <T> void bind(Class<T> intf, Class<? extends T> impl) throws ToManyConstructorsException,
            BindingNotFoundException, ConstructorNotFoundException {
        ConstructorType constructorType = checkConstructorBeforeBinding(impl);
        container.add(new ServiceDescription(intf,impl,ServiceLifeTime.Prototype,constructorType));
    }

    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) throws ToManyConstructorsException,
            ConstructorNotFoundException, BindingNotFoundException {
        ConstructorType constructorType = checkConstructorBeforeBinding(impl);
        container.add(new ServiceDescription(intf,impl,ServiceLifeTime.Singleton,constructorType));
    }

    public  ConstructorType  checkConstructorBeforeBinding(Class impl) throws ToManyConstructorsException,
            BindingNotFoundException, ConstructorNotFoundException {
        int injectContainer = 0;
        boolean haveDefConstructor = false;
        ConstructorType constructorType = ConstructorType.Default;
        Constructor<?>[] constructors = impl.getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                haveDefConstructor = true;
                injectContainer++;
                if (injectContainer > 1) {
                    throw new ToManyConstructorsException();
                }

                Class[] parameters = constructor.getParameterTypes();
                for (Class parameter : parameters) {
                    boolean haveBind = false;
                    for (ServiceDescription description : container) {
                        if (parameter.getName().equals(description.intf.getName())) {
                            haveBind = true;
                        }
                    }
                    if (!haveBind) {
                        throw new BindingNotFoundException();
                    }
                }
                constructorType = ConstructorType.Inject;
            }
            else if (constructor.getParameterCount() == 0) {
                haveDefConstructor = true;
            }
        }
        if (!haveDefConstructor) {
            throw new ConstructorNotFoundException();
        }
        return  constructorType;
    }
}
