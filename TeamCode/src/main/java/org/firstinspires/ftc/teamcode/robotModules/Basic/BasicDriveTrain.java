package org.firstinspires.ftc.teamcode.robotModules.Basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/**
 * В этом классе описываются основные методы для управления колесной базой из 4 меканум колес
 * установленных крестом.
 */
public class BasicDriveTrain {
    private DcMotor TL, TR, BL, BR;

    private LinearOpMode driveTrainOpMode = null;
    private boolean inited = false, encoded = false;
    double maximumSpeed = 1;

    /**
     * Создаем колесную базу, как класс внутри opMod-а
     */
    public BasicDriveTrain(){}

    /**
     * Создаем колесную базу, как класс внутри opMod-а
     * @param opMode - обычно "this", задает в каком потоке оперирует наша кб
     */
    public BasicDriveTrain(LinearOpMode opMode) {
        driveTrainOpMode = opMode;
    }

    public LinearOpMode getOpMode() {
        return driveTrainOpMode;
    }

    public void setOpMode(LinearOpMode opMode) { driveTrainOpMode = opMode;}

    /**
     * Инициализация кб для opMode. Добавление в конфигурацию
     */
    public void initMotors(){
        TL = driveTrainOpMode.hardwareMap.dcMotor.get("leftFront");
        TR = driveTrainOpMode.hardwareMap.dcMotor.get("rightFront");
        BL = driveTrainOpMode.hardwareMap.dcMotor.get("leftRear");
        BR = driveTrainOpMode.hardwareMap.dcMotor.get("rightRear");
        inited = true;

        driveTrainOpMode.telemetry.addLine("Driver ready!");
    }

    /**
     * [энкодеры] моторы можно запустить в режиме run_to_position или run_using_encoder или run_without_encoder
     */
    public void setModes(DcMotor.RunMode mode){
        if(inited){
            TL.setMode(mode);
            TR.setMode(mode);
            BL.setMode(mode);
            BR.setMode(mode);
            if(mode == DcMotor.RunMode.RUN_USING_ENCODER || mode == DcMotor.RunMode.RUN_TO_POSITION)
                encoded = true;
        } else {
            driveTrainOpMode.telemetry.addLine("NOT INITED WHEELBASE");
            driveTrainOpMode.telemetry.addLine("must repair code");
            driveTrainOpMode.telemetry.update();
        }
    }


    /**
     * [энкодеры] новая целевая позиция для мотора
     */
    public void setTargets(int[] newTarget){
        if(inited){
            TL.setTargetPosition(newTarget[0]);     //possibly can cause errors! tests recommended
            TR.setTargetPosition(newTarget[1]);
            BL.setTargetPosition(newTarget[2]);
            BR.setTargetPosition(newTarget[3]);
        } else {
            driveTrainOpMode.telemetry.addLine("NOT INITED WHEELBASE");
            driveTrainOpMode.telemetry.addLine("must repair code");
            driveTrainOpMode.telemetry.update();
        }
    }

    /**
     * устанавливает направление вращения моторов
     * @param dir FORWARD или REVERSE
     */
    public void setOneDirection(DcMotorSimple.Direction dir){
        if(inited){
            TL.setDirection(dir);
            TR.setDirection(dir);
            BL.setDirection(dir);
            BR.setDirection(dir);
        } else {
            driveTrainOpMode.telemetry.addLine("NOT INITED WHEELBASE");
            driveTrainOpMode.telemetry.addLine("must repair code");
            driveTrainOpMode.telemetry.update();
        }
    }

    /**
     * задает режим остановки всем 4м моторам
     */
    public void setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior behavior) {
        if (inited) {
            TL.setZeroPowerBehavior(behavior);
            TR.setZeroPowerBehavior(behavior);
            BL.setZeroPowerBehavior(behavior);
            BR.setZeroPowerBehavior(behavior);
        } else {
            driveTrainOpMode.telemetry.addLine("NOT INITED WHEELBASE");
            driveTrainOpMode.telemetry.addLine("must repair code");
            driveTrainOpMode.telemetry.update();
        }
    }

    /**
     * движение колесной базы робота
     * @param x движение вдоль оси x. меньше 0 - ехать влево, больше 0 - ехать вправо
     * @param y движение вдоль оси y. меньше 0 - ехать назад, больше 0 - ехать вперед
     * @param r движение вокруг своей оси. больше 0 - разворот по часовой, меньше 0 - разворот против часовой.
     */
    public void move(double x, double y, double r){
        if(inited){
            TL.setPower(Range.clip((x+y+r), -maximumSpeed, maximumSpeed));
            TR.setPower(Range.clip((x-y+r), -maximumSpeed, maximumSpeed));
            BL.setPower(Range.clip((-x+y+r), -maximumSpeed, maximumSpeed));
            BR.setPower(Range.clip((-x-y+r), -maximumSpeed, maximumSpeed));
        }
        else {
            driveTrainOpMode.telemetry.addLine("NOT INITED WHEELBASE");
            driveTrainOpMode.telemetry.addLine("must repair code");
            driveTrainOpMode.telemetry.update();
        }
    }

    /**
     * Добавляем в телеметрию основную информацию о состоянии моторов колесной базы
     */
    public void wheelbaseTelemetry(){
        driveTrainOpMode.telemetry.addData("TL : TR ", "%2.3f : %2.3f", TL.getPower(), TR.getPower());
        driveTrainOpMode.telemetry.addData("BL : BR ", "%2.3f : %2.3f", BL.getPower(), BR.getPower());
        if(inited && encoded){
            driveTrainOpMode.telemetry.addLine();
            driveTrainOpMode.telemetry.addData("TL: actual : target ", "%4d : %4d", TL.getCurrentPosition(), TL.getTargetPosition());
            driveTrainOpMode.telemetry.addData("TR: actual : target ", "%4d : %4d", TR.getCurrentPosition(), TR.getTargetPosition());
            driveTrainOpMode.telemetry.addData("BL: actual : target ", "%4d : %4d", BL.getCurrentPosition(), BL.getTargetPosition());
            driveTrainOpMode.telemetry.addData("BR: actual : target ", "%4d : %4d", BR.getCurrentPosition(), BR.getTargetPosition());
        }
    }

    /**
     * Метод возвращает количество импульсов которые посчитал энкодер
     * @param motor мотор с которого мы принимаем значение энкодера
     * @return возвращает положение энкодеров (количество посчитанных ими импульсов)
     */
    public int getPosition(DcMotor motor){
        return motor.getCurrentPosition();
    }
    public DcMotor getTL() {return TL;}     //change motor modifier on "protected" recommended
    public DcMotor getTR() {return TR;}
    public DcMotor getBL() {return BL;}
    public DcMotor getBR() {return BR;}

    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;

    }
}
