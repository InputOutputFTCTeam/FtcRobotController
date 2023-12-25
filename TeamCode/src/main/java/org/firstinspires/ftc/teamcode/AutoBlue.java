package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "AutoBlue", group = "Actual")
public class AutoBlue extends LinearOpMode {
    public DcMotor TL, TR, BL, BR, Intake;
    public Servo Servo1;



    @Override
    public void runOpMode() {
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        Intake = hardwareMap.dcMotor.get("intake");

        Servo1 = hardwareMap.servo.get("servo1");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        Servo1.setPosition(0.0);

        Pose2d startPose = new Pose2d(0, 0, 0);

        SampleMecanumDrive driveBlue = new SampleMecanumDrive(hardwareMap);
/*//        TrajectorySequence trajBlue = driveBlue.trajectorySequenceBuilder(startPose)
//                .forward(36)
//                .addTemporalMarker(3-+96-, () -> {Servo1.setPosition(0.75);})
//                .strafeLeft(30)
//                .back(25)
//                .addTemporalMarker(5, () -> {Servo1.setPosition(0);})
//                .build();
*/
        TrajectorySequence trjectoryBlue = driveBlue.trajectorySequenceBuilder(startPose)
                        .forward(36)
                                .addTemporalMarker(-0.5,() -> {Servo1.setPosition(0.75);})
                                        .addTemporalMarker(-0.3, () -> {Servo1.setPosition(-0.167);})
                                                .addTemporalMarker(-0.1, () -> {Servo1.setPosition(0);})
                                                        .back(18)
                                                                .turn(90)
                                                                        .forward(36)
                                                                            .build();

        telemetry.addLine("Ready to start");
        waitForStart();
        if (opModeIsActive()) {
            driveBlue.followTrajectorySequence(trjectoryBlue);
        }
    }
}
