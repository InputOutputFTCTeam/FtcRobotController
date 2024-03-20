package org.firstinspires.ftc.teamcode.notUsed_trash.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.notUsed_trash.RoadRunnerMethods.drive.SampleMecanumDrive;

@Disabled
@Autonomous(name="Auto")
public class AutoSoft extends LinearOpMode {
    public DcMotor TL, TR, BL, BR;
    double tl, tr, bl, br;
    public Servo Servo1, Servo2;
    @Override
    public void runOpMode() {
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        Servo1 = hardwareMap.servo.get("servo1");
        Servo2 = hardwareMap.servo.get("servo2");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Servo1.setPosition(0.7);
        Servo2.setPosition(0);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Trajectory traject = drive.trajectoryBuilder(new Pose2d())
                .forward(36)
                .build();

        telemetry.addLine("Ready to start");
        waitForStart();
            if (opModeIsActive()) {

                drive.followTrajectory(traject);
            }
    }
}
