package org.firstinspires.ftc.teamcode.teleops;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robotModules.Basic.Catch;
import org.firstinspires.ftc.teamcode.robotModules.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Box;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Hook;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Intaker;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lift;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lohotron;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Plane;

@TeleOp(name = "Tele V8", group = "1alfa")
public class TeleOperatingMode extends LinearOpMode {
    Thread movement, raiseaArm, midlower, closeClaw, openClaw, intakethread, closehook, midhook, openhook, liftThread, grab, ungrab, pushUp, agelUp;
    BasicDriveTrain wheelbase = new BasicDriveTrain(this);
    //Intaker intake = new Intaker(this);
    Lohotron lohotron = new Lohotron(this);
    //Box box = new Box(this);
    Lift lift = new Lift(this);
    Catch aCatch = new Catch(this);
    Hook hook = new Hook(this);
    Plane plane = new Plane(this);

    double INTAKE_SPEED = -0.4;

    @Override
    public void runOpMode(){

        wheelbase.initMotors();
        wheelbase.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelbase.setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelbase.setOneDirection(DcMotorSimple.Direction.FORWARD);
        wheelbase.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        //intake.initIntake(hardwareMap);

        lohotron.initLohotron(hardwareMap);

        //box.initBox();

        lift.initLift();
        lift.liftDirection(DcMotorSimple.Direction.FORWARD);
        lift.liftMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        aCatch.initCatch();

        plane.initPlane();

        hook.initHooks();

        telemetry.update();

        while(!isStarted()){
            wheelbase.wheelbaseTelemetry();
            lohotron.lohotronTelemetry();
            //intake.telemetryIntaker();
            //box.telemetryBox();
            lift.telemetryLift();
            aCatch.telemetryBack();
            hook.telemetryHooks();
            plane.telemetryPlane();

            telemetry.update();
            idle();
        }

        while(opModeIsActive()){
            movement = new Thread(() -> {
                wheelbase.move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);
            });movement.start();

            raiseaArm = new Thread(() -> {
                //if(gamepad2.y) lohotron.armRaiser();    //better be 1 logical button with lowerer
                //if(gamepad2.a) lohotron.armLowerer();   //better be 1 logical button with raiser
                if(gamepad2.a) lohotron.armLogicalRaise_Lower();
            }); raiseaArm.start();
            midlower = new Thread(() -> {
                if(gamepad2.y) lohotron.armLogicalMid_Lower();
            }); midlower.start();
            closeClaw = new Thread(() -> {
                if(gamepad2.x) lohotron.closeClaw();
            });closeClaw.start();
            openClaw = new Thread(() -> {
                if(gamepad2.b) lohotron.openClaw();
            }); openClaw.start();

            if (gamepad2.dpad_up) aCatch.openGrab();
            if (gamepad2.dpad_down) aCatch.closeGrab();


            /*intakethread = new Thread(() -> {
                intake.runIntake((gamepad2.right_trigger - gamepad2.left_trigger) * INTAKE_SPEED);
            }); intakethread.start();
            */
            closehook = new Thread(() -> {
                if(gamepad1.dpad_down) hook.closeHook();
            }); closehook.start();
            midhook = new Thread(() -> {
                //if(gamepad2.dpad_right) hook.midHook();
            }); midhook.start();
            openhook = new Thread(() -> {
                //if(gamepad1.dpad_up) hook.openHook();
            }); openhook.start();
            liftThread = new Thread(() -> {
                lift.run(gamepad2.right_stick_y);
            }); liftThread.start();

            grab = new Thread(() -> {
                if(gamepad2.right_bumper) aCatch.grab();    //или gamepad2.right_stick_button
            }); grab.start();
            ungrab = new Thread(() -> {
                if(gamepad2.left_bumper) aCatch.ungrab();       //    gamepad2.left_stick_button
            }); ungrab.start();

            ////if(gamepad1.a) hook.switchHook();
            //if(gamepad1.dpad_up) hook.openHook();
            pushUp = new Thread(() -> {
                if(gamepad1.x) plane.pushUp();
            }); pushUp.start();
            agelUp = new Thread(() -> {
                if (gamepad1.b) plane.angleUp();
            }); agelUp.start();
            composeTelemery();



        }
    }

    public void composeTelemery(){
        wheelbase.wheelbaseTelemetry();
        lohotron.lohotronTelemetry();
        //intake.telemetryIntaker();
        //box.telemetryBox();
        lift.telemetryLift();
        aCatch.telemetryBack();
        hook.telemetryHooks();
        plane.telemetryPlane();

        telemetry.update();
    }
}
