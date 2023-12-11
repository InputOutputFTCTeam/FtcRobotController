package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "TeleOp")
public class TeleOpi extends LinearOpMode {
    DcMotor TR, TL, BR, BL;
    Servo s1, s2;
    CRServo cr;

    double x, y, r;

    @Override
    public void runOpMode(){
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        s1 = hardwareMap.servo.get("servo1");
        s2 = hardwareMap.servo.get("servo2");
        cr = hardwareMap.crservo.get("cr");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        s1.setPosition(0);
        s2.setPosition(0);
        telemetry.addLine("Ready to start");

        waitForStart();
        while(opModeIsActive()){
            x = gamepad1.right_stick_x;
            y = -gamepad1.right_stick_y;
            r = (gamepad1.right_trigger - gamepad1.left_trigger);

            TR.setPower(-x-y+r);
            BR.setPower(x-y+r);
            BL.setPower(x+y+r);
            TL.setPower(-x+y+r);

            if (gamepad2.dpad_up) {
                s1.setPosition(0.75);
            }

            if (gamepad2.dpad_right) {
                s1.setPosition(0.167);
            }

            if (gamepad2.dpad_down) {
                s1.setPosition(0);
            }

            if (gamepad2.a) {
                s2.setPosition(0.5);
            }

            cr.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
            telemetry.addData("servo1", s1.getPosition());
            telemetry.update();
        }
    }
}
