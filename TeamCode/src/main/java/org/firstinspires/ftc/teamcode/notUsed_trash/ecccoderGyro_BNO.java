package org.firstinspires.ftc.teamcode.notUsed_trash;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;

import java.util.Locale;

@Disabled
@Autonomous(name = "encGyro_BNO 90R_90L", group = "alfa")
public class ecccoderGyro_BNO extends enccoder{
    //initialize imu
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    double targetHeading = 0;   //целевое значение поворота
    double tolerance = 5;       //предельное отклонение в разнице между целевым и реальным положениями

    @Override
    public void runOpMode(){
        //create what has to be created in runOpMode (because we Override it actually)
        //тут началась инициализация...

        //записываем моторы и сервы для проверки конфигурационным файлом (а может быть можно создать xml файл и разметить в нем конфигурацию, чтобы никогда не приходилось ее настраивать???)
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        //задаем моторам направление вращения
        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        //включаем режим работы по энкодерам. все провода должны быть подкючены
        TL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //create imu and co. for config
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample OpMode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //было бы здорово понимать, что происходит с данными робота и геймпадов, пока кнопка старт не была нажата
        while(!isStarted()){
            telemetry.addData("now heading: ", angles.firstAngle);
            composeTelemetry();
            telemetry.update();
            idle();
        }

        waitForStart();
        if(opModeIsActive()){
            rotateByGyro(0.5, 90);
            sleep(1000);
            rotateByGyro(-0.5, 90);
        }
    }

    public void rotateByGyro(double r, int degrees){
        double startHeading = angles.firstAngle;
        while(opModeIsActive() && Math.abs(degrees - startHeading) > tolerance){
            move(0, 0, r);
            telemetry.addData("start heading: ", startHeading);
            telemetry.addData("now heading: ", angles.firstAngle);
        }
        stopp();

        telemetry.addLine();
        telemetry.addLine();
        composeTelemetry();
        telemetry.update();
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
