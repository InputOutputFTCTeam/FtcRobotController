package org.firstinspires.ftc.teamcode.onTest.restart;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Hook {
    private Servo leftHook1, leftHook2, rightHook1, rightHook2;
    private boolean inited = true, hooked = false;
    private LinearOpMode hookOpMode;

    public Hook(LinearOpMode opMode){
        hookOpMode = opMode;
    }

    public void initHooks(){
        leftHook1 = hookOpMode.hardwareMap.servo.get("leftHook1");
        leftHook2 = hookOpMode.hardwareMap.servo.get("leftHook2");
        rightHook1 = hookOpMode.hardwareMap.servo.get("rightHook1");
        rightHook2 = hookOpMode.hardwareMap.servo.get("rightHook2");

        hookOpMode.telemetry.addLine("Hook ready!");
    }

    public void telemetryHooks(){
        hookOpMode.telemetry.addData("hooks state: ", hooked);
    }

    public void openHook(){
        leftHook1.setPosition(0.05);
        rightHook1.setPosition(0.05);

        hookOpMode.sleep(100);

        leftHook2.setPosition(1);
        rightHook2.setPosition(1);
    }

    public void closeHook(){
        leftHook2.setPosition(0.07);
        rightHook2.setPosition(0.07);

        hookOpMode.sleep(100);

        leftHook1.setPosition(0.7);
        rightHook1.setPosition(0.7);
    }
    public void switchHook(){
        if(hooked) openHook(); else closeHook();
        hooked = !hooked;
    }

}
