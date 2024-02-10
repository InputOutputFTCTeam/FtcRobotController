package org.firstinspires.ftc.teamcode.onTest.restart;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class BackupCatch {
    private LinearOpMode backOpMode;
    private boolean inited = false, grabbed = false;
    private Servo left, right;

    public BackupCatch(LinearOpMode opMode){
        backOpMode = opMode;
    }
    public void initBack(){
        left = backOpMode.hardwareMap.servo.get("drop1");
        right = backOpMode.hardwareMap.servo.get("drop2");
        inited = true;
    }
    public void grab(){
        left.setPosition(0.6);   //TODO: подобрать значение
        right.setPosition(0.6);   //TODO: подобрать значение
        grabbed = true;
    }
    public void ungrab(){
        left.setPosition(0);   //TODO: подобрать значение
        right.setPosition(0);   //TODO: подобрать значение
        grabbed = false;
    }
    public void telemetryBack(){
        backOpMode.telemetry.addData("grabbed: ", grabbed);
    }
}
