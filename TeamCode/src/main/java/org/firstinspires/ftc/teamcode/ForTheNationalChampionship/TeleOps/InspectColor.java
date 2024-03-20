package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors.ColorSensorModule;

@TeleOp
public class InspectColor extends LinearOpMode {
    private ColorSensorModule clr = new ColorSensorModule(this);
    private BasicDriveTrain dt = new BasicDriveTrain(this);

    @Override
    public void runOpMode() throws InterruptedException{
        clr.initColorSensor();
        double gain = 30;

        waitForStart();
        while (opModeIsActive()) {
            dt.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);
            if (gamepad1.a) {
                gain += 0.01;
                clr.setCSGain(gain);
            }
            if (gamepad1.b) {
                gain -=0.01;
                clr.setCSGain(gain);
            }
            clr.telemetryColor();
            telemetry.update();
        }
    }
}
