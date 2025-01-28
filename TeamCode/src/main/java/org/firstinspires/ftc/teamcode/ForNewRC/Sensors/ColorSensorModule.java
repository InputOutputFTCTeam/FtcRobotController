package org.firstinspires.ftc.teamcode.ForNewRC.Sensors;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class ColorSensorModule {
    private NormalizedColorSensor colorSensor;
    private final float[] hsvValues = new float[3];
    private final float[] rgbValues = new float[3];
    private NormalizedRGBA colors;
    private LinearOpMode colorSensorOpMode = null;

    public ColorSensorModule(LinearOpMode opMode) {
        colorSensorOpMode = opMode;
    }

    /**
     * инициализируем колор сенсор
     */
    public void initColorSensor() {
        colorSensor = colorSensorOpMode.hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        setCSGain();    //установка значения чувствительности датчика
        colors = colorSensor.getNormalizedColors();     //сканирование цвета
        Color.colorToHSV(colors.toColor(), hsvValues);  //перевод сканированного цвета в HSV
        colorRGB();                                     //перевод сканированного цвета в RGB
    }

    /**
     * Метод устанавливающий чувствительность датчика цвета. Без параметра задается 30
     */
    public void setCSGain() {  //задаём баланс белого (gain - баланс белого)
        float gain = 30;
        colorSensor.setGain(gain);
    }

    /**
     * Сканирование цвета объекта перед датчиком, передает значения в HSV формате
     */
    public float[] colorHSV() {
        colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues;
    }

    /**
     * Сканирование цвета объекта перед датчиком, передает значения в RGB формате
     */
    public float[] colorRGB() {
        colors = colorSensor.getNormalizedColors();
        rgbValues[0] = colors.red;
        rgbValues[1] = colors.green;
        rgbValues[2] = colors.blue;
        return rgbValues;
    }

    /**
     * Обновляет значения с датчика цвета
     */
    public void updateColor() {
        colorHSV();
        colorRGB();
    }
}
