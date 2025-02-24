package org.firstinspires.ftc.teamcode.onTest;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors.DistanceSensorModule;

/**
 * А работает ли он вообще, в принципе?
 * подключили, написали его в Configure Robot, запустили этот опмод
 */
@Disabled
@TeleOp(name = "testing Distance Sensor", group = "alfa")
public class TestingDistanceSensor extends LinearOpMode {
    DistanceSensorModule dsens = new DistanceSensorModule(this);

    @Override
    public void runOpMode(){
        dsens.initDistanceSensor();

        waitForStart();
        while (opModeIsActive()){
            dsens.telemetryDistance();
            telemetry.update();
        }
    }
}
