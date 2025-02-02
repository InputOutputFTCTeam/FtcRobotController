package org.firstinspires.ftc.teamcode.ForNewRC.camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class opencv {
    double cX = 0; //координаты камеры
    double cY = 0;
    double width = 0;

    //инициализируем камеру
    private OpenCvCamera cam;
    private  static final int CAMERA_WIDTH = 1280;
    private static final int CAMERA_HEIGHT = 720;
    public static final double realObjectUnits = 5; //число-звглушка длины объекта в реальном времени
    public static final double focalLenght = 379; //тоже число-заглушка фокусного расстояния камеры (в пикселях)

    private LinearOpMode opencvOpMode = null;

    public opencv() {}

    public opencv(LinearOpMode opMode) {opencvOpMode = opMode; }

    public LinearOpMode getOpMode() {return opencvOpMode; }

    public void setOpMode(LinearOpMode opMode) {opencvOpMode = opMode; }

    /** создаём объект камеры */
    public void initOpenCv(){
        int cameraMotionField = opencvOpMode.hardwareMap.appContext.getResources().getIdentifier(
                "cameraMotionFieldID", "ID",opencvOpMode.hardwareMap.appContext.getPackageName());
        cam=OpenCvCameraFactory.getInstance().createWebcam(opencvOpMode.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMotionField);
        cam.openCameraDevice();
        cam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);

    }
    /** основная штука для детекта объектов */
    class YellowBlobDetectionPipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            // Preprocess the frame to detect yellow regions
            Mat yellowMask = preprocessFrame(input);

            //Find contours of the detected yellow regions
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(yellowMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            //find largest yellow contour (blob)
            MatOfPoint largestContour = findLargestContour(contours);

            if (largestContour != null) {
                // Draw a red outline around the largest detected object
                Imgproc.drawContours(input, contours, contours.indexOf(largestContour), new Scalar(255, 0, 0), 2);
                // Calculate the width of the bounding box
                width = calculateWidth(largestContour);

                // Display the width next to the label
                String widthLabel = "Width: " + (int) width + " pixels";
                Imgproc.putText(input, widthLabel, new Point(cX + 10, cY + 20), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
                //Display the Distance
                String distanceLabel = "Distance: " + String.format("%.2f", getDistance(width)) + " inches";
                Imgproc.putText(input, distanceLabel, new Point(cX + 10, cY + 60), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
                // Calculate the centroid of the largest contour
                Moments moments = Imgproc.moments(largestContour);
                cX = moments.get_m10() / moments.get_m00();
                cY = moments.get_m01() / moments.get_m00();

                // Draw a dot at the centroid
                String label = "(" + (int) cX + ", " + (int) cY + ")";
                Imgproc.putText(input, label, new Point(cX + 10, cY), Imgproc.FONT_HERSHEY_COMPLEX, 0.5, new Scalar(0, 255, 0), 2);
                Imgproc.circle(input, new Point(cX, cY), 5, new Scalar(0, 255, 0), -1);

            }
            return input;
        }
    }
    /** именно этот метод отвечает за детект цветов */
    private  Mat preprocessFrame(Mat frame) {
        Mat hsvFrame = new Mat();
        Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);
        Scalar lowerYellow = new Scalar(100,100,100);
        Scalar upperYellow = new Scalar(180,255,255);
        Mat yellowMask = new Mat();
        Core.inRange(hsvFrame, upperYellow, lowerYellow, yellowMask);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
        Imgproc.morphologyEx(yellowMask, yellowMask, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(yellowMask, yellowMask, Imgproc.MORPH_OPEN, kernel);
        return  yellowMask;
    }

    /** находим самый большой блок цвета */
    private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
        double maxArea=0;
        MatOfPoint largestContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestContour = contour;
            }
        }
        return largestContour;
    }

    private double calculateWidth(MatOfPoint contour) {
        Rect boundingReact = Imgproc.boundingRect(contour);
        return boundingReact.width;
    }
     /** рассчитываем дистанцию до объекта */
    private static double getDistance (double width) {
        double distance = (realObjectUnits * focalLenght) / width;
        return distance;
    }
}
