package org.firstinspires.ftc.teamcode.robotModules.Sensors;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
public class OurColor {
    ColorSensor colorSensor;
    ElapsedTime timer = new ElapsedTime();
    public void init (HardwareMap hwMap){
        colorSensor=hwMap.get(ColorSensor.class, "sensor_color");
        colorSensor.enableLed(true);
    }

    public float[] getHsv(){
        float hsvValues[] = {0,0,0};
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        return hsvValues;
    }

    public enum LineColor {
        WHITE,
        BLUE,
        RED,
        BLACK
    }
    public LineColor getWhiteLine(){
        if (getHsv()[2]>42 && getHsv()[0]>100 && getHsv()[0]<190 ){ // getHSV[2]:middle gray-35 black-31(last-18)
            return LineColor.WHITE;
        }
        else{
            return LineColor.BLACK;
        }
    }

    public LineColor getCoroledLine(){
        if (getHsv()[0]>190 && getHsv()[0]<300 && getHsv()[2]<50){
            return LineColor.BLUE;
        }
        else{
            if (getHsv()[0]>100 && getHsv()[0]<300 || getHsv()[2]>50){
                return LineColor.BLACK;
            }
            else {
                return LineColor.RED;
            }
        }
    }

    public boolean getHighSpeedLine(){                                  //хз, зачем складывать hsv values. разберемся позже
        float previoustHSVSum;
        previoustHSVSum=getHsv()[0]+getHsv()[1]+getHsv()[2];
        timer.reset();
        while (timer.milliseconds()<10){
        }
        float nextHSVSum;
        nextHSVSum=getHsv()[0]+getHsv()[1]+getHsv()[2];
        if(Math.abs(previoustHSVSum-nextHSVSum)<10){
            return false;
        }
        else{
            return true;
        }

    }

}

/*
public class Color{
    NormalizedColorSensor colorSensor;
    ElapsedTime timer = new ElapsedTime();
    public void init (HardwareMap hwMap){
        colorSensor=hwMap.get(NormalizedColorSensor.class, "sensor_color");
        colorSensor.enableLed(true);
    }

    public float[] getHsv(){
        float hsvValues[] = {0,0,0};
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        android.graphics.Color.colorToHSV(colors.toColor(), hsvValues);
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        return hsvValues;
    }

    public enum LineColor {
        WHITE,
        BLUE,
        RED,
        BLACK
    }
    public LineColor getWhiteLine(){
        if (getHsv()[2]>42 && getHsv()[0]>100 && getHsv()[0]<190 ){ // getHSV[2]:middle gray-35 black-31(last-18)
            return LineColor.WHITE;
        }
        else{
            return LineColor.BLACK;
        }
    }

    public LineColor getCoroledLine(){
        if (getHsv()[0]>190 && getHsv()[0]<300 && getHsv()[2]<50){
            return LineColor.BLUE;
        }
        else{
            if (getHsv()[0]>100 && getHsv()[0]<300 || getHsv()[2]>50){
                return LineColor.BLACK;
            }
            else {
                return LineColor.RED;
            }
        }
    }
    public boolean getHighSpeedLine(){
        float previoustHSVSum;
        previoustHSVSum=getHsv()[0]+getHsv()[1]+getHsv()[2];
        timer.reset();
        while (timer.milliseconds()<10){
        }
        float nextHSVSum;
        nextHSVSum=getHsv()[0]+getHsv()[1]+getHsv()[2];
        if(Math.abs(previoustHSVSum-nextHSVSum)<10){
            return false;
        }
        else{
            return true;
        }

    }

}
*/

