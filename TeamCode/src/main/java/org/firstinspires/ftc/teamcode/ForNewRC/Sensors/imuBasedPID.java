package org.firstinspires.ftc.teamcode.ForNewRC.Sensors;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.ForNewRC.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.ForNewRC.Basic.Constants;

public class imuBasedPID extends LinearOpMode {

    double integralSum = 0;
    double Kp = Constants.Kp;
    double Ki = Constants.Ki;
    double Kd = Constants.Kd;
    private double lastError = 0;
    ElapsedTime timer = new ElapsedTime();
    BasicDriveTrain base = new BasicDriveTrain(this);
    private BNO055IMU imu;

    @Override
    public void runOpMode() {
        base.initMotors();
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        imuInit();

        double refrenceAngle = Math.toRadians(90);
        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Target IMU Angle", refrenceAngle);
            telemetry.addData("Current IMU Angle", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
            double power = PIDControl(refrenceAngle, imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle);
            //здесб надо роботу передвигаться
            telemetry.update();
        }

    }

    public void imuInit() {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }



    public double PIDControl(double refrence, double state) {
        double error = angleWrap(refrence - state);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki);
        return output;
    }

    public double angleWrap(double radians){
        while(radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while(radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }
}
