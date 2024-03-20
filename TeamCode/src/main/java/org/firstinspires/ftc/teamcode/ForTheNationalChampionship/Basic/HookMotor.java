package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class HookMotor {
    private LinearOpMode hookMotorOpMode;
    private DcMotor hookMotor;


    public HookMotor(LinearOpMode opMode){
        hookMotorOpMode = opMode;
    }

    public void initHookMotor(){
        hookMotor = hookMotorOpMode.hardwareMap.dcMotor.get("HookMotor");
        hookMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hookMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        hookMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        hookMotorOpMode.telemetry.addLine("Lift ready!");
    }

    public void hookMotorDirection(DcMotorSimple.Direction dir){
        hookMotor.setDirection(dir);
    }

    public void liftDirection(DcMotorSimple.Direction dir){
        hookMotor.setDirection(dir);
    }

    public void hookMotorMode(DcMotor.RunMode mode){
        hookMotor.setMode(mode);
    }

    public void run(double power){
        hookMotor.setPower(power);
    }

    public void runHook(){
        hookMotor.setPower(0.1);
        hookMotorOpMode.sleep(200);
        hookMotor.setPower(0);

    }


    public void telemetryLift(){
        hookMotorOpMode.telemetry.addData("current power: ", hookMotor.getPower());
    }

}
