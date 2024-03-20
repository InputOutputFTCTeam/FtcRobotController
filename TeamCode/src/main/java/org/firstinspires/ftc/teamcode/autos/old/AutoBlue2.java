package org.firstinspires.ftc.teamcode.autos.old;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.notUsed_trash.Box;
import org.firstinspires.ftc.teamcode.notUsed_trash.Intaker;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.Capture;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensored.IMUDriveTrain;

@Disabled
@Autonomous(name = "AutoBlue2")
public class AutoBlue2 extends LinearOpMode {
    IMUDriveTrain imuDriveTrain = new IMUDriveTrain(this);
    BasicDriveTrain basicDriveTrain = new BasicDriveTrain(this);
    Capture pixel = new Capture(this);
    Box box = new Box(this);
    Intaker intake = new Intaker(this);

    @Override
    public void runOpMode() {
        imuDriveTrain.initIDT();
        basicDriveTrain.initMotors();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d()) //проезд до реквизитов
                .forward(9)
                .build();

        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(new Pose2d()) //проезд до задника
                .strafeLeft(50)
                .forward(10)
                .strafeRight(10)
                .build();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(new Pose2d()) //проезд до парковки
                .strafeRight(10)
                .forward(9)
                .build();
    }

    /* if (положение реквизита)  {traj1 -> imuDriveTrain.turnToHeading(градусная мера) -> traj2 -> сброс пикселя }
     * сделать так со всеми реквизитами */

}
