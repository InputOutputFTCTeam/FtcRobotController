package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.EOCVSamples.PhantomSamples.Methods_for_OpenCV;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.Catch;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.Capture;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensored.MegaDriveTrain;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensors.ColorSensorModule;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous (name = "G_blue2.1")
public class G_blue2_1 extends LinearOpMode {
    MegaDriveTrain robot = new MegaDriveTrain(this);
    private static int valLeft = -1;
    private static int valRight = -1;
    public OpenCvWebcam phoneCam;
    Catch pixel = new Catch(this);
    Capture lohotron = new Capture(this);

    @Override
    public void runOpMode() {
        robot.initMega();

        Methods_for_OpenCV methodsForOpenCV  = new Methods_for_OpenCV();
        int rows = methodsForOpenCV.getRows();
        int cols = methodsForOpenCV.getCols();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(new org.firstinspires.ftc.robotcontroller.EOCVSamples.PhantomSamples.Methods_for_OpenCV.StageSwitchingPipeline());
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.UPRIGHT);
        Thread thread = new Thread(() -> {
            while (opModeInInit()){
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

        lohotron.initLohotron();
        pixel.initCatch();


        pixel.ungrab();
        lohotron.armMid();

        waitForStart();
        if (opModeIsActive()) {

            valLeft = Methods_for_OpenCV.getValLeft();
            valRight = Methods_for_OpenCV.getValRight();
            phoneCam.stopStreaming();

            robot.colorRun(0, 0.35, 0, ColorSensorModule.colorsField.BLUE);

            if (valLeft == 255) {
                robot.encoderRun(0, 0.6, 75);
                pixel.grab();
                robot.encoderRun(0, 0.6, 100);
                sleep(500);
                //robot.encoderRun(0, 0.6, 50);
                robot.imuTurn(0.6, 90);
                //robot.encoderRun(0, -0.6, -882);
            }
            else if(valRight == 255) {
                robot.imuTurn(0.6, 90);
                pixel.grab();
                robot.encoderRun(0, 0.6, 50);
                robot.imuTurn(0.6, 180);

            }
            else{
                robot.imuTurn(0.7, 90);
                robot.encoderRun(0, 0.5, 100);
                pixel.grab();
                robot.encoderRun(0, 0.5, -100);
                robot.imuTurn(0.7, -90);
            }

            robot.encoderRun(0, -1, -1219);

            lohotron.armRaiser();
            lohotron.closeClaw();
            sleep(1000);        //ждем, пока упадет желтый пиксель
            robot.encoderRun(0, 0.4, 50);
            sleep(250);
            lohotron.armMid();

            }


            /*valLeft = Methods_for_OpenCV.getValLeft();
            valRight = Methods_for_OpenCV.getValRight();
            robot.colorRun(0, 0.6, 0, ColorSensorModule.colorsField.BLUE);  //проезд к трем линиям
            phoneCam.stopStreaming();
            if (valRight == 255) {
                //сюда добавить небольшой проезд, потому что держатель пикселя находится слева у робота. из-за этого нам надо доезжать
                robot.encoderRun(0, -0.6, 55);
                robot.imuTurn(-0.6, -90);
                pixel.grab();
                pixel.ungrab();

                robot.imuTurn(0.6, 90);




            } else if (valLeft == 255) {
                pixel.grab();
            } else{
                idt.initIDT();
                idt.turnToHeading(0.7, 90);        //поворот к линии
                idt.switchToRRDirections();

                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).back(15).build());
                pixel.grab();                   //чуть чуть отъезжаем от нее и сбрасываем. потом подъезжаем обратно к линии
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(25).build());

                idt.initIDT();
                idt.turnToHeading(0.7, -90);         //поворот в изначальное положение
                idt.switchToRRDirections();
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(40).build());

            }



            //encoder run - проезд вперёд, imuTurn- оаорот, объявить сервы что использовать как в голуюой тест


            //sleep(5000);
            *///robot.colorRun(0.5, 0, 0, ColorSensorModule.colorsField.BLUE);
        }
    }

