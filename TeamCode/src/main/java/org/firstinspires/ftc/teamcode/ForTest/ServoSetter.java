package org.firstinspires.ftc.teamcode.ForTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoSetter", group = "TeleOps")
public class ServoSetter extends LinearOpMode {
    Servo grab, proba;

    double pos = 0.5;

    @Override
    public void runOpMode(){

        grab = hardwareMap.servo.get("grab");
        proba = hardwareMap.servo.get("proba");

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
            if(gamepad2.x){
                grab.setPosition(pos); //0.7 для
            }
            if(gamepad2.b){
                proba.setPosition(pos);
            }
            telemetry.addData("pos: ", pos);
            telemetry.addLine("grab - x, proba - b ");

            telemetry.update();
        }

    }

}
