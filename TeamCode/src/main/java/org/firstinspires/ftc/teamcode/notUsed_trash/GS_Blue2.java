package org.firstinspires.ftc.teamcode.notUsed_trash;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors.ColorSensorModule;

@Disabled
@Autonomous
public class GS_Blue2 extends LinearOpMode {
    //create Simplified Chad
    SimplifiedChadDriveTrain robot = null;

    @Override
    public void runOpMode() {
        //init Chad
        robot = new SimplifiedChadDriveTrain(this);
        robot.initSimple();

        waitForStart();
        if (opModeIsActive()) {
            telemetry.addLine("spj 108");
            telemetry.update();

            robot.move(0, 1, 0);

            telemetry.addLine("storming");
            telemetry.update();
            sleep(10000);

            robot.move(0,0,0);

            telemetry.addLine("stormed");
            telemetry.update();
            sleep(1000);

            robot.colorRun(0, 1, 0, ColorSensorModule.colorsField.BLUE);

            telemetry.addLine("to the RIGHT");
            telemetry.update();
            sleep(500);
            /*robot.encoderRun(0.5, 0, 1000);sleep(1000);

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

            telemetry.addLine("1FREEE BIIIIRDDD YEAH");
            telemetry.update();
            robot.imuTurn(0.5, 90);
            telemetry.addLine("2FREEE BIIIIRDDD YEAH");
            telemetry.update();
            robot.imuTurn(0.5, 90);
            telemetry.addLine("3FREEE BIIIIRDDD YEAH");
            telemetry.update();
            robot.imuTurn(0.5, 90);
            telemetry.addLine("4FREEE BIIIIRDDD YEAH");
            telemetry.update();
            robot.imuTurn(0.5, 90);

            */
        }
    }
}
