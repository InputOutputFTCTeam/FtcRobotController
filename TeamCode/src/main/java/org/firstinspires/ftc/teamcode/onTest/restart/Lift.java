package org.firstinspires.ftc.teamcode.onTest.restart;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Lift {
    private LinearOpMode liftOpMode;
    private DcMotor lift;
    private boolean inited = false;

    public Lift(LinearOpMode opMode){
        liftOpMode = opMode;
    }

    public void initLift(){
        lift = liftOpMode.hardwareMap.dcMotor.get("lift");
        inited = true;

        liftOpMode.telemetry.addLine("Lift ready!");
    }
    public void liftDirection(DcMotorSimple.Direction dir){
        lift.setDirection(dir);
    }

    /**
     * @param mode better be RUN_WITHOUT_ENCODER
     */
    public void liftMode(DcMotor.RunMode mode){
        lift.setMode(mode);
    }
    public void run(double power){
        lift.setPower(power);
    }
    public void telemetryLift(){
        liftOpMode.telemetry.addData("current power: ", lift.getPower());
    }
}
