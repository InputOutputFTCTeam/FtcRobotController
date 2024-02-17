package org.firstinspires.ftc.teamcode.robotModules.Basic;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Этот класс описывает работу лохотрона на нашем роботе
 */

public class Lohotron {
    private Servo perevorot, main, claw;
    private LinearOpMode lohotronOpMode = null; //объект описывающий опмод, в котором будет использоваться наш лохотрон
    private boolean down = false;

    /**
     * Создаем лохотрон, как класс внутри opMod-а
     * @param opMode - обычно "this", задает в каком потоке оперирует наш лохотрон
     */
    public Lohotron(LinearOpMode opMode){
        lohotronOpMode = opMode;
    }

    /**
     * Инициализация лохотрона для opMode. Добавление в конфигурацию
     * @param hwMap - hardwareMap того OpMode в котором запускается робот
     */
    public void initLohotron(HardwareMap hwMap){
        perevorot = hwMap.servo.get("lohotron");
        main =      hwMap.servo.get("lohotronMain");
        claw =      hwMap.servo.get("zahvat");

        lohotronOpMode.telemetry.addLine("Lohotron ready!");
    }

    /**
     * Добавляем в телементрию информацию о положении ключевых механизмов
     */
    public void lohotronTelemetry(){
        lohotronOpMode.telemetry.addData("lohotron ", down ? "opushen" : "podniat");
        lohotronOpMode.telemetry.addData("lohotron position: ", perevorot.getPosition());
        lohotronOpMode.telemetry.addData("lohotronMain position: ", main.getPosition());
        lohotronOpMode.telemetry.addData("zahvat position: ", claw.getPosition());
        lohotronOpMode.telemetry.addData("zahvacheno: ", isClawClosed());
    }

    /**
     * Поднимает лохотрон
     */
    public void armRaiser(){
        main.setPosition(0.64);
        lohotronOpMode.sleep(150);
        perevorot.setPosition(0);
        down = false;
        lohotronOpMode.sleep(75);
    }

    /**
     * Опускает лохотрон
     */
    public void armLowerer(){
        perevorot.setPosition(0.88);            ////Тестить это
        lohotronOpMode.sleep(150);
        main.setPosition(0.03);
        down = true;
        lohotronOpMode.sleep(75);
    }

    /**
     * Ставим лохотрон в серединное положение
     */
    public void armMid(){
        main.setPosition(0.08);
        perevorot.setPosition(0.95);
        down = false;
    }

    private boolean armRaised = false;

    /**
     * Поднимаем и опускаем лохотрон с помощью одной кнопки
     */
    public void armLogicalRaise_Lower(){
        if(armRaised) armRaiser(); else armLowerer();
        armRaised = !armRaised;
    }

    /**
     * Держать пиксель
     */
    public void closeClaw(){
        claw.setPosition(0.1);
    }

    /**
     * Отпустить пиксель
     */
    public void openClaw(){
        claw.setPosition(0);
    }

    /**
     * Логическая функция, которая возвращает информацию о нынешнем положении захвата
     * @return
     */
    public boolean isClawClosed(){
        return claw.getPosition() == 0.15;
    }
}
