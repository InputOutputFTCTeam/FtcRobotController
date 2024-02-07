package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name = "TideColor")
public class ColorSensorTele extends LinearOpMode {
    //создать экземпляр (объект) класса OurColor
    @Override
    public void runOpMode(){
        //инициализировать объект с помощью метода init(  параметром hardwareMap )

        waitForStart();
        while(opModeIsActive()){
            //выполнять функцию getColoredLine()
            //записывать значение этой функции в телеметрию

        }
    }
}
