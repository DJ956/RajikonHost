package controller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;

import java.util.List;

public interface Motor {
    int DEFAULT_POWER = 40;
    int PWM_RANGE = 100;
    GpioController CONTROLLER = GpioFactory.getInstance();
    Orientation getOrientation();
    void forward(int power);
    void back(int power);
    void stop();
    List<Pin> getPinList();
}
