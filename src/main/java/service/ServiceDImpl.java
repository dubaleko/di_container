package service;

public class ServiceDImpl implements ServiceD {

    public ServiceA serviceA;

    public ServiceDImpl(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    @Override
    public String jobC() {
        return "Service D";
    }
}
