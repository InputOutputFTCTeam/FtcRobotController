package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotModules.Sensored.IMUDriveTrain;


@Autonomous(name = "imuAuto")
public class imuAuto extends LinearOpMode {
    IMUDriveTrain imuDriveTrain = new IMUDriveTrain(this);

    @Override
    public void runOpMode() {
        imuDriveTrain.initIDT();

        waitForStart();

        if (opModeIsActive()){
            imuDriveTrain.turnToHeading(0.5, 90);
        }
    }
}
