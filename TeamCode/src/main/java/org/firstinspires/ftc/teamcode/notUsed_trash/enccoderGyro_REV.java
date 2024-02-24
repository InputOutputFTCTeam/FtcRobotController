package org.firstinspires.ftc.teamcode.notUsed_trash;

import com.qualcomm.hardware.bosch.BNO055IMUNew;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Disabled
@Autonomous(name = "ecnGyro_REV 90R_90L", group = "alfa")
public class enccoderGyro_REV extends enccoder{
    BNO055IMUNew imu = null;

    double tolerance = 5;
    double kp = 1;

    @Override
    public void runOpMode(){
        //create what hsa to be created in runOpMode (because we Override it actually)
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

        //initialize imu and co.
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu = hardwareMap.get(BNO055IMUNew.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        while(!isStarted()){
            telemetry.addData("now heading: ", getHeading());
            composeTelemetry();
            telemetry.update();
        }

        imu.resetYaw();

        waitForStart();
        if(opModeIsActive()){
            rotateByGyro(0.5, 90);
            sleep(1000);
            rotateByGyro(-0.5, -90); //sign must be the same.... либо выкручивайся (выкручивайся)

        }
    }

    //same logic as in enccoderGyro_BNO
    public void rotateByGyro(double r, double degrees){
        double startHeading = getHeading();
        while(opModeIsActive() && Math.abs(degrees - startHeading) > tolerance){
            move(0, 0, r);
            telemetry.addData("start heading: ", startHeading);
            telemetry.addData("now heading: ", getHeading());
            telemetry.addData("target heading: ", startHeading + degrees);
        }
        stopp();

        telemetry.addLine();
        telemetry.addLine();
        composeTelemetry();
        telemetry.update();
    }

    public double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }
}
