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
    private static final EV3LargeRegulatedMotor largeRegulatedMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    private static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
    private static final EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
    private static final EV3MediumRegulatedMotor mediumRegulatedMotor = new EV3MediumRegulatedMotor(MotorPort.B);
    private static final LegoRobot robot = new LegoRobot();

    public void moveSorter(int speed, boolean directionForwards) {
        largeRegulatedMotor.setSpeed(speed);
        if (directionForwards) {
            largeRegulatedMotor.forward();
        } else {
            largeRegulatedMotor.backward();
        }
    }

    public void resetDispenser() {
        while (!mediumRegulatedMotor.isStalled()) {
            mediumRegulatedMotor.setSpeed(150);
            mediumRegulatedMotor.forward();
        }
    }

    public void dispense() {
        mediumRegulatedMotor.setSpeed(250);
        mediumRegulatedMotor.backward();
        Delay.msDelay(1000);
    }

    public void sortColor(int delay, String currentColor) {
        // move to dispense location
        robot.moveSorter(600, true);
        Delay.msDelay(delay);
        largeRegulatedMotor.stop();

        // chill
        Delay.msDelay(250);

        // dispense that shit
        robot.dispense();
        mediumRegulatedMotor.stop();

        // reset dispenser
        robot.resetDispenser();
        mediumRegulatedMotor.stop();

        if (robot.getColour().equals(currentColor)) {
            // dispense that shit
            robot.dispense();
            mediumRegulatedMotor.stop();

            // reset dispenser
            robot.resetDispenser();
            mediumRegulatedMotor.stop();
        }

        // go back to start
        while (!touchSensor.isPressed()) {
            robot.moveSorter(600, false);
        }
        largeRegulatedMotor.stop();
    }

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

        // go back to start
        while (!touchSensor.isPressed()) {
            robot.moveSorter(600, false);
        }
        largeRegulatedMotor.stop();

        // reset dispenser
        robot.resetDispenser();
        mediumRegulatedMotor.stop();

        int blackCounter = 0;

        while (true) {
            while (!robot.getColour().equals("BLACK")) {
                switch (robot.getColour()) {
                    case "BLUE":
                        robot.sortColor(0, robot.getColour());
                        break;
                    case "GREEN":
                        robot.sortColor(225, robot.getColour());
                        break;
                    case "RED":
                        robot.sortColor(550, robot.getColour());
                        break;
                    case "YELLOW":
                        robot.sortColor(775, robot.getColour()); // 825?
                        break;
                }
            }

            Delay.msDelay(1000);

            blackCounter++;
            System.out.println(blackCounter);

            if (blackCounter == 5) {
                // reset dispenser
                robot.resetDispenser();
                mediumRegulatedMotor.stop();

                blackCounter = 0;
            }
        }
    }
}
