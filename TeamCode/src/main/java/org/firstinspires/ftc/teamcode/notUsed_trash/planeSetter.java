package org.firstinspires.ftc.teamcode.onTest;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


/*
     возможно, оно не будет работать начиная с 62 строки...

     todo: переместить в Мусор после Национального
 */

@Disabled
@TeleOp(name = "plane setter", group = "alfa")
public class planeSetter extends LinearOpMode {
    Servo angle, shooter;

    DcMotor TR, TL, BR, BL;
    double x = 0, y = 0, r = 0, pos = 0;
    boolean shotNewAngle = false;

    @Override
    public void runOpMode(){
        //тут запускается инициализация...

        //записываем моторы и сервы для проверки конфигурационным файлом (а может быть можно создать xml файл и разметить в нем конфигурацию, чтобы никогда не приходилось ее настраивать???)
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");

        angle = hardwareMap.servo.get("angle");
        shooter = hardwareMap.servo.get("push");

        //задаем моторам направление вращения
        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        //было бы здорово понимать, что происходит с данными робота и геймпадов, пока кнопка старт не была нажата
        while(!isStarted()){
            composeTelemetry();
            telemetry.update();
            idle();
        }

        waitForStart();
        while(opModeIsActive()){
            //задаем значения для движения
            x = gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            r = (gamepad1.right_trigger - gamepad1.left_trigger);

            //передаем значения движения моторам
            TR.setPower(-x-y+r);
            BR.setPower(x-y+r);
            BL.setPower(x+y+r);
            TL.setPower(-x+y+r);

            //опрашиваем переключатель, можем ли мы менять значение угла запуска
            if(!shotNewAngle){
                //я вам запрещаю... менять угол взлета, но в этот раз можно
                shotNewAngle = !shotNewAngle;

                //нажимая на стрелку вверх или вниз, изменяем значение угла запуска на 9 градусов
                if(gamepad1.dpad_up){
                    pos += 0.05;
                }
                if(gamepad1.dpad_down){
                    pos -=0.05;
                }
            } else {
                //а, я вам запретил, да? ну раз уж вы когда-то поменяли, то в следующий раз поменяете...
                shotNewAngle = false;
            }

            //применяем измененное значение
            if(gamepad1.x) {
                angle.setPosition(pos);
            }

            //ВЫСТРЕЛ
            if(gamepad1.y) {
                shooter.setPosition(0.65);
            }

            //ЗАРЯЖАААЙ
            if(gamepad1.a){
                shooter.setPosition(0.2); //ЗАРЯЖЕНО
            }

            //смотрим, что происходит с роботом после прохода через цикл
            composeTelemetry();
            telemetry.update();
        }
    }

    void composeTelemetry(){
        //смотрим положения серв
        telemetry.addLine()
                .addData("angle position: ", angle.getPosition())
                .addData("shooter position: ", shooter.getPosition());

        //смотрим мощность на моторах
        telemetry.addLine("motors power ")
                .addData("TR: ", TR.getPower())
                .addData("TL: ", TL.getPower())
                .addData("BR: ", BR.getPower())
                .addData("BL: ", BL.getPower());

        //смотрим информацию для отладки
        telemetry.addLine()
                .addData("pos: ", pos)
                .addData("x: ", x)
                .addData("y: ", y)
                .addData("r: ", r);
    }
}
