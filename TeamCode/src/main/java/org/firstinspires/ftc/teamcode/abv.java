package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@TeleOp
public class abv extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        DistanceSensor sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");
        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("RANGESENS", sensorDistance.getDistance(DistanceUnit.MM));
            telemetry.update();
        }
    }
}
