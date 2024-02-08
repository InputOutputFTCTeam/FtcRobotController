package org.firstinspires.ftc.teamcode.teleops;

import android.renderscript.ScriptGroup;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.visions.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


@TeleOp(name = "TeleOp")
public class TeleOpi extends LinearOpMode {
    DcMotor TR, TL, BR, BL, Intake, Lift;
    Servo servobox, lohotronMain, lohotron, claw,
            drop1, drop2,   //задний захват
            angle, push,    //самолетопускатель
            rightHook1, rightHook2, //правый крюк
            leftHook1, leftHook2;   //левый крюк

    protected Recognition recognition;
    OpenCvCamera webcam;

    double x, y, r;     //переменные направления движения
    double INTAKE_SPEED = 0.4;  //скорость вращения захвата         ("он очень резвый. мне нравится" (c) Николай Ростиславович)



    public void Hook() {
        rightHook2.setPosition(0.5);
    }

    public void armLower() {//0, m0
        lohotronMain.setPosition(0);
        sleep(100);
        lohotron.setPosition(1);
        claw.setPosition(0);
    }

    public void armRaise() { //m0.5 1
        lohotronMain.setPosition(0.5);
        sleep(100);
        lohotron.setPosition(0);
    }

    public void armMiddle() {//0.6 m0.3

    }

    @Override

    public void runOpMode() {

        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        Intake = hardwareMap.dcMotor.get("intake");
        Lift = hardwareMap.dcMotor.get("lift");

        servobox = hardwareMap.servo.get("servobox");
        rightHook2 = hardwareMap.servo.get("rightHook2");
        lohotron = hardwareMap.servo.get("lohotron");
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        claw = hardwareMap.servo.get("zahvat");
        drop1 = hardwareMap.servo.get("drop1");
        drop2 = hardwareMap.servo.get("drop2");
        angle = hardwareMap.servo.get("angle");
        push = hardwareMap.servo.get("push");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Lift.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Ready to start");
        waitForStart();
        while (opModeIsActive()) {
            x = -gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            r = (gamepad1.right_trigger - gamepad1.left_trigger);

            TR.setPower(-x-y+r);
            BR.setPower(x-y+r);
            BL.setPower(x+y+r);
            TL.setPower(-x+y+r);

            if (gamepad1.dpad_right) {
                drop2.setPosition(0);    //серва коробки поднимается в горизонтальное положение
            }

            if (gamepad1.dpad_left) {
                drop2.setPosition(0.55);    //серва коробки поднимается в горизонтальное положение
            }

            if (gamepad1.dpad_up) {
                drop1.setPosition(0);    //серва коробки поднимается в горизонтальное положение
            }

            if (gamepad1.dpad_down) {
                drop1.setPosition(0.65);    //серва коробки поднимается в горизонтальное положение
            }

            if (gamepad1.a) {
                Hook();//с
            }

            if (gamepad1.x) {
                push.setPosition(0.65);//с
            }
            if (gamepad1.y) {
                angle.setPosition(0.92);
            }
            if (gamepad2.dpad_right) {
                servobox.setPosition(0.55);    //серва коробки поднимается в горизонтальное положение
            }
            if (gamepad2.dpad_up) {
                servobox.setPosition(0.005);    //серва косается земли
            }
            if (gamepad2.dpad_down) {
                servobox.setPosition(0.7);
            }

            //Lift.setPower(-gamepad2.left_stick_y*0.6);    //выясним потом куда будет поднимать или опускать

            if (gamepad2.y) {
                armRaise();     //переворот захвата
            }

            if (gamepad2.a) {
                armLower();     //опускает лохотрон
            }

            if (gamepad2.b) {
                armMiddle();
            }

            /*if (gamepad2.left_bumper) {   //отпускает
                zahvat.setPosition(0.6);
            }

            if (gamepad2.right_bumper) {     //захватывает
                zahvat.setPosition(0);
            }*/

            Intake.setPower((gamepad2.left_trigger - gamepad2.right_trigger) * INTAKE_SPEED);

            telemetry.addData("angle", angle.getPosition());
            telemetry.addData("servo1", servobox.getPosition());
            telemetry.update();


        }
    }
}
