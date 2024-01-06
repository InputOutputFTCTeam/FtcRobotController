package org.firstinspires.ftc.teamcode.visions;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSensor {
    com.qualcomm.robotcore.hardware.ColorSensor colorSensor;

    public void init (HardwareMap hwMap){
        colorSensor=hwMap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, "sensor_color");
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

    public boolean getHighSpeedLine() {
        float previoustHSVSum;
        previoustHSVSum=getHsv()[0]+getHsv()[1]+getHsv()[2];
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
