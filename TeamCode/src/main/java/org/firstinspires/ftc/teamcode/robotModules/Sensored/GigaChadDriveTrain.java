package org.firstinspires.ftc.teamcode.robotModules.Sensored;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.reflect.Array.get;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.robotModules.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.ColorSensorModule;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.DistanceSensorModule;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.IMUAsSensor;

import java.lang.reflect.Array;

public class GigaChadDriveTrain extends BasicDriveTrain {
    //объявить imu, color sensor, motors in encoder mode, distance
    LinearOpMode gigaOpMode;
    DistanceSensorModule dist;
    ColorSensorModule clr;
    IMUAsSensor imu;

    public GigaChadDriveTrain(LinearOpMode opMode) {
        super(opMode);  //new BasicDriveTrain(opMode);
        gigaOpMode = opMode;
        dist = new DistanceSensorModule(gigaOpMode);
        clr = new ColorSensorModule(gigaOpMode);
        imu = new IMUAsSensor(gigaOpMode);
    }

    public void initGigaChad() {
        initMotors();
        setModes(DcMotor.RunMode.RUN_USING_ENCODER);
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        dist.initDistanceSensor();
        clr.initColorSensor();
        imu.initIMU();
        gigaOpMode.telemetry.addLine("all inited");
        gigaOpMode.telemetry.update();
    }

    /*
    imuTurn -- тупа поворот по гироскопу
    encoderRun -- тупа проезд прямо или вбок по энкодерам
    colorRun -- тупа едем, пока не упремся в линию
    distanceRun -- тупа едем, пока не достигнем какого-то расстояния
    imuSteerEncoder -- интеллектуально едем на определенное расстояние и корректируем направление езды с помощью поворота encoderRun(x,y,turnToHeading)
     */

    // ------------------>>>    imu
    // TODO: вставить сюда значения из IMUDriveTrain
    private double headingError = 0;
    private final double P_TURN_GAIN = -0.01;
    private double targetHeading = 0;
    private double turnSpeed = 0;
    private double leftSpeed = 0;
    private double rightSpeed = 0;
    private final double HEADING_THRESHOLD = 1.0;
    private final double MIN_TURN_SPEED = 0.08;

    private double getSteeringCorrection(double desiredHeading, double proportionalGain) {
        targetHeading = desiredHeading;
        headingError = targetHeading - imu.getHeading();

        while (headingError > 180) headingError -= 360;
        while (headingError <= -180) headingError += 360;

        return Range.clip(headingError * proportionalGain, -1, 1);
    }

    private void moveRobot(double drive, double turn) {
        turnSpeed = turn;

        leftSpeed = drive - turn;
        rightSpeed = drive + turn;

        double max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
        if (max > 1.0) {
            leftSpeed /= max;
            rightSpeed /= max;
        }

        move(0, 0, rightSpeed - leftSpeed);
    }

    /**
     * Функция для поворота на определенный угол
     * @param maxTurnSpeed максимальная скорость поворота
     * @param heading направление, в котором роботу следует развернуться
     */
    public void imuTurn(double maxTurnSpeed, double heading) {
        getSteeringCorrection(heading, P_TURN_GAIN);
        while (gigaOpMode.opModeIsActive() && abs(headingError) > HEADING_THRESHOLD) {
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);
            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);
            moveRobot(0, turnSpeed);
            if (abs(turnSpeed) < MIN_TURN_SPEED) break;
        }
        //остановка??
    }

    private int distanceMM2Ticks(double distanceMM) {
        final double WHEEL_DIAMETER = 4 * 25.4;
        final double WHEEL_PERIMETER = WHEEL_DIAMETER * PI;
        final double WHEEL_REVOLUTIONS = distanceMM / WHEEL_PERIMETER;
        final int TICKS_PER_REVOLUTION = 1440;
        final double wheel_revs = 2;    //
        final double motor_revs = 3;    //
        //final double GEAR_RATIO = (double) motor_revs / wheel_revs;
        return (int) (WHEEL_REVOLUTIONS * (wheel_revs / motor_revs) * TICKS_PER_REVOLUTION);
    }

    /**
     * Проезд на расстояние в мм, используя энкодеры
     * @param x скорость вдоль оси x (езда вправо-влево)
     * @param y скорость вдоль оси y (езда вперед-назад)
     * @param distanceMM расстояние на которое надо проехать
     */
    public void encoderRun(double x, double y, double distanceMM) {
        int[] startPosition = {getTL().getCurrentPosition(), getTR().getCurrentPosition(), getBL().getCurrentPosition(), getBR().getCurrentPosition()};
        DcMotor[] motors = {getTL(), getTR(), getBL(), getBR()};
        //надо ли сделать STOP_AND_RESET_ENCODER?
        for (DcMotor motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + distanceMM2Ticks(distanceMM));    //надо ли это отлаживать? протестить и посмотрим...
            setModes(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (getOpMode().opModeIsActive() && getTL().isBusy() && getTR().isBusy() && getBL().isBusy() && getBR().isBusy()) {
            move(x, y, 0);
        }

        move(0, 0, 0);
    }

    /**
     * Проезд на неопределенное расстояние до встречи с линией определенного цвета
     * @param x скорость вдоль оси x (езда вправо-влево)
     * @param y скорость вдоль оси y (езда вперед-назад)
     * @param r скорость поворота вокруг своей оси
     * @param colorName название цвета линии на поле
     */
    public void colorRun(double x, double y, double r, ColorSensorModule.colorsField colorName) {
        while (gigaOpMode.opModeIsActive() && clr.getColorOfField() != colorName) {
            move(x, y, r);
        }
        move(0, 0, 0);
    }

    /**
     * Проезд для приближения на определенную дистанцию к объекту перед датчиком расстояния
     * @param x скорость вдоль оси x (езда вправо-влево)
     * @param y скорость вдоль оси y (езда вперед-назад)
     * @param r скорость поворота вокруг своей оси
     * @param distanceMM минимальное расстояние между объектом и датчиком расстояния
     */
    public void distanceRunEnclose(double x, double y, double r, int distanceMM) {
        while (gigaOpMode.opModeIsActive() && dist.distanceMM() >= distanceMM) {
            move(x, y, r);
        }
        move(0, 0, 0);
    }

    /**
     * Проезд для отдаления на определенную дистанцию от объекта перед датчиком расстояния
     * @param x скорость вдоль оси x (езда вправо-влево)
     * @param y скорость вдоль оси y (езда вперед-назад)
     * @param r скорость поворота вокруг своей оси
     * @param distanceMM максимальное расстояние между объектом и датчиком расстояния
     */
    public void distanceRunRetreat(double x, double y, double r, int distanceMM) {
        while (gigaOpMode.opModeIsActive() && dist.distanceMM() <= distanceMM) {
            move(x, y, r);
        }
        move(0, 0, 0);
    }

    /**
     * Проезд на расстояние с контролем направления этого движения. Можно повернуться просто, а можно проехать поворачиваясь.
     * @param x скорость вдоль оси x (езда вправо-влево)
     * @param y скорость вдоль оси y (езда вперед-назад)
     * @param r скорость поворота вокруг своей оси
     * @param desiredDirection напраление в котором должен смотреть робот
     * @param distanceMM максимальное расстояние между объектом и датчиком расстояния
     */
    public void imuSteerEncoder(double x, double y ,double r, double desiredDirection, double distanceMM){
        //возможно, оригинальное moveRobot из IMUDriveTrain позволит сделать такой проезд, но хзхз
        //encoderRun(x,y,turnToHeading, desiredDirection)??? где turnToHeading будет зависеть от desiredDirection
        // Determine new target position, and pass to motor controller
        DcMotor[] motors = {getTL(), getTR(), getBL(), getBR()};
        //надо ли сделать STOP_AND_RESET_ENCODER?
        for (DcMotor motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + distanceMM2Ticks(distanceMM));    //надо ли это отлаживать? протестить и посмотрим...
            setModes(DcMotor.RunMode.RUN_TO_POSITION);
        }
        move(x, y, 0);

        // keep looping while we are still active, and BOTH motors are running.
        while (gigaOpMode.opModeIsActive() && getTL().isBusy() && getTR().isBusy() && getBL().isBusy() && getBR().isBusy()) {

            // Determine required steering to keep on heading
            turnSpeed = getSteeringCorrection(desiredDirection, P_TURN_GAIN);

            // if driving in reverse, the motor correction also needs to be reversed
            if (distanceMM < 0)
                turnSpeed *= -1.0;

            // Apply the turning correction to the current driving speed.
            move(x, y, turnSpeed);
        }

        // Stop all motion & Turn off RUN_TO_POSITION
        move(0, 0, 0);
        setModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}





















