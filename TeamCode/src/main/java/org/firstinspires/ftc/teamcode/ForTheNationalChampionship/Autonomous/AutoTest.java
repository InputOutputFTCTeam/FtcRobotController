package org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Basic.BasicDriveTrain;
import org.firstinspires.ftc.teamcode.ForTheNationalChampionship.Sensored.MegaDriveTrain;

@Autonomous
public class AutoTest extends LinearOpMode {
    BasicDriveTrain train = new BasicDriveTrain(this);
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() {
        train.initMotors();
        train.setOneDirection(DcMotorSimple.Direction.FORWARD);
        train.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()) {
           train.move(-0.5,0,0);
        }
    }
}
