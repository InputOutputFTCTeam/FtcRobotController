package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "ParkingAuto", group = "Autonomous")
public class ParkingAuto extends LinearOpMode{

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
        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Ожидание старта автономного режима
        waitForStart();
        if (opModeIsActive()) {
            move(0.3, 10000);
        }
    }

    /**
     * Метод для движения робота без использования энкодеров
     * @param power Мощность моторов (от -1.0 до 1.0)
     * @param time Время движения в миллисекундах
     */
    private void move(double power, int time) {
        TL.setPower(-power);
        TR.setPower(power);
        BL.setPower(power);
        BR.setPower(-power);
        sleep(time);
        stopMotors();
    }

    /**
     * Метод для поворота робота без использования энкодеров
     * @param power Мощность поворота (от -1.0 до 1.0)
     * @param time Время поворота в миллисекундах
     */
    private void turn(double power, int time) {
        TL.setPower(power);
        TR.setPower(power);
        BL.setPower(power);
        BR.setPower(power); //минус вправо
        sleep(time);
        stopMotors();
    }

    /**
     * Остановка всех моторов
     */
    private void stopMotors() {
        TL.setPower(0);
        TR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }
}

