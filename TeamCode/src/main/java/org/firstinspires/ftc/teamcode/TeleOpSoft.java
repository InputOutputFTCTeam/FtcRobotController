package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TeleOp")
public class TeleOpSoft extends LinearOpMode {
    public DcMotor TL, TR, BL, BR;
    public Servo Servo1, Servo2; // верт (15, -90), гориз (-15, 60)
    public CRServo CR;
    double tl, tr, bl, br;

    @Override
    public void runOpMode() {
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        Servo1 = hardwareMap.servo.get("servo1");  //
        Servo2 = hardwareMap.servo.get("servo2");  //
        CR = hardwareMap.crservo.get("cr");  //

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Servo1.setPosition(0.7);
        Servo2.setPosition(0);

        telemetry.addLine("Ready to start");
        waitForStart();
        while (opModeIsActive()) {
            Move(
                    gamepad1.left_stick_x,
                    gamepad1.left_stick_y,
                    gamepad1.right_trigger - gamepad1.left_trigger,
                    1
            );
            //ServoMove(gamepad2.right_stick_x, gamepad2.right_stick_y, 100);
            ServoMove(gamepad2.left_stick_x, gamepad2.right_stick_y + 0.70, 1);
//            if (gamepad2.dpad_up) {
//                Servo1.setPosition(Servo1.getPosition() + 0.05);
//            }
//            if (gamepad2.dpad_down) {
//                Servo1.setPosition(Servo1.getPosition() - 0.05);
//            }
//            if (gamepad2.dpad_left) {
//                Servo1.setPosition(Servo2.getPosition() + 0.05);
//            }
//            if (gamepad2.dpad_right) {
//                Servo1.setPosition(Servo2.getPosition() - 0.05);
//            }
            CR.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
            telemetry.addData("servo1", Servo1.getPosition());
            telemetry.addData("servo2", Servo2.getPosition());
            telemetry.update();
        }
    }
    public void Move(double x, double y, double r, double k) {
        x = x * k;
        y = y * k;
        r = r * 1;
        tl = x + y - r;
        tr = -x - y - r;
        bl = -x - y - r;
        br = -x - y + r;

        TL.setPower(-tl);
        TR.setPower(tr);
        BL.setPower(bl);
        BR.setPower(br);
    }
    public void ServoMove(double x, double y, int k) {
//        if (-0.5 <= Servo1.getPosition() && Servo1.getPosition() <= 0.15) {
//            Servo1.setPosition(Servo1.getPosition() + (y / k));
//        }
//        if (-0.15 <= Servo2.getPosition() && Servo2.getPosition() < 0.7) {
//            Servo2.setPosition(Servo2.getPosition() + (x / k));
//        }
//        Servo1.setPosition(Servo1.getPosition() + (y / k));
//        Servo2.setPosition(Servo2.getPosition() + (x / k));
        Servo1.setPosition(y);
        Servo2.setPosition(x);
    }
}
