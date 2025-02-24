package org.firstinspires.ftc.teamcode.notUsed_trash.Sensors.visions;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

@Disabled
@TeleOp(name = "camtest")
public class visiontelemetry extends LinearOpMode {
    protected Recognition recognition;
    OpenCvCamera webcam;
    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        recognition = new Recognition();
        recognition.getAnalysis();
        webcam.setPipeline(recognition);
        webcam.openCameraDevice();

        waitForStart();
        while (opModeIsActive()) {
            if (recognition.getAnalysis() == Recognition.RingPosition.ZERO) {
                telemetry.addLine("zone A");
                telemetry.update();
            }
            if (recognition.getAnalysis() == Recognition.RingPosition.ONE) {
                telemetry.addLine("zone B");
                telemetry.update();
            }
            if (recognition.getAnalysis() == Recognition.RingPosition.FOUR) {
                telemetry.addLine("zone C");
                telemetry.update();
            }

            telemetry.addData("position is ", recognition.getAnalysis());
            telemetry.addData("avg1 is ", recognition.getAvgs()[0]);
            telemetry.addData("avg 2 is", recognition.getAvgs()[1]);
            telemetry.addData("black avg is", recognition.getAvgs()[2]);
            telemetry.addData("thresh", recognition.thresh);
            telemetry.addData("maxval", recognition.maxval);
            telemetry.update();
        }
    }
}
