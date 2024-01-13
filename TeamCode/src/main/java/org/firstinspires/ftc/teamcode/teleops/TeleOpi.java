package org.firstinspires.ftc.teamcode.teleops;

import android.renderscript.ScriptGroup;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "TeleOp")
public class TeleOpi extends LinearOpMode {
    DcMotor TR, TL, BR, BL, Intake, Lift;
    Servo servobox, lohotronMain, lohotron, zahvat;

    double x, y, r;     //переменные направления движения
    double INTAKE_SPEED = 0.7;  //скорость вращения захвата         ("он очень резвый. мне нравится" (c) Николай Ростиславович)
    public void armRaise(){
        lohotronMain.setPosition(0.85);
        sleep(100);
        lohotron.setPosition(1);
    }
    public void armLower(){
        lohotron.setPosition(0);
        sleep(50);
        lohotronMain.setPosition(0);
    }
    public void armMiddle(){
        lohotron.setPosition(0.6);
        sleep(50);
        lohotronMain.setPosition(0.5);
    }

    @Override
    public void runOpMode(){
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        Intake = hardwareMap.dcMotor.get("intake");
        Lift = hardwareMap.dcMotor.get("lift");

        servobox = hardwareMap.servo.get("servobox");
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        lohotron = hardwareMap.servo.get("lohotron");
        zahvat = hardwareMap.servo.get("zahvat");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
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
        while(opModeIsActive()){

            x = gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            r = (gamepad1.right_trigger - gamepad1.left_trigger);

            TR.setPower((-x-y+r)*(-1));
            BR.setPower((x-y+r)*(-1));
            BL.setPower(x+y+r);
            TL.setPower(-x+y+r);

            if (gamepad2.dpad_right) {
                servobox.setPosition(0.55);    //серва коробки поднимается в горизонтальное положение
            }

            if (gamepad2.dpad_up) {
                servobox.setPosition(0.05);    //серва косается земли
            }
            if (gamepad2.dpad_down) {
                servobox.setPosition(0.75);
            }

            Lift.setPower(-gamepad2.left_stick_y);    //выясним потом куда будет поднимать или опускать

            if (gamepad2.x) {
                armRaise();     //переворот захвата
            }

            if (gamepad2.a) {
                armLower();     //опускает лохотрон
            }

            if (gamepad2.b) {
                armMiddle();
            }

            if (gamepad2.left_bumper) {   //отпускает
                zahvat.setPosition(0.5);
            }

            if (gamepad2.right_bumper) {     //захватывает
                zahvat.setPosition(0);
            }

            Intake.setPower((gamepad2.left_trigger - gamepad2.right_trigger) * INTAKE_SPEED);

            telemetry.addData("servo1", servobox.getPosition());
            telemetry.update();


        }
    }
}
