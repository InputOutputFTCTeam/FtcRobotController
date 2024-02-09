package org.firstinspires.ftc.teamcode.onTest.restart;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Tele V8", group = "alfa")
public class TeleOperatingMode extends LinearOpMode {
    BasicDriveTrain wheelbase = new BasicDriveTrain(this);
    Intaker intake = new Intaker(this);
    Lohotron lohotron = new Lohotron(this);

    double INTAKE_SPEED = 0.4;

    @Override
    public void runOpMode(){

        wheelbase.initMotors(hardwareMap);
        wheelbase.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelbase.setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelbase.setOneDirection(DcMotorSimple.Direction.FORWARD);
        wheelbase.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.initIntake(hardwareMap);

        lohotron.initLohotron(hardwareMap);

        while(!isStarted()){
            wheelbase.wheelbaseTelemetry();
            lohotron.lohotronTelemetry();
            intake.telemetryIntaker();

            telemetry.update();
            idle();
        }

        while(opModeIsActive()){
            wheelbase.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);

            if(gamepad2.y) lohotron.armRaiser();    //better be 1 logical button with lowerer
            if(gamepad2.a) lohotron.armLowerer();   //better be 1 logical button with raiser
            if(gamepad2.b) lohotron.armMid();

            intake.runIntake((gamepad2.right_trigger - gamepad2.left_trigger) * INTAKE_SPEED);

            /*
            if(gamepad2.x) lohotron.closeClaw();
            if(gamepad2.y) lohotron.openClaw();

            if(gamepad2.dpad_down) box.down();
            if(gamepad2.dpad_right) box.mid();
            if(gamepad2.dpad_up) box.up();

            lift.run(gamepad2.right_trigger - gamepad2.left_trigger); //???????

            if(gamepad2.right_button) backup.down();    //или gamepad2.right_stick_button
            if(gamepad2.left_button) backup.up();       //    gamepad2.left_stick_button

            if(gamepad1.a) hook.openHook();
            if(gamepad1.b) hook.closeHook();

            if(gamepad1.x) plane.fly();
             */
        }
    }
}
