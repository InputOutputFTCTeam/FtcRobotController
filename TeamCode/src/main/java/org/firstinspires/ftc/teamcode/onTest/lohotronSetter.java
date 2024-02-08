package org.firstinspires.ftc.teamcode.onTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "lohoSet", group = "alfa")
public class lohotronSetter extends LinearOpMode {
    Servo servobox, lohotronMain, lohotron, claw;
    //gamepad 1 - settings; gamepad2 - test
    double pos = 0.5;
    @Override
    public void runOpMode(){
        servobox = hardwareMap.servo.get("servobox");
        lohotron = hardwareMap.servo.get("lohotron");
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        claw = hardwareMap.servo.get("zahvat");

        waitForStart();
        while(opModeIsActive()){

            if(gamepad2.dpad_up){
                //увеличиваем значение угла запуска на 9 градусов
                pos+=0.01;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad2.dpad_up && opModeIsActive()) {
                    telemetry.addLine("RELEASE up BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad2.dpad_down){
                //увеличиваем значение угла запуска на 9 градусов
                pos-=0.01;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad2.dpad_down && opModeIsActive()) {
                    telemetry.addLine("RELEASE up BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

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
            telemetry.addData("pos: ", pos);
            telemetry.addLine("box - a, main - y, ");
            telemetry.addLine("loh - b, claw - x");
            telemetry.update();
        }
    }
}
