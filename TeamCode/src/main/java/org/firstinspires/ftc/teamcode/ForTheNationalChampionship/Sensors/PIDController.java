// TODO: связать этот пид и хардварь
package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensors;
/**
 * Здесь описывается базовый пид-регулятор
 */
public class PIDController {
    private double Kp;
    private double Ki;
    private double Kd;

    private double integral;
    private  double lastError;

    /**
     * PID-контроллер (или Пропорционально-Интегрально-Дифференцирующий) контроллер, нужный для поддержания значения
     * @param Kp - коэффиуетт пропорции
     * @param Ki - коэффициент интеграла
     * @param Kd - коэффиуиент дифференциации
     * @param integral
     * @param lastError  - значение ошибки
     * */
    public PIDController (double Kp, double Ki, double Kd, double integral, double lastError) {
        this.Kp=Kp;
        this.Ki=Ki;
        this.Kd=Kd;
        this.integral=0;
        this.lastError=0;

    }

    /**
     * основная формула PID
     * @param setpoint - желаемое значение
     * @param currentValue - нынешнее значение
     * @param dt - промежуток времени,
     * @return output - возвращает значение ошибки
     */
    public double update(double setpoint, double currentValue, double dt) {
        double error = setpoint-currentValue;
        integral += error * dt;
        double differenceError = (error - lastError) / dt;
        double output = (Kp * error) + (Ki * integral) + (Kd*differenceError);
        lastError = output;
        return output;
    }
}
