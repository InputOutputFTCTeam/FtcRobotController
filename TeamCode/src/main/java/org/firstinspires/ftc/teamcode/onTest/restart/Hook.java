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
        //leftHook1.setPosition();      //TODO: подобрать значения для четырех серв
        //rightHook1.setPosition();

        hookOpMode.sleep(100);

        //leftHook2.setPosition();
        //rightHook2.setPosition();
    }

    public void closeHook(){
        //leftHook2.setPosition();      //TODO: подобрать значения для четырех серв
        //rightHook2.setPosition();

        hookOpMode.sleep(100);

        //leftHook1.setPosition();
        //rightHook1.setPosition();
    }
    public void switchHook(){
        if(hooked) openHook(); else closeHook();
        hooked = !hooked;
    }

}
