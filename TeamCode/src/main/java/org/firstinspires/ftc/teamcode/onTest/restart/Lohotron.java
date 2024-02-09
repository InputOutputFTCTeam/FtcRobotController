package org.firstinspires.ftc.teamcode.onTest.restart;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lohotron {
    private Servo perevorot, main, claw;
    private LinearOpMode lohotronOpMode = null; //объект описывающий опмод, в котором будет использоваться наш лохотрон
    boolean down = false;
    public Lohotron(LinearOpMode opMode){
        lohotronOpMode = opMode;
    }

    public void initLohotron(HardwareMap hwMap){
        perevorot = hwMap.servo.get("lohotron");
        main =      hwMap.servo.get("lohotronMain");
        claw =      hwMap.servo.get("zahvat");

        lohotronOpMode.telemetry.addLine("Lohotron ready!");
    }

    public void lohotronTelemetry(){
        lohotronOpMode.telemetry.addData("lohotron ", down ? "opushen" : "podniat");
        lohotronOpMode.telemetry.addData("lohotron position: ", perevorot.getPosition());
        lohotronOpMode.telemetry.addData("lohotronMain position: ", main.getPosition());
        lohotronOpMode.telemetry.addData("zahvat position: ", claw.getPosition());
        lohotronOpMode.telemetry.addData("zahvacheno: ", isClawClosed());
    }

    public void armRaiser(){
        main.setPosition(0.57);
        lohotronOpMode.sleep(100);
        perevorot.setPosition(0);
        down = false;
    }
    public void armLowerer(){
        perevorot.setPosition(1);
        lohotronOpMode.sleep(100);
        main.setPosition(0);
        down = true;
    }
    public void armMid(){
        main.setPosition(0.4);
    }

    boolean armRaised = false;
    public void armLogicalRaise_Lower(){
        if(armRaised) armRaiser(); else armLowerer();
        armRaised = !armRaised;
    }

    public void closeClaw(){
        claw.setPosition(0.15);
    }
    public void openClaw(){
        claw.setPosition(0);
    }
    public boolean isClawClosed(){
        return claw.getPosition() == 0.15;
    }
}
