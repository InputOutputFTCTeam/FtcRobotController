package org.firstinspires.ftc.teamcode.onTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robotModules.Basic.BasicDriveTrain;

//TODO: добавить в Robot/modules

public class DriveTrainWithDistanceControl extends BasicDriveTrain{
    DistanceSensorModule ds = new DistanceSensorModule(this);
    BasicDriveTrain dt = new BasicDriveTrain(this);

    double POWER_CONTROL = 1;
    @Override
    public void runOpMode(){
        ds.initDistanceSensor();
        dt.initMotors();
        dt.setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt.setOneDirection(DcMotorSimple.Direction.FORWARD);
        dt.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()){
            if(ds.distanceMM() > 200){
                POWER_CONTROL = 1;
            } else if (ds.distanceMM() > 50 && ds.distanceMM() < 200) {
                POWER_CONTROL = 0.4;
            }
            dt.move(POWER_CONTROL * gamepad1.left_stick_x,
                    POWER_CONTROL * gamepad1.left_stick_y,
                    POWER_CONTROL * (gamepad1.right_trigger - gamepad2.left_trigger));

            telemetry.addData("Power control: ", POWER_CONTROL);
            telemetry.addData("Distance: ", ds.distanceMM());
            telemetry.update();
        }
    }
}
