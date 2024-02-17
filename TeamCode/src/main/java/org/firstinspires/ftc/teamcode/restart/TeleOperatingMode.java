package org.firstinspires.ftc.teamcode.restart;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robotModules.Basic.BackupCatch;
import org.firstinspires.ftc.teamcode.robotModules.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Box;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Hook;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Intaker;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lift;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lohotron;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Plane;

@TeleOp(name = "Tele V8", group = "1alfa")
public class TeleOperatingMode extends LinearOpMode {
    BasicDriveTrain wheelbase = new BasicDriveTrain(this);
    Intaker intake = new Intaker(this);
    Lohotron lohotron = new Lohotron(this);
    Box box = new Box(this);
    Lift lift = new Lift(this);
    BackupCatch backupCatch = new BackupCatch(this);
    Hook hook = new Hook(this);
    Plane plane = new Plane(this);

    double INTAKE_SPEED = -0.4;

    @Override
    public void runOpMode(){

        wheelbase.initMotors(hardwareMap);
        wheelbase.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelbase.setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelbase.setOneDirection(DcMotorSimple.Direction.FORWARD);
        wheelbase.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.initIntake(hardwareMap);

        lohotron.initLohotron(hardwareMap);

        box.initBox();

        lift.initLift();
        lift.liftDirection(DcMotorSimple.Direction.FORWARD);
        lift.liftMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backupCatch.initBack();

        plane.initPlane();

        hook.initHooks();

        telemetry.update();

        while(!isStarted()){
            wheelbase.wheelbaseTelemetry();
            lohotron.lohotronTelemetry();
            intake.telemetryIntaker();
            box.telemetryBox();
            lift.telemetryLift();
            backupCatch.telemetryBack();
            hook.telemetryHooks();
            plane.telemetryPlane();

            telemetry.update();
            idle();
        }

        while(opModeIsActive()){
            wheelbase.move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);

            //if(gamepad2.y) lohotron.armRaiser();    //better be 1 logical button with lowerer
            //if(gamepad2.a) lohotron.armLowerer();   //better be 1 logical button with raiser
            if(gamepad2.a) lohotron.armLogicalRaise_Lower();
            if(gamepad2.y) lohotron.armMid();
            if(gamepad2.x) lohotron.closeClaw();
            if(gamepad2.b) lohotron.openClaw();

            intake.runIntake((gamepad2.right_trigger - gamepad2.left_trigger) * INTAKE_SPEED);

            if(gamepad2.dpad_down) box.down();
            if(gamepad2.dpad_right) box.mid();
            if(gamepad2.dpad_up) box.upp();

            lift.run(gamepad2.right_stick_y);

            if(gamepad2.right_bumper) backupCatch.grab();    //или gamepad2.right_stick_button
            if(gamepad2.left_bumper) backupCatch.ungrab();       //    gamepad2.left_stick_button

            //if(gamepad1.a) hook.switchHook();
            if(gamepad1.dpad_up) hook.openHook();
            if(gamepad1.x) plane.pushUp();
            if (gamepad1.b) plane.angleUp();
            composeTelemery();
        }
    }

    public void composeTelemery(){
        wheelbase.wheelbaseTelemetry();
        lohotron.lohotronTelemetry();
        intake.telemetryIntaker();
        box.telemetryBox();
        lift.telemetryLift();
        backupCatch.telemetryBack();
        hook.telemetryHooks();
        plane.telemetryPlane();

        telemetry.update();
    }
}
