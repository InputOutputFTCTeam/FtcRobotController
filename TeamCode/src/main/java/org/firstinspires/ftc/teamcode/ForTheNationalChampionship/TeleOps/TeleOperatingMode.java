package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.TeleOps;


import android.text.TextDirectionHeuristic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored.BackBoard;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Catch;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Hook;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.HookMotor;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Elevator;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Capture;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.Plane;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored.PIDControledDriveTrain;
import org.firstinspires.ftc.teamcode.notUsed_trash.RoadRunnerMethods.util.DashboardUtil;

/**
 * В данном классе описывается основной телеоп
 */

@TeleOp(name = "Tele V8", group = "1alfa")
public class TeleOperatingMode extends LinearOpMode {
    //Thread movement, raiseaArm, midlower, closeClaw, openClaw, intakethread, closehook, midhook, openhook, liftThread, grab, ungrab, pushUp, agelUp;
    DistanceSensor sensorDistance;
    //BasicDriveTrain wheelbase = new BasicDriveTrain(this);
    Capture lohotron = new Capture(this);
    Elevator lift = new Elevator(this);
    Catch aCatch = new Catch(this);
    Hook hook = new Hook(this);
    Plane plane = new Plane(this);
    BackBoard wheelBaseBackBoarded = new BackBoard(this);
    HookMotor hookMotor = new HookMotor(this);
    PIDControledDriveTrain pid = new PIDControledDriveTrain(this);

    @Override
    public void runOpMode() {
            //инициализация модулей робота
            sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");

            lohotron.initLohotron();

            lift.initLift();
            lift.liftDirection(DcMotorSimple.Direction.FORWARD);
            lift.liftMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            hookMotor.initHookMotor();

            wheelBaseBackBoarded.initBackBoard();

            aCatch.initCatch();

            plane.initPlane();

            hook.initHooks();

            pid.initPIDPT();

            telemetry.update();



        //вывод данных о состоянии модулей робота на момент перед стартом
        while (!isStarted()) {
            lohotron.lohotronTelemetry();
            lift.telemetryLift();
            aCatch.telemetryBack();
            hook.telemetryHooks();
            plane.telemetryPlane();

            telemetry.update();
            idle();
        }
        boolean driveMode = true;

        plane.angleDown();

        while (opModeIsActive()) {

            /* y - режим проезда к заднику с использованием датчика расстояния
             * left stick x/y - передвижение по оси Х/Y
             * right trigger - поворот вправо
             * left trigger - поворот влево
             */

                wheelBaseBackBoarded.smartMove(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);
                if (gamepad1.left_bumper)
                    wheelBaseBackBoarded.setMaximumSpeed(0.5); pid.PIDMove();   // left bumper - медленная езда

                if (gamepad1.right_bumper)
                    wheelBaseBackBoarded.setMaximumSpeed(1);  pid.PIDMove();  // right bumper - быстрая езда



                hookMotor.run(gamepad1.right_stick_y);  // right stick y - запуск домкрата

                if (gamepad2.a)
                    lohotron.armLogicalRaise_Lower();   // a - переворот и нижнее положение захвата
                if (gamepad2.y)
                    lohotron.armLogicalMid_Lower();     // y - среднее и нижнее положение захвата

                if (gamepad2.b) lohotron.closeClaw();   // x - открыть захват
                if (gamepad2.x) lohotron.openClaw();    // b - закрыть захват

                if (gamepad2.dpad_left) aCatch.openGrab();   // dpad left - открыть держатель пикселей
                if (gamepad2.dpad_right) aCatch.closeGrab(); // dpad right - закрыть держатель пикселей


                if (gamepad1.dpad_down) hook.closeHook();   // dpad down - опустить подвес
                if (gamepad1.dpad_up) hook.openHook();      // dpad up - поднять подвес

                lift.run(gamepad2.right_stick_y);   // right stick y - изменение позиции лифта

                if (gamepad2.right_bumper)
                    aCatch.grab();   // right bumper -  открыть захват одной сервой
                if (gamepad2.left_bumper) aCatch.ungrab();  // left bumper - закрыть захват одной сервой


                if (gamepad1.x) plane.pushUp();     // х - запуск самолётика
                if (gamepad1.b) plane.logicalAngle();

                composeTelemery();
        }
    }


    /**
     * Собираем всю телеметрию с модулей робота, чтобы отправить ее в основной поток телеметрии
     */
    public void composeTelemery() {
        telemetry.addData("rangesens", sensorDistance.getDistance(DistanceUnit.MM));
        wheelBaseBackBoarded.telemetryBackBoard();
        lohotron.lohotronTelemetry();
        lift.telemetryLift();
        aCatch.telemetryBack();
        hook.telemetryHooks();
        plane.telemetryPlane();

        telemetry.update();
    }

}
