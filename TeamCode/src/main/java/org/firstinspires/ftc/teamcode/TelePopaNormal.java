package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;

@TeleOp(name = "TelePopaNormal", group = "Actual")
public class TelePopaNormal extends LinearOpMode {
    double k = 0.4;
    double servoPos;
    public Servo ssl, ssr;
    public DcMotor TL, TR, BL, BR;
    public DcMotor lift;

    @Override
    public void runOpMode() {
        Methods methods = new Methods(hardwareMap);
        //StandardTrackingWheelLocalizer myLocalizer = new StandardTrackingWheelLocalizer(hardwareMap);
        //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        ssl = hardwareMap.servo.get("ssl");
        ssr = hardwareMap.servo.get("ssr");
        //drive.setPoseEstimate(new Pose2d(10, 10, Math.toRadians(90)));

        telemetry.addLine("Ready to start");

        waitForStart();
        while (opModeIsActive()){

            //drive.update();
            //Pose2d Pose = drive.getPoseEstimate();

            if(gamepad1.left_bumper){
                k = 0.4;
            }

            if(gamepad1.right_bumper){
                k = 0.5;
            }

            methods.move(
                    gamepad1.left_stick_x + gamepad1.right_stick_x * 0.5,
                    gamepad1.left_stick_y + gamepad1.right_stick_y * 0.5,
                    gamepad1.right_trigger - gamepad1.left_trigger,
                    k
            );

            methods.liftRun(gamepad2.right_trigger - gamepad2.left_trigger);


            if (gamepad2.dpad_up) {
                ssl.setPosition(0.6);
                ssr.setPosition(1-0.6);
            }

            if (gamepad2.dpad_down) {
                ssl.setPosition(0.725);
                ssr.setPosition(1-0.725);
            }

           // telemetry.addData("x", Pose.getX());
            //telemetry.addData("y", Pose.getY());
            //telemetry.addData("heading", Pose.getHeading());
            //telemetry.update();
        }
    }
}
