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

    public static void main(String[] args) {
        // go back to start
        while (!touchSensor.isPressed()) {
            largeRegulatedMotor.setSpeed(150);
            largeRegulatedMotor.backward();
        }
        largeRegulatedMotor.stop();

        // reset dispenser
        while (!mediumRegulatedMotor.isStalled()) {
            mediumRegulatedMotor.setSpeed(150);
            mediumRegulatedMotor.forward();
        }
        mediumRegulatedMotor.stop();

        int blackCounter = 0;

        while (true) {
            colorSensor.getColorIDMode();

            // A float array that holds the samples
            float[] colorSample = new float[colorSensor.sampleSize()];

            // Returns a float from 0 to 7
            colorSensor.fetchSample(colorSample, 0);

            String[] colors = {"NONE", "BLACK", "BLUE", "GREEN", "YELLOW", "RED", "WHITE", "BROWN"};

            // Returns a String from the colors array based on what float it fetches, converted to int
            String color = colors[(int) colorSample[0]];

            while (!color.equals("BLACK")) {
                colorSensor.getColorIDMode();

                // A float array that holds the samples
                colorSample = new float[colorSensor.sampleSize()];

                // Returns a float from 0 to 7
                colorSensor.fetchSample(colorSample, 0);

                // Returns a String from the colors array based on what float it fetches, converted to int
                color = colors[(int) colorSample[0]];

                System.out.println(color);

                switch (color) {
                    case "BLUE":
                        // go back to start
                        while (!touchSensor.isPressed()) {
                            largeRegulatedMotor.setSpeed(150);
                            largeRegulatedMotor.backward();
                        }
                        largeRegulatedMotor.stop();

                        // dispense that shit
                        mediumRegulatedMotor.setSpeed(250);
                        mediumRegulatedMotor.backward();
                        Delay.msDelay(1000);
                        mediumRegulatedMotor.stop();

                        // reset dispenser
                        while (!mediumRegulatedMotor.isStalled()) {
                            mediumRegulatedMotor.setSpeed(150);
                            mediumRegulatedMotor.forward();
                        }
                        mediumRegulatedMotor.stop();
                        break;
                    case "GREEN":
                        // go back to start
                        while (!touchSensor.isPressed()) {
                            largeRegulatedMotor.setSpeed(150);
                            largeRegulatedMotor.backward();
                        }
                        largeRegulatedMotor.stop();

                        // move to dispense location
                        largeRegulatedMotor.setSpeed(150);
                        largeRegulatedMotor.forward();
                        Delay.msDelay(1000);
                        largeRegulatedMotor.stop();
                        Delay.msDelay(1000);

                        // dispense that shit
                        mediumRegulatedMotor.setSpeed(250);
                        mediumRegulatedMotor.backward();
                        Delay.msDelay(1000);
                        mediumRegulatedMotor.stop();

                        // reset dispenser
                        while (!mediumRegulatedMotor.isStalled()) {
                            mediumRegulatedMotor.setSpeed(150);
                            mediumRegulatedMotor.forward();
                        }
                        mediumRegulatedMotor.stop();

                        // go back to start
                        while (!touchSensor.isPressed()) {
                            largeRegulatedMotor.setSpeed(150);
                            largeRegulatedMotor.backward();
                        }
                        largeRegulatedMotor.stop();
                        break;
                    case "RED":
                        // go back to start
                        while (!touchSensor.isPressed()) {
                            largeRegulatedMotor.setSpeed(150);
                            largeRegulatedMotor.backward();
                        }
                        largeRegulatedMotor.stop();

                        // move to dispense location
                        largeRegulatedMotor.setSpeed(150);
                        largeRegulatedMotor.forward();
                        Delay.msDelay(1900);
                        largeRegulatedMotor.stop();
                        Delay.msDelay(1000);

                        // dispense that shit
                        mediumRegulatedMotor.setSpeed(250);
                        mediumRegulatedMotor.backward();
                        Delay.msDelay(1000);
                        mediumRegulatedMotor.stop();

                        // reset dispenser
                        while (!mediumRegulatedMotor.isStalled()) {
                            mediumRegulatedMotor.setSpeed(150);
                            mediumRegulatedMotor.forward();
                        }
                        mediumRegulatedMotor.stop();

                        // go back to start
                        while (!touchSensor.isPressed()) {
                            largeRegulatedMotor.setSpeed(150);
                            largeRegulatedMotor.backward();
                        }
                        largeRegulatedMotor.stop();
                        break;
                    case "YELLOW":
                        // go back to start
                        while (!touchSensor.isPressed()) {
                            largeRegulatedMotor.setSpeed(150);
                            largeRegulatedMotor.backward();
                        }
                        largeRegulatedMotor.stop();

                        // move to dispense location
                        largeRegulatedMotor.setSpeed(150);
                        largeRegulatedMotor.forward();
                        Delay.msDelay(2800);
                        largeRegulatedMotor.stop();
                        Delay.msDelay(1000);

                        // dispense that shit
                        mediumRegulatedMotor.setSpeed(250);
                        mediumRegulatedMotor.backward();
                        Delay.msDelay(1000);
                        mediumRegulatedMotor.stop();

                        // reset dispenser
                        while (!mediumRegulatedMotor.isStalled()) {
                            mediumRegulatedMotor.setSpeed(150);
                            mediumRegulatedMotor.forward();
                        }
                        mediumRegulatedMotor.stop();

                        // go back to start
                        while (!touchSensor.isPressed()) {
                            largeRegulatedMotor.setSpeed(150);
                            largeRegulatedMotor.backward();
                        }
                        largeRegulatedMotor.stop();
                        break;
                }
            }

            Delay.msDelay(1000);
            blackCounter++;
            System.out.println(blackCounter);

            if (blackCounter == 5) {
                // reset dispenser
                while (!mediumRegulatedMotor.isStalled()) {
                    mediumRegulatedMotor.setSpeed(150);
                    mediumRegulatedMotor.forward();
                }
                mediumRegulatedMotor.stop();

                blackCounter = 0;
            }
        }
    }
}
