package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * В этом телеопе мы можем проверить, в каком положении сервы надо установить качалку на серву и
 * какие значения надо задавать для телеопа.
 *
 */
@TeleOp(name = "lohoSet", group = "testing")
public class LohotronSetter extends LinearOpMode {
    Servo servobox, lohotronMain, lohotron, claw;   //объявили сервы нашего лохотрона
    Servo leftHook1, leftHook2, rightHook1, rightHook2;

    //gamepad 1 - settings; gamepad2 - test
    double pos = 0.5, pos2 = 0.5;       //переменная, которая будет задавать положение каждой серве

    @Override
    public void runOpMode(){
        //задали имена сервам в конфигурации
        servobox = hardwareMap.servo.get("servobox");
        lohotron = hardwareMap.servo.get("lohotron");
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        claw = hardwareMap.servo.get("zahvat");

        leftHook1 = hardwareMap.servo.get("leftHook1");
        leftHook2 = hardwareMap.servo.get("leftHook2");
        rightHook1 = hardwareMap.servo.get("rightHook1");
        rightHook2 = hardwareMap.servo.get("rightHook2");

        waitForStart();
        while(opModeIsActive()){

            if(gamepad2.dpad_up){
                //увеличиваем значение угла на 1.8 градусов
                pos+=0.01;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad2.dpad_up && opModeIsActive()) {
                    telemetry.addLine("RELEASE up BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad2.dpad_down){
                //увеличиваем значение угла на 1.8 градусов
                pos-=0.01;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad2.dpad_down && opModeIsActive()) {
                    telemetry.addLine("RELEASE down BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad2.dpad_right){
                //увеличиваем значение угла на 1.8 градусов
                pos+=0.1;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad2.dpad_right && opModeIsActive()) {
                    telemetry.addLine("RELEASE right BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad2.dpad_left){
                //увеличиваем значение угла на 1.8 градусов
                pos-=0.1;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad2.dpad_left && opModeIsActive()) {
                    telemetry.addLine("RELEASE left BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            // передаем значение в соответствующую серву. записываем на бумажке значение,
            // которое мы только что передали.     (Это конечно не control award... но попробуйте сами написать настройку четырех серв с возможностью сохранения данных! Это займет больше времени, чем у нас есть)
            if(gamepad2.a){
                servobox.setPosition(pos);
            }
            if(gamepad2.y){
                lohotronMain.setPosition(pos);
            }
            if(gamepad2.b){
                lohotron.setPosition(pos);
            }
            if(gamepad2.x){
                claw.setPosition(pos);
            }

            /////////////////////////       положения      для      крюков       /////////////////////////

            if(gamepad1.dpad_left){
                //уменьшаем значение угла на 18 градусов
                pos2-=0.1;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad1.dpad_left && opModeIsActive()) {
                    telemetry.addLine("RELEASE left BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad1.dpad_right){
                //увеличиваем значение угла на 18 градусов
                pos2+=0.1;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad1.dpad_right && opModeIsActive()) {
                    telemetry.addLine("RELEASE right BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad1.dpad_up){
                //увеличиваем значение угла на 1.8 градусов
                pos2+=0.01;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad1.dpad_up && opModeIsActive()) {
                    telemetry.addLine("RELEASE up BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad1.dpad_down){
                //уменьшаем значение угла на 1.8 градусов
                pos2-=0.01;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad1.dpad_down && opModeIsActive()) {
                    telemetry.addLine("RELEASE down BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad1.a){
                leftHook1.setPosition(pos2);
            }
            if(gamepad1.y){
                rightHook1.setPosition(pos2);
            }
            if(gamepad1.b){
                leftHook2.setPosition(pos2);
            }
            if(gamepad1.x){
                rightHook2.setPosition(pos2);
            }

            telemetry.addData("pos: ", pos);
            telemetry.addLine("box - a, main - y, ");
            telemetry.addLine("loh - b, claw - x");

            telemetry.addData("pos2: ", pos2);
            telemetry.addLine("hookMainLeft - a, hookMainRight - y, ");
            telemetry.addLine("hookSmolLeft - b, hookSmolRight - x");

            telemetry.update();
        }
    }

    //@Override
    //public void runOpMode() throws InterruptedException {

    }

