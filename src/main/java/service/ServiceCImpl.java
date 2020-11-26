package service;

import annotation.Inject;

public class ServiceCImpl implements ServiceC {

    public  ServiceA serviceA;

    @Inject
    public ServiceCImpl() {

    }

    @Inject
    public ServiceCImpl(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    @Override
    public String jobC() {
        System.out.println(serviceA.jobA());
        return "Service C";
    }
}
