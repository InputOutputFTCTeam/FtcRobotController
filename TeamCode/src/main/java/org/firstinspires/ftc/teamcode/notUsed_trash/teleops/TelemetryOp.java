package org.firstinspires.ftc.teamcode.notUsed_trash.teleops;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 *  Этот телеоп проверяет работоспособность геймпадов. Проверяем значения, получаемые из геймпада
 *  через вывод в телеметрию.
 */

//@Disabled
@TeleOp(name = "gamepads monitor", group = "testing")
public class TelemetryOp extends LinearOpMode {
    boolean gm1 = false, gm2 = false;

    @Override
    public void runOpMode() {
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        float[] values = {0,0,0};

        waitForStart();
        while (opModeIsActive()) {
            //стики
            telemetry.addData("left_stick_y: ", "%5.3f : %5.3f", gamepad1.left_stick_y, gamepad2.left_stick_y);
            telemetry.addData("left_stick_x: ", "%5.3f : %5.3f", gamepad1.left_stick_x, gamepad2.left_stick_x);
            telemetry.addData("right_stick_y: ", "%5.3f : %5.3f", gamepad1.right_stick_y, gamepad2.right_stick_y);
            telemetry.addData("right_stick_x: ", "%5.3f : %5.3f", gamepad1.right_stick_x, gamepad2.right_stick_x);
            telemetry.addData("left_stick_button: ", "%b : %b", gamepad1.left_stick_button, gamepad2.left_stick_button);
            telemetry.addData("right_stick_button: ", "%b : %b", gamepad1.right_stick_button, gamepad2.right_stick_button);

            //курки
            telemetry.addData("left_trigger: ", "%5.3f : %5.3f", gamepad1.left_trigger, gamepad2.left_trigger);
            telemetry.addData("right_trigger: ", "%5.3f : %5.3f", gamepad1.right_trigger, gamepad2.right_trigger);
            telemetry.addData("left_bumper: ", "%b : %b", gamepad1.left_bumper, gamepad2.left_bumper);
            telemetry.addData("right_bumper: ", "%b : %b", gamepad1.right_bumper, gamepad2.right_bumper);

            //левый крест
            telemetry.addData("right: ", "%b : %b", gamepad1.dpad_right, gamepad2.dpad_right);
            telemetry.addData("left ", "%b : %b", gamepad1.dpad_left, gamepad2.dpad_left);
            telemetry.addData("up: ", "%b : %b", gamepad1.dpad_up, gamepad2.dpad_up);
            telemetry.addData("down: ", "%b : %b", gamepad1.dpad_down, gamepad2.dpad_down);

            //правый крест
            telemetry.addData("x: ", "%b : %b", gamepad1.x, gamepad2.x);
            telemetry.addData("y: ", "%b : %b", gamepad1.y, gamepad2.y);
            telemetry.addData("a: ", "%b", gamepad2.a); //проверяем статус кнопки несоответствующего геймпада в последнюю очередь
            telemetry.addData("b: ", "%b", gamepad1.b);

            //проверили, что все работает - жмем соответствующую кнопку и меняем цвет фона
            //+синий - первый, +красный - второй. желтый - обоссаться, ничего не работает... фиолетовый - играем!
            if(gamepad1.a) gm1 = true;
            if(gamepad2.b) gm2 = true;

            if(gm1) relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.BLUE);
                }
            });
            if(gm2) relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.RED);
                }
            });
            if(!gm1 && !gm2) relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.YELLOW);
                }
            });
            if(gm1 && gm2) relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(0xFF00FF);    //это фиолетовый. смесь Red и Blue, где Green == 0
                }
            });

            telemetry.update();
        }
    }

}
