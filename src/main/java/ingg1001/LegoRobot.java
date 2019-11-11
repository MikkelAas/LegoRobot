package ingg1001;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;

import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.ev3.EV3TouchSensor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

import lejos.utility.Delay;

/**
 * Class for robot that sorts colours
 * @author Aas, Mikkel
 * @author Karlsmoen, F. Jakob
 * @author Ronning-nyvold, Petter
 * @author Sandvold, Mathias
 */

public class LegoRobot {

    // Making objects of the hardware in use
    public static final EV3LargeRegulatedMotor largeRegulatedMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    public static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
    public static final EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
    public static final EV3MediumRegulatedMotor mediumRegulatedMotor = new EV3MediumRegulatedMotor(MotorPort.B);

    // A method that sorts by Blue
    public void sortBlue() {
        returnToStart();
        moveSorter(300, 200, true);
        dispenseObject();
    }

    // A method that sorts by Green
    public void sortGreen() {
        returnToStart();
        moveSorter(300, 400, true);
        dispenseObject();
    }

    // A method that sortrs by Red
    public void sortRed() {
        returnToStart();
        moveSorter(300, 600, true);
        dispenseObject();
    }

    // A method that sorts by Yellow
    public void sortYellow(){
        returnToStart();
        moveSorter(300, 800, true);
        dispenseObject();
    }

    public void moveSorter(int speed, int duration, boolean directionForwards) {
        largeRegulatedMotor.setSpeed(speed);
        if (directionForwards) {
            largeRegulatedMotor.forward();
        } else {
            largeRegulatedMotor.backward();
        }

        Delay.msDelay(duration);
    }

    public void dispenseObject() {
        mediumRegulatedMotor.setSpeed(150);
        mediumRegulatedMotor.backward();
        Delay.msDelay(700);

        while (!mediumRegulatedMotor.isStalled()) {
            mediumRegulatedMotor.setSpeed(150);
            mediumRegulatedMotor.forward();
        }
    }

    public void resetObjectDispenser() {
        while (!mediumRegulatedMotor.isStalled()) {
            mediumRegulatedMotor.setSpeed(150);
            mediumRegulatedMotor.forward();
        }
    }

    public void returnToStart() {
        while (!touchSensor.isPressed()) {
            moveSorter(300, 5, false);
        }
    }

    public void resetPositions() {
        returnToStart();
        resetObjectDispenser();
    }

    // A method that prints the colour of the scanned objects
    public String getColour() {
        colorSensor.getColorIDMode();

        // A float array that holds the samples
        float[] colorSample = new float[colorSensor.sampleSize()];

        // Returns a float from 0 to 7
        colorSensor.fetchSample(colorSample, 0);

        String[] colors = {"NONE", "BLACK", "BLUE", "GREEN", "YELLOW", "RED", "WHITE", "BROWN"};

        // Returns a String from the colors array based on what float it fetches, converted to int
        return colors[(int) colorSample[0]];
    }

    public static void main(String[] args) {
        LegoRobot robot = new LegoRobot();
        while (true) {
            System.out.println(robot.getColour());
            Delay.msDelay(1000);

            robot.sortBlue();
            robot.sortGreen();
            robot.sortYellow();
            robot.sortRed();

        }
    }
}
