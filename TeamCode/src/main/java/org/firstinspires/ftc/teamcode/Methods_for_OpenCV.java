package org.firstinspires.ftc.teamcode.methods;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import java.util.ArrayList;
import java.util.List;




import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Todo: create there all necessary for OpenCV instead of creating new instance in every Autonomous Method
public class Methods_for_OpenCV extends  LinearOpMode{
    static int valLeft;
    static int valRight;
    private static double rectHeight = 0.5 / 8;
    private static double rectWidth = 0.5 / 8;
    private static double rectHeight1 = 0.5 / 8;
    private static double rectWidth1 = 0.5 / 8;

    private static float offsetX = 0f / 8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    private static float offsetY = 0f / 8f;//changing this moves the three

    // rects and circles up or down, range: (-4, 4) not inclusive

    private static float[] leftPos = {3f / 11f + offsetX, 4f / 8f + offsetY};
    private static float[] rightPos = {6f / 8f + offsetX, 5 / 8.7f + offsetY};

    final int rows = 640;
    final int cols = 480;

    public static int getValLeft() {
        return valLeft;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public static float[] getLeftPos() {
        return leftPos;
    }

    public static void setLeftPos(float[] leftPos) {
        Methods_for_OpenCV.leftPos = leftPos;
    }

    public static float[] getRightPos() {
        return rightPos;
    }

    public static void setRightPos(float[] rightPos) {
        Methods_for_OpenCV.rightPos = rightPos;
    }

    public static void setValLeft(int valLeft) {
        Methods_for_OpenCV.valLeft = valLeft;
    }

    public static int getValRight() {
        return valRight;
    }

    public static void setValRight(int valRight) {
        Methods_for_OpenCV.valRight = valRight;
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public static class StageSwitchingPipeline extends OpenCvPipeline {
        Mat yCbCrChan2Mat = new Mat();
        Mat thresholdMat = new Mat();
        Mat all = new Mat();
        List<MatOfPoint> contoursList = new ArrayList<>();

        enum Stage {//color difference. greyscale
            detection,//includes outlines
            THRESHOLD,//b&w
            RAW_IMAGE,//displays raw view
        }

        private Methods_for_OpenCV.StageSwitchingPipeline.Stage stageToRenderToViewport = Methods_for_OpenCV.StageSwitchingPipeline.Stage.detection;
        private  Methods_for_OpenCV.StageSwitchingPipeline.Stage[] stages = Methods_for_OpenCV.StageSwitchingPipeline.Stage.values();

        @Override
        public void onViewportTapped() {
            /*
             * Note that this method is invoked from the UI thread
             * so whatever we do here, we must do quickly.
             */

            int currentStageNum = stageToRenderToViewport.ordinal();

            int nextStageNum = currentStageNum + 1;

            if (nextStageNum >= stages.length) {
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override
        public Mat processFrame(Mat input) {
            contoursList.clear();
            /*
             * This pipeline finds the contours of yellow blobs such as the Gold Mineral
             * from the Rover Ruckus game.
             */
            Mat rgbImage = new Mat();
            Imgproc.cvtColor(input, rgbImage, Imgproc.COLOR_BGR2RGB);

            // Преобразование RGB в HSV
            Mat hsvImage = new Mat();
            Imgproc.cvtColor(rgbImage, hsvImage, Imgproc.COLOR_RGB2HSV);

            // Определение диапазона красного цвета в HSV
            Scalar lowerRed = new Scalar(0,20,20);
            Scalar upperRed = new Scalar(160,255,255);

            // Определение диапазона синего цвета в HSV
            Scalar lowerBlue = new Scalar(160, 40, 40);
            Scalar upperBlue = new Scalar(255, 255, 255);

            // Создание масок для красного и синего цветов
            Mat redMask = new Mat();
            Mat blueMask = new Mat();
            Core.inRange(hsvImage, lowerRed, upperRed, redMask);
            Core.inRange(hsvImage, lowerBlue, upperBlue, blueMask);

            // Создание маски для желтого цвета
            Mat yellowMask = new Mat();
            //Core.addWeighted(redMask, 1.0, blueMask, 1.0, 0.0, yellowMask);
            // Core.bitwise_or(redMask, blueMask, yellowMask);

            // Применение маски к изображению
            Mat hsvImageBlue = new Mat();
            Mat hsvImageRed = new Mat();

            Mat yellowResult = new Mat();
            Core.add(hsvImage, new Scalar(60, 100, 100), rgbImage, blueMask);
            Core.add(hsvImage, new Scalar(60, 100, 100), rgbImage, redMask);
            Core.bitwise_and(rgbImage, rgbImage, yellowResult, yellowMask);
            Mat yellowResultRGB = new Mat();
            Imgproc.cvtColor(yellowResult, yellowResultRGB, Imgproc.COLOR_HSV2RGB);



            //color diff cb.
            //lower cb = more blue = skystone = white
            //higher cb = less blue = oyellow stne = grey



            Imgproc.cvtColor(yellowResultRGB, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);//converts rgb to ycrcb


            Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);//takes cb difference and stores


            //b&w
            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 120, 255, Imgproc.THRESH_BINARY_INV);

            //outline/contour
            Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            yCbCrChan2Mat.copyTo(all);//copies mat object
            // Imgproc.drawContours(all, contoursList, -1, new Scalar(255, 0, 0), 3, 8);//draws blue contours


            //get values from frame


            double[] pixLeft = thresholdMat.get((int) (input.rows() * leftPos[1]), (int) (input.cols() * leftPos[0]));//gets value at circle
            valLeft = (int) pixLeft[0];

            double[] pixRight = thresholdMat.get((int) (input.rows() * rightPos[1]), (int) (input.cols() * rightPos[0]));//gets value at circle
            valRight = (int) pixRight[0];

            //create three points
            Point pointLeft = new Point((int) (input.cols() * leftPos[0]), (int) (input.rows() * leftPos[1]));
            Point pointRight = new Point((int) (input.cols() * rightPos[0]), (int) (input.rows() * rightPos[1]));

            //draw circles on those points
            Imgproc.circle(all, pointLeft, 5, new Scalar(240, 100, 100), 1);//draws circle
            Imgproc.circle(all, pointRight, 5, new Scalar(240, 100, 100), 1);//draws circle

            //draw 3 rectangles
            Imgproc.rectangle(//1-3
                    all,
                    new Point(
                            input.cols() * (leftPos[0] - rectWidth1 / 2),
                            input.rows() * (leftPos[1] - rectHeight1 / 2)),
                    new Point(
                            input.cols() * (leftPos[0] + rectWidth1 / 2),
                            input.rows() * (leftPos[1] + rectHeight1 / 2)),
                    new Scalar(240, 100, 100), 3);

            Imgproc.rectangle(//5-7
                    all,
                    new Point(
                            input.cols() * (rightPos[0] - rectWidth / 2),
                            input.rows() * (rightPos[1] - rectHeight / 2)),
                    new Point(
                            input.cols() * (rightPos[0] + rectWidth / 2),
                            input.rows() * (rightPos[1] + rectHeight / 2)),
                    new Scalar(240, 100, 100), 3);

            switch (stageToRenderToViewport) {
                case THRESHOLD: {
                    return thresholdMat;
                }

                case detection: {
                    return all;
                }

                case RAW_IMAGE: {
                    return input;
                }

                default: {
                    return input;
                }
            }
        }
    }
}

