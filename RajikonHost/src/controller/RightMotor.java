package controller;

import com.pi4j.io.gpio.*;

import java.util.ArrayList;
import java.util.List;

public class RightMotor implements Motor {
    private static final Pin PIN_14 = RaspiPin.GPIO_14;
    private static final Pin PIN_13 = RaspiPin.GPIO_13;
    private static final Orientation ORIENTATION = Orientation.Right;

    private List<Pin> pins;
    private RotationDirection direction;

    private final GpioPinPwmOutput motor1;
    private final GpioPinPwmOutput motor2;

    public RightMotor(){
        pins = new ArrayList<>();
        pins.add(PIN_13);
        pins.add(PIN_14);

        motor1 = CONTROLLER.provisionPwmOutputPin(PIN_14, "MOTOR1", DEFAULT_POWER);
        motor2 = CONTROLLER.provisionPwmOutputPin(PIN_13, "MOTOR2", DEFAULT_POWER);

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
