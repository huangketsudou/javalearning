package DesignPattern.command.simpleremote;

public class LightOnCommand implements Command {
    Light light;

    @Override
    public void execute() {
        light.on();
    }

    public LightOnCommand(Light light) {
        this.light = light;
    }
}
