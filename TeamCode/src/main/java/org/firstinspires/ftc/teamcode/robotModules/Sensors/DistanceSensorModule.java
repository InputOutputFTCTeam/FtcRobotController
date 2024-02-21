package org.firstinspires.ftc.teamcode.robotModules.Sensors;

/*
TODO: добавить этот класс в Robot.Sensors
 */

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Класс датчика расстояния для измерения расстояния до Задника, чтобы притормозить, когда будем
 * близко к заднику
 * <p>
 * Измеримое расстояние от 50мм до 2000мм
 */

public class DistanceSensorModule {
    private DistanceSensor sensorDistance;
    private LinearOpMode dsOpMode;

    public DistanceSensorModule(LinearOpMode opMode){
        dsOpMode = opMode;
    }

    public void initDistanceSensor(){
        sensorDistance = dsOpMode.hardwareMap.get(DistanceSensor.class, "sensor_distance");
    }

    public double distanceMM(){
        return sensorDistance.getDistance(DistanceUnit.MM);
    }

    public void telemetryDistance(){
        dsOpMode.telemetry.addData("measured distance: ", distanceMM());
    }
}
