package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.BasicDriveTrain;

/**
 * В этом классе описываются методы работы для датчика расстояния в режиме TeleOp
 */

public class BackBoard extends BasicDriveTrain {
    //private BasicDriveTrain wheelbase;
    private DistanceSensor sensorDistance;
    private LinearOpMode dsOpMode;
    double distance;
    public BackBoard(LinearOpMode opMode) {
        dsOpMode = opMode;
        new BasicDriveTrain(dsOpMode);
    }

    public void setSensorDistance(DistanceSensor sensorDistance) {
        this.sensorDistance = sensorDistance;
    }

    public void initBackBoard() {   //Инициализируем метод
        setOpMode(dsOpMode);
        initMotors();
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
    }



    public void backboard_slowly(double x, double y, double r) {
        if (distance <= 2000){
            distance = sensorDistance.getDistance(DistanceUnit.MM);
        }

        if (distance <= 50) {   //Движение у задника на расстоянии меньше 50 миллиметров
            setMaximumSpeed(0.3);
            move(x, -abs(y), 0);

        } else if (distance <= 500 && distance > 50) {  //Движение на расстоянии от задника больше 50 и меньше 500 миллиметров
            setMaximumSpeed(0.5);
            move(x, y, r);
        } else if (distance > 500) {    //Движение на расстоянии от задника больше 500 миллиметров
            setMaximumSpeed(1);
            move(x, y, r);
        }

    }

    boolean driveMode = true; //false - no distance control; true - distance control;
    public void smartMove(double x, double y, double r) {
        if (dsOpMode.gamepad1.y) {
            driveMode = !driveMode;
            dsOpMode.sleep(200);
        }
        if (driveMode)
            backboard_slowly(x, y, r);
        else {
            setMaximumSpeed(1);
            move(x, y, r);
        }
    }

    public void telemetryBackBoard() {
        wheelbaseTelemetry();
        dsOpMode.telemetry.addLine(driveMode ? "DISTANCE FAST" : "DISTANCE SLOW");
    }
}
