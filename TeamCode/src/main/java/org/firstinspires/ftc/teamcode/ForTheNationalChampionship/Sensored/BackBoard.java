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

    public BackBoard(LinearOpMode opMode) {
        dsOpMode = opMode;
        new BasicDriveTrain(dsOpMode);
    }

    public void initBackBoard() {   //Инициализируем метод
        sensorDistance = dsOpMode.hardwareMap.get(DistanceSensor.class, "sensor_distance");
        initMotors();
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public double distanceMM() {    //Задаём дистанцию в миллиметрах
        return sensorDistance.getDistance(DistanceUnit.MM);
    }

    public void backboard_slowly(double x, double y, double r) {
        if (distanceMM() <= 50) {   //Движение у задника на расстоянии меньше 50 миллиметров
            setMaximumSpeed(0.3);
            move(x, -abs(y), 0);

        } else if (distanceMM() <= 500 && distanceMM() > 50) {  //Движение на расстоянии от задника больше 50 и меньше 500 миллиметров
            setMaximumSpeed(0.5);
            move(x, y, r);
        } else if (distanceMM() > 500) {    //Движение на расстоянии от задника больше 500 миллиметров
            setMaximumSpeed(1);
            move(x, y, r);
        }

    }

    boolean driveMode = false; //false - no distance control; true - distance control;
    public void smartMove(double x, double y, double r) {
        if (dsOpMode.gamepad1.y) driveMode = !driveMode;
        if (driveMode)
            backboard_slowly(x, y, r);
        else {
            setMaximumSpeed(1);
            move(x, y, r);
        }
    }
}
