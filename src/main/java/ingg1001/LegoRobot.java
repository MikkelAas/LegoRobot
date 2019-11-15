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

    /**
     * Creates a new instance of the EV3 large regulated motor that is connected to motor port: A.
     */
    private static final EV3LargeRegulatedMotor largeRegulatedMotor = new EV3LargeRegulatedMotor(MotorPort.A);Creates a new instance of the EV3
    /**
     * Creates a new instance of the EV3 color sensor that is connected to sensor port: S1.
     */
    private static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
    /**
     * Creates a new instance of the EV3 touch sensor that is connected to sensor port: S2.
     */
    private static final EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
    /**
     * Creates a new instance of the EV3 medium regulated motor that is connected to motor port: B.
     */
    private static final EV3MediumRegulatedMotor mediumRegulatedMotor = new EV3MediumRegulatedMotor(MotorPort.B);
    /**
     * Creates a robot object
     */
    private static final LegoRobot robot = new LegoRobot();

    /**
     * Holds a fixed array of Strings with the colors that the color sensor can read
     */
    private static final String[] colors = {"NONE", "BLACK", "BLUE", "GREEN", "YELLOW", "RED", "WHITE", "BROWN"};

    /**
     *
     */
    private enum MoveDistances {
        BLUE(0),
        GREEN(150),
        YELLOW(450),
        RED(750);

        private final int moveDistance;

        MoveDistances(int moveDistance) {
            this.moveDistance = moveDistance;
        }

        public int getMoveDistance() {
            return this.moveDistance;
        }
    }

    /**
     * A method that resets the dispenser rotation and sorter position.
     */
    public void init() {
        robot.resetDispenserRotation();
        robot.resetSorterPosition();
    }

    /**
     * A method that moves the sorter
     * @param speed Takes a speed parameter of type int, that sets the speed of the sorter
     * @param directionForwards Takes a direction boolean that decides whether the sorter should move backward or forward.
     */
    public void moveSorter(int speed, boolean directionForwards) {
        largeRegulatedMotor.setSpeed(speed);
        if (directionForwards) {
            largeRegulatedMotor.forward();
        } else {
            largeRegulatedMotor.backward();
        }
    }

    /**
     * Resets the sorter position.
     */
    public void resetSorterPosition() {
        // go back to start
        while (!touchSensor.isPressed()) {
            robot.moveSorter(600, false);
        }
        largeRegulatedMotor.stop();
    }

    /**
     * Resets the dispenser rotation
     */
    public void resetDispenserRotation() {
        while (!mediumRegulatedMotor.isStalled()) {
            mediumRegulatedMotor.setSpeed(150);
            mediumRegulatedMotor.forward();
        }
        mediumRegulatedMotor.stop();
    }

    /**
     * Dispenses the sorting object.
     */
    public void dispense() {
        mediumRegulatedMotor.setSpeed(250);
        mediumRegulatedMotor.backward();
        Delay.msDelay(1000);
        mediumRegulatedMotor.stop();
    }

    /**
     * Sorts based on the color.
     * @param delay
     * @param currentColor
     */
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
        robot.resetDispenserRotation();
        mediumRegulatedMotor.stop();

        Delay.msDelay(250);

        if (robot.getColorID() == currentColor) {
            System.out.println("Sorted double!");

            // dispense that shit
            robot.dispense();
            mediumRegulatedMotor.stop();

            // reset dispenser
            robot.resetDispenserRotation();
            mediumRegulatedMotor.stop();
        }

        // go back to start
        while (!touchSensor.isPressed()) {
            robot.moveSorter(600, false);
        }
        largeRegulatedMotor.stop();
    }

    /**
     * Prints the color that the sensor is registering.
     * @return Returns the color as an int.
     */
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
        robot.resetSorterPosition();
        mediumRegulatedMotor.stop();
        robot.resetDispenserRotation();
        largeRegulatedMotor.stop();


        while (true) {
            int currentColor = robot.getColorID();

            switch (currentColor) {
                case 2: // BLUE
                    robot.sortColor(MoveDistances.BLUE.getMoveDistance(), currentColor);
                    System.out.println(colors[currentColor]);
                    break;
                case 3: // GREEN
                    robot.sortColor(MoveDistances.GREEN.getMoveDistance(), currentColor);
                    System.out.println(colors[currentColor]);
                    break;
                case 4: // YELLOW
                    robot.sortColor(MoveDistances.YELLOW.getMoveDistance(), currentColor);
                    System.out.println(colors[currentColor]);
                    break;
                case 5: // RED
                    robot.sortColor(MoveDistances.RED.getMoveDistance(), currentColor);
                    System.out.println(colors[currentColor]);
                    break;
            }
        }
    }
}
