package org.firstinspires.ftc.teamcode.ForNewRC.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColSensor  extends LinearOpMode {
    private ColorSensor colorSensor;
    private double redValue;
    private double greenValue;
    private double blueValue;
    private double alphaValue; // this value for light
    private  double targetValue = 1000;

    public void runOpMode() {
    }

    public void initColSensor() {
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    public void getColor() {
        redValue =  colorSensor.red();
        greenValue = colorSensor.green();
        blueValue = colorSensor.blue();
        alphaValue = colorSensor.alpha();
    }

    public void colorTelemetry() {
        telemetry.addData("red value: ", "%.2f", redValue);
        telemetry.addData("green value: ", "%.2f", greenValue);
        telemetry.addData("blue value: ", "%.2f", blueValue);
        telemetry.addData("alpha value: ", "%.2f", alphaValue);
    }
}
