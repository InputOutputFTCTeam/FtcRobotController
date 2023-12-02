package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "servelat")
public class servotest extends LinearOpMode {
    Servo ss;
    CRServo sss;

    @Override
    public void runOpMode(){
        ss = hardwareMap.get(Servo.class, "ss");
        sss = hardwareMap.get(CRServo.class, "sss");
        waitForStart();
        if(opModeIsActive()){
            for(int i = 0; i<3; i++){
                sss.setPower(1);
                sleep(500);
                sss.setPower(0);
                sleep(100);
                sss.setPower(-1);
            }

            ss.setPosition(1);
            sleep(100);
            ss.setPosition(0);
            sleep(100);
            ss.setPosition(1);
        }
    }
}
