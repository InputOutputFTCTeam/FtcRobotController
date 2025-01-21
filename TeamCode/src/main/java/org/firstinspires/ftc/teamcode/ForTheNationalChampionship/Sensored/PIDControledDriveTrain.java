package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.BasicDriveTrain;

public class    PIDControledDriveTrain extends BasicDriveTrain{
    private double Kp=0;
    private double Ki=0;
    private double Kd=0;

    private double integral=0;
    private  double lastError=0;

    ElapsedTime timer = new ElapsedTime();

    /**
     * Создаём КБ как класс внутри OpMode
     */
    public PIDControledDriveTrain(LinearOpMode opMode) {
        new BasicDriveTrain(opMode);
        setOpMode(opMode);
    }

    /**
     * Инициализирует КБ для OpMode
     */
    public void initPIDPT() {
        initMotors();
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setOneDirection(DcMotorSimple.Direction.FORWARD);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Основное уравнение PID
     * @param reference - желаемое значение
     * @param state - нынешнее значение
     * @return output
     */
    public double PIDControl(double reference, double state) {
        double error = reference-state;
        integral += error * timer.seconds();
        double derivative = (error - lastError) / timer.seconds();
        timer.reset();
        double output = (Kp * error) + (Ki * integral) + (Kd*derivative);
        return output;
    }

    /**
     * передвижение с помощью ПИД-а
     */
    public void PIDMove () {
        getTL().setPower((PIDControl(100, getTL().getCurrentPosition())));
        getTR().setPower((PIDControl(100, getTR().getCurrentPosition())));
        getBL().setPower((PIDControl(100, getBL().getCurrentPosition())));
        getBR().setPower((PIDControl(100, getBR().getCurrentPosition())));

    }
}
