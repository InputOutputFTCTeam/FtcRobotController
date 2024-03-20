package org.firstinspires.ftc.teamcode.notUsed_trash;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored.MegaDriveTrain;

@Autonomous(name = "g_blue2")
public class G_Blue2 extends LinearOpMode {
    MegaDriveTrain robot = new MegaDriveTrain(this);

    @Override
    public void runOpMode(){
        //robot = new GigaChadDriveTrain(this);
        robot.initMega();

        waitForStart();
        if (opModeIsActive()) {
            /*telemetry.addLine("spj 108");
            telemetry.update();

            robot.move(0, 1, 0);

            telemetry.addLine("storming");
            telemetry.update();
            sleep(10000);

            robot.move(0,0,0);

            telemetry.addLine("stormed");
            telemetry.update();
            sleep(1000);*/

            //robot.colorRun(0, 0.5, 0, ColorSensorModule.colorsField.BLUE);

            telemetry.addLine("to the RIGHT");
            telemetry.update();
            sleep(500);
            robot.encoderRun(1, 0, 1000);
            sleep(1000);

            /*telemetry.addLine("to the FORWARD");
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
            */
        }
    }
}
