package org.firstinspires.ftc.teamcode.notUsed_trash;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * В этом классе описывается модуль захвата
 */
public class Intaker {
    private DcMotor intake;
    private LinearOpMode intakerOpMode;
    private boolean inited = false;

    /**
     * Создаем захват, как класс внутри opMod-а
     * @param opMode - обычно "this", задает в каком потоке оперирует наш захват
     */
    public Intaker (LinearOpMode opMode){
        intakerOpMode = opMode;
    }

    /**
     * Инициализируем захват. Настраиваем режимы работы для него. Добавляем в конфигурацию
     * @param hwMap - hardwareMap того OpMode, в котором конфигурируется захват
     */
    public void initIntake(HardwareMap hwMap){
        intake = intakerOpMode.hardwareMap.dcMotor.get("intake"); //hwMap.dcMotor.get("intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        inited = true;

        intakerOpMode.telemetry.addLine("Intaker ready!");

    }

    /**
     * Запускаем захват
     * @param power - задает мощность с которой крутится захват
     */
    public void runIntake(double power){
        intake.setPower(power);
    }

    /**
     * Добавляем в телеметрию основную информацию о работе модуля
     */
    public void telemetryIntaker(){
        intakerOpMode.telemetry.addData("intake power: ", intake.getPower());
    }
}
