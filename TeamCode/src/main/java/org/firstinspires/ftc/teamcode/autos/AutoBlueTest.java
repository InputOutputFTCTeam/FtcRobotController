package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.robotModules.Basic.BackupCatch;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lohotron;
import org.firstinspires.ftc.teamcode.robotModules.Sensored.IMUDriveTrain;

//TODO: AutoBlue2

@Autonomous(name = "AutoBlueTest", group = "Actul")
public class AutoBlueTest extends LinearOpMode {
    DcMotor TR, TL, BR, BL;

    BackupCatch pixel = new BackupCatch(this);
    IMUDriveTrain idt = new IMUDriveTrain(this);
    Lohotron lohotron = new Lohotron(this);


    @Override
    public void runOpMode() {
        idt.initIDT();
        lohotron.initLohotron(this.hardwareMap);

        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        pixel.initBack();

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //тута штуки всяике тестятся

        ///!протестить и исправить если надо!!

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d())

                .back(31) //к линии        //проезды задаются непонятной системой мер forward - вперед, back - назад, strafeRight/Left - стрейфить
                .build();

        TrajectorySequence traj1_1 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(27)
                .build();

        TrajectorySequence traj1_2 = drive.trajectorySequenceBuilder(new Pose2d())
                .turn(0.192)
                .build();

        TrajectorySequence traj1_3 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(120)
                .build();

        TrajectorySequence traj1_4 = drive.trajectorySequenceBuilder(new Pose2d())
                .turn(-0.192)
                .build();

        TrajectorySequence traj1_5 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(15)
                .build();

        TrajectorySequence traj1_6 = drive.trajectorySequenceBuilder(new Pose2d())
                .turn(0.192)
                .build();

        TrajectorySequence traj1_7 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(10)
                .build();

        TrajectorySequence traj1_8 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(10)
                .build();

        TrajectorySequence traj1_9 = drive.trajectorySequenceBuilder(new Pose2d())
                .strafeRight(40)
                .build();

        TrajectorySequence traj_costil = drive.trajectorySequenceBuilder(new Pose2d())
                .turn(0)
                .build();

        TrajectorySequence traj1_10 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(15)
                .build();

        pixel.ungrab();


        waitForStart();

        if (opModeIsActive()) {
            drive.followTrajectorySequence(traj1);

            pixel.grab();

            drive.followTrajectorySequence(traj1_1);

            pixel.ungrab();

            drive.followTrajectorySequence(traj1_2);
            /*idt.initIDT();
            idt.turnToHeading(0.7, -90);*/

            drive.followTrajectorySequence(traj1_3);

            drive.followTrajectorySequence(traj1_4);

            drive.followTrajectorySequence(traj1_5);

            drive.followTrajectorySequence(traj1_6);

            sleep(500);

            lohotron.armRaiser();
            //чуть медленно взад
            drive.followTrajectorySequence(traj1_7);

            lohotron.openClaw();
            //вперед немного
            drive.followTrajectorySequence(traj1_8);

            //в бок
            drive.followTrajectorySequence(traj1_9);

            //назад парковка
            drive.followTrajectorySequence(traj1_10);
            drive.followTrajectorySequence(traj_costil);
        }
    }
}