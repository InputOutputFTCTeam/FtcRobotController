package org.firstinspires.ftc.teamcode.robotModules.Basic;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class BackBoard {
    private BasicDriveTrain wheelbase;
    private DistanceSensor sensorDistance;
    private LinearOpMode dsOpMode;

    public BackBoard(LinearOpMode opMode) {
        dsOpMode = opMode;
    }

    public void initBackBoard() {
        wheelbase = new BasicDriveTrain(dsOpMode);
        sensorDistance = dsOpMode.hardwareMap.get(DistanceSensor.class, "sensor_distance");
        wheelbase.initMotors();
        wheelbase.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelbase.setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelbase.setOneDirection(DcMotorSimple.Direction.FORWARD);
        wheelbase.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public double distanceMM() {
        return sensorDistance.getDistance(DistanceUnit.MM);
    }

    public void backboard_slowly(double x, double y, double r) {
        if (distanceMM() <= 50) {
            wheelbase.setMaximumSpeed(0.3);
            wheelbase.move(x, -abs(y), 0);

        } else if (distanceMM() <= 500 && distanceMM() > 50) {
            wheelbase.setMaximumSpeed(0.5);
            wheelbase.move(x, y, r);
        } else if (distanceMM() > 500) {
            wheelbase.setMaximumSpeed(1);
            wheelbase.move(x, y, r);
        }

    }
}
