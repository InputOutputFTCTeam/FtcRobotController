package org.firstinspires.ftc.teamcode;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureRequest;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSequenceId;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraException;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.CameraControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.concurrent.TimeUnit;


/// Todo: there will be code that combines opencv april tags and tensorflow
public class VisionPortall extends Methods {
    public double x;
    public double y;
    private static final String TFOD_MODEL_ASSET1 = "CenterStageobh.tflite";
    private static final String TFOD_MODEL_ASSET2 = "CenterStageBlue.tflite";
    private static final String[] LABELS = {
            "red allience",
            "blue allience"
    };
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    public AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    public TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */

    public VisionPortal visionPortal;
    ExposureControl myExposureControl;
    public void initVisionPortal() {


            // -----------------------------------------------------------------------------------------
            // AprilTag Configuration
            // -----------------------------------------------------------------------------------------
            aprilTag = new AprilTagProcessor.Builder()

                    .setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                    .build();

            // -----------------------------------------------------------------------------------------
            // TFOD Configuration
            // -----------------------------------------------------------------------------------------

            tfod = new TfodProcessor.Builder()

                    .setMaxNumRecognitions(1)
                    .setModelAssetName(TFOD_MODEL_ASSET1)
                    .setModelLabels(LABELS)
                    .build();



    }

    public TfodProcessor getTfod() {
        return tfod;
    }

    public void setTfod(TfodProcessor tfod) {
        this.tfod = tfod;
    }

    public AprilTagProcessor getAprilTag() {
        return aprilTag;
    }

    public void setAprilTag(AprilTagProcessor aprilTag) {
        this.aprilTag = aprilTag;
    }
}