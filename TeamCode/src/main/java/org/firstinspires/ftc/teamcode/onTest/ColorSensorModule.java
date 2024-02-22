package org.firstinspires.ftc.teamcode.onTest;

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
        ((SwitchableLight) colorSensor).enableLight(true);
        setCSGain();
        colors = colorSensor.getNormalizedColors();
    }

    public void setCSGain() {
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

    public enum colorOf {
        WHITE,
        BLACK,
        BLUE,   //line colors
        RED,
        YELLOW, //pixel colors
        PURPLE,
        GREEN
    }

    public colorOf getColorOf() {
        boolean condition = false;
        //TODO: написать условия, при которых будут соответствующие цвета
        // (можно через ИЛИ || добавить условия в RGB. или сделать отдельным методом,
        // возращающим colorOf значения

        if(condition)
            return colorOf.BLACK;
        if(condition)
            return colorOf.WHITE;
        if(condition)
            return colorOf.BLUE;
        if(colorHSV()[0] > 0 && colorHSV()[0] < 50 && colorHSV()[1] > 0.5 && colorHSV()[2] > 0.5)
            return colorOf.RED;
        if(condition)
            return colorOf.YELLOW;
        if(condition)
            return colorOf.PURPLE;
        if(condition)
            return colorOf.GREEN;

        return null;
    }
}
