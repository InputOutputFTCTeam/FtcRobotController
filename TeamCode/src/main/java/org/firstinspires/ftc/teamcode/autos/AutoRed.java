package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.visions.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

@Autonomous(name = "AutoRed", group = "Actual")
public class AutoRed extends LinearOpMode {
    DcMotor TR, TL, BR, BL, Intake, Lift;
    Servo servobox, lohotronMain, lohotron, zahvat;
    protected Recognition recognition;
    OpenCvCamera webcam;
    double INTAKE_SPEED = 0.7;
    public void armRaise(){
        lohotronMain.setPosition(0.267);
        lohotron.setPosition(1);
    }
    public void armLower(){
        lohotronMain.setPosition(0);
        lohotron.setPosition(0);
    }

    @Override
    public void runOpMode() {
        recognition = new Recognition();
        recognition.getAnalysis();
        webcam.openCameraDevice();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(recognition);

        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        Intake = hardwareMap.dcMotor.get("intake");
        Lift = hardwareMap.dcMotor.get("lift");

        servobox = hardwareMap.servo.get("servobox");
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        lohotron = hardwareMap.servo.get("lohotron");
        zahvat = hardwareMap.servo.get("zahvat");

        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Lift.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
