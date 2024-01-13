package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
//автоном чтобы тестить сервы!
@TeleOp(name = "servelat")
public class servotest extends LinearOpMode {
    DcMotor Intake, Lift;
    Servo servobox, lohotronMain, lohotron, zahvat;
    double x, y, r;
    double INTAKE_SPEED = 0.7;
    public void armRaise(){
        lohotronMain.setPosition(0.8);
        sleep(100);
        lohotron.setPosition(1);
    }
    public void armLower(){
        lohotron.setPosition(0);
        sleep(50);
        lohotronMain.setPosition(0);
    }

    @Override
    public void runOpMode(){
        servobox = hardwareMap.servo.get("servobox");
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        lohotron = hardwareMap.servo.get("lohotron");
        zahvat = hardwareMap.servo.get("zahvat");

        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Lift.setDirection(DcMotorSimple.Direction.FORWARD);

        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servobox.setPosition(0);
        telemetry.addLine("Ready to start");
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad2.y) {
                armRaise();     //переворот захвата
            }

            if (gamepad2.a) {
                armLower();     //опускает лохотрон
            }

            if (gamepad2.dpad_down) {   //отпускает
                zahvat.setPosition(0.45);
            }

            if (gamepad2.dpad_up) {     //захватывает
                zahvat.setPosition(0);
            }

            Intake.setPower((gamepad2.left_trigger - gamepad2.right_trigger) * INTAKE_SPEED);

            telemetry.addData("servobox", servobox.getPosition());
            telemetry.addData("lohotronMain", servobox.getPosition());
            telemetry.addData("lohotron", servobox.getPosition());
            telemetry.addData("zahvat", servobox.getPosition());
            telemetry.update();
        }
    }
}
