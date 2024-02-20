package org.firstinspires.ftc.teamcode.robotModules.Sensors.visions;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Recognition extends OpenCvPipeline {
    public int thresh=92, maxval=255;
    public RingPosition position = RingPosition.ZERO;
    public enum RingPosition {
        ZERO,
        ONE,
        FOUR;
    }
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);
    Mat region1_Cb, region2_Cb, region3_Cb;
    Mat HSV = new Mat();
    Mat Cb = new Mat();
    Mat Bin = new Mat();
    int avg1, avg2, avg3;

    void inputToCb(Mat input) {
        Imgproc.cvtColor(input, Cb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(Cb, Bin, 2);
        Imgproc.threshold(Bin, Bin, thresh, maxval, Imgproc.THRESH_BINARY_INV);
    }

    @Override
    public void init(Mat firstFrame) {
        inputToCb(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToCb(input);

        Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(100, 540);
        Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(710, 540);
        Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(1400, 290);
        final int REGION_WIDTH = 250;
        final int REGION_HEIGHT = 250;

        Point region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        Point region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region2_pointA = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x,
                REGION2_TOPLEFT_ANCHOR_POINT.y);
        Point region2_pointB = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        Point region3_pointA = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x,
                REGION3_TOPLEFT_ANCHOR_POINT.y);
        Point region3_pointB = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);

        region1_Cb = Bin.submat(new Rect(region1_pointA, region1_pointB));
        region2_Cb = Bin.submat(new Rect(region2_pointA, region2_pointB));
        region3_Cb = Bin.submat(new Rect(region3_pointA, region3_pointB));

        avg1 = (int) Core.mean(region1_Cb).val[0];
        avg2 = (int) Core.mean(region2_Cb).val[0];
        avg3 = (int) Core.mean(region3_Cb).val[0];

        Imgproc.rectangle(
                input,
                region1_pointA,
                region1_pointB,
                BLUE,
                5
        );

        Imgproc.rectangle(
                input,
                region2_pointA,
                region2_pointB,
                BLUE,
                5
        );

        Imgproc.rectangle(
                input,
                region3_pointA,
                region3_pointB,
                BLUE,
                5
        );

        int max = Math.max(avg1, avg2);

        if ((avg3 < avg2) && (Math.abs(avg1 - avg2) < 210)) {
            position = RingPosition.FOUR;

            Imgproc.rectangle(
                    input,
                    region2_pointA,
                    region2_pointB,
                    GREEN,
                    -1);
        }

        else if (avg3 < avg1) {
            position = RingPosition.ONE;

            Imgproc.rectangle(
                    input,
                    region1_pointA,
                    region1_pointB,
                    GREEN,
                    -1);
        }

        else {
            position = RingPosition.ZERO;
        }

        return input;

    }

    public RingPosition getAnalysis() {
        return position;
    }

    public int[] getAvgs(){
        int[] res= {avg1,avg2,avg3};
        return res;
    }

}
