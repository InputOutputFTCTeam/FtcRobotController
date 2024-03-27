package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.EOCVSamples.PhantomSamples.Methods_for_OpenCV;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Catch;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Capture;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored.MegaDriveTrain;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors.ColorSensorModule;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class AB2 extends LinearOpMode {
    MegaDriveTrain base = new MegaDriveTrain(this);
    Catch pix = new Catch(this);
    Capture lohotron = new Capture(this);

    private static int valLeft = -1;
    private static int valRight = -1;
    public OpenCvWebcam phoneCam;

    @Override
    public void runOpMode() {
        base.initMega();
        pix.initCatch();
        lohotron.initLohotron();

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
                FtcDashboard dashboard = FtcDashboard.getInstance();
                Telemetry dashtelemetry = dashboard.getTelemetry();
                dashtelemetry.addData("Values", valLeft + "  " + valRight);
                dashtelemetry.update();
                dashboard.startCameraStream(phoneCam, 210);
                telemetry.addData("Values", valLeft + "  " + valRight);
                telemetry.update();
                // visionPortall.telemetryAprilTag();
                valLeft = Methods_for_OpenCV.getValLeft();
                valRight = Methods_for_OpenCV.getValRight();
            }
        });

        thread.start();

        waitForStart();
        if (opModeIsActive()) {
            valLeft = Methods_for_OpenCV.getValLeft();
            valRight = Methods_for_OpenCV.getValRight();
            phoneCam.stopStreaming();

            //подкатываем к точке сброса фиолетового
            pix.grab();
            base.colorRun(0, 0.5, 0, ColorSensorModule.colorsField.idkWtfIsThisColor); // red

            if (valLeft == 255) {           //центр
                //надо ли небольшой отъезд назад? (датчик же не спереди робота идет)
                base.encoderRun(0, -0.5, -50);
                base.encoderRun(0,0.5,100);
                pix.ungrab();
                sleep(1000);
                base.encoderRun(0, 1, 160); //(1219) мы проезжаем две плитки (4 фута == 1219мм) и выравниваемся об стенку
                pix.grab();
                base.imuTurn(0.5, 93);
                base.encoderRun(0, -1, -2180);

            } else if (valRight == 255) {   //право
                base.encoderRun(0,0.7, 50);
                base.imuTurn(0.7, -90);
                base.encoderRun(0,-0.7,-168);
                base.encoderRun(0,0.7, 50);
                pix.ungrab();
                sleep(1000);
                base.encoderRun(0,0.5,145);
                pix.grab();
                base.imuTurn(0.7, 5);
                base.encoderRun(0,0.7, 750);
                base.imuTurn(0.7, 91);
                base.encoderRun(0, -1, -1950);
                base.imuTurn(0.7,0);
                base.encoderRun(0,-0.7,-650);
                base.imuTurn(0.7, 90);
                base.encoderRun(0,-0.7,-150);
            } else {                        //лево
                //надо ли небольшой отъезд назад? (датчик же не спереди робота идет)
                base.encoderRun(0,0.7, 35);
                base.imuTurn(0.7, 90);
                base.encoderRun(0,-0.7,-168);
                base.encoderRun(0,0.7, 45);
                pix.ungrab();
                sleep(1000);
                base.encoderRun(0,0.5,158);
                pix.grab();
                base.imuTurn(0.7, 0);
                base.encoderRun(0,0.7, 715);
                base.imuTurn(0.7, 91);
                base.encoderRun(0, -1, -2100);
                base.imuTurn(0.7,0);
                base.encoderRun(0,-0.7,-325);
                base.imuTurn(0.7, 90);
                base.encoderRun(0,-0.7,-100);
            }

            //подкатываем к доске
            //base.colorRun(0, 1, 0, ColorSensorModule.colorsField.BLUE); //едем до разметочной линии перед доской

            //подкатываем к правильной колонке
            /*
            if (valLeft == 255) {           //центр

                base.encoderRun(0.7, 0, -915);   //полторы клетки влево
            } else if (valRight == 255) {   //право
                base.encoderRun(0.7, 0, -1006);  //чуть больше, чем полторы клетки влево
            } else {                        //лево
                base.encoderRun(0.7, 0, -823);   //чуть меньше, чем полторы клетки влево
            }
            */
            //роняем запад
            //роняем запад
            lohotron.armRaiser();
            sleep(1000);
            lohotron.closeClaw();
            sleep(1000);        //ждем, пока упадет желтый пиксель
            base.encoderRun(0, 0.4, 170);
            sleep(250);
            lohotron.armMid();
            sleep(1000);
            lohotron.initLohotron();

            //паркуемся

            if (valLeft == 255) {           //центр
                //  base.imuSteerEncoder(0.5, 0, 0, 90, 1015);  \ //полторы клетки вправо
                base.imuTurn(0.7, -180);
                base.encoderRun(0, -0.7, -520);
            } else if (valRight == 255) {   //право
                base.imuTurn(0.7,0);
                base.encoderRun(0,0.7,-300);//чуть больше, чем полторы клетки вправо
            } else {                        //лево
                base.imuTurn(0.7,0);
                base.encoderRun(0,0.7,400);
            }


            //base.imuTurn(1, 0);     //hold position
        }
    }
}
