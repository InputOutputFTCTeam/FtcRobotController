package org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensored;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.BasicDriveTrain;

public class DriveEncoder extends BasicDriveTrain {
    LinearOpMode encoderLinearOpMode;
    //BasicDriveTrain train;

    public DriveEncoder(LinearOpMode opMode){
        new BasicDriveTrain(opMode);
        setOpMode(opMode);
        encoderLinearOpMode = opMode;
        //train = new BasicDriveTrain(encoderLinearOpMode);
    }

    public void initDE(){
        initMotors();
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_USING_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void oneSpin(DcMotor motor){
        int stPos = Math.abs(motor.getCurrentPosition());
        while(encoderLinearOpMode.opModeIsActive() && Math.abs(Math.abs(motor.getCurrentPosition()) - stPos) < 1440){
            motor.setPower(0.5);
        }
    }

    /**
     * Движение по одному энкодеру. Во время тестов заметили, что робот едет криво.
     * @param motor мотор в который воткнут провод энкодера
     * @param x скорость водль оси x
     * @param y скорость вдоль оси y
     * @param r скорость разворота (>0 по часовой, <0 против часовой)
     * @param target количество импульсов энкодера
     */
    public void driveBy1Endcoder(DcMotor motor, double x, double y, double r, int target){
        int startPos = motor.getCurrentPosition();
        while(encoderLinearOpMode.opModeIsActive() && Math.abs(Math.abs(motor.getCurrentPosition()) - Math.abs(startPos)) < target){
            move(x, y, r);
        }
    }

    /**
     * Движение по четырем энкодерам.
     * @param x скорость вдоль оси x
     * @param y скорость водль оси y
     * @param r скорость разворота (>0 по часовой, <0 против часовой)
     * @param ticks количество импульсов энкодера
     */
    public void driveBy4Encoder(double x, double y, double r, int ticks){
        int startPositionTL = getPosition(getTL());
        int startPositionTR = getPosition(getTR());
        int startPositionBL = getPosition(getBL());
        int startPositionBR = getPosition(getBR());

        //какую дистанцию проехать надо? (в тиках энкодера)
        //тут можно написать формулу для пересчета тиков энкодера в дистанцию
        //1440 тиков на оборот //4 дюйма диаметр колеса // 3:2 (мотор:колесо) передача
        //double revolutions = distanceCM / 3 * 2 / (Math.PI * 4 * 2.54);
        //double ticks = revolutions * 1440;

        //записать моторам мощность, пока они не достигнут целевой позиции
        while(encoderLinearOpMode.opModeIsActive() && (
                Math.abs(Math.abs(getPosition(getTL())) - Math.abs(startPositionTL)) < ticks ||
                Math.abs(Math.abs(getPosition(getTR())) - Math.abs(startPositionTR)) < ticks ||
                Math.abs(Math.abs(getPosition(getBL())) - Math.abs(startPositionBL)) < ticks ||
                Math.abs(Math.abs(getPosition(getBR())) - Math.abs(startPositionBR)) < ticks   )   )
        {
            //даем моторам мощность
            move(x, y, r);

            //выводим абсолютную разницу между нынешним и целевым значениями
            encoderLinearOpMode.telemetry.addData(" TL diff: ", Math.abs(Math.abs(getPosition(getTL())) - ticks));
            encoderLinearOpMode.telemetry.addData(" TR diff: ", Math.abs(Math.abs(getPosition(getTR())) - ticks));
            encoderLinearOpMode.telemetry.addData(" BL diff: ", Math.abs(Math.abs(getPosition(getBL())) - ticks));
            encoderLinearOpMode.telemetry.addData(" BR diff: ", Math.abs(Math.abs(getPosition(getBR())) - ticks));
            encoderLinearOpMode.telemetry.update();
        }
        move(0,0,0);
    }
}
