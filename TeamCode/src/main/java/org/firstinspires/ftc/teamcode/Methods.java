package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

@Autonomous(name="Methods")
@Disabled

public class Methods extends LinearOpMode {
    public DcMotor TL, TR, BL, BR;
    public DcMotor lift;
    public Servo intakeL, intakeR;
    double tl, tr, bl, br;
    DigitalChannel digitalTouch;

    // imu
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    double servoPos = 0.6; // Стартовое положение Серво-моторов

    HardwareMap hwMap = null; // Конструктор
    ElapsedTime Timer = new ElapsedTime();

    public Methods(HardwareMap hardwareMap) { // Конструктор
        hwMap = hardwareMap;


//        intakeR = hwMap.servo.get("ssr");
//        intakeL = hwMap.servo.get("ssl");

        TL = hardwareMap.dcMotor.get("tl");
        TR = hardwareMap.dcMotor.get("tr");
        BL = hardwareMap.dcMotor.get("bl");
        BR = hardwareMap.dcMotor.get("br");
        lift = hardwareMap.dcMotor.get("lift");

        intakeL = hardwareMap.servo.get("ssl");
        intakeR = hardwareMap.servo.get("ssr");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        TL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        digitalTouch = hardwareMap.get(DigitalChannel.class, "butt");
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
    }

    /*
        Метод для управления кб в телеопе
        Принимает параметры:
        double x - передвижение по воображаемой оси x
        double y - передвижение по воображаемой оси y
        double r - вращение вокруг оси робота
        double k - коэффициент передаваемой на моторы мощности
     */

    public void resetEnc() {
        TL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    // IMU
    public void imuInit() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
    
    public double imuUpdate() {
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES);
        gravity  = imu.getGravity();

        return Double.parseDouble(formatAngle(angles.angleUnit,
                angles.firstAngle));
    }

    public void imuRotate(double targetAngle, double speed) {
        imuInit();
        while (imuUpdate() < targetAngle) {
            move(0, 0, -speed, 1);
        }
        stopMove();
        sleep(200);
        while (imuUpdate() > targetAngle) {
            move(0, 0, speed * 0.5, 1);
        }
        stopMove();
        sleep(200);
        while (imuUpdate() < targetAngle) {
            move(0, 0, -speed * 0.5, 1);
        }
        stopMove();
        sleep(200);
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

//    public AngleUnit imuGet() {
//        return(angles.angleUnit);
//    }

    public void move(double x, double y, double r, double k) {
        x = x * 0.6;
        y = y * k;
        r = r * 0.2;

        tl = x - y + r;
        tr = x + y + r;
        br = -x + y + r;
        bl = -x - y + r;

        TL.setPower(tl);
        TR.setPower(tr);
        BL.setPower(bl);
        BR.setPower(br);
    }

    public void moveEnc(double x, double y, double r, int tE) {
        tl = x - y + r;
        tr = x + y + r;
        br = -x + y + r;
        bl = -x - y + r;

        int stpos = TL.getCurrentPosition();
        while (/*linearOpMode.opModeIsActive() &&*/ ((Math.abs(Math.abs(TL.getCurrentPosition()) - Math.abs(stpos)) < tE)
                || (Math.abs(Math.abs(TR.getCurrentPosition()) - Math.abs(stpos)) < tE)
                || (Math.abs(Math.abs(BL.getCurrentPosition()) - Math.abs(stpos)) < tE)
                || (Math.abs(Math.abs(BR.getCurrentPosition()) - Math.abs(stpos)) < tE))) {
            TL.setPower(-tl);
            TR.setPower(-tr);
            BL.setPower(-bl);
            BR.setPower(-br);
        }
        stopMove();
        debugDelay();
    }

    public void moveTimer(double x, double y, double r, double time) {
        tl = x - y + r;
        tr = x + y + r;
        br = -x + y + r;
        bl = -x - y + r;

        TL.setPower(-tl);
        TR.setPower(-tr);
        BL.setPower(-bl);
        BR.setPower(-br);

        while (Timer.milliseconds() < time) {
        }
        stopMove();
    }

    public void stopMove() {
        TL.setPower(-TL.getPower());
        BL.setPower(-BL.getPower());
        TR.setPower(-TR.getPower());
        BR.setPower(-BR.getPower());
        Timer.reset();
        while (Timer.milliseconds() < 10) {
        }

        TL.setPower(0);
        BL.setPower(0);
        TR.setPower(0);
        BR.setPower(0);
    }

//    public void liftRun(double liftD) {
//        if (digitalTouch.getState()) {
//            lift.setPower(liftD);
//            telemetry.addData("Digital Touch", "Is not Pressed");
//        } else {
//            lift.setPower(0);
//            telemetry.addData("shit: ", lift.getCurrentPosition());
//            if (liftD > 0) {
//                lift.setPower(liftD);
//            }
//        }
//    }
//
//    public void liftEnc(int pos, double speed) {
//        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lift.setTargetPosition(pos);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        lift.setPower(speed);
//
//        while (opModeIsActive() && lift.isBusy()) {
//            telemetry.addData("lift is running", "at %f %%", lift.getCurrentPosition(), lift.getCurrentPosition() / lift.getTargetPosition() * 100);
//            telemetry.update();
//        }
        /*while (Math.abs(lift.getCurrentPosition()) < Math.abs(pos))   {
            lift.setPower(speed);

        }*/

//        lift.setPower(0);
//        sleep(100);
//    }
//
//    public void liftStep(int cm, double speed) {
//        liftEnc(cm * 80, speed);
//    }
//
//    public void liftDrop(double speed){
//        while (digitalTouch.getState()) {
//            liftRun(-speed);
//        }
//    }
//
//    public void liftTimer(int t, double speed) {
//        liftRun(speed);
//        sleep(t);
//        liftStop();
//    }
//
//    public void liftDrop() {
//        while (digitalTouch.getState()) {
//            liftRun(-1);
//        }
//        lift.setPower(0);
//    }
//
//    public void liftStop() {
//        lift.setPower(0);
//    }

    //   для корректного завершения исполняющегося метода
    public void debugDelay() {
        Timer.reset();
        while (Timer.milliseconds() < 10) {
        }
    }

//    public void servoRun(double servoPos) {
//        intakeL.setPosition(servoPos);
//        intakeR.setPosition(1.0 - servoPos);
//    }


    public void imuTelemetry() {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

        telemetry.addData("Heading", angles.firstAngle);
        telemetry.addData("Roll", angles.secondAngle);
        telemetry.addData("Pitch", angles.thirdAngle);
        telemetry.update();
    }

    @Override
    public void runOpMode(){}
}

