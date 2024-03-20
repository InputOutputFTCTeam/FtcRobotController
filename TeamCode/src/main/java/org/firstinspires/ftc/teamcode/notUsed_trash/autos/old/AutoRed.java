package org.firstinspires.ftc.teamcode.notUsed_trash.autos.old;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.notUsed_trash.RoadRunnerMethods.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.notUsed_trash.RoadRunnerMethods.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Capture;

@Disabled
@Autonomous(name = "AutoRed", group = "Actual")
public class AutoRed extends LinearOpMode {
    DcMotor TR, TL, BR, BL, Intake;
    Servo servobox, lohotronMain, lohotron, zahvat, drop2, drop1, leftHook1, rightHook1;

    double INTAKE_SPEED = 0.7;
    public void armRaise(){
        lohotronMain.setPosition(0.9); //lohotronMain - подымает всю палку
        sleep(100);
        lohotron.setPosition(1);
    }
    public void armLower(){
        lohotronMain.setPosition(0);
        sleep(50);
        lohotron.setPosition(0);
    }

    Capture pixel = new Capture(this);

    @Override
    public void runOpMode() {

        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        Intake = hardwareMap.dcMotor.get("intake");


        servobox = hardwareMap.servo.get("servobox");
        //lohotronMain = hardwareMap.servo.get("lohotronMain");
        //lohotron = hardwareMap.servo.get("lohotron");
        //zahvat = hardwareMap.servo.get("zahvat");
        drop2 = hardwareMap.servo.get("drop2");
        drop1 = hardwareMap.servo.get("drop1");
        rightHook1 = hardwareMap.servo.get("rightHook1");
        leftHook1 = hardwareMap.servo.get("leftHook1");

        pixel.initLohotron();

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //тута штуки всяике тестятся
        ///!протестить и исправить если надо!!

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d())

                .strafeRight(50) //к линии        //проезды задаются непонятной системой мер forward - вперед, back - назад, strafeRight/Left - стрейфить
                .build();

        TrajectorySequence traj1_1 = drive.trajectorySequenceBuilder(new Pose2d())
                .strafeLeft(10)
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
