package DesignPattern.decorator.starbuzz;
public class Mocha extends CondimentDecorator {
    // 被装饰者
    Beverage beverage;

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return .20 + beverage.cost();
    }

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }
}