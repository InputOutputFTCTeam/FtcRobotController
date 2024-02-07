package org.firstinspires.ftc.teamcode.teleops;

import android.renderscript.ScriptGroup;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "PushTest")
public class PushTest extends LinearOpMode {
    Servo up, Airplane;

    @Override
    public void runOpMode(){


        up = hardwareMap.servo.get("Up");
        Airplane = hardwareMap.servo.get("Airplane");

        telemetry.addLine("Ready to start");
        waitForStart();
        while(opModeIsActive()) {

            if (gamepad2.y) {
                Airplane.setPosition(0);
            }

            if (gamepad2.a) {
                Airplane.setPosition(1.0);     //переворот захвата
            }

            telemetry.addData("up position: ", up.getPosition());

            telemetry.addData("Airplane position: ", Airplane.getPosition());

            telemetry.update();

        }
    }
}
