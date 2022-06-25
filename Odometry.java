package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

// Odometry class for a differential drive with a gyro.
public class Odometry {

    private double[] position; // Index 0 is the x value, index 1 is the y value.
    private double wheelCircumferenceCM;
    private Timer timer;

    public Odometry() {
        this.position = {0, 0};
        this.timer = new Timer();
    }

    public Odometry(double x, double y) {
        this.position = {x, y};
        this.timer = new Timer();
    }

    public void setWheelDiameterCM(double wheelDiameterCM) {
        this.wheelCircumferenceCM = wheelDiameterCM * 3.14159265;
    }

    public double calculateSpeedMPS(double leftRPM, double rightRPM) {
        try {
            return ((leftRPM + rightRPM) /0.2) * this.wheelCircumferenceCM /100; // Because centimeters to meters
        }
        catch(NullPointerException e) {
            DriverStation.reportError("Must set wheel diameter before calculating speed!", true);
            return 0;
        }
    }

    /**
     * While in motion, this method should be called periodically for accurate results.
     * It returns an array in which index 0 is the x value, and index 1 is the y value.
     * If the speed parameter is in meters per second, then the x and y values are meters.
     */
    public double[] calculatePosition(double angle, double speed) {
        double distance = speed * this.timer.get();
        this.position[0] =+ Math.sin(angle) * distance;
        this.position[1] =+ Math.cos(angle) * distance;
        this.timer.reset();
        this.timer.start();
        return this.position;
    }

    public void resetPosition() {
        this.timer.stop();
        this.timer.reset();
        this.position[0] = 0;
        this.position[1] = 0;
    }
}

