package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotModules.Sensored.GigaChadDriveTrain;
import org.firstinspires.ftc.teamcode.robotModules.Sensors.ColorSensorModule;

@Autonomous (name = "G_blue2.1")
public class G_blue2_1 extends LinearOpMode {
    GigaChadDriveTrain robot = new GigaChadDriveTrain(this);

    @Override
    public void runOpMode() {
        //robot = new GigaChadDriveTrain(this);
        robot.initGigaChad(this);

        waitForStart();
        if (opModeIsActive()) {
            robot.imuSteerEncoder(1, 0, 0, 0, 1000);

            //encoder run - проезд вперёд, imuTurn- оаорот, объявить сервы что использовать как в голуюой тест


            //sleep(5000);
            //robot.colorRun(0.5, 0, 0, ColorSensorModule.colorsField.BLUE);
        }
    }
}
