package DesignPattern.state.gumballstate;
public class GumballMachineTestDrive {
    public static void main(String[] args) {
        GumballMachine gumballMachine = new GumballMachine(2);

        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        gumballMachine.refill(5);
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println(gumballMachine);
    }
}
/*
Mighty Gumball, Inc.
Java-enabled Standing Gumball Model #2004
Inventory: 2 gumballs
Machine is waiting for quarter

You inserted a quarter
You turned...

Mighty Gumball, Inc.
Java-enabled Standing Gumball Model #2004
Inventory: 2 gumballs
Machine is dispensing a gumball

Please wait, we're already giving you a gumball
Turning twice doesn't get you another gumball!
Please wait, we're already giving you a gumball
Turning twice doesn't get you another gumball!
The gumball machine was just refilled; it's new count is: 7
Please wait, we're already giving you a gumball
Turning twice doesn't get you another gumball!

Mighty Gumball, Inc.
Java-enabled Standing Gumball Model #2004
Inventory: 7 gumballs
Machine is dispensing a gumball

 */