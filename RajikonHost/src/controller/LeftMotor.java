package controller;

import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import java.util.ArrayList;
import java.util.List;

public class LeftMotor implements Motor {

    private static final Pin PIN_2 = RaspiPin.GPIO_02;
    private static final Pin PIN_0 = RaspiPin.GPIO_00;

    private static final Orientation ORIENTATION = Orientation.Left;

    private final GpioPinPwmOutput motor1;
    private final GpioPinPwmOutput motor2;

    private RotationDirection direction;

    private List<Pin> pins;

    public LeftMotor(){
        pins = new ArrayList<>();
        pins.add(PIN_2);
        pins.add(PIN_0);

        motor1 = CONTROLLER.provisionPwmOutputPin(PIN_2, "Motor1", DEFAULT_POWER);
        motor2 = CONTROLLER.provisionPwmOutputPin(PIN_0, "Motor2", DEFAULT_POWER);

        motor1.setShutdownOptions(true, PinState.LOW);
        motor2.setShutdownOptions(true, PinState.LOW);

        motor1.setPwmRange(PWM_RANGE);
        motor2.setPwmRange(PWM_RANGE);
    }

    @Override
    public Orientation getOrientation() {
        return ORIENTATION;
    }

    @Override
    public void forward(int power) {
        if(direction == RotationDirection.back){
            stop();
        }

        motor1.setPwm(power);
        motor2.setPwm(0);
        direction = RotationDirection.forward;
    }

    @Override
    public void back(int power) {
        if(direction == RotationDirection.forward){
            stop();
        }

        motor1.setPwm(0);
        motor2.setPwm(power);
        direction = RotationDirection.back;
    }

    @Override
    public void stop() {
        motor1.setPwm(0);
        motor2.setPwm(0);
    }

    @Override
    public List<Pin> getPinList() {
        return pins;
    }
}
