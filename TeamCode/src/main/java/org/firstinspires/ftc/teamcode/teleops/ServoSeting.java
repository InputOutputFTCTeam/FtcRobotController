package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ServoSet")
public class ServoSeting extends LinearOpMode {//
    Servo lohotronMain, lohotron, zahvat;

    double newUpPosition = 0.5, newDownPosition = 0.5; //lohotron
    double newRaisedPosition = 0.5, newLoweredPosition = 0.5; //lohotronMain

    @Override
    public void runOpMode(){
        lohotronMain = hardwareMap.servo.get("lohotronMain");
        lohotron = hardwareMap.servo.get("lohotron");
        zahvat = hardwareMap.servo.get("zahvat");


        waitForStart();
        while(opModeIsActive()){
            if(gamepad2.dpad_right){        //настройка нижней позиции лохотрона
                newDownPosition += 0.05;
                lohotron.setPosition(newDownPosition);
            }
            if(gamepad2.dpad_left){
                newDownPosition -= 0.05;
                lohotron.setPosition(newDownPosition);
            }

            if(gamepad2.b){                 //натсройка верхней позиции лохотрона
                newUpPosition += 0.05;
                lohotron.setPosition(newUpPosition);
            }
            if(gamepad2.x){
                newUpPosition -= 0.05;
                lohotron.setPosition(newUpPosition);
            }



            if(gamepad2.right_bumper){          //настрока поднятой позиции основной сервы (на лифте)
                newRaisedPosition += 0.05;
                lohotronMain.setPosition(newRaisedPosition);
            }
            if(gamepad2.left_bumper){
                newRaisedPosition -= 0.05;
                lohotronMain.setPosition(newRaisedPosition);
            }

            if(gamepad2.right_stick_button){       //настройка опущеной позиции основной сервы (на лифте)
                newLoweredPosition += 0.05;
                lohotronMain.setPosition(newLoweredPosition);
            }
            if(gamepad2.left_stick_button){
                newLoweredPosition -= 0.05;
                lohotronMain.setPosition(newLoweredPosition);
            }


            //проверяем работу...
            if (gamepad1.y) {
                armRaise();     //переворот захвата
            }

            if (gamepad1.a) {
                armLower();     //опускает лохотрон
            }

            if(gamepad1.dpad_up){
                zahvat.setPosition(0);
            }
            if(gamepad1.dpad_down){
                zahvat.setPosition(0.45);
            }

            telemetry.addData("lohotronMain pos: ", lohotronMain.getPosition());
            telemetry.addData("lohotron pos: ", lohotron.getPosition());
            telemetry.addData("zahvat pos: ", zahvat.getPosition());
            telemetry.update();
        }
    }

    public void armRaise(){
        lohotronMain.setPosition(newRaisedPosition);
        sleep(100);
        lohotron.setPosition(newUpPosition);
    }
    public void armLower(){
        lohotron.setPosition(newDownPosition);
        sleep(50);
        lohotronMain.setPosition(newLoweredPosition);
    }

}
