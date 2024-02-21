package org.firstinspires.ftc.teamcode.onTest;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

/*
    TODO: проверить enccoder.java сначала!
        проверить цвета внутри инициализации
        протестировать остальную часть этого opMode
 */

@Autonomous(name = "skiesOfBlue", group = "alfa")
public class colorSens extends LinearOpMode {
    //создать датчик цвета и моторы
    DcMotor TL, TR, BL, BR;
    NormalizedColorSensor colorSensor;
    NormalizedRGBA colors;

    float hsvValues[] = new float[3];

    int startPositionTL, startPositionTR, startPositionBL, startPositionBR;

    @Override
    public void runOpMode() {
        //init sensor and motors
        //записываем моторы и сервы для проверки конфигурационным файлом (а может быть можно создать xml файл и разметить в нем конфигурацию, чтобы никогда не приходилось ее настраивать???)
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        //задаем моторам направление вращения
        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        //включаем режим работы по энкодерам. все провода должны быть подкючены
        TL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);   //люстра
        }

        //было бы здорово понимать, что происходит с данными робота и геймпадов, пока кнопка старт не была нажата
        while (!isStarted()) {
            //composeTelemetry();
            Color.colorToHSV(colorSensor.getNormalizedColors().toColor(), hsvValues);
            telemetry.addData("hue: %f \nsaturation: %f \nvalue: %f", hsvValues);
            telemetry.update();
//
            idle();
        }

        waitForStart();
        if (opModeIsActive()) {
            if (!isOnBlueLine()) {
                //moveBy4Encoders(0, 0.5, 0, 100, true);
                sleep(500);
            }
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

    //полиморфизм. в одном из методов не проверяется наличие линии, чтобы тот в котором проверяется наличие линии мог сделать небольшой проезд
    /*public void moveBy4Encoders(double x, double y, double r, double distanceCM){
        startPositionTL = TL.getCurrentPosition();
        startPositionTR = TR.getCurrentPosition();
        startPositionBL = BL.getCurrentPosition();
        startPositionBR = BR.getCurrentPosition();

        //какую дистанцию проехать надо? (в тиках энкодера)
        //тут можно написать формулу для пересчета тиков энкодера в дистанцию //1440 тиков на оборот //4 дюйма диаметр колеса
        double revolutions = distanceCM / 3 * 2 / (Math.PI * 4 * 2.54);
        double ticks = revolutions * 1440; // 1440 ticks per revolution on TETRIX TorqueNADO 1:60 gearmotor
        //записать моторам мощность, пока они не достигнут целевой позиции
        while(opModeIsActive() && ( (Math.abs(Math.abs(TL.getCurrentPosition()) - Math.abs(startPositionTL))) < ticks ||
                (Math.abs(Math.abs(BL.getCurrentPosition()) - Math.abs(startPositionBL))) < ticks ||
                (Math.abs(Math.abs(TR.getCurrentPosition()) - Math.abs(startPositionTR))) < ticks ||
                (Math.abs(Math.abs(BR.getCurrentPosition()) - Math.abs(startPositionBR))) < ticks   )   )
        {
            //даем моторам мощность
            move(x, y, r);

            //выводим абсолютную разницу между нынешним и целевым значениями
            telemetry.addData(" TL diff: ", Math.abs(Math.abs(TL.getCurrentPosition()) - ticks));
            telemetry.addData(" TR diff: ", Math.abs(Math.abs(TR.getCurrentPosition()) - ticks));
            telemetry.addData(" BL diff: ", Math.abs(Math.abs(BL.getCurrentPosition()) - ticks));
            telemetry.addData(" BR diff: ", Math.abs(Math.abs(BR.getCurrentPosition()) - ticks));
        }
        stopp();

        telemetry.addLine();
        telemetry.addLine();
        composeTelemetry();
        telemetry.update();
    }*/

    /*public void moveBy4Encoders(double x, double y, double r, double distanceCM, boolean lines){
        startPositionTL = TL.getCurrentPosition();
        startPositionTR = TR.getCurrentPosition();
        startPositionBL = BL.getCurrentPosition();
        startPositionBR = BR.getCurrentPosition();

        //какую дистанцию проехать надо? (в тиках энкодера)
        //тут можно написать формулу для пересчета тиков энкодера в дистанцию //1440 тиков на оборот //4 дюйма диаметр колеса
        double revolutions = distanceCM / 3 * 2 / (Math.PI * 4 * 2.54);
        double ticks = revolutions * 1440; // 1440 ticks per revolution on TETRIX TorqueNADO 1:60 gearmotor
        //записать моторам мощность, пока они не достигнут целевой позиции
        while(opModeIsActive() && ( (Math.abs(Math.abs(TL.getCurrentPosition()) - Math.abs(startPositionTL))) < ticks ||
                (Math.abs(Math.abs(BL.getCurrentPosition()) - Math.abs(startPositionBL))) < ticks ||
                (Math.abs(Math.abs(TR.getCurrentPosition()) - Math.abs(startPositionTR))) < ticks ||
                (Math.abs(Math.abs(BR.getCurrentPosition()) - Math.abs(startPositionBR))) < ticks   )   )
        {
            //даем моторам мощность
            move(x, y, r);
            if(lines) {

                //проверяем, не наехали мы уже на линию
                if (isOnBlueLine()) {
                    //на этой строчке можно написать дополнительный небольшой проезд, про который говорилось в строке со словом полиморфизм
                    moveBy4Encoders(0,0.2,0,5);
                    stopp();
                    telemetry.addLine("BLUE");
                    telemetry.update();
                    break;
                }

                if (isOnRedLine()) {
                    //на этой строчке можно написать дополнительный небольшой проезд, про который говорилось в строке со словом полиморфизм
                    moveBy4Encoders(0,0.2,0,5);
                    stopp();
                    telemetry.addLine("RED");
                    telemetry.update();
                    break;
                }
            }

            //выводим абсолютную разницу между нынешним и целевым значениями
            telemetry.addData(" TL diff: ", Math.abs(Math.abs(TL.getCurrentPosition()) - ticks));
            telemetry.addData(" TR diff: ", Math.abs(Math.abs(TR.getCurrentPosition()) - ticks));
            telemetry.addData(" BL diff: ", Math.abs(Math.abs(BL.getCurrentPosition()) - ticks));
            telemetry.addData(" BR diff: ", Math.abs(Math.abs(BR.getCurrentPosition()) - ticks));
        }
        stopp();

        telemetry.addLine();
        telemetry.addLine();
        composeTelemetry();
        telemetry.update();
    }*/

    public void move(double x, double y, double r) {
        TR.setPower(-x - y + r);
        BR.setPower(x - y + r);
        BL.setPower(x + y + r);
        TL.setPower(-x + y + r);
    }

    public void stopp() {
        move(0, 0, 0);
    }

    /*void composeTelemetry(){
        //значения rgb и hsv с датчика цвета
        colors = colorSensor.getNormalizedColors();     //получить значения с датчика в стандартном виде в RGB палитре
        Color.colorToHSV(colors.toColor(), hsvValues);  //перевод значений из датчика в массив, хранящий значения hsv палитры

        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
        telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0])
                .addData("Saturation", "%.3f", hsvValues[1])
                .addData("Value", "%.3f", hsvValues[2]);
        telemetry.addLine(isOnBlueLine() ? "BLUE" : "NOT BLUE");
        telemetry.addLine(isOnRedLine() ? "RED" : "NOT RED");

        //смотрим положение моторов
        telemetry.addLine()
                .addData("TR pos: ", TR.getCurrentPosition())
                .addData("TL pos: ", TL.getCurrentPosition())
                .addData("BR pos: ", BR.getCurrentPosition())
                .addData("BL pos: ", BL.getCurrentPosition());

        //смотрим мощность на моторах
        telemetry.addLine("motors power ")
                .addData("TR: ", TR.getPower())
                .addData("TL: ", TL.getPower())
                .addData("BR: ", BR.getPower())
                .addData("BL: ", BL.getPower());
    }*/
}
