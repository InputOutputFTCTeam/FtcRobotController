package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
// это блять как его... автоном чтобы тестить сервы!
@TeleOp(name = "servelat")
public class servotest extends LinearOpMode {
    //Servo ss;
    Servo sss;

    @Override
    public void runOpMode(){
        //ss = hardwareMap.get(Servo.class, "ss");
        sss = hardwareMap.get(Servo.class, "s");
        waitForStart();
        while(opModeIsActive()){
            sss.setPosition((gamepad2.right_trigger - gamepad1.left_trigger) * 2 - 1); //допустим я сделал изменение в проекте, чтобы сделать pull request
            telemetry.addData("ser1 pow: ", sss.getPosition());
            telemetry.addData("gamepad set pow: ", gamepad2.right_trigger - gamepad2.left_trigger);
            telemetry.update();
            /*ss.setPosition(1);
            sleep(100);
            ss.setPosition(0);
            sleep(100);
            ss.setPosition(1);*/
            //типо еще две строки
        }
    }
}
