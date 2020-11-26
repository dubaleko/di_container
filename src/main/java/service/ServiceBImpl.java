package service;

import annotation.Inject;

public class ServiceBImpl implements ServiceB {

    public  ServiceA serviceA;

    @Inject
    public ServiceBImpl(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public String jobB() {
        System.out.println(serviceA.jobA());
        return "Service B";
    }
}
