package org.firstinspires.ftc.teamcode.notUsed_trash;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/*
    TODO: run test on robot
 */
@Disabled
@Autonomous(name = "encoder test", group = "alfa")
public class enccoder extends LinearOpMode {
    DcMotor TL, TR, BL, BR;

    int startPositionTL, startPositionTR, startPositionBL, startPositionBR;

    @Override
    public void runOpMode(){
        //тут началась инициализация...

        //записываем моторы и сервы для проверки конфигурационным файлом (а может быть можно создать xml файл и разметить в нем конфигурацию, чтобы никогда не приходилось ее настраивать???)
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        //задаем моторам направление вращения
        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        //включаем режим работы по энкодерам. все провода должны быть подкючены
        TL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        TL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //было бы здорово понимать, что происходит с данными робота и геймпадов, пока кнопка старт не была нажата
        while(!isStarted()){
            composeTelemetry();
            telemetry.update();
            idle();
        }

        waitForStart();
        if(opModeIsActive()){
            moveBy4Encoders(0.5,0,0,10);
            adjDelay();
            moveBy4Encoders(0, 0.5, 0, 10);
            adjDelay();
            moveBy4Encoders(0, 0, 0.5, 10);
        }
    }

    void composeTelemetry(){
        //смотрим положение моторов
        telemetry.addLine()
            .addData("TR pos: ", TR.getCurrentPosition())
            .addData("TL pos: ", TL.getCurrentPosition())
            .addData("BR pos: ", BR.getCurrentPosition())
            .addData("BL pos: ", BL.getCurrentPosition());

        //смотрим мощность на моторах
        telemetry.addLine("motors power ")
                .addData("TR: ", TR.getPower())
                .addData("TL: ", TL.getPower())
                .addData("BR: ", BR.getPower())
                .addData("BL: ", BL.getPower());
    }

    public void moveBy4Encoders(double x, double y, double r, double distanceCM){
        startPositionTL = TL.getCurrentPosition();
        startPositionTR = TR.getCurrentPosition();
        startPositionBL = BL.getCurrentPosition();
        startPositionBR = BR.getCurrentPosition();

        //какую дистанцию проехать надо? (в тиках энкодера)
        //тут можно написать формулу для пересчета тиков энкодера в дистанцию
        //1440 тиков на оборот //4 дюйма диаметр колеса // 3:2 (мотор:колесо) передача
        double revolutions = distanceCM / 3 * 2 / (Math.PI * 4 * 2.54);
        double ticks = revolutions * 1440;

        //записать моторам мощность, пока они не достигнут целевой позиции
        while(opModeIsActive() && ( (Math.abs(Math.abs(TL.getCurrentPosition()) - Math.abs(startPositionTL))) < ticks ||
                                    (Math.abs(Math.abs(BL.getCurrentPosition()) - Math.abs(startPositionBL))) < ticks ||
                                    (Math.abs(Math.abs(TR.getCurrentPosition()) - Math.abs(startPositionTR))) < ticks ||
                                    (Math.abs(Math.abs(BR.getCurrentPosition()) - Math.abs(startPositionBR))) < ticks   )   )
        {
            //даем моторам мощность
            move(x, y, r);

            //выводим абсолютную разницу между нынешним и целевым значениями
            telemetry.addData(" TL diff: ", Math.abs(Math.abs(TL.getCurrentPosition()) - ticks));
            telemetry.addData(" TR diff: ", Math.abs(Math.abs(TR.getCurrentPosition()) - ticks));
            telemetry.addData(" BL diff: ", Math.abs(Math.abs(BL.getCurrentPosition()) - ticks));
            telemetry.addData(" BR diff: ", Math.abs(Math.abs(BR.getCurrentPosition()) - ticks));
            telemetry.update();
        }
        stopp();

        telemetry.addLine();
        telemetry.addLine();
        composeTelemetry();
        telemetry.update();
}

    public void move(double x, double y, double r){
        TR.setPower(-x-y+r);
        BR.setPower(x-y+r);
        BL.setPower(x+y+r);
        TL.setPower(-x+y+r);
    }

    public void adjDelay(){
        move(0,0,0);
        sleep(100);
    }

    public void stopp(){
        move(0,0,0);
    }
}
