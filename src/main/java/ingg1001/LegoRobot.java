package ingg1001;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;

import ev3dev.sensors.ev3.EV3ColorSensor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

import lejos.utility.Delay;

public class LegoRobot {
    private static final EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
    private static final EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.C);
    private static final EV3ColorSensor light = new EV3ColorSensor(SensorPort.S1);

    public static void main(String[] args) {
        System.out.println("READY");
        boolean turningLeft = true;
        boolean turningRight = false;
        int turningLeftCounter = 0;
        int turningRightCounter = 0;
        int blackCounter = 0;
        light.getRedMode();

        while (true) {
            float[] lightReflected = new float[light.sampleSize()];
            light.fetchSample(lightReflected, 0);

            if (lightReflected[0] < 30) {
                blackCounter++;
                if (blackCounter > 15) {
                    motorLeft.setSpeed(720);
                    motorRight.setSpeed(720);
                    motorRight.forward();
                    motorLeft.forward();
                } else {
                    motorLeft.setSpeed(500);
                    motorRight.setSpeed(500);
                    motorRight.forward();
                    motorLeft.forward();
                }
            } else {
                blackCounter = 0;
                while (turningLeft) {
                    lightReflected = new float[light.sampleSize()];
                    light.fetchSample(lightReflected, 0);

                    turningLeftCounter++;

                    motorLeft.setSpeed(100);
                    motorRight.setSpeed(100);
                    motorRight.forward();
                    motorLeft.backward();
                    Delay.msDelay(5);

                    if (lightReflected[0] < 30) {
                        turningLeftCounter = 0;
                        turningLeft = false;
                        turningRight = false;
                    }

                    if (turningLeftCounter == 20) {
                        turningLeft = false;
                        turningRight = true;
                        turningLeftCounter = 0;
                    }
                }

                while (turningRight) {
                    lightReflected = new float[light.sampleSize()];
                    light.fetchSample(lightReflected, 0);

                    motorLeft.setSpeed(100);
                    motorRight.setSpeed(100);
                    motorRight.backward();
                    motorLeft.forward();
                    Delay.msDelay(5);

                    turningRightCounter++;

                    if (lightReflected[0] < 30) {
                        turningRightCounter = 0;
                        turningRight = false;
                    }

                    if (turningRightCounter == 60) {
                        turningRight = false;
                        turningRightCounter = 0;
                    }
                }

                turningLeft = true;
            }
        }
    }
}
