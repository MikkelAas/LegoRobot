package ingg1001;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.ev3.EV3TouchSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

public class LegoRobot {

    // Making objects of the hardware in use
    public static final EV3LargeRegulatedMotor largeRegulatedMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    public static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
    public static final EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
    public static final EV3MediumRegulatedMotor mediumRegulatedMotor = new EV3MediumRegulatedMotor(MotorPort.B);

    // A method that returns the sorting machine to a default/prefixed position
    public void returnToDefaultPosition(){
        // Returns to a prefixed position, write once, use it A LOT
    }

    // A method that sorts by Blue
    public void sortBlue(){
        /* If the color is blue, move to a fixed position and the deposit the green matter before returning to the
        default position
         */
    }

    // A method that sorts by Green
    public void sortGreen(){
        /* If the color is green, move to a fixed position and the deposit the green matter before returning to the
        default position
         */
    }

    // A method that sortrs by Red
    public void sortRed(){
        /* If the color is red, move to a fixed position and the deposit the green matter before returning to the
        default position
         */
    }

    // A method that sorts by Yellow
    public void sortYellow(){
        /* If the color is yellow, move to a fixed position and the deposit the green matter before returning to the
        default position
         */
    }

    public static void main(String[] args) {


    }
}
