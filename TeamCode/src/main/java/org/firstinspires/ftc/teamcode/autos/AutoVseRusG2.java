package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcontroller.EOCVSamples.PhantomSamples.Methods_for_OpenCV;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.Catch;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.Capture;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensored.IMUDriveTrain;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous( name = "VseRusG2", group = "Actul")
public class AutoVseRusG2 extends LinearOpMode {
    private static int valLeft = -1;
    private static int valRight = -1;
    public OpenCvWebcam phoneCam;
    Catch pixel = new Catch(this);
    IMUDriveTrain idt = new IMUDriveTrain(this);
    Capture lohotron = new Capture(this);

    @Override
    public void runOpMode() {
        Methods_for_OpenCV methodsForOpenCV = new Methods_for_OpenCV();
        int rows = methodsForOpenCV.getRows();
        int cols = methodsForOpenCV.getCols();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(new org.firstinspires.ftc.robotcontroller.EOCVSamples.PhantomSamples.Methods_for_OpenCV.StageSwitchingPipeline());
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.UPRIGHT);
        Thread thread = new Thread(() -> {
            while (opModeInInit()) {
                telemetry.addData("Values", valLeft + "  " + valRight);
                telemetry.update();
                // visionPortall.telemetryAprilTag();
                valLeft = Methods_for_OpenCV.getValLeft();
                valRight = Methods_for_OpenCV.getValRight();
            }
        });
        FtcDashboard.getInstance().startCameraStream(phoneCam, 210);
        FtcDashboard.getInstance().getTelemetry();
        thread.start();
        idt.initIDT();
        lohotron.initLohotron();
        pixel.initCatch();
        idt.switchToRRDirections();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(35)
                .build();

        TrajectorySequence traj1_1 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(35)
                .build();


        pixel.ungrab();

        waitForStart();

        if (opModeIsActive()) {
            valLeft = Methods_for_OpenCV.getValLeft();
            valRight = Methods_for_OpenCV.getValRight();
            drive.followTrajectorySequence(traj1);  //Проезд к трем линиям
            phoneCam.stopStreaming();
            if (valRight == 255) { //Проезд к правой позиции
                idt.initIDT();
                idt.turnToHeading(0.7, -90);        //поворот к линии
                idt.switchToRRDirections();

                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).back(20).forward(5).build());
                pixel.grab();
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(25).build());

                idt.initIDT();
                idt.turnToHeading(0.7, 90);         //поворот в изначальное положение
                idt.switchToRRDirections();
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(40).build());
            } else if (valLeft == 255) { //Проезд к центру
                pixel.grab();
            } else { //Проезд к левой позиции
                idt.initIDT();
                idt.turnToHeading(0.7, 90);        //поворот к линии
                idt.switchToRRDirections();

                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).back(15).build());
                pixel.grab();
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(25).build());

                idt.initIDT();
                idt.turnToHeading(0.7, -90);         //поворот в изначальное положение
                idt.switchToRRDirections();
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(40).build());
            }
        }
    }
}
