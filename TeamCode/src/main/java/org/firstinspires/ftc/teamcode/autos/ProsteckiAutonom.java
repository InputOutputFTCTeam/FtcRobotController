package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "prostecki")
public class ProsteckiAutonom extends LinearOpMode {
    //моторы
    DcMotor TR, TL, BR, BL;

    double x, y;
    //объявить хардвер
    @Override
    public void runOpMode(){
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        telemetry.addLine("Ready to start");
        telemetry.update();
        waitForStart();
        if(opModeIsActive()) {   //действия, которые будут выполняться во время автонома
            //мотор мощь
            MoveByTime(0.5, 0, 29000);
            telemetry.addLine("stopping");
            telemetry.update();

            //мототр стой
            Ostanovka();
        }
    }

    public void MoveByTime(double x, double y, int milliseconds){

        TR.setPower(-x-y);
        BR.setPower(x-y);
        BL.setPower(x+y);
        TL.setPower(-x+y);
        telemetry.addLine("running");
        telemetry.update();

        sleep(milliseconds);


    }
    public void Ostanovka(){
        TR.setPower(0);
        BR.setPower(0);
        BL.setPower(0);
        TL.setPower(0);

    }


}
