package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.TeleOps;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoDeep", group = "a")
public class IntoTheDeepServo extends LinearOpMode {
    Servo diver, grab, proba;



    double pos = 0.5, pos2 = 0.5;

    @Override
    public void runOpMode(){

        diver = hardwareMap.servo.get("diver");
//        grab = hardwareMap.servo.get("grab");
//        proba = hardwareMap.servo.get("proba");

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
            //if(gamepad2.a){
            //servobox.setPosition(pos);
            //}
            if(gamepad2.y){
                diver.setPosition(pos);
            }
//            if(gamepad2.b){
//                grab.setPosition(pos);
//            }
//            if(gamepad2.x){
//                proba.setPosition(pos);
//            }
            telemetry.addData("pos: ", pos);
            telemetry.addLine("diver - y, ");
//            telemetry.addLine("loh - b, claw - x");

            telemetry.update();
        }

    }

}
