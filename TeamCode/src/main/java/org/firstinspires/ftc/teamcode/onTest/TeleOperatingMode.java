package org.firstinspires.ftc.teamcode.onTest;

//тестовый телеоп
//TODO: написать телеоп только с колеской и с контролем скорости (тест DriveTrainWithDistanceControl)
// для чего? чтобы потом реализовать эти настройки в актуальном телеопе и в автономе



//ждем, пока моделисты смоделит крепление для датчика расстояния, харды установят его... и только тогда мы сможем протестить этот телеоп с этим модулем...
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Этот телеоп создан, чтобы протестировать езду с контролем скорости при приближении к препятсвию (заднику)
 */

@TeleOp(name = "distance driver", group = "alfa")
public class TeleOperatingMode extends LinearOpMode {
    DriveTrainWithDistanceControl dtdc = new DriveTrainWithDistanceControl(this);

    @Override
    public void runOpMode(){
        dtdc.initDTDS();
        while (opModeIsActive()){
            dtdc.move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_trigger-gamepad1.left_trigger);

            dtdc.dtdsTelemetry();
            telemetry.update();
        }
    }
}
