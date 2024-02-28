package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotModules.Sensored.GigaChadDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.ColorSensorModule;

public class G_Blue2 extends LinearOpMode {
    GigaChadDriveTrain robot = new GigaChadDriveTrain(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initGigaChad();

        if (opModeIsActive()) {
            robot.colorRun(0, 0.5, 0, ColorSensorModule.colorsField.BLUE);

            telemetry.addLine("to the RIGHT");
            telemetry.update();
            robot.encoderRun(0.5, 0, 1000);sleep(1000);

            telemetry.addLine("to the FORWARD");
            telemetry.update();
            robot.encoderRun(0, 0.5, 1000);
            sleep(1000);

            telemetry.addLine("to the LEFT");
            telemetry.update();
            robot.encoderRun(0.5, 0, -1000);
            sleep(1000);

            telemetry.addLine("to the BACKWARD");
            telemetry.update();
            robot.encoderRun(0, 0.5, -1000);

            telemetry.addLine("FREEE BIIIIRDDD YEAH");
            telemetry.update();
            robot.imuTurn(0.5, 90);
            robot.imuTurn(0.5, 90);
            robot.imuTurn(0.5, 90);
            robot.imuTurn(0.5, 90);
        }
    }
}
