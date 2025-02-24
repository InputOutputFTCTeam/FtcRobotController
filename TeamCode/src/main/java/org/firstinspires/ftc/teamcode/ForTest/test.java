package org.firstinspires.ftc.teamcode.ForTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "TeleOp with BNO055IMU" , group = "TeleOps")
public class test extends LinearOpMode {

    DcMotor TR, TL, BR, BL, eL, eE;
    Servo proba, grab;
    BNO055IMU imu;

    double x, y, r, speed=1, z, w, pos=0.6, l;

    @Override
    public void runOpMode() {

        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        eL = hardwareMap.dcMotor.get("elevatorLifting");
        eE = hardwareMap.dcMotor.get("elevatorExtension");
        grab = hardwareMap.servo.get("grab");
        proba = hardwareMap.servo.get("proba");
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        eL.setDirection(DcMotorSimple.Direction.FORWARD);
        eE.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        eL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        eE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(parameters);

        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            telemetry.addLine("Calibrating IMU...");
            telemetry.update();
        }

        telemetry.addLine("Ready to start");
        waitForStart();
        while (opModeIsActive()) {
            y = -gamepad1.left_stick_x;
            x = -gamepad1.left_stick_y;
            Orientation angles = imu.getAngularOrientation();
            float heading = angles.firstAngle;

            r = gamepad1.right_trigger;
            l = gamepad1.left_trigger;

            // Применяем коррекцию гироскопа только при стрейфе
            if (l > 0) {
                l += heading * 0.01;
            }
            if (r > 0) {
                r -= heading * 0.01;
            }

            z = -gamepad2.left_stick_y;
            w = -gamepad2.right_stick_y;
            pos += (gamepad2.right_trigger - gamepad2.left_trigger) * 0.001;

            if(gamepad1.x){
                speed = 1;
            }

            if(gamepad1.b){
                speed = 0.6;
            }

            if(gamepad1.y){
                speed = 0.8;
            }
            if(gamepad1.b){
                speed = 0.4;
            }

            if (gamepad2.y) {
                proba.setPosition(0.85); //разжата
            }
            if (gamepad2.a) {
                proba.setPosition(1); //сжата
            }
            TR.setPower(((y + x) * speed) - r + l);
            BR.setPower(((y - x) * speed) + r - l);
            BL.setPower(((y + x) * speed) + r - l);
            TL.setPower(((y - x) * speed) - r + l);
            eL.setPower(z);
            eE.setPower(w);
            grab.setPosition(pos);

            telemetry.addData("TR power:", TR.getPower());
            telemetry.addData("BR power:", BR.getPower());
            telemetry.addData("BL power:", BL.getPower());
            telemetry.addData("TL power:", TL.getPower());
            telemetry.addData("eL power:", eL.getPower());
            telemetry.addData("eE power:", eE.getPower());
            telemetry.addData("IMU Heading:", heading);

            telemetry.update();
        }
    }
}

