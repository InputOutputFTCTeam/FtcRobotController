package org.firstinspires.ftc.teamcode.onTest.restart;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Plane {

    private Servo angle, push;
    private LinearOpMode planeOpMode;
    private boolean inited = false;

    public Plane (LinearOpMode opMode){
        planeOpMode = opMode;
    }

    public void initPlane(){
        angle = planeOpMode.hardwareMap.servo.get("angle");
        push = planeOpMode.hardwareMap.servo.get("push");
        inited = true;
        pushDown();
        planeOpMode.telemetry.addLine("Plane ready!");
    }

    public void angleUp() {
        angle.setPosition(0.92);
    }

    public void pushDown() {
        push.setPosition(0.51);
    }

    public void pushUp() {
        push.setPosition(0.65);
    }
    public void telemetryPlane(){
        planeOpMode.telemetry.addData("pushPosition", push.getPosition());
        planeOpMode.telemetry.addData("anglePosition", angle.getPosition());


    }
}
