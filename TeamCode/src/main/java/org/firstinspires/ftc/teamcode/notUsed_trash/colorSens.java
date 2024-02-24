package org.firstinspires.ftc.teamcode.notUsed_trash;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@Disabled
@TeleOp(name = "skiesOfBlue", group = "alfa")
public class colorSens extends LinearOpMode {
    NormalizedColorSensor colorSensor;
    NormalizedRGBA colors;

    float[] hsvValues = new float[3];

    @Override
    public void runOpMode() {

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);   //люстра
        }

        //было бы здорово понимать, что происходит с данными робота и геймпадов, пока кнопка старт не была нажата
        while (!isStarted()) {
            //composeTelemetry();
            Color.colorToHSV(colorSensor.getNormalizedColors().toColor(), hsvValues);
            telemetry.addLine("hue: " + hsvValues[0] + "\nsaturation: " + hsvValues[1] + "\nvalue: " + hsvValues[2]);
            telemetry.update();
            //idle();
        }

        waitForStart();
        while (opModeIsActive()) {
            Color.colorToHSV(colorSensor.getNormalizedColors().toColor(), hsvValues);
            telemetry.addLine("hue: " + hsvValues[0] + "\nsaturation: " + hsvValues[1] + "\nvalue: " + hsvValues[2]);

            if (!isOnBlueLine()) {
                telemetry.addLine("hue: " + hsvValues[0] + "\nsaturation: " + hsvValues[1] + "\nvalue: " + hsvValues[2]);
                telemetry.addLine("i'm blue dabudidabudae");
            }

            telemetry.update();
        }
    }

    //функция опроса датчика  цвета
    boolean isOnBlueLine() {
        boolean res = false;
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);

        if (hsvValues[0] > 190 && hsvValues[0] < 250) { //синий цвет в этом диапозоне
            res = true;
        }
        return res;
    }

    boolean isOnRedLine() {
        boolean res = false;
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);

        if ((hsvValues[0] > 0 && hsvValues[0] < 50) || hsvValues[0] > 320 && hsvValues[0] < 360) { //красный цвет в этом диапозоне
            res = true;
        }
        return res;
    }
}
