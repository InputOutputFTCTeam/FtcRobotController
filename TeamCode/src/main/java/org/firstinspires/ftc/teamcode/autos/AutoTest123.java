package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Autotest123", group = "Actual")
public class AutoTest123 extends LinearOpMode {
    public DcMotor TL, TR, BL, BR, Intake;
    public Servo Servo1;
    public CRServo CR;
    @Override
    public void runOpMode() {
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        Intake = hardwareMap.dcMotor.get("intake");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake = hardwareMap.dcMotor.get("intake");

        Servo1 = hardwareMap.servo.get("servo1");
        CR = hardwareMap.crservo.get("cr");

        //тута штуки всяике тестятся

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d())
                        .forward(40)
                                .UNSTABLE_addTemporalMarkerOffset(3,()-> Servo1.setPosition(0))
                                        .UNSTABLE_addTemporalMarkerOffset(2, ()-> Servo1.setPosition(0.7))
                                                .UNSTABLE_addTemporalMarkerOffset(1,()-> Servo1.setPosition(0))
                                                        .waitSeconds(4)
                                                                .turn(Math.toRadians(90))
                                                                        .forward(40)
                                                                                .build();



        waitForStart();

        if (opModeIsActive()) {
            drive.followTrajectorySequence(traj);
        }
    }
}
