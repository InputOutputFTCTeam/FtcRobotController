package org.firstinspires.ftc.teamcode.autos;



import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.robotModules.Lohotron;


@Autonomous(name = "AutoBlueTest", group = "Actul")

public class AutoBlueTest extends LinearOpMode{
    DcMotor TR, TL, BR, BL;

    Servo servobox, lohotronMain, lohotron, zahvat, drop2, drop1, leftHook1, rightHook1;

    Lohotron pixel = new Lohotron(this);


    double INTAKE_SPEED = 0.7;

    public void armRaise(){
        lohotronMain.setPosition(0.9); //lohotronMain - подымает всю палку
        sleep(100);
        lohotron.setPosition(1); //lohotron - серва на захвате у лохотрона0

    }

    public void armLower(){
        lohotronMain.setPosition(0);
        sleep(50);
        lohotron.setPosition(0);

    }

    public void armMiddle(){
        lohotron.setPosition(0.6);
        sleep(50);
        lohotronMain.setPosition(0.5);
    }

    @Override

    public void runOpMode() {
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        servobox = hardwareMap.servo.get("servobox");
        //lohotronMain = hardwareMap.servo.get("lohotronMain");
        //lohotron = hardwareMap.servo.get("lohotron");
        //zahvat = hardwareMap.servo.get("zahvat");
        drop2 = hardwareMap.servo.get("drop2");
        drop1 = hardwareMap.servo.get("drop1");
        rightHook1 = hardwareMap.servo.get("rightHook1");
        leftHook1 = hardwareMap.servo.get("leftHook1");

        pixel.initLohotron(hardwareMap);

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

                .strafeLeft(50) //к линии        //проезды задаются непонятной системой мер forward - вперед, back - назад, strafeRight/Left - стрейфить
                .build();

        TrajectorySequence traj1_1 = drive.trajectorySequenceBuilder(new Pose2d())
                .strafeRight(10)
                .build();

        TrajectorySequence traj1_2 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(40)
                .build();

        TrajectorySequence traj1_3 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(9)
                .build();

        pixel.closeClaw();
        servobox.setPosition(0.5);

        waitForStart();

        if (opModeIsActive()) {
            drive.followTrajectorySequence(traj1);

            drive.followTrajectorySequence(traj1_1);

            drive.followTrajectorySequence(traj1_2);

            pixel.armRaiser();

            sleep(1000);

            pixel.openClaw();

            sleep(1000);



            drive.followTrajectorySequence(traj1_3);

            sleep(1000);
        }
    }
}