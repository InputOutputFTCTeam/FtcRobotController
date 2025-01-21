package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors;

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

    /**
     * Конструктор класса датчика цвета
     * @param opMode передает конструктору информацию, в каком OpMode будет использоваться сенсор
     */
    public ColorSensorModule(LinearOpMode opMode) {
        colorSensorOpMode = opMode;
    }

    /**
     * Инициализация датчика цвета
     */
    public void initColorSensor() {
        colorSensor = colorSensorOpMode.hardwareMap.get(NormalizedColorSensor.class, "sensor_color");   //название в конфигурации
        //((SwitchableLight) colorSensor).enableLight(true);
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
     * Метод устанавливающий чувствительность датчика цвета
     *
     * @param gain собственное значение для чувствительности. Всегда должно быть больше 1
     */
    public void setCSGain(double gain) {
        colorSensor.setGain((float)gain);
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
     * Использует датчик цвета, как сущность датчика расстояния
     * @return значение расстояния в мм
     */
    public double colorDistanceMM() {
        return ((DistanceSensor) colorSensor).getDistance(DistanceUnit.MM);
    }

    /**
     * Список цветов разметки на поле
     */
    public enum colorsField {
        WHITE,
        BLACK,
        RED,
        BLUE,
        idkWtfIsThisColor
    }

    /**
     * Список цветов пикселя
     */
    public enum colorsPixels {
        WHITE,
        YELLOW,
        PURPLE,
        GREEN,
        idkWtfIsThisColor
    }

    /**
     * Обновляет значения с датчика цвета
     */
    public void updateColor() {
        //colorSensorOpMode.sleep(2);
        colorHSV();
        colorRGB();
    }

    /**
     * Метод сравнивающий последнее полученное HSV значение датчика цвета с заданными значениями цветов поля и передает это значение, как название цвета.
     * @return объект списка цветов разметки поля
     */
    public colorsField getColorOfField() {

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

    /**
     * Метод сравнивающий последнее полученное HSV значение датчика цвета с заданными значениями цветов пикселей и передает это значение, как название цвета.
     * @return объект списка цветов пикселей
     */
    public colorsPixels getColorOfPixel() {
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

    /**
     * Конструирует телеметрию датчика цвета - значения RGB, HSV и название цвета.
     */
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