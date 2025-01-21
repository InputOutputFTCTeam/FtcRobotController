package org.firstinspires.ftc.teamcode.ForNewRC;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * В этом классе описывается работа концувика. Он нужен в нашем роботе для стабильной работы лифта.
 */

public class TouchSensorModule {
    LinearOpMode touchSensorOpMode;
    com.qualcomm.robotcore.hardware.TouchSensor ts;
    public TouchSensorModule(LinearOpMode opMode) {
        touchSensorOpMode = opMode;
    }

    /**
     * Инициализация концевика (кнопки). Записывает его имя в конфигурации.
     */
    public void initTouch() {   //
        ts = touchSensorOpMode.hardwareMap.get(com.qualcomm.robotcore.hardware.TouchSensor.class, "sensor_touch");
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