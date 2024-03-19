package org.firstinspires.ftc.teamcode.robotModules.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * В этом классе описывается работа концувика. Он нужен в нашем роботе для стабильной работы лифта.
 */

public class TouchSensorr {     //Объявляем концевик
    LinearOpMode touchSensorOpMode;
    TouchSensor ts;
    public TouchSensorr(LinearOpMode opMode) {
        touchSensorOpMode = opMode;
    }

    public void initTouch() {   //Инициализируем концевик
        ts = touchSensorOpMode.hardwareMap.get(TouchSensor.class, "sensor_touch");
    }

    public boolean isPressed() {
        return ts.isPressed();
    }

    public void touchTelemetry() {  //Выводим телеметрию
        touchSensorOpMode.telemetry.addLine(isPressed() ? "pressed" : "not pressed");
    }
}