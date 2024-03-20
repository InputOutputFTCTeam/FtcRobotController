package org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensors;

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

    /**
     * Конструктор класса датчика расстояния
     * @param opMode передает конструктору OpMode, в котором датчик будет использоваться
     */
    public DistanceSensorModule(LinearOpMode opMode){
        dsOpMode = opMode;
    }

    /**
     * Инициализация датчика расстояния в OpMode и записывает его имя в конфигурации
     */
    public void initDistanceSensor(){
        sensorDistance = dsOpMode.hardwareMap.get(DistanceSensor.class, "sensor_distance");
    }

    /**
     * Измеряет расстояние между датчиком и объектом перед ним в мм
     * @return значение расстояния в мм
     */
    public double distanceMM(){
        return sensorDistance.getDistance(DistanceUnit.MM);
    }

    /**
     * Конструируем телеметрию датчика расстояния. Выводит расстояние в мм.
     */
    public void telemetryDistance(){
        dsOpMode.telemetry.addData("measured distance: ", distanceMM());
    }
}
