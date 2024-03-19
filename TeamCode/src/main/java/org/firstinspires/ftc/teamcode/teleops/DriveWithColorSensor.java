package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.onTest.ForTheNationalChampionship.Sensors.ColorSensorModule;

//телеоп, в котором замедление, если есть какой-то цвет под датчиком


@Disabled
@TeleOp(name = "line driver", group = "alfa")
public class DriveWithColorSensor extends LinearOpMode {
    BasicDriveTrain dt = new BasicDriveTrain(this);
    ColorSensorModule field = new ColorSensorModule(this);

    @Override
    public void runOpMode(){
        dt.initMotors();
        dt.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt.setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt.setOneDirection(DcMotorSimple.Direction.FORWARD);
        dt.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        field.initColorSensor();

        waitForStart();
        while(opModeIsActive()){
            if(!field.getColorOfField().equals(ColorSensorModule.colorsField.BLACK))
                dt.setMaximumSpeed(0.2);
            else
                dt.setMaximumSpeed(1);
            dt.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_trigger - gamepad1.left_trigger);
            field.telemetryColor();
            telemetry.update();
        }
    }
}
