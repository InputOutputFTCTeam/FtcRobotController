package org.firstinspires.ftc.teamcode.notUsed_trash;


import static org.firstinspires.ftc.teamcode.robotModules.Sensors.visions.Recognition.RingPosition.ZERO;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RoadRunnerMethods.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.visions.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Disabled
@Autonomous (name = "TestAuto", group = "Actul")
public class TestAuto extends LinearOpMode {

    DcMotor TR, TL, BR, BL, Intake, Lift;

    Servo drop, lohotronMain, lohotron, zahvat;

    protected Recognition recognition;

    OpenCvCamera webcam;


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



    drop = hardwareMap.servo.get("drop");
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

    /*TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d())

        .back(44) //к линии        //проезды задаются тиками энкодера forward - вперед, back - назад, strafeRight/Left - стрейфить
        .addTemporalMarker(5, () -> {drop.setPosition(0);}); // сброс пикселфя на центр (доп действия)
        //.back(2) //           //turn - поворот (в градусах)
        //.strafeLeft(18)
        //.turn(-90)
        //.forward(54)

        //.strafeRight(18)
        //.forward(18)
        //.build();*/

        waitForStart();


        if (opModeIsActive()) {


            if (recognition.getAnalysis() == ZERO) {

                //drive.followTrajectorySequence(traj1);
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


            telemetry.addData("position is ", recognition.getAnalysis());
            telemetry.addData("avg1 is ", recognition.getAvgs()[0]);
            telemetry.addData("avg 2 is", recognition.getAvgs()[1]);
            telemetry.addData("black avg is", recognition.getAvgs()[2]);

        }

    }

}


