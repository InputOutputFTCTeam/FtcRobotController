package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 *  Этот телеоп проверяет работоспособность геймпада. Проверяем значения, получаемые из геймпада
 *  через вывод в телеметрию.
 */
@Disabled
@TeleOp(name = "telemetry")
public class TelemetryOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            //double тип данных.
            telemetry.addData("left_stick_y", gamepad1.left_stick_y);
            telemetry.addData("left_stick_x", gamepad1.left_stick_x);
            telemetry.addData("right_stick_y", gamepad1.right_stick_y);
            telemetry.addData("right_stick_x", gamepad1.right_stick_x);
            telemetry.addData("right_trigger", gamepad1.right_trigger);
            telemetry.addData("left_trigger", gamepad1.left_trigger);
            telemetry.addData("right_bumper", gamepad1.right_bumper);
            telemetry.addData("left_bumper", gamepad1.left_bumper);

            //boolean тип данных.
            //может быть лучший метод сделать отдельный вывод состояния кнопки, но мы его не знаем :)
            if(gamepad1.a) telemetry.addLine("'a' is pressed");
            if(gamepad1.b) telemetry.addLine("'b' is pressed");
            if(gamepad1.x) telemetry.addLine("'x' is pressed");
            if(gamepad1.y) telemetry.addLine("'y' is pressed");

            if(gamepad1.dpad_down)  telemetry.addLine("'dpad_down' is pressed");
            if(gamepad1.dpad_right) telemetry.addLine("'dpad_right' is pressed");
            if(gamepad1.dpad_left)  telemetry.addLine("'dpad_left' is pressed");
            if(gamepad1.dpad_up)    telemetry.addLine("'dpad_up' is pressed");

            if(gamepad1.right_stick_button) telemetry.addLine("'right_stick_button' is pressed");
            if(gamepad1.left_stick_button)  telemetry.addLine("'left_stick_button' is pressed");

            if(gamepad1.right_bumper)   telemetry.addLine("'right_bumper' is pressed");
            if(gamepad1.left_bumper)    telemetry.addLine("'left_bumper' is pressed");

            telemetry.update();
        }
    }

}
