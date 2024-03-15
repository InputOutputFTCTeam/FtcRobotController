package org.firstinspires.ftc.teamcode.robotModules.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TouchSensor {
    LinearOpMode touchSensorOpMode;
    TouchSensor ts;
    public TouchSensor(LinearOpMode opMode) {
        touchSensorOpMode = opMode;
    }

    public void initTouch() {
        ts = touchSensorOpMode.hardwareMap.get(TouchSensor.class, "sensor_touch");
    }

    public boolean isPressed() {
        return ts.isPressed();
    }

    public void touchTelemetry() {
        touchSensorOpMode.telemetry.addLine(isPressed() ? "pressed" : "not pressed");
    }
}