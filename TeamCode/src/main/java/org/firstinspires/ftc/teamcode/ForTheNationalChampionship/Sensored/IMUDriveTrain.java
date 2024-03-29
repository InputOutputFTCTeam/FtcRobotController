package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored;

import com.qualcomm.hardware.bosch.BNO055IMUNew;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.BasicDriveTrain;

/**
 * В этом классе описываются основные методы для гироскопа на основе BNO055IMU.
 * Класс наследуется от BasicDriveTrain
 */
public class IMUDriveTrain extends BasicDriveTrain {
    BNO055IMUNew imu = null;
    double headingError  = 0;
    static final double     P_TURN_GAIN            = -0.02;
    static final double     P_DRIVE_GAIN           = 0.03;
    private double  targetHeading = 0;
    private double  driveSpeed    = 0;
    private double  turnSpeed     = 0;
    private double  leftSpeed     = 0;
    private double  rightSpeed    = 0;
    private int     leftTarget    = 0;
    private int     rightTarget   = 0;

    final double MIN_TURN_SPEED = 0.08;
    static final double     HEADING_THRESHOLD       = 1.0 ;
    static final double     SPEED_THRESHOLD         = 0.2 ;
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;

    /**
     * Настроиваем ориентацию Контрол/Экспеншн хаба в пространстве
     */
    RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;       //направление того, как установлен Rev Hub (logo)
    RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.DOWN;   //направление того, как установлен Rev Hub (USB)
    RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);


    public IMUDriveTrain() {
    }

    /**
     * Создаём КБ, как класс внутри opMode
     */
    public IMUDriveTrain(LinearOpMode gyroOpMode) {
        setOpMode(gyroOpMode);
    }

    /**
     * Инициалируем КБ для opMode,настроиваем гироскоп
     */
    public void initIDT() {
        initMotors();
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
        imu = getOpMode().hardwareMap.get(BNO055IMUNew.class, "imu");
        imu.initialize(new com.qualcomm.robotcore.hardware.IMU.Parameters(orientationOnRobot));
        imu.resetYaw();
    }
    /** переключение направлений моторов для работы Road Runner */
    public void switchToRRDirections(){
        getTL().setDirection(DcMotorSimple.Direction.FORWARD);
        getTR().setDirection(DcMotorSimple.Direction.REVERSE);
        getBL().setDirection(DcMotorSimple.Direction.FORWARD);
        getBR().setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Метод для поворота на heading гразусов с n-й скоростью
     *
     * @param maxTurnSpeed максимальная корость поворота
     * @param heading      новое направление
     */
    public void turnToHeading(double maxTurnSpeed, double heading) {

        getSteeringCorrection(heading, P_TURN_GAIN);

        while (getOpMode().opModeIsActive() && (Math.abs(headingError) > HEADING_THRESHOLD)){

            // Determine required steering to keep on heading
            turnSpeed = getSteeringCorrection(heading, P_TURN_GAIN);

            // Clip the speed to the maximum permitted value.
            turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);

            // Pivot in place by applying the turning correction
            moveRobot(0,turnSpeed);

            // Display drive status for the driver.
            //sendTelemetry(false);

            getOpMode().telemetry.addData("turnSpeed: ", turnSpeed);    //рост
            getOpMode().telemetry.addData("heading: ", getHeading());   //отрицательный рост
            getOpMode().telemetry.addData("heading Error: ", headingError); //рост
            getOpMode().telemetry.addData("steering correction: ", getSteeringCorrection(90, P_TURN_GAIN)); //рост
            getOpMode().telemetry.update();

            if (Math.abs(turnSpeed) < MIN_TURN_SPEED)
                break;
        }

        // Stop all motion;
        //moveRobot(0, 0);
        move(0, 0, 0);
    }

    /**
     * Метод для коррекции отклонения от целевого значения
     * @param desiredHeading желаемое направление в пространстве
     * @param proportionalGain пропорциональный коэффициент изменения скорости
     */
    public double getSteeringCorrection(double desiredHeading, double proportionalGain) {       //П-регулируемый поворот
        targetHeading = desiredHeading;  // Save for telemetry

        // Determine the heading current error
        headingError = targetHeading - getHeading();

        // Normalize the error to be within +/- 180 degrees
        while (headingError > 180) headingError -= 360;
        while (headingError <= -180) headingError += 360;

        // Multiply the error by the gain to determine the required steering correction/  Limit the result to +/- 1.0
        return Range.clip(headingError * proportionalGain, -1, 1);
    }

    /**
     * Метод для передвижения робота в пространстве
     */
    public void moveRobot(double drive, double turn) {
        driveSpeed = drive;
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

    /** выводим телеметрию */
    public void sendTelemetry(boolean straight) {

        if (straight) {
            getOpMode().telemetry.addData("Motion", "Drive Straight");
            getOpMode().telemetry.addData("Target Pos Left:Right",  "%7d:%7d",      leftTarget,  rightTarget);
            getOpMode().telemetry.addData("Actual Pos Left:Right",  "%7d:%7d",      getTL().getCurrentPosition(),
                    getTR().getCurrentPosition(), getBL().getCurrentPosition(), getBR().getCurrentPosition());
        } else {
            getOpMode().telemetry.addData("Motion", "Turning");
        }

        getOpMode().telemetry.addData("Heading- Target : Current", "%5.2f : %5.0f", targetHeading, getHeading());
        getOpMode().telemetry.addData("Error  : Steer Pwr",  "%5.1f : %5.1f", headingError, turnSpeed);
        getOpMode().telemetry.addData("Wheel Speeds L : R", "%5.2f : %5.2f", leftSpeed, rightSpeed);
        getOpMode().telemetry.update();
    }
    /**
     * поиск направления робота в пространстве
     */
    public double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }

}
