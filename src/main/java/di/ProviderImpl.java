package di;

import annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ProviderImpl implements Provider {

    public ServiceDescription description;

    public ProviderImpl(ServiceDescription description) {
        this.description = description;
    }

    @Override
    public Object getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        if (this.description.constructorType == ConstructorType.Default) {
            return Class.forName(this.description.impl.getName()).newInstance();
        }
        else {
            Constructor<?>[] constructors = this.description.impl.getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.isAnnotationPresent(Inject.class)) {
                    Class[] parameters = constructor.getParameterTypes();
                    List<Object> values = getParamsForConstructor(parameters);
                    return description.impl.getConstructor(parameters).newInstance(values.toArray());
                }
            }
            return null;
        }
    }

    public List<Object> getParamsForConstructor(Class[] parameters) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> paramsValues = new ArrayList();
        for (Class parameter : parameters) {
            for (ServiceDescription serviceDescription : InjectorImpl.container) {
                if (serviceDescription.intf.equals(parameter)) {
                    if (ServiceLifeTime.Singleton == serviceDescription.lifeTime) {
                        if (InjectorImpl.singletonObjects.containsKey(description.impl.getName())) {
                            paramsValues.add(InjectorImpl.singletonObjects.get(description.impl.getName()));
                        }
                    }
                    else {
                        Provider provider = new ProviderImpl(serviceDescription);
                        paramsValues.add(provider.getInstance());
                    }
                }
            }
        }
        return paramsValues;
    }
}
