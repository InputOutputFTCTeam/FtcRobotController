package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.EOCVSamples.PhantomSamples.Methods_for_OpenCV;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.robotModules.Basic.Catch;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lohotron;
import org.firstinspires.ftc.teamcode.robotModules.Sensored.GigaChadDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Sensored.IMUDriveTrain;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

//TODO: AutoBlue2

@Autonomous(name = "AutoBlueTest", group = "Actul")
public class AutoBlueTest extends LinearOpMode {
    DcMotor TR, TL, BR, BL;
    private static int valLeft = -1;
    private static int valRight = -1;
    public OpenCvWebcam phoneCam;
    Catch pixel = new Catch(this);
    IMUDriveTrain idt = new IMUDriveTrain(this);
    Lohotron lohotron = new Lohotron(this);
    GigaChadDriveTrain robot = new GigaChadDriveTrain(this);

    @Override
    public void runOpMode() {
        robot.initGigaChad(this);

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
        idt.initIDT();
        lohotron.initLohotron(this.hardwareMap);
        pixel.initCatch();
        idt.switchToRRDirections();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(35) //к линии        //проезды задаются непонятной системой мер forward - вперед, back - назад, strafeRight/Left - стрейфить
                .build();

        TrajectorySequence traj1_1 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(25)
                .build();

        TrajectorySequence traj1_3 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(180)
                .build();

        TrajectorySequence traj1_5 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(65)
                .build();

        TrajectorySequence traj1_7 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(10)
                .build();

        TrajectorySequence traj1_8 = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(20)
                .build();

        TrajectorySequence traj1_9 = drive.trajectorySequenceBuilder(new Pose2d())
                .strafeRight(75)
                .build();

        TrajectorySequence traj1_10 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(45)
                .build();

        TrajectorySequence traj_costil = drive.trajectorySequenceBuilder(new Pose2d())
                .turn(0)
                .build();

        pixel.ungrab();

        waitForStart();


        if (opModeIsActive()) {

            valLeft = Methods_for_OpenCV.getValLeft();
            valRight = Methods_for_OpenCV.getValRight();
            drive.followTrajectorySequence(traj1);  //проезд к трем линиям
            phoneCam.stopStreaming();
            if (valRight == 255) {
                //сюда добавить небольшой проезд, потому что держатель пикселя находится слева у робота. из-за этого нам надо доезжать
                idt.initIDT();
                idt.turnToHeading(0.7, -90);        //поворот к линии
                idt.switchToRRDirections();

                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).back(20).build());
                pixel.grab();                   //чуть чуть отъезжаем от нее и сбрасываем. потом подъезжаем обратно к линии
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(25).build());

                idt.initIDT();
                idt.turnToHeading(0.7, 90);         //поворот в изначальное положение
                idt.switchToRRDirections();
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d()).forward(40).build());
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

            drive.followTrajectorySequence(traj1_1);        //возврат к борту поля
            pixel.ungrab();
            idt.initIDT();
            idt.turnToHeading(0.7, 90); //поворот на самый большой проезд
            idt.switchToRRDirections();

            drive.followTrajectorySequence(traj1_3);    //самый длинный омуль
            idt.initIDT();
            idt.turnToHeading(0.7, -90);    //поворот на проезды к подъездам
            idt.switchToRRDirections();

            drive.followTrajectorySequence(traj1_5);    //центральное положение
            idt.initIDT();
            idt.turnToHeading(0.7, 90);     //разворот, чтобы смотреть на доску
            idt.switchToRRDirections();

            lohotron.armRaiser();                       //подняли
            drive.followTrajectorySequence(traj1_7);    //чуть медленно взад, чтобы коснуться рукой доски
            lohotron.openClaw();                        //отпустили
            //вперед немного
            drive.followTrajectorySequence(traj1_8);    //убираем руку от доски
            //в бок
            drive.followTrajectorySequence(traj1_9);    //бочком катимся к парковке
            lohotron.armLowerer();                      //опускаем руку                     //можно будет закомментить, если времени не будет хватать
            //назад парковка
            drive.followTrajectorySequence(traj1_10);   //парковка
            drive.followTrajectorySequence(traj_costil);//не нужный кусок, но пусть будет, так как он ничего не делает.

        }
    }
}