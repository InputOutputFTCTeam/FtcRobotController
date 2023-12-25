package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "AutoRed", group = "Actual")
public class AutoRed extends LinearOpMode {
    public DcMotor TL, TR, BL, BR, Intake;
    public Servo Servo1, Servo2;

    @Override
    public void runOpMode() {
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);

        Servo1 = hardwareMap.servo.get("servo1");
        Servo2 = hardwareMap.servo.get("servo2");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        Servo1.setPosition(0.7);
        Servo2.setPosition(0);

        SampleMecanumDrive driveBlue = new SampleMecanumDrive(hardwareMap);
        Trajectory trajBlue = driveBlue.trajectoryBuilder(new Pose2d())
                .forward(10)
                .build();

        telemetry.addLine("Ready to start");
        waitForStart();
        if (opModeIsActive()) {
            driveBlue.followTrajectory(trajBlue);
        }
    }
}
