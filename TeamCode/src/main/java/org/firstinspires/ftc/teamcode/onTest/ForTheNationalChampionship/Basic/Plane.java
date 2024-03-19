package org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * В этом классе описывается пускатель самолёта
 */

public class Plane {

    private Servo angle, push;
    private LinearOpMode planeOpMode;
    private boolean inited = false;

    /**
     * Создаем самолет, как класс внутри opMod-а
     * @param opMode - обычно "this", задает в каком потоке оперирует наш самолетопускатель
     */
    public Plane (LinearOpMode opMode){
        planeOpMode = opMode;
    }

    /**
     * Инициализация самолетопускателя для opMode. Добавление в конфигурацию
     */
    public void initPlane(){
        angle = planeOpMode.hardwareMap.servo.get("angle");
        push = planeOpMode.hardwareMap.servo.get("push");
        inited = true;
        pushDown();
        angleUp();
        planeOpMode.telemetry.addLine("Plane ready!");
    }

    /**
     * Выставляем оптимальный угол запуска
     */
    public void angleUp() {
        angle.setPosition(0.92);
    }
    public void angleDown() {
        angle.setPosition(0.7);
    }

    /**
     * Включаем удерживающий гак в правильном положении
     */
    public void pushDown() {
        push.setPosition(0.51);
    }

    /**
     * Отпускаем гак, толкатель ускоряется, самолет взлетает
     */
    public void pushUp() {
        push.setPosition(0.65);
    }

    /**
     * Добавляем в телеметрию информацию о положении ключевых серв
     */
    public void telemetryPlane(){
        planeOpMode.telemetry.addData("pushPosition", push.getPosition());
        planeOpMode.telemetry.addData("anglePosition", angle.getPosition());
    }
}
