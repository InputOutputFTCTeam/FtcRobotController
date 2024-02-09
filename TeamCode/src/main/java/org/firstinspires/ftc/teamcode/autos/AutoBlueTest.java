package org.firstinspires.ftc.teamcode.autos;



import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.FOUR;

import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.ONE;

import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.ZERO;



import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;



import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.visions.Recognition;

import org.openftc.easyopencv.OpenCvCamera;

import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


@Autonomous(name = "AutoBlueTest", group = "Actul")

public class AutoBlueTest extends LinearOpMode{
    DcMotor TR, TL, BR, BL;

    Servo servobox, lohotronMain, lohotron, zahvat, drop2, drop1;





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
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        lohotron = hardwareMap.servo.get("lohotron");
        zahvat = hardwareMap.servo.get("zahvat");
        drop2 = hardwareMap.servo.get("drop2");
        drop1 = hardwareMap.servo.get("drop1");



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
                .back(33) //к линии        //проезды задаются непонятной системой мер forward - вперед, back - назад, strafeRight/Left - стрейфить
                .build();

        TrajectorySequence traj1_2 = drive.trajectorySequenceBuilder(new Pose2d())
                .turn(0.270)
                .back(50)

                        .build();

        drop2.setPosition(0.6);
        drop1.setPosition(0.6);

        waitForStart();


        if (opModeIsActive()) {
            //drive.followTrajectorySequence(traj1);
            drop2.setPosition(0);
            drive.followTrajectorySequence(traj1_2);





            sleep(1000);





        }
    }

}