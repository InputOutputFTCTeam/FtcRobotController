package org.firstinspires.ftc.teamcode.robotModules.Basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * В этом классе описывается захват для фиолетового пикселя
 */

public class BackupCatch {
    private LinearOpMode backOpMode;
    private boolean grabbed = false;
    private Servo left /*, right*/;

    /**
     * Создаем задний захват, как класс внутри opMod-а
     * @param opMode обычно "this", задает в каком потоке оперирует наш з-захват
     */

    public BackupCatch(LinearOpMode opMode){
        backOpMode = opMode;
    }

    /**
     * Инициализация з-захвата для opMode. Добавление в конфигурацию
     */
    public void initBack(){
        left = backOpMode.hardwareMap.servo.get("drop1");
        //right = backOpMode.hardwareMap.servo.get("drop2");
    }

    /**
     * Захватить захват завхатывать захват вооруженный одной сервой
     */
    public void grab(){
        left.setPosition(0.6);   //TODO: подобрать значение
        //right.setPosition(0.6);
        grabbed = true;
    }

    /**
     * Отпустить захват
     */
    public void ungrab(){
        left.setPosition(0);  //TODO: подобрать значение
        //right.setPosition(0);
        grabbed = false;
    }

    /**
     * Добавляем в телеметрию ключевые данные о механизме
     */
    public void telemetryBack(){
        backOpMode.telemetry.addData("grabbed: ", grabbed);
    }
}
