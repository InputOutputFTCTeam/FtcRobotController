package org.firstinspires.ftc.teamcode.robotModules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class Hook {
    private Servo leftHook1, rightHook1;
    private CRServo leftHook2, rightHook2;
    private boolean inited = true, hooked = false;
    private LinearOpMode hookOpMode;

    public Hook(LinearOpMode opMode){
        hookOpMode = opMode;
    }

    public void initHooks(){
        leftHook1 = hookOpMode.hardwareMap.servo.get("leftHook1");
        leftHook2 = hookOpMode.hardwareMap.crservo.get("leftHook2");
        rightHook1 = hookOpMode.hardwareMap.servo.get("rightHook1");
        rightHook2 = hookOpMode.hardwareMap.crservo.get("rightHook2");

        hookOpMode.telemetry.addLine("Hook ready!");
    }

    public void telemetryHooks(){
        hookOpMode.telemetry.addData("hooks state: ", hooked);
    }

    public void openHook(){
        leftHook1.setPosition(0.010);
        rightHook1.setPosition(0.010);

        hookOpMode.sleep(100);

        leftHook2.setPower(-1);
        rightHook2.setPower(1);

        hookOpMode.sleep(50);
    }

    public void closeHook(){
        leftHook2.setPower(1);
        rightHook2.setPower(1);

        hookOpMode.sleep(1000);

        leftHook1.setPosition(-0.3);
        rightHook1.setPosition(0.5);

        hookOpMode.sleep(50);
    }
    public void switchHook(){
        if(hooked) openHook(); else closeHook();
        hooked = !hooked;
    }

}
