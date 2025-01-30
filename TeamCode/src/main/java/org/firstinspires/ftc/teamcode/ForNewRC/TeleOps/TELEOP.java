package org.firstinspires.ftc.teamcode.ForNewRC.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TELEOP" , group = "TELEOP")
public class TELEOP extends LinearOpMode{
    DcMotor TR, TL, BR, BL, eL, eE; // Двигатели для колес: передний правый, передний левый, задний правый, задний левый, подъемник и выдвижение лифта
    CRServo proba, grab; // Сервоприводы для захвата и манипуляций

    double x, y, r, z, w; // Переменные для управления движением робота

    @Override
    public void runOpMode() {

        // Инициализация моторов через hardwareMap
        TL = hardwareMap.dcMotor.get("leftFront");
        TR = hardwareMap.dcMotor.get("rightFront");
        BL = hardwareMap.dcMotor.get("leftRear");
        BR = hardwareMap.dcMotor.get("rightRear");
        eL = hardwareMap.dcMotor.get("elevatorLifting");
        eE = hardwareMap.dcMotor.get("elevatorExtension");
        grab = hardwareMap.crservo.get("grab");
        proba = hardwareMap.crservo.get("proba");

        // Установка направлений вращения двигателей
        TL.setDirection(DcMotorSimple.Direction.FORWARD);
        TR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        eL.setDirection(DcMotorSimple.Direction.FORWARD);
        eE.setDirection(DcMotorSimple.Direction.FORWARD);

        // Установка поведения при остановке для двигателей
        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        eL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        eE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Ready to start");
        waitForStart();

        while (opModeIsActive()) {
            x = -gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            r = (gamepad1.right_trigger - gamepad1.left_trigger);
            z = -gamepad2.left_stick_y;
            w = -gamepad2.right_stick_y;

            TR.setPower(y + x - r);
            BR.setPower(y - x - r);
            BL.setPower(y + x + r);
            TL.setPower(y - x + r);
            eL.setPower(z);
            eE.setPower(w);

            if (gamepad2.x) {
                proba.setPower(-0.56);
            }
            if (gamepad2.b) {
                proba.setPower(-0.16);
            }

            if (gamepad2.y) {
                grab.setPower(-0.3);
            }
            if (gamepad2.a) {
                proba.setPower(-0.6);
            }

            telemetry.addData("TR Power", TR.getPower());
            telemetry.addData("BR Power", BR.getPower());
            telemetry.addData("BL Power", BL.getPower());
            telemetry.addData("TL Power", TL.getPower());


            telemetry.update();
        }
    }
}

