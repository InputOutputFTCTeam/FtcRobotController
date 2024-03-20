package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * В этом классе описывается захват для фиолетового пикселя
 */

public class Catch {
    private LinearOpMode backOpMode;
    private boolean grabbed = false;
    private Servo leftBack, leftFront, rightFront ;

    /**
     * Создаем задний захват, как класс внутри opMod-а
     * @param opMode обычно "this", задает в каком потоке оперирует наш з-захват
     */

    public Catch(LinearOpMode opMode){
        backOpMode = opMode;
    }

    /**
     * Инициализация з-захвата для opMode. Добавление в конфигурацию
     */
    public void initCatch(){
        leftBack = backOpMode.hardwareMap.servo.get("drop");
        leftFront = backOpMode.hardwareMap.servo.get("leftCatch");
        rightFront = backOpMode.hardwareMap.servo.get("rightCatch");

    }

    /**
     * Захватить захват завхатывать захват вооруженный одной сервой
     */
    public void grab(){
        leftBack.setPosition(0.6);   //TODO: подобрать значение

        grabbed = true;
    }

    /**
     * Отпустить захват
     */
    public void ungrab(){
        leftBack.setPosition(0);  //TODO: подобрать значение

        grabbed = false;
    }

    public void closeGrab(){
        leftFront.setPosition(0.92);
        rightFront.setPosition(0.12);
        grabbed = false;
    }

    public void openGrab(){
        leftFront.setPosition(0.04);
        rightFront.setPosition(1);
        grabbed = false;
    }

    /**
     * Добавляем в телеметрию ключевые данные о механизме
     */
    public void telemetryBack(){
        backOpMode.telemetry.addData("grabbed: ", grabbed);
    }
}
