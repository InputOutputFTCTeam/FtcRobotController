package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors.ColorSensorModule;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors.DistanceSensorModule;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors.IMUAsSensor;

public class MegaDriveTrain extends BasicDriveTrain {
    LinearOpMode gigaOpMode;
    DistanceSensorModule dist = null;
    ColorSensorModule clr = null;
    IMUAsSensor imu = null;

    /**
     * Конструирует класс колесной базы
     * @param opMode передается конструктору, в каком OpMode работает колесная база
     */
    public MegaDriveTrain(LinearOpMode opMode) {
        gigaOpMode = opMode;
        new BasicDriveTrain(gigaOpMode);        //конструирует базовую к.б.
        dist = new DistanceSensorModule(gigaOpMode);
        clr = new ColorSensorModule(gigaOpMode);
        imu = new IMUAsSensor(gigaOpMode);
    }

    /**
     * Инициализация колесной базы
     */
    public void initMega() {
        setOpMode(gigaOpMode);
        initMotors();
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);

        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        dist.initDistanceSensor();
        clr.initColorSensor();
        imu.initIMU();

        gigaOpMode.telemetry.addLine("all inited");
        gigaOpMode.telemetry.update();
    }

    /*
    imuTurn -- поворот по гироскопу
    encoderRun -- проезд прямо или в бок по энкодерам
    colorRun -- едем, пока не зафиксируем нужный цвет
    distanceRun -- едем, пока не достигнем какого-то расстояния
    imuSteerEncoder -- едем на определенное расстояние и корректируем направление езды с помощью поворота encoderRun(x,y,turnToHeading)
     */

    // ------------------>>>    imu
    private double headingError = 0;
    private double P_TURN_GAIN = -0.02;
    private double targetHeading = 0;
    private double turnSpeed = 0;
    private double leftSpeed = 0;
    private double rightSpeed = 0;
    private final double HEADING_THRESHOLD = 1.0;
    private final double MIN_TURN_SPEED = 0.13;

    /**
     * Рассчитывает угол, на который надо скорректировать направление робота.
     * @param desiredHeading желаемое направление, в котором должен смотреть робот
     * @param proportionalGain пропорциональный коэффициент, по которому робот замедляется
     * @return Пропорционально скорректированную мощность для моторов.
     */
    private double getSteeringCorrection(double desiredHeading, double proportionalGain) {
        targetHeading = desiredHeading;
        headingError = targetHeading - imu.getHeading();

        while (headingError > 180) headingError -= 360;
        while (headingError <= -180) headingError += 360;

        return Range.clip(headingError * proportionalGain, -1, 1);
    }

    /**
     * Метод для управления роботом, в котором мощности колес с разных сторон скорректированы пропорционально друг другу.
     * @param drive скорость прямолинейного движения
     * @param turn скорость разворота
     */
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
     * @param heading направление, в котором роботу следует развернуться (>0 - против часовой, <0 - по часовой)
     */
    public void imuTurn(double maxTurnSpeed, double heading) {
        getSteeringCorrection(heading, P_TURN_GAIN);
        while (gigaOpMode.opModeIsActive() && abs(headingError) > HEADING_THRESHOLD) {
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);
            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);
            move(0, 0, turnSpeed);
            if (abs(turnSpeed) < MIN_TURN_SPEED) break;
        }
        move(0, 0, 0);
    }

    /**
     * Перевод из расстояния в мм в тики энкодера, встроенного в мотор Neverest GearMotor 60:1
     * @param distanceMM дистанция в мм
     * @return количество импульсов энкодера (integer значение)
     */
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
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // задаем направления, в которых должны вращаться моторы при движении в соответствующие стороны
        if (x != 0 && y == 0) {         //езда в бок
            setOneDirection(DcMotorSimple.Direction.FORWARD);
            getTL().setDirection(DcMotorSimple.Direction.REVERSE);
            getTR().setDirection(DcMotorSimple.Direction.REVERSE);
        } else if (x == 0 && y != 0) {  //езда прямо
            setOneDirection(DcMotorSimple.Direction.FORWARD);
            getTR().setDirection(DcMotorSimple.Direction.REVERSE);
            getBR().setDirection(DcMotorSimple.Direction.REVERSE);
        }

        DcMotor[] motors = {getTL(), getTR(), getBL(), getBR()};

        gigaOpMode.telemetry.addData("distanceMM2Ticks: ", distanceMM2Ticks(distanceMM));

        //задаем целевое положение для мотора. Сколько оборотов он должен сделать
        for (DcMotor motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + distanceMM2Ticks(distanceMM));
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            gigaOpMode.telemetry.addData("target: ", motor.getTargetPosition());
        }
        gigaOpMode.telemetry.update();

        //подаем моторам мощность, пока они не достигнут целевого положения
        while (gigaOpMode.opModeIsActive() && getTL().isBusy() && getTR().isBusy() && getBL().isBusy() && getBR().isBusy()) {
            move(x, y, 0);
            for (DcMotor motor : motors) {
                gigaOpMode.telemetry.addData("business: ", motor.isBusy());
                gigaOpMode.telemetry.addData("Current Position: ", motor.getCurrentPosition());
            }
            gigaOpMode.telemetry.update();
        }

        move(0, 0, 0);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
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
        ElapsedTime timer = new ElapsedTime();
        timer.reset();  //создаем и запускаем таймер, который по истечении времени остановит работу процедуры.
        while (gigaOpMode.opModeIsActive() && !clr.getColorOfField().equals(colorName)) {
            clr.updateColor();
            move(x, y, r);
            gigaOpMode.telemetry.addLine(String.format("%5f %5f", getTL().getPower(), getTR().getPower()));
            gigaOpMode.telemetry.addLine(String.format("%5f %5f", getBL().getPower(), getBR().getPower()));

            gigaOpMode.telemetry.addData("i see ", clr.getColorOfField());
            clr.telemetryColor();
            gigaOpMode.telemetry.update();

            //проверяем, не вышло ли время на выполнение задачи
            if (timer.milliseconds() > 2500) {
                break;
            }
        }
        move(0, 0, 0);
        gigaOpMode.sleep(100);
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
        while (gigaOpMode.opModeIsActive() && dist.distanceMM() >= distanceMM) {
            move(x, y, r);
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
        while (gigaOpMode.opModeIsActive() && dist.distanceMM() <= distanceMM) {
            move(x, y, r);
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
        //Determine new target position, and pass to motor controller
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (x != 0 && y == 0) {
            setOneDirection(DcMotorSimple.Direction.FORWARD);
            getTL().setDirection(DcMotorSimple.Direction.REVERSE);
            getTR().setDirection(DcMotorSimple.Direction.REVERSE);
        } else if (x == 0 && y != 0) {
            setOneDirection(DcMotorSimple.Direction.FORWARD);
            getTR().setDirection(DcMotorSimple.Direction.REVERSE);
            getBR().setDirection(DcMotorSimple.Direction.REVERSE);
        }


        DcMotor[] motors = {getTL(), getTR(), getBL(), getBR()};
        gigaOpMode.telemetry.addData("distanceMM2Ticks: ", distanceMM2Ticks(distanceMM));
        for (DcMotor motor : motors) {
            motor.setTargetPosition(motor.getCurrentPosition() + distanceMM2Ticks(distanceMM));    //надо ли это отлаживать? протестить и посмотрим...
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            gigaOpMode.telemetry.addData("target: ", motor.getTargetPosition());
        }
        move(abs(x), abs(y), 0);

        P_TURN_GAIN = -0.3;
        getSteeringCorrection(desiredDirection, P_TURN_GAIN);
        // keep looping while we are still active, and BOTH motors are running.
        while (gigaOpMode.opModeIsActive() && getTL().isBusy() && getTR().isBusy() && getBL().isBusy() && getBR().isBusy()) {
            // Determine required steering to keep on heading
            turnSpeed = getSteeringCorrection(desiredDirection, P_TURN_GAIN);
            // if driving in reverse, the motor correction also needs to be reversed
            if (distanceMM < 0)
                turnSpeed *= -1.0;

            // Apply the turning correction to the current driving speed.
            move(x, y, -turnSpeed);

            for (DcMotor motor : motors) {
                gigaOpMode.telemetry.addData("business: ", motor.isBusy());
                gigaOpMode.telemetry.addData("Current Position: ", motor.getCurrentPosition());
            }
            gigaOpMode.telemetry.addData("direction: ", imu.getHeading());
            gigaOpMode.telemetry.addData("turn speed: ", turnSpeed);
            gigaOpMode.telemetry.addData("x: ", x);
            gigaOpMode.telemetry.addData("y: ", y);

            gigaOpMode.telemetry.update();
        }

        // Stop all motion & Turn off RUN_TO_POSITION
        move(0, 0, 0);
        setModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}