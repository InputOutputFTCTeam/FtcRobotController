package org.firstinspires.ftc.teamcode.robotModules.Sensors;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Этот класс описывает датчик цвета и его методы
 */

public class ColorSensorModule {
    private NormalizedColorSensor colorSensor;
    private final float[] hsvValues = new float[3];
    private final float[] rgbValues = new float[3];
    private LinearOpMode colorSensorOpMode = null;
    private NormalizedRGBA colors;

    public ColorSensorModule(LinearOpMode opMode) {
        colorSensorOpMode = opMode;
    }

    public void initColorSensor() {
        colorSensor = colorSensorOpMode.hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        //((SwitchableLight) colorSensor).enableLight(true);
        setCSGain();
        colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
        colorRGB();
    }

    public void setCSGain() {  //задаём баланс белого (gain - баланс белого)
        float gain = 30;
        colorSensor.setGain(gain);
    }

    public void setCSGain(float gain) {
        colorSensor.setGain(gain);
    }

    public float[] colorHSV() {
        colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues;
    }

    public float[] colorRGB() {
        colors = colorSensor.getNormalizedColors();
        rgbValues[0] = colors.red;
        rgbValues[1] = colors.green;
        rgbValues[2] = colors.blue;
        return rgbValues;
    }

    public double colorDistanceMM() {
        return ((DistanceSensor) colorSensor).getDistance(DistanceUnit.MM);
    }

    public enum colorsField {
        WHITE,
        BLACK,
        RED,
        BLUE,
        idkWtfIsThisColor
    }

    public enum colorsPixels {
        WHITE,
        YELLOW,
        PURPLE,
        GREEN,
        idkWtfIsThisColor
    }

    public void updateColor() {
        colorHSV();
        colorRGB();
        colorSensorOpMode.sleep(5);
    }

    public colorsField getColorOfField() {
        //TODO: написать условия, при которых будут соответствующие цвета
        // (можно через ИЛИ || добавить условия в RGB. или сделать отдельным методом,
        // возращающим colorOf значения

        if (colorRGB()[0] > 0.9 && colorRGB()[1] > 0.9 && colorRGB()[2] > 0.9)
            return colorsField.WHITE;
        if (colorRGB()[0] < 0.3 && colorRGB()[1] < 0.3 && colorRGB()[2] < 0.3)
            return colorsField.BLACK;
        if (colorHSV()[0] > 180 && colorHSV()[0] < 260 && colorHSV()[1] > 0.3 && colorHSV()[2] > 0.5)
            return colorsField.BLUE;
        if (!(colorHSV()[0] > 30 && colorHSV()[0] < 320) && colorHSV()[1] > 0.3 && colorHSV()[2] > 0.5)
            return colorsField.RED;
        return colorsField.idkWtfIsThisColor;
    }

    public colorsPixels getColorOfPixel(float[] color) {
        if (colorRGB()[0] > 0.9 && colorRGB()[1] > 0.9 && colorRGB()[2] > 0.9)
            return colorsPixels.WHITE;
        if (colorHSV()[0] > 35 && colorHSV()[0] < 60 && colorHSV()[1] > 0.75 && colorHSV()[2] > 0.75)
            return colorsPixels.YELLOW;
        if (colorHSV()[0] > 260 && colorHSV()[0] < 320 && colorHSV()[1] > 0.75 && colorHSV()[2] > 0.75)
            return colorsPixels.PURPLE;
        if (colorHSV()[0] > 70 && colorHSV()[0] < 170 && colorHSV()[1] > 0.75 && colorHSV()[2] > 0.75)
            return colorsPixels.GREEN;
        return colorsPixels.idkWtfIsThisColor;
    }

    public void telemetryColor() {
        colorSensorOpMode.telemetry.addLine(
                "red: " + colorRGB()[0] +
                        "\ngreen: " + colorRGB()[1] +
                        "\nblue: " + colorRGB()[2]
        );
        colorSensorOpMode.telemetry.addLine(
                "\nhue: " + colorHSV()[0] +
                        "\nsaturation: " + colorHSV()[1] +
                        "\nvalue: " + colorHSV()[2]
        );
        colorSensorOpMode.telemetry.addData("\ni see ", getColorOfField());
    }
}