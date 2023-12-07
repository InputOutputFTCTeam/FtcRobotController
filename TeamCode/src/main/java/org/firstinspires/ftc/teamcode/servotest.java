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
    CRServo sss;

    @Override
    public void runOpMode(){
        //ss = hardwareMap.get(Servo.class, "ss");
        sss = hardwareMap.get(CRServo.class, "s");
        waitForStart();
        while(opModeIsActive()){
            sss.setPower(gamepad2.right_trigger - gamepad1.left_trigger);

            /*ss.setPosition(1);
            sleep(100);
            ss.setPosition(0);
            sleep(100);
            ss.setPosition(1);*/
        }
    }
}
