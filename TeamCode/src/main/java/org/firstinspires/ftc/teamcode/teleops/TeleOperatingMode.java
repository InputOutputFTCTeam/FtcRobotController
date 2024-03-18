package org.firstinspires.ftc.teamcode.teleops;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robotModules.Basic.BackBoard;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Catch;
import org.firstinspires.ftc.teamcode.robotModules.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Hook;
import org.firstinspires.ftc.teamcode.robotModules.Basic.HookMotor;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lift;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Lohotron;
import org.firstinspires.ftc.teamcode.robotModules.Basic.Plane;

/**
 * В данном классе описывается основной телеоп
 */

@TeleOp(name = "Tele V8", group = "1alfa")
public class TeleOperatingMode extends LinearOpMode {
    //Thread movement, raiseaArm, midlower, closeClaw, openClaw, intakethread, closehook, midhook, openhook, liftThread, grab, ungrab, pushUp, agelUp;

    BasicDriveTrain wheelbase = new BasicDriveTrain(this);
    Lohotron lohotron = new Lohotron(this);
    Lift lift = new Lift(this);
    Catch aCatch = new Catch(this);
    Hook hook = new Hook(this);
    Plane plane = new Plane(this);
    BackBoard backBoard = new BackBoard(this);
    HookMotor hookMotor = new HookMotor(this);

    @Override
    public void runOpMode() {
        //инициализация модулей робота

        wheelbase.initMotors();
        wheelbase.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelbase.setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelbase.setOneDirection(DcMotorSimple.Direction.FORWARD);
        wheelbase.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        lohotron.initLohotron();

        lift.initLift();
        lift.liftDirection(DcMotorSimple.Direction.FORWARD);
        lift.liftMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hookMotor.initHookMotor();

        backBoard.initBackBoard();

        aCatch.initCatch();

        plane.initPlane();

        hook.initHooks();

        telemetry.update();

        //вывод данных о состоянии модулей робота на момент перед стартом
        while (!isStarted()) {
            wheelbase.wheelbaseTelemetry();
            lohotron.lohotronTelemetry();
            lift.telemetryLift();
            aCatch.telemetryBack();
            hook.telemetryHooks();
            plane.telemetryPlane();

            telemetry.update();
            idle();
        }
        boolean driveMode = true;

        while (opModeIsActive()) {
            /* y - режим проезда к заднику с использованием датчика расстояния
             * left stick x/y - передвижение по оси Х/Y
             * right trigger - поворот вправо
             * left trigger - поворот влево
             */
            if (gamepad1.y) {
                driveMode = !driveMode;
                sleep(150);
            }

            if (driveMode == true) {
                wheelbase.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);
            }
            if (driveMode == false) {
                backBoard.backboard_slowly(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);
            }

            if (gamepad1.left_bumper)
                wheelbase.setMaximumSpeed(0.5);   // left bumper - медленная езда
            if (gamepad1.right_bumper)
                wheelbase.setMaximumSpeed(1);    // right bumper - быстрая езда

            hookMotor.run(gamepad1.right_stick_y);  // right stick y - запуск домкрата

            if (gamepad2.a)
                lohotron.armLogicalRaise_Lower();   // a - переворот и нижнее положение захвата
            if (gamepad2.y)
                lohotron.armLogicalMid_Lower();     // y - среднее и нижнее положение захвата

            if (gamepad2.x) lohotron.closeClaw();   // x - открыть захват
            if (gamepad2.b) lohotron.openClaw();    // b - закрыть захват

            if (gamepad2.dpad_left) aCatch.openGrab();   // dpad left - открыть держатель пикселей
            if (gamepad2.dpad_right) aCatch.closeGrab(); // dpad right - закрыть держатель пикселей

            /*intakethread = new Thread(() -> {
                intake.runIntake((gamepad2.right_trigger - gamepad2.left_trigger) * INTAKE_SPEED);
            }); intakethread.start();
            */

            if (gamepad1.dpad_down) hook.closeHook();   // dpad down - опустить подвес
            if (gamepad1.dpad_up) hook.openHook();      // dpad up - поднять подвес

            lift.run(gamepad2.right_stick_y);   // right stick y - изменение позиции лифта

            if (gamepad2.right_bumper)
                aCatch.grab();   // right bumper -  открыть захват одной сервой
            if (gamepad2.left_bumper) aCatch.ungrab();  // left bumper - закрыть захват одной сервой


            if (gamepad1.x) plane.pushUp();     // х - запуск самолётика
            if (gamepad1.b) plane.angleUp();    // b - изменение угла запуска самолётика
            //if (gamepad1.b) plane.angleDown();

            composeTelemery();
        }
    }


    /**
     * Собираем всю телеметрию с модулей робота, чтобы отправить ее в основной поток телеметрии
     */
    public void composeTelemery() {
        wheelbase.wheelbaseTelemetry();
        lohotron.lohotronTelemetry();
        lift.telemetryLift();
        aCatch.telemetryBack();
        hook.telemetryHooks();
        plane.telemetryPlane();

        telemetry.update();
    }

}
