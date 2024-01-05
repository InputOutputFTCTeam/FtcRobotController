package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name="TeleOp1")
public class TeleOpSoft extends LinearOpMode {
    public DcMotor TL, TR, BL, BR;
    public Servo Servo1; // верт (15, -90), гориз (-15, 60)
    public CRServo CR;
    double tl, tr, bl, br;

    @Override
    public void runOpMode() {
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        Servo1 = hardwareMap.servo.get("servo1");
        CR = hardwareMap.crservo.get("cr");  //

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Servo1.setPosition(0.667);
        Move(0,0,0,0);

        telemetry.addLine("Ready to start");
        waitForStart();
        while (opModeIsActive()) {
            Move(
                    gamepad1.right_stick_x,
                    gamepad1.right_stick_y,
                    gamepad1.right_trigger - gamepad1.left_trigger,
                    1
            );
            if (gamepad2.dpad_up) {
                Servo1.setPosition(0.75);
            }
            if (gamepad2.dpad_right) {      //поставить соответствующие кнопки
                Servo1.setPosition(0.167);  //поменять на соответствующее горизонтальному положению (0 - вертикаль, (0.667)* - опущено до пола) *-настроить
            }
            if (gamepad2.dpad_down) {
                Servo1.setPosition(0);
            }

            CR.setPower((gamepad2.right_trigger - gamepad2.left_trigger));

            CR.setPower(gamepad2.right_trigger - gamepad2.left_trigger);
            telemetry.addData("servo1", Servo1.getPosition());
            //telemetry.addData("servo2", Servo2.getPosition());
            telemetry.update();
        }
    }
    public void Move(double x, double y, double r, double k) {
        x = x * k;
        y = y * k;
        r = r * k;
        tl = x + y + r;
        tr = -x - y - r;
        bl = -x - y + r;
        br = -x - y - r;

        TL.setPower(-tl);
        TR.setPower(tr);
        BL.setPower(bl);
        BR.setPower(br);
    }
}
