package org.firstinspires.ftc.teamcode.robotModules.Sensors.visions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp
public class BestWebcamDetectionAlgorithm extends LinearOpMode {
    OpenCvWebcam webcam;
    DETERMINATOR alkorithm;

    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setMillisecondsPermissionTimeout(1000);          //это максимальное время ожидания пока камера не успела открыться. если не удается открыть ее в течении этих 1000мс, камера кидает в exception

        alkorithm = new DETERMINATOR();
        //--------> webcam.setPipeline(alkorithm);
        webcam.openCameraDevice();                  //попробуем проверить, как работает это. Кажется, что камера должна пересылать изображение, как обычная FPV


        /* webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);

        waitForStart();
        webcam.stopStreaming();
        if(opModeIsActive()){
            telemetry.addLine("hi. camera has opened just fine.");
            telemetry.addLine("i'm stopping after 5 sec. see ya");
            telemetry.update();
            sleep(5000);
        }
         */

        //*/
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.setPipeline(alkorithm);
                /* Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 */
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addLine("ERROR OPENING CAMERA. FIX CODE");
            }
        });

        waitForStart();
        if (opModeIsActive()) {
            int i = 1;  //...
            telemetry.addData("ME SEE: ", i);
        }
    }

    public enum powifion {
        LEFT,
        CENTER,
        RIGHT
    }

    private class DETERMINATOR extends OpenCvPipeline { //собственно, алгоритм определения будет внутри этого класса
        //у нас изображение 640*480

        //определяем константы для областей определения
        static final int WIDTH = 20;
        static final int HEIGHT = 20;
        final Point left_TL_square = new Point(40, 300);
        final Point center_TL_square = new Point(310, 300);
        final Point right_TL_square = new Point(550, 300);
        final Point left_BR_square = new Point(left_TL_square.x+WIDTH, left_TL_square.y+HEIGHT);
        final Point center_BR_square = new Point(center_TL_square.x+WIDTH, center_TL_square.y+HEIGHT);
        final Point right_BR_square = new Point(right_TL_square.x+WIDTH, right_TL_square.y+HEIGHT);

        private volatile powifion pos = powifion.CENTER;

        @Override
        public Mat processFrame(Mat input) {
            return null;
        }

        boolean viewportPaused = false;

        @Override
        public void onViewportTapped(){
            viewportPaused = !viewportPaused;

            if(viewportPaused)
            {
                webcam.pauseViewport();
            }
            else
            {
                webcam.resumeViewport();
            }
        }
    }
}
