package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.FOUR;
import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.ONE;
import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.ZERO;

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
import org.firstinspires.ftc.teamcode.visions.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

@Disabled
@Autonomous(name = "AutoBlue2")
public class AutoBlue2 extends LinearOpMode {
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

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(18)
                .turn(90)
                .back(2)
                .turn(180)
                .forward(18)
                .addTemporalMarker(5, () -> {servobox.setPosition(0);})
                .build();

        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(18)
                .back(2)
                .turn(270)
                .forward(18)
                .build();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(18)
                .turn(270)
                .forward(18)
                .addTemporalMarker(5,() -> {servobox.setPosition(0);})
                .build();

        waitForStart();

        while (opModeIsActive()) {
            if (recognition.getAnalysis() == ZERO) {
                drive.followTrajectorySequence(traj1);
                telemetry.addLine("zone A");
                telemetry.update();
            }
            if (recognition.getAnalysis() == ONE) {
                drive.followTrajectorySequence(traj2);
                telemetry.addLine("zone B");
                telemetry.update();
            }
            if (recognition.getAnalysis() == FOUR) {
                drive.followTrajectorySequence(traj3);
                telemetry.addLine("zone C");
                telemetry.update();
            }

            telemetry.addData("position is ", recognition.getAnalysis());
            telemetry.addData("avg1 is ", recognition.getAvgs()[0]);
            telemetry.addData("avg 2 is", recognition.getAvgs()[1]);
            telemetry.addData("black avg is", recognition.getAvgs()[2]);
        }
    }
}
