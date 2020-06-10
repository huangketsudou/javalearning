package chapter20;
// generics/MultipleInterfaceVariants.java
// {WillNotCompile}


interface Payable<T> {}

class Employee implements Payable<Employee> {}

class Hourly extends Employee implements Payable<Hourly> {}//会被擦除为相同的Payble接口
