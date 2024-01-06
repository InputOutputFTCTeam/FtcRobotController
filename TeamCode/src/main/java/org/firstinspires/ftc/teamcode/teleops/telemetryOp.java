package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "telemetry")
public class telemetryOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("left_stick_y", gamepad1.left_stick_y);
            telemetry.addData("left_stick_x", gamepad1.left_stick_x);
            telemetry.addData("right_stick_y", gamepad1.right_stick_y);
            telemetry.addData("right_stick_x", gamepad1.right_stick_x);
            telemetry.addData("right_trigger", gamepad1.right_trigger);
            telemetry.addData("left_trigger", gamepad1.left_trigger);
            telemetry.addData("right_bumper", gamepad1.right_bumper);
            telemetry.addData("left_bumper", gamepad1.left_bumper);
            telemetry.update();
        }
    }

}
