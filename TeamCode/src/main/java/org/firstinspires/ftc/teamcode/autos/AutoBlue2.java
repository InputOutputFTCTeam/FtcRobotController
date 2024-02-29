package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.robotModules.Sensors.visions.Recognition.RingPosition.FOUR;
import static org.firstinspires.ftc.teamcode.robotModules.Sensors.visions.Recognition.RingPosition.ONE;
import static org.firstinspires.ftc.teamcode.robotModules.Sensors.visions.Recognition.RingPosition.ZERO;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.robotModules.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Box;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Intaker;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lohotron;
import org.firstinspires.ftc.teamcode.robotModules.Sensored.IMUDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.visions.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

@Disabled
@Autonomous(name = "AutoBlue2")
public class AutoBlue2 extends LinearOpMode {
    IMUDriveTrain imuDriveTrain = new IMUDriveTrain(this);
    BasicDriveTrain basicDriveTrain = new BasicDriveTrain(this);
    Lohotron pixel = new Lohotron(this);
    Box box = new Box(this);
    Intaker intake = new Intaker(this);

    //TODO: настроить дистанцию проезда
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
