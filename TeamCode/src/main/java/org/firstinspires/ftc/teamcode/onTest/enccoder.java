package org.firstinspires.ftc.teamcode.onTest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/*
    TODO: здесь где-то conversion type error
    f ! = java lang Integer
 */

@Autonomous(name = "encoder test")
public class enccoder extends LinearOpMode {
    DcMotor TL, TR, BL, BR;

    int startPositionTL, startPositionTR, startPositionBL, startPositionBR;

    @Override
    public void runOpMode(){

        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        TL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        if(opModeIsActive()){
            moveBy4Encoders(0.5,0,0,500);
            adjDelay();
            moveBy4Encoders(0, 0.5, 0, 500);
            adjDelay();
            moveBy4Encoders(0, 0, 0.5, 500);
        }
    }

    public void adjDelay(){
        move(0,0,0);
        sleep(100);
    }
    public void moveBy4Encoders(double x, double y, double r, int distance){
        startPositionTL = TL.getCurrentPosition();
        startPositionTR = TR.getCurrentPosition();
        startPositionBL = BL.getCurrentPosition();
        startPositionBR = BR.getCurrentPosition();

        //какую дистанцию проехать надо? (в тиках энкодера)
        //тут можно написать формулу для пересчета тиков энкодера в дистанцию //1440 тиков на оборот //4 дюйма диаметр колеса

        //записать моторам мощность, пока они не достигнут целевой позиции
        while(opModeIsActive() && (Math.abs(Math.abs(TL.getCurrentPosition()) - Math.abs(startPositionTL))) < distance ||
                (Math.abs(Math.abs(TR.getCurrentPosition()) - Math.abs(startPositionTR))) < distance ||
                (Math.abs(Math.abs(BL.getCurrentPosition()) - Math.abs(startPositionBL))) < distance ||
                (Math.abs(Math.abs(BR.getCurrentPosition()) - Math.abs(startPositionBR))) < distance)
        {
            //даем моторам мощность
            move(x, y, r);
            telemetry.addLine(String.format("current position TL %5.1f", TL.getCurrentPosition()));
            telemetry.addLine(String.format("current position TR %5.1f", TR.getCurrentPosition()));
            telemetry.addLine(String.format("current position BL %5.1f", BL.getCurrentPosition()));
            telemetry.addLine(String.format("current position BR %5.1f", BR.getCurrentPosition()));
            telemetry.update();
        }
        stopp();
    }

    public void move(double x, double y, double r){
        TR.setPower(-x-y+r);
        BR.setPower(x-y+r);
        BL.setPower(x+y+r);
        TL.setPower(-x+y+r);
    }

    public void stopp(){
        move(0,0,0);
    }
}
