package org.firstinspires.ftc.teamcode.onTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.robotModules.Basic.BasicDriveTrain;

//TODO: добавить в Robot/modules

public class DriveTrainWithDistanceControl extends BasicDriveTrain{
    private LinearOpMode DTDSOpMode = null;
    private DistanceSensorModule ds = null;

    private static final double POWER_CONTROL = 1;

    public DriveTrainWithDistanceControl(LinearOpMode opMode){
        DTDSOpMode = opMode;
        ds = new DistanceSensorModule(DTDSOpMode);
    }

    public void initDTDS(){
        ds.initDistanceSensor();
        initMotors();
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //@Override     //to do this superclass must be abstract. but is it possible to make it abstract?
    public void move(double x, double y, double r){
        if(ds.distanceMM() > 200){
            getTL().setPower(Range.clip((x+y+r), -1, 1));
            getTR().setPower(Range.clip((x-y+r), -1, 1));
            getBL().setPower(Range.clip((-x+y+r), -1, 1));
            getBR().setPower(Range.clip((-x-y+r), -1, 1));
        } else if((ds.distanceMM() > 50 && ds.distanceMM() < 200) || ds.distanceMM()<50){   //блин, а какие значения он будет выдавать на расстоянии меньше 5 см и на расстоянии больше 200 см???
            getTL().setPower(Range.clip((x+y+r) * POWER_CONTROL, -1, 1));
            getTR().setPower(Range.clip((x-y+r) * POWER_CONTROL, -1, 1));
            getBL().setPower(Range.clip((-x+y+r) * POWER_CONTROL, -1, 1));
            getBR().setPower(Range.clip((-x-y+r) * POWER_CONTROL, -1, 1));
        }
    }

    public void dtdsTelemetry() {
        DTDSOpMode.telemetry.addData("distance: ", ds.distanceMM());
    }
}
