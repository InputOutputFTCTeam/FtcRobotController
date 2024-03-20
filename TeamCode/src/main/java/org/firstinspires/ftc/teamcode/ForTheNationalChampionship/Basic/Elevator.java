package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * В этом классе описывается подъемник пикселей
 */

public class Elevator {
    private LinearOpMode liftOpMode;
    private DcMotor lift;

    /**
     * Создаем лифт, как класс внутри opMod-а
     * @param opMode - обычно "this", задает в каком потоке оперирует наш лифт
     */
    public Elevator(LinearOpMode opMode){
        liftOpMode = opMode;
    }

    /**
     * Инициализация самолетопускателя для opMode. Добавление в конфигурацию
     */
    public void initLift(){
        lift = liftOpMode.hardwareMap.dcMotor.get("lift");

        liftOpMode.telemetry.addLine("Lift ready!");
    }

    /**
     * Устанавливаем направление для вращения мотора лифта
     * @param dir - DcMotorSimple.Direction.FORWARD или REVERSE
     */
    public void liftDirection(DcMotorSimple.Direction dir){
        lift.setDirection(dir);
    }

    /**
     * Устанавливаем режим работы для лифта
     * @param mode проще работать в режиме DcMotor.RunMode.RUN_WITHOUT_ENCODER
     */
    public void liftMode(DcMotor.RunMode mode){
        lift.setMode(mode);
    }

    /**
     * Запуск лифта
     * @param power - мощность мотора Лифта (от -1 до 1, где 0 - остановка (инерция?))
     */
    public void run(double power){
        lift.setPower(power);
    }

    /**
     * Добавляем в телеметрию информацию о мощности с которой работает мотор
     */
    public void telemetryLift(){
        liftOpMode.telemetry.addData("current power: ", lift.getPower());
    }
}
