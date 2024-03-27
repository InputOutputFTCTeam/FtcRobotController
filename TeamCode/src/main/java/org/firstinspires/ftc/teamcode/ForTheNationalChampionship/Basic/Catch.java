package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * В этом классе описывается захват для фиолетового пикселя
 */

public class Catch {
    private LinearOpMode backOpMode;
    private boolean grabbed = false;
    private CRServo leftBack, leftFront, rightFront ;

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
        leftBack = backOpMode.hardwareMap.crservo.get("drop");
        leftFront = backOpMode.hardwareMap.crservo.get("leftCatch");
        rightFront = backOpMode.hardwareMap.crservo.get("rightCatch");

    }

    /**
     * Захватить захват завхатывать захват вооруженный одной сервой
     */
    public void grab(){
        leftBack.setPower(-0.85);   //TODO: подобрать значение

        grabbed = true;
    }

    /**
     * Отпустить захват
     */
    public void ungrab(){
        leftBack.setPower(0.3);  //TODO: подобрать значение

        grabbed = false;
    }

    public void closeGrab(){
        leftFront.setPower(0.75);
        rightFront.setPower(-0.75);
        grabbed = false;
    }

    public void openGrab(){
        leftFront.setPower(-1);
        rightFront.setPower(1);
        grabbed = false;
    }

    /**
     * Добавляем в телеметрию ключевые данные о механизме
     */
    public void telemetryBack(){
        backOpMode.telemetry.addData("grabbed: ", grabbed);
        backOpMode.telemetry.addData("grabbed pos", leftBack.getPower());
    }
}
