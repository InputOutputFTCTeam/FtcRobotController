package org.firstinspires.ftc.teamcode.ForNewRC.TeleOps;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoDeep", group = "a")
public class IntoTheDeepServo extends LinearOpMode {
    CRServo grab, proba;
    Servo diver;


    /**
     * lift -
     * proba -  сами клешни -0.56 клешни разжаты   -0.16 - прижаты в плотную
     * grab - -0.3 для того, чтобы выкидвать в корзину,
     */
    double pos = 0.5, pos2 = 0.5;

    @Override
    public void runOpMode(){

        diver = hardwareMap.servo.get("lift");
        grab = hardwareMap.crservo.get("grab");
        proba = hardwareMap.crservo.get("proba");

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

            if(gamepad2.y){
                diver.setPosition(pos);
            }
            if(gamepad2.x){
                grab.setPower(pos);
            }
            if(gamepad2.b){
                proba.setPower(pos);
            }
            telemetry.addData("pos: ", pos);
            telemetry.addLine("lift - y, proba - b ");
            telemetry.addLine("grab - x");

            telemetry.update();
        }

    }

}
