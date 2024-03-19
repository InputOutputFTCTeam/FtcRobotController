package org.firstinspires.ftc.teamcode.notUsed_trash;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensors.ColorSensorModule;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensors.DistanceSensorModule;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.IMUAsSensor;

public class SimplifiedChadDriveTrain {
    /*
    объявим здесь ДСмоторы как приват
    объявим здесь датчики как приват
    объявим здесь методы как паблик
        методы офкос из базикДрайвТрейн
        методы офкос для работы с датчиками
    НИКАКОГО ООП НИКАКИХ ТЕБЕ КЛАССОВ
    ПАПА МОЖЕТ В СИ
    ПАПА МОЖЕТ В СИ
    ПОКА ЭТО ТАК, ВСЕ В ПОРЯДКЕ НА РУСИ
     */

    private DcMotor TL, TR, BL, BR;
    private LinearOpMode simpleOpMode = null;
    private DistanceSensorModule dist = null;
    private ColorSensorModule clr = null;
    private IMUAsSensor imu = null;

    double maximumSpeed = 1;
    boolean inited = false;

    public SimplifiedChadDriveTrain(LinearOpMode opMode) {
        simpleOpMode = opMode;
        dist = new DistanceSensorModule(simpleOpMode);
        clr = new ColorSensorModule(simpleOpMode);
        imu = new IMUAsSensor(simpleOpMode);
    }

    private void initMotors() {
        TL = simpleOpMode.hardwareMap.dcMotor.get("leftFront");
        TR = simpleOpMode.hardwareMap.dcMotor.get("rightFront");
        BL = simpleOpMode.hardwareMap.dcMotor.get("leftRear");
        BR = simpleOpMode.hardwareMap.dcMotor.get("rightRear");
        inited = true;
    }

    private void setModes(DcMotor.RunMode mode) {
        TL.setMode(mode);
        TR.setMode(mode);
        BL.setMode(mode);
        BR.setMode(mode);
    }

    private void setOneDirection(DcMotorSimple.Direction dir) {
        TL.setDirection(dir);
        TR.setDirection(dir);
        BL.setDirection(dir);
        BR.setDirection(dir);
    }

    private void setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior behavior) {
        TL.setZeroPowerBehavior(behavior);
        TR.setZeroPowerBehavior(behavior);
        BL.setZeroPowerBehavior(behavior);
        BR.setZeroPowerBehavior(behavior);
    }

    public void move(double x, double y, double r) {
        if (inited) {
            TL.setPower(Range.clip((x + y + r), -maximumSpeed, maximumSpeed));
            TR.setPower(Range.clip((x - y + r), -maximumSpeed, maximumSpeed));
            BL.setPower(Range.clip((-x + y + r), -maximumSpeed, maximumSpeed));
            BR.setPower(Range.clip((-x - y + r), -maximumSpeed, maximumSpeed));
        } else {
            simpleOpMode.telemetry.addLine("NOT INITED WHEELBASE");
            simpleOpMode.telemetry.addLine("must repair code");
            simpleOpMode.telemetry.update();
        }
    }

    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public void initSimple() {
        initMotors();
        setModes(DcMotor.RunMode.RUN_USING_ENCODER);
        //setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        dist.initDistanceSensor();
        clr.initColorSensor();
        imu.initIMU();

        simpleOpMode.telemetry.addLine("all inited");
        simpleOpMode.telemetry.update();
    }

    private double headingError = 0;
    private final double P_TURN_GAIN = -0.02;
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
     *
     * @param maxTurnSpeed максимальная скорость поворота
     * @param heading      направление, в котором роботу следует развернуться
     */
    public void imuTurn(double maxTurnSpeed, double heading) {
        getSteeringCorrection(heading, P_TURN_GAIN);
        while (simpleOpMode.opModeIsActive() && abs(headingError) > HEADING_THRESHOLD) {
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);
            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);
            moveRobot(0, turnSpeed);
            if (abs(turnSpeed) < MIN_TURN_SPEED) break;
        }
        move(0, 0, 0);
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
     *
     * @param x          скорость вдоль оси x (езда вправо-влево)
     * @param y          скорость вдоль оси y (езда вперед-назад)
     * @param distanceMM расстояние на которое надо проехать
     */
    public void encoderRun(double x, double y, double distanceMM) {
        int[] startPosition = {TL.getCurrentPosition(), TR.getCurrentPosition(), BL.getCurrentPosition(), BR.getCurrentPosition()};
        DcMotor[] motors = {TL, TR, BL, BR};
        //надо ли сделать STOP_AND_RESET_ENCODER?
        for (DcMotor motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + distanceMM2Ticks(distanceMM));    //надо ли это отлаживать? протестить и посмотрим...
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (simpleOpMode.opModeIsActive() &&
                TL.isBusy() && TR.isBusy() && BL.isBusy() && BR.isBusy()) {
            move(x, y, 0);
        }
        move(0, 0, 0);
    }

    /**
     * Проезд на неопределенное расстояние до встречи с линией определенного цвета
     *
     * @param x         скорость вдоль оси x (езда вправо-влево)
     * @param y         скорость вдоль оси y (езда вперед-назад)
     * @param r         скорость поворота вокруг своей оси
     * @param colorName название цвета линии на поле
     */
    public void colorRun(double x, double y, double r, ColorSensorModule.colorsField colorName) {
        while (simpleOpMode.opModeIsActive() && !clr.getColorOfField().equals(colorName)) {
            move(x, y, r);
            simpleOpMode.telemetry.addData("i see ", clr.getColorOfField());
            clr.telemetryColor();
            simpleOpMode.telemetry.update();
        }
        move(0, 0, 0);
        //simpleOpMode.sleep(1000);
    }

    /**
     * Проезд для приближения на определенную дистанцию к объекту перед датчиком расстояния
     *
     * @param x          скорость вдоль оси x (езда вправо-влево)
     * @param y          скорость вдоль оси y (езда вперед-назад)
     * @param r          скорость поворота вокруг своей оси
     * @param distanceMM минимальное расстояние между объектом и датчиком расстояния
     */
    public void distanceRunEnclose(double x, double y, double r, int distanceMM) {
        while (simpleOpMode.opModeIsActive() && dist.distanceMM() >= distanceMM) {
            move(x, y, r);
            dist.telemetryDistance();
            simpleOpMode.telemetry.update();
        }
        move(0, 0, 0);
    }

    /**
     * Проезд для отдаления на определенную дистанцию от объекта перед датчиком расстояния
     *
     * @param x          скорость вдоль оси x (езда вправо-влево)
     * @param y          скорость вдоль оси y (езда вперед-назад)
     * @param r          скорость поворота вокруг своей оси
     * @param distanceMM максимальное расстояние между объектом и датчиком расстояния
     */
    public void distanceRunRetreat(double x, double y, double r, int distanceMM) {
        while (simpleOpMode.opModeIsActive() && dist.distanceMM() <= distanceMM) {
            move(x, y, r);
            dist.telemetryDistance();
            simpleOpMode.telemetry.update();
        }
        move(0, 0, 0);
    }

    /**
     * Проезд на расстояние с контролем направления этого движения. Можно повернуться просто, а можно проехать поворачиваясь.
     *
     * @param x                скорость вдоль оси x (езда вправо-влево)
     * @param y                скорость вдоль оси y (езда вперед-назад)
     * @param r                скорость поворота вокруг своей оси
     * @param desiredDirection напраление в котором должен смотреть робот
     * @param distanceMM       максимальное расстояние между объектом и датчиком расстояния
     */
    public void imuSteerEncoder(double x, double y, double r, double desiredDirection, double distanceMM) {
        //возможно, оригинальное moveRobot из IMUDriveTrain позволит сделать такой проезд, но хзхз
        //encoderRun(x,y,turnToHeading, desiredDirection)??? где turnToHeading будет зависеть от desiredDirection
        // Determine new target position, and pass to motor controller
        DcMotor[] motors = {TL, TR, BL, BR};
        //надо ли сделать STOP_AND_RESET_ENCODER?
        for (DcMotor motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + distanceMM2Ticks(distanceMM));    //надо ли это отлаживать? протестить и посмотрим...
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        move(x, y, 0);

        getSteeringCorrection(desiredDirection, P_TURN_GAIN);
        // keep looping while we are still active, and BOTH motors are running.
        while (simpleOpMode.opModeIsActive() && TL.isBusy() && TR.isBusy() && BL.isBusy() && BR.isBusy()) {

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
