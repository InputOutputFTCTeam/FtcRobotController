package org.firstinspires.ftc.teamcode.onTest;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Это рабочая заготовка кода. отсюда можно взять пример, как сделать кнопку срабатывающую в режиме
 * нажал-отпустил
 * <p>
 * Этот код работает через цикл while. В external.samples есть пример рабочего кода с датчиком цвета. Там
 * переключается встроенный в датчик светодиод. Тот пример реализован через if (отслеживается xButtonPreviouslyPressed)
 */

//  todo: переместить в Мусор после Национального

@TeleOp(name = "plane setter while", group = "alfa")
public class planeSetter_while extends LinearOpMode {
    Servo angle, shooter;

    DcMotor TR, TL, BR, BL;
    double x = 0, y = 0, r = 0, pos = 0;

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

            //нажали на стрелку вверх на геймпаде
            if(gamepad1.dpad_up){
                //увеличиваем значение угла запуска на 9 градусов
                pos+=0.05;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad1.dpad_up && opModeIsActive()) {
                    telemetry.addLine("RELEASE up BUTTON PLS");
                    telemetry.update();
                    idle();
                }
            }

            if(gamepad1.dpad_down){
                //уменьшаем значение угла запуска на ~9 градусов
                pos -= 0.05;

                //ждем, пока драйвер отпустит кнопку
                while(gamepad1.dpad_down && opModeIsActive()){
                    telemetry.addLine("RELEASE down BUTTON PLS");
                    telemetry.update();
                    idle();
                }
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
