package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.BasicDriveTrain;
//TODO: проверить документацию для датчика расстояния
/**
 * В этом классе описываются методы работы для датчика расстояния в режиме TeleOp
 */

public class BackBoard extends BasicDriveTrain {
    //private BasicDriveTrain wheelbase;
    private DistanceSensor sensorDistance;
    private LinearOpMode dsOpMode;
    /**
     * коснтруктор класса датчика расстояния
     * @param opMode - передаётся конструктору, в opMod-e которого работает этот датчик
     */
    public BackBoard(LinearOpMode opMode) {
        dsOpMode = opMode;
        new BasicDriveTrain(dsOpMode);
    }
    /**
     * иниицализация колёсной базы
     */
    public void initBackBoard() {
        setOpMode(dsOpMode);
        sensorDistance = dsOpMode.hardwareMap.get(DistanceSensor.class, "sensor_distance");
        initMotors();
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    /**
     * задаёт дистанцию в миллиметрах
     * @return возвращает дистанцию в миллиметрах
     */
    public double distanceMM() {
        return sensorDistance.getDistance(DistanceUnit.MM);
    }
    /**
     * регуляция скорости движения в зависимости от расстояния до задника (в миллиметрах)
     * @param x - скорость вдоль оси x
     * @param y - скорость вдоль оси y
     * @param r - скорость разворота (>0 по часовой, <0 против часовой)
     */
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
    /**
     * движение по датчику расстояния
     * @param x - скорость вдоль оси x
     * @param y - скорость вдоль оси y
     * @param r - скорость разворота (>0 по часовой, <0 против часовой)
     */
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
    /**
     * добавление отдельного потока телеметрии о текущем расстоянии до задника
     */
    public void telemetryBackBoard() {
        Thread thread = new Thread(() -> {
            dsOpMode.telemetry.addData("ranghesens", distanceMM());
            dsOpMode.telemetry.update();
        });
        thread.start();
        wheelbaseTelemetry();
        dsOpMode.telemetry.addLine(driveMode ? "DISTANCE FAST" : "DISTANCE SLOW");
    }
}
