package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * в этом классе описываются методы для мотора подвеса
 */
public class HookMotor {
    private LinearOpMode hookMotorOpMode;
    private DcMotor hookMotor;

    /**
     * конструирует класс мотора подвеса
     * @param opMode - задёт поток в котором оперирует мотор
     */
    public HookMotor(LinearOpMode opMode){
        hookMotorOpMode = opMode;
    }
    /**
     * инициализирует и добавляет в конфигурацию мотор подвеса
     */
    public void initHookMotor(){
        hookMotor = hookMotorOpMode.hardwareMap.dcMotor.get("HookMotor");
        hookMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hookMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        hookMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        hookMotorOpMode.telemetry.addLine("Lift ready!");
    }
    /**
     * задаёт направление вращения мотора подвеса
     * @param dir - направление вращения мотора (forward или reverse)
     */
    public void hookMotorDirection(DcMotorSimple.Direction dir){
        hookMotor.setDirection(dir);
    }
    /**
     * задаёт  направление работы мотора, который подталкивает робота при подвесе
     * @param dir -  направление вращения мотора (forward или reverse)
     */
    public void liftDirection(DcMotorSimple.Direction dir){
        hookMotor.setDirection(dir);
    }
    /**
     * задаёт режим работы мотора подвеса
     * @param mode - режим работы мотора (run_to_position или run_using_encoder или run_without_encoder)
     */
    public void hookMotorMode(DcMotor.RunMode mode){
        hookMotor.setMode(mode);
    }
    /**
     * запуск мотора подвеса
     * @param power - мощность раюоты мотора (может принимать значения от -1 до 1, где 0 - остановка)
     */
    public void run(double power){
        hookMotor.setPower(power);
    }

    public void runHook(){
        hookMotor.setPower(0.1);
        hookMotorOpMode.sleep(200);
        hookMotor.setPower(0);

    }
    /**
     * добавление телеметрии о мощности мотора подвеса
     */
    public void telemetryLift(){
        hookMotorOpMode.telemetry.addData("current power: ", hookMotor.getPower());
    }

}
