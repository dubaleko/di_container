package di;

public class ServiceDescription {
    public  Class intf;
    public  Class impl;
    public  ServiceLifeTime lifeTime;
    public  ConstructorType constructorType;

    public ServiceDescription(Class intf, Class impl,ServiceLifeTime lifeTime, ConstructorType constructorType) {
        this.intf = intf;
        this.impl = impl;
        this.lifeTime = lifeTime;
        this.constructorType = constructorType;
    }
}
