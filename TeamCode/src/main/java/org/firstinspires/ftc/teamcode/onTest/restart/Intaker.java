package org.firstinspires.ftc.teamcode.onTest.restart;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * В этом классе описывается модуль захвата. (да, один мотор, но все же...)
 */
public class Intaker {
    private DcMotor intake;
    private LinearOpMode intakerOpMode;
    private boolean inited = false;

    public Intaker (LinearOpMode opMode){
        intakerOpMode = opMode;
    }
    public void initIntake(HardwareMap hwMap){
        intake = intakerOpMode.hardwareMap.dcMotor.get("intake"); //hwMap.dcMotor.get("intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        inited = true;

        intakerOpMode.telemetry.addLine("Intaker ready!");

    }

    public void runIntake(double power){
        intake.setPower(power);
    }
    public void telemetryIntaker(){
        intakerOpMode.telemetry.addData("intake power: ", intake.getPower());
    }

}
