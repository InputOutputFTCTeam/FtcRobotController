package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleTraining" , group = "a")
public class TrainingTeleOp extends LinearOpMode {

    DcMotor TR, TL, BR, BL;
    CRServo diver, grab, proba;

    double x, y, r;

    @Override
    public void runOpMode() {

        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        diver = hardwareMap.crservo.get("lift");
        grab = hardwareMap.crservo.get("grab");
        proba = hardwareMap.crservo.get("proba");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Ready to start");
        waitForStart();
        while (opModeIsActive()) {
            x = -gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            r = (gamepad1.right_trigger - gamepad1.left_trigger);

            TR.setPower(x+y-r);
            BR.setPower(-x+y-r);
            BL.setPower(x+y+r);
            TL.setPower(-x-y+r);

            if (gamepad1.x) {
                diver.setPower(0.74);    //серва коробки поднимается в горизонтальное положение
            }
            if (gamepad1.y) {
                diver.setPower(0.64);    //серва коробки поднимается в горизонтальное положение
            }
            if (gamepad1.b) {
                diver.setPower(0.29);    //серва коробки поднимается в горизонтальное положение
            }
            if (gamepad1.dpad_right) {
                proba.setPower(-0.16);    //серва коробки поднимается в горизонтальное положение
            }
            if (gamepad1.dpad_left) {
                proba.setPower(-0.59);    //серва коробки поднимается в горизонтальное положение
            }
            if (gamepad1.dpad_up) {
                grab.setPower(0.55);    //серва коробки поднимается в горизонтальное положение
            }
            if (gamepad1.dpad_down) {
                grab.setPower(-0.5);    //серва коробки поднимается в горизонтальное положение
            }


            telemetry.update();
        }

    }
}
