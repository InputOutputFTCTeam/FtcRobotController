package org.firstinspires.ftc.teamcode.onTest.restart;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Box {
    private Servo servoBox;
    private LinearOpMode boxOpMode;
    private boolean inited = false;

    public Box(LinearOpMode opMode){
        boxOpMode = opMode;
    }

    public void initBox(){
        servoBox = boxOpMode.hardwareMap.servo.get("servobox");
        inited = true;
    }

    public void telemetryBox(){
        boxOpMode.telemetry.addData("box position: ", servoBox.getPosition());
    }

    public void upp(){
        servoBox.setPosition(0);
    }
    public void down(){
        servoBox.setPosition(0.79);
    }
    public void mid(){
        servoBox.setPosition(0.5);
    }
}
