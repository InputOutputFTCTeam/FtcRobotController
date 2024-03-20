package org.firstinspires.ftc.teamcode.autos.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensored.IMUDriveTrain;

@Disabled
@Autonomous(name = "imuAuto")
public class imuAuto extends LinearOpMode {
    IMUDriveTrain imuDriveTrain = new IMUDriveTrain(this);

    @Override
    public void runOpMode() {
        imuDriveTrain.initIDT();

        waitForStart();

        if (opModeIsActive()){
            imuDriveTrain.turnToHeading(0.2, 90);
            sleep(1000);
            imuDriveTrain.turnToHeading(0.2, 90);
            sleep(1000);
            imuDriveTrain.turnToHeading(0.2, 0);
            sleep(1000);
            imuDriveTrain.turnToHeading(0.2, -90);
        }
    }
}
