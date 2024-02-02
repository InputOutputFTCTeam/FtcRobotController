package org.firstinspires.ftc.teamcode.autos;



import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.FOUR;

import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.ONE;

import static org.firstinspires.ftc.teamcode.visions.Recognition.RingPosition.ZERO;



import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;



import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.RoadRunnerMethods.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.visions.Recognition;

import org.openftc.easyopencv.OpenCvCamera;

import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


@Autonomous(name = "AutoBlueTest", group = "Actul")

public class AutoBlueTest extends LinearOpMode{
    DcMotor TR, TL, BR, BL, Intake, Lift;

    Servo servobox, lohotronMain, lohotron, zahvat, drop2;

    protected Recognition recognition;

    OpenCvCamera webcam;

    double INTAKE_SPEED = 0.7;

    public void armRaise(){
        lohotronMain.setPosition(0.9); //lohotronMain - подымает всю палку
        sleep(100);
        lohotron.setPosition(1); //lohotron - серва на захвате у лохотрона0

    }

    public void armLower(){
        lohotronMain.setPosition(0);
        sleep(50);
        lohotron.setPosition(0);

    }

    public void armMiddle(){
        lohotron.setPosition(0.6);
        sleep(50);
        lohotronMain.setPosition(0.5);
    }

    @Override

    public void runOpMode() {
        recognition = new Recognition();
        recognition.getAnalysis();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.openCameraDevice();
        webcam.setPipeline(recognition);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(1920,1080, OpenCvCameraRotation.UPRIGHT); //поменять ориентацию камеры  SIDEWAYS_LEFT
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

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
        drop2 = hardwareMap.servo.get("drop2");



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

        //тута штуки всяике тестятся

        ///!протестить и исправить если надо!!

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d());
                //.forward(44)
                // .strafeRight(100) //позиция поворота
                //.strafeLeft(12)
//                 .turn(0.135)
//                .forward(164) //до щита
                //1 ~ [270;320], 0.25 ~ 135, 0.18 ~ чуть больше 90, 0.15 ~ 90, 0.14 (при 12.74V) ~ 85
                ////.addTemporalMarker(5, () -> {servobox.setPosition(0);})
                ////.turn(90)
//                .forward(164)
                //.turn(0.350) //на 180
//                .addTemporalMarker(3, () -> armRaise())
//                .addTemporalMarker(1,() -> lohotron.setPosition(0))
                //.addTemporalMarker(5, () -> {armRaise(); zahvat.setPosition(0);})
                //.strafeRight(18)
                //.forward(18)    //*/
                //.build();


        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d())
                .back(33) //к линии        //проезды задаются непонятной системой мер forward - вперед, back - назад, strafeRight/Left - стрейфить
                .addTemporalMarker(5, () -> {drop2.setPosition(0);})
                .build();
                //.addTemporalMarker(5, () -> {servobox.setPosition(0);}) // сброс пикселфя на центр (доп действия)
                //.back(2) //           //turn - поворот (в градусах)
                //.strafeLeft(18)
                //.turn(-90)
                //.forward(54)
                //.addTemporalMarker(5, () -> {servobox.setPosition(0);})
                //.strafeRight(18)
                //.forward(18)
                //.build();




        //TrajectorySequence traj3 = drive.trajectorySequenceBuilder(new Pose2d())
                //.forward(18)
                //.turn(90)
                //.addTemporalMarker(5, () -> {servobox.setPosition(0);})
                //.turn(-90)
                //.back(2)
                //.strafeLeft(18)
                //.turn(-90)
               //.forward(54)
                //.addTemporalMarker(5, () -> {servobox.setPosition(0);})
                //.strafeRight(18)
                //.forward(18)
                //.build();

        drop2.setPosition(0.7);

        waitForStart();


        if (opModeIsActive()) {


            if (recognition.getAnalysis() == ZERO) {
                servobox.setPosition(0.55);
                drive.followTrajectorySequence(traj1);
                telemetry.addLine("zone A");
                telemetry.update();
            }

            /*if (recognition.getAnalysis() == ONE) {
                drive.followTrajectorySequence(traj2);
                telemetry.addLine("zone B");
                telemetry.update();
            }

            if (recognition.getAnalysis() == FOUR) {
                drive.followTrajectorySequence(traj3);
                telemetry.addLine("zone C");
                telemetry.update();
            }*/

            sleep(1000);
            armRaise();
            zahvat.setPosition(0.5);

            telemetry.addData("position is ", recognition.getAnalysis());
            telemetry.addData("avg1 is ", recognition.getAvgs()[0]);
            telemetry.addData("avg 2 is", recognition.getAvgs()[1]);
            telemetry.addData("black avg is", recognition.getAvgs()[2]);

        }

    }

}