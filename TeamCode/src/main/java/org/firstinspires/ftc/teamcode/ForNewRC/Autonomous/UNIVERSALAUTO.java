package org.firstinspires.ftc.teamcode.ForNewRC.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous(name = "UNIVERSALAUTO", group = "AUTONOMOUS")
public class UNIVERSALAUTO extends LinearOpMode{
    // Объявление моторов для колес робота
    private DcMotor TR, TL, BR, BL;

    @Override
    public void runOpMode() {
        // Инициализация моторов через hardwareMap
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        // Установка направления вращения для правильной координации движения
        TL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);

        // Установка поведения моторов при нулевой мощности (чтобы робот не катился)
        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Сброс значений энкодеров для точного отслеживания расстояния
        TL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Переключение моторов в режим работы с энкодерами для точного перемещения
        TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Ожидание старта автономного режима
        waitForStart();

        if (opModeIsActive()) {
            // Движение вперед на 1000 тиков с мощностью 0.5
            driveToPosition(1000, 0.5);
            sleep(2000); // Пауза для завершения движения

            // Разворот на 90 градусов вправо
            turnToPosition(500, 0.5);
            sleep(2000);

            // Движение назад на 1000 тиков с мощностью 0.5
            driveToPosition(-1000, 0.5);
        }
    }

    /**
     * Метод для движения робота на заданное количество тиков с заданной мощностью
     * @param position Количество тиков (положительное значение - вперед, отрицательное - назад)
     * @param power Мощность моторов (0.0 - 1.0)
     */
    private void driveToPosition(int position, double power) {
        TL.setTargetPosition(position);
        TR.setTargetPosition(position);
        BL.setTargetPosition(position);
        BR.setTargetPosition(position);

        TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        TL.setPower(power);
        TR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

        while (opModeIsActive() && (TL.isBusy() || TR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("TR Position", TR.getCurrentPosition());
            telemetry.addData("BR Position", BR.getCurrentPosition());
            telemetry.addData("BL Position", BL.getCurrentPosition());
            telemetry.addData("TL Position", TL.getCurrentPosition());
            telemetry.update();
        }

        TL.setPower(0);
        TR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    /**
     * Метод для поворота робота на заданное количество тиков
     * @param position Количество тиков (положительное - поворот вправо, отрицательное - влево)
     * @param power Мощность моторов (0.0 - 1.0)
     */
    private void turnToPosition(int position, double power) {
        TL.setTargetPosition(position);
        TR.setTargetPosition(-position);
        BL.setTargetPosition(position);
        BR.setTargetPosition(-position);

        TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        TL.setPower(power);
        TR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

        while (opModeIsActive() && (TL.isBusy() || TR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("TR Position", TR.getCurrentPosition());
            telemetry.addData("BR Position", BR.getCurrentPosition());
            telemetry.addData("BL Position", BL.getCurrentPosition());
            telemetry.addData("TL Position", TL.getCurrentPosition());
            telemetry.update();
        }

        TL.setPower(0);
        TR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }
}
