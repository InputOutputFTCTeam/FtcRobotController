package org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * В этом классе описывается работа концувика. Он нужен в нашем роботе для стабильной работы лифта.
 */

public class TouchSensorr {
    LinearOpMode touchSensorOpMode;
    TouchSensor ts;
    public TouchSensorr(LinearOpMode opMode) {
        touchSensorOpMode = opMode;
    }

    /**
     * Инициализация концевика (кнопки). Записывает его имя в конфигурации.
     */
    public void initTouch() {   //
        ts = touchSensorOpMode.hardwareMap.get(TouchSensor.class, "sensor_touch");
    }

    /**
     * Проверяет, нажат ли концевой датчик
     * @return true, если нажат; false, если отпущен.
     */
    public boolean isPressed() {
        return ts.isPressed();
    }

    /**
     * Выводим телеметрию концевика. Нажат или не нажат.
     */
    public void touchTelemetry() {
        touchSensorOpMode.telemetry.addLine(isPressed() ? "pressed" : "not pressed");
    }
}