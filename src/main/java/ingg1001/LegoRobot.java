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

    // hardware
    private static final EV3LargeRegulatedMotor largeRegulatedMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    private static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
    private static final EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
    private static final EV3MediumRegulatedMotor mediumRegulatedMotor = new EV3MediumRegulatedMotor(MotorPort.B);

    // robot
    private static final LegoRobot robot = new LegoRobot();

    // color array
    private static final String[] colors = {"NONE", "BLACK", "BLUE", "GREEN", "YELLOW", "RED", "WHITE", "BROWN"};

    /**
     * Enum containing the values for how far the sorter should move for each color
     */
    private enum MoveDistances {
        // sets the colors and their respective move distances
        BLUE(0),
        GREEN(150),
        YELLOW(450),
        RED(750);

        // moveDistance field
        private final int moveDistance;

        /**
         * Constructor for enum to store the distance the sorter should move depending on color
         * @param moveDistance The value that defines the amount of time the sorter should move
         */
        MoveDistances(int moveDistance) {
            this.moveDistance = moveDistance;
        }

        /**
         * Method in enum to return the defined move distance for the different colors
         * @return The distance for the sorter to move as int
         */
        int getMoveDistance() {
            return this.moveDistance;
        }
    }

    /**
     * Initializes robot by resetting all positions
     */
    private void init() {
        robot.resetDispenserRotation();
        robot.resetSorterPosition();
    }

    /**
     * Method for moving the sorter
     * @param speed Sets the speed for how fast the sorter should move
     * @param directionForwards Sets which direction to move in
     */
    private void moveSorter(int speed, boolean directionForwards) {
        // sets speed for sorter
        largeRegulatedMotor.setSpeed(speed);

        // move sorter, either forwards or backwards
        if (directionForwards) {
            largeRegulatedMotor.forward();
        } else {
            largeRegulatedMotor.backward();
        }
    }

    /**
     * Method for moving the sorter to the start position
     */
    private void resetSorterPosition() {
        // move the sorter towards it's start position until reset button is pressed
        while (!touchSensor.isPressed()) {
            robot.moveSorter(600, false);
        }

        // stop the motor
        largeRegulatedMotor.stop();
    }

    /**
     * Method for resetting the dispenser mechanism
     */
    private void resetDispenserRotation() {
        // rotate the dispenser mechanism towards it's start position until it is stalled
        while (!mediumRegulatedMotor.isStalled()) {
            mediumRegulatedMotor.setSpeed(150);
            mediumRegulatedMotor.forward();
        }

        // stop the motor
        mediumRegulatedMotor.stop();
    }

    /**
     * Method for dispensing a single color block
     */
    private void dispense() {
        // rotate the dispenser mechanism for a set time to dispense a color
        mediumRegulatedMotor.setSpeed(250);
        mediumRegulatedMotor.backward();
        Delay.msDelay(1000);

        // stop the motor
        mediumRegulatedMotor.stop();
    }

    /**
     * Method for moving the sorter to the correct position and dispensing the color block
     * @param delay Delay defining (in ms) how much time it should spend moving the sorter before dispensing a color
     * @param currentColor Sets the current color getting sorted for the method to compare to the next
     */
    private void sortColor(int delay, int currentColor) {
        System.out.println("Sorting " + colors[currentColor] + "!");

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

        // wait for new color to fall in place
        Delay.msDelay(250);

        // read new color and compare to last, if equal dispense the new color block
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
     * Retrieves the current color to be sorted
     * @return The ID corresponding with the color read
     */
    private int getColorID() {
        colorSensor.getColorIDMode();

        // a float array that is going to store the sample from the color sensor
        float[] colorSample = new float[colorSensor.sampleSize()];

        // returns a color ID from 0-7 depending on color read
        colorSensor.fetchSample(colorSample, 0);

        // returns the color ID as an int
        return (int) colorSample[0];
    }

    public static void main(String[] args) {
        // initialize robot by resetting all position
        robot.init();
        mediumRegulatedMotor.stop();
        largeRegulatedMotor.stop();

        while (true) {
            // read current color
            int currentColor = robot.getColorID();

            // check read color and call sortColor method
            switch (currentColor) {
                case 2: // BLUE
                    robot.sortColor(MoveDistances.BLUE.getMoveDistance(), currentColor);
                    break;
                case 3: // GREEN
                    robot.sortColor(MoveDistances.GREEN.getMoveDistance(), currentColor);
                    break;
                case 4: // YELLOW
                    robot.sortColor(MoveDistances.YELLOW.getMoveDistance(), currentColor);
                    break;
                case 5: // RED
                    robot.sortColor(MoveDistances.RED.getMoveDistance(), currentColor);
                    break;
            }
        }
    }
}
