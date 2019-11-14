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
 *
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

    private static final String[] colors = {"NONE", "BLACK", "BLUE", "GREEN", "YELLOW", "RED", "WHITE", "BROWN"};


    private enum MoveDistances {
        BLUE(0),
        GREEN(225),
        YELLOW(550),
        RED(775);

        private final int moveDistance;

        MoveDistances(int moveDistance) {
            this.moveDistance = moveDistance;
        }

        public int getMoveDistance() {
            return this.moveDistance;
        }

    }

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

    public void sortColor(int delay, int currentColor) {
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

        if (robot.getColorID() == currentColor) {
            System.out.println("Sorted double!");

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

    public int getColorID() {
        colorSensor.getColorIDMode();

        // A float array that holds the samples
        float[] colorSample = new float[colorSensor.sampleSize()];

        // Returns a float from 0 to 7
        colorSensor.fetchSample(colorSample, 0);

        // Returns a String from the colors array based on what float it fetches, converted to int
        return (int) colorSample[0];
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

        while (true) {
            int currentColor = robot.getColorID();

            System.out.println(colors[currentColor]);

            switch (robot.getColorID()) {
                case 2: // BLUE
                    robot.sortColor(MoveDistances.BLUE.getMoveDistance(), currentColor);
                    break;
                case 3:
                    robot.sortColor(MoveDistances.GREEN.getMoveDistance(), currentColor);
                    break;
                case 4:
                    robot.sortColor(MoveDistances.YELLOW.getMoveDistance(), currentColor);
                    break;
                case 5:
                    robot.sortColor(MoveDistances.RED.getMoveDistance(), currentColor);
                    break;
            }
        }
    }
}
