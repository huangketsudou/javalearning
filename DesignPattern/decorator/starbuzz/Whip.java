package DesignPattern.decorator.starbuzz;
public class Whip extends CondimentDecorator {
    Beverage beverage;

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    public double cost() {
        return 0.10 + beverage.cost();
    }

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }
}