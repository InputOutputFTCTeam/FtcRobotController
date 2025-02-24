
package org.firstinspires.ftc.teamcode.notUsed_trash.Sensors.visions;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@Disabled
@TeleOp
public class CenterstagePositionDeterminationTest extends LinearOpMode
{
    OpenCvInternalCamera phoneCam;
    CenterstagePositionDeterminationPipeline pipeline;

    @Override
    public void runOpMode()
    {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new CenterstagePositionDeterminationPipeline();
        phoneCam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.

        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(960,544, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        waitForStart();

        while (opModeIsActive())
        {
            telemetry.addData("Analysis", pipeline.getAnalysis());
            telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }
    }

    public static class CenterstagePositionDeterminationPipeline extends OpenCvPipeline
    {
        public enum CenterstageMarkerPosition
        {
            LEFT,
            CENTER,
            RIGHT,
            NOT_DETERMINED
        }

        public enum MarkerColour
        {
            RED,
            BLUE,
            NOT_DETERMINED
        }

        //Colors for drawing in BRG
        // private static final Scalar BLUE   = new Scalar(255,   0,   0) ;
        private static final Scalar GREEN  = new Scalar(0,   255, 0  ) ;
        private static final Scalar CYAN  = new Scalar(255,   255, 0  ) ;
        private static final Scalar YELLOW  = new Scalar(0,   255, 255  ) ;
        // private static final Scalar BLACK  = new Scalar(0,   0,   0  ) ;
        private static final Scalar WHITE  = new Scalar(255, 255, 255) ;
        //RGB



        private static final Scalar MAX_RGB_RED_SC    = new Scalar(200, 50,  50 );
        private static final Scalar MIN_RGB_RED_SC    = new Scalar(140,30,   30 );
        private static final Scalar MAX_RGB_BLUE_SC   = new Scalar(40, 40,   180);
        private static final Scalar MIN_RGB_BLUE_SC   = new Scalar(30, 30,    140);


        private static final Mat MAX_RGB_RED    = new Mat(1, 1, CvType.CV_8UC3, MAX_RGB_RED_SC  );
        private static final Mat MIN_RGB_RED    = new Mat(1, 1, CvType.CV_8UC3, MIN_RGB_RED_SC  );
        private static final Mat MAX_RGB_BLUE   = new Mat(1, 1, CvType.CV_8UC3, MAX_RGB_BLUE_SC );
        private static final Mat MIN_RGB_BLUE   = new Mat(1, 1, CvType.CV_8UC3, MIN_RGB_BLUE_SC );

        private static final Mat MAX_HSL_RED    = new Mat();
        private static final Mat MIN_HSL_RED    = new Mat();
        private static final Mat MAX_HSL_BLUE  = new Mat();
        private static final Mat MIN_HSL_BLUE  = new Mat();

/*
        Imgproc.cvtColor(MAX_RGB_YELLOW, MAX_HSL_YELLOW, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(MIN_RGB_YELLOW, MIN_HSL_YELLOW, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(MAX_RGB_BLACK,  MAX_HSL_BLACK,  Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(MIN_RGB_BLACK,  MIN_HSL_BLACK,  Imgproc.COLOR_RGB2HSV);
*/ static {
    Imgproc.cvtColor(MAX_RGB_RED, MAX_HSL_RED, Imgproc.COLOR_RGB2HSV);
    Imgproc.cvtColor(MIN_RGB_RED, MIN_HSL_RED, Imgproc.COLOR_RGB2HSV);
    Imgproc.cvtColor(MAX_RGB_BLUE, MAX_HSL_BLUE, Imgproc.COLOR_RGB2HSV);
    Imgproc.cvtColor(MIN_RGB_BLUE, MIN_HSL_BLUE, Imgproc.COLOR_RGB2HSV);
}

        private static final double[] MAX_HSL_RED_CH_DATA    = MAX_HSL_RED.get(0, 0);
        private static final double[] MIN_HSL_RED_CH_DATA    = MIN_HSL_RED.get(0, 0);
        private static final double[] MAX_HSL_BLUE_CH_DATA   = MAX_HSL_BLUE.get(0, 0);
        private static final double[] MIN_HSL_BLUE_CH_DATA   = MIN_HSL_BLUE.get(0, 0);

        private static final Scalar MAX_HSL_RED_SC  = new Scalar(MAX_HSL_RED_CH_DATA[0]  ,MAX_HSL_RED_CH_DATA[1]  ,MAX_HSL_RED_CH_DATA[2] );
        private static final Scalar MIN_HSL_RED_SC  = new Scalar(MIN_HSL_RED_CH_DATA[0]  ,MIN_HSL_RED_CH_DATA[1]  ,MIN_HSL_RED_CH_DATA[2] );
        private static final Scalar MAX_HSL_BLUE_SC  = new Scalar(MAX_HSL_BLUE_CH_DATA[0]  ,MAX_HSL_BLUE_CH_DATA[1]  ,MAX_HSL_BLUE_CH_DATA[2] );
        private static final Scalar MIN_HSL_BLUE_SC  = new Scalar(MIN_HSL_BLUE_CH_DATA[0]  ,MIN_HSL_BLUE_CH_DATA[1]  ,MIN_HSL_BLUE_CH_DATA[2] );

        private static final int REGION_WIDTH  = 20;
        private static final int REGION_HEIGHT = 20;

        private static final int IMAGE_CENTER_VERTICAL   = 272;//amera resolution height / 2 expected
        private static final int IMAGE_CENTER_HORIZONTAL = 480;

//        private static final double REGION_VERTICAL_INTERVAL = 0.5 ; // 0;1 interval between regions 0 - share same side 1 - side on edge of image
          private static final double REGION_HORIZONTAL_INTERVAL = 0.8; // 0;1
/*

        private static final Point regionUpTopleftAnchorPoint   = new Point(IMAGE_CENTER_HORIZONTAL - ( REGION_WIDTH / 2 ), (IMAGE_CENTER_VERTICAL * ( REGION_VERTICAL_INTERVAL + 1 ) + REGION_HEIGHT ));
        private static final Point regionDownTopleftAnchorPoint = new Point(IMAGE_CENTER_HORIZONTAL - ( REGION_WIDTH / 2 ), (IMAGE_CENTER_VERTICAL * ( REGION_VERTICAL_INTERVAL + 1 )) - IMAGE_CENTER_VERTICAL ) ;

*/
        private static final Point regionCenterTopleftAnchorPoint = new Point(IMAGE_CENTER_HORIZONTAL - (REGION_WIDTH / 2), IMAGE_CENTER_VERTICAL + (REGION_HEIGHT / 2));
        // private static final Point regionLeftTopleftAnchorPoint   = new Point(IMAGE_CENTER_HORIZONTAL - (IMAGE_CENTER_HORIZONTAL * REGION_HORIZONTAL_INTERVAL) - REGION_WIDTH
        private static final Point regionLeftTopleftAnchorPoint   = new Point(IMAGE_CENTER_HORIZONTAL - (IMAGE_CENTER_HORIZONTAL * REGION_HORIZONTAL_INTERVAL) - REGION_WIDTH, IMAGE_CENTER_VERTICAL + (REGION_HEIGHT / 2));
        private static final Point regionRightTopleftAnchorPoint  = new Point(IMAGE_CENTER_HORIZONTAL + (IMAGE_CENTER_HORIZONTAL * REGION_HORIZONTAL_INTERVAL) + REGION_WIDTH, IMAGE_CENTER_VERTICAL + (REGION_HEIGHT / 2));


        private static final Point regionLeft_pointA = new Point(
                regionLeftTopleftAnchorPoint.x,
                regionLeftTopleftAnchorPoint.y);
        private static final Point regionLeft_pointB = new Point(
                regionLeftTopleftAnchorPoint.x + REGION_WIDTH,
                regionLeftTopleftAnchorPoint.y + REGION_HEIGHT);

        private static final Point regionCenter_pointA = new Point(
                regionCenterTopleftAnchorPoint.x, regionCenterTopleftAnchorPoint.y);
        private static final Point regionCenter_pointB = new Point(
                regionCenterTopleftAnchorPoint.x + REGION_WIDTH,
                regionCenterTopleftAnchorPoint.y + REGION_HEIGHT);

        private static final Point regionRight_pointA = new Point(
            regionRightTopleftAnchorPoint.x,
            regionRightTopleftAnchorPoint.y);
        private static final Point regionRight_pointB = new Point(
                regionRightTopleftAnchorPoint.x + REGION_WIDTH,
                regionRightTopleftAnchorPoint.y + REGION_HEIGHT);

        /*
         * Working variables
         */

        private static Mat HSV = new Mat();

        private static Mat regionLeft_HSV, regionCenter_HSV, regionRight_HSV;
        private static Scalar regionLeft_HSV_mean, regionCenter_HSV_mean, regionRight_HSV_mean;

        // Volatile since accessed by OpMode thread w/o synchronization
/*
        private static volatile MarkerPosition position = MarkerPosition.NOT_DETERMINED;
        private static volatile MarkerRegionColour UpMarkerRegionColour = MarkerRegionColour.NOT_DETERMINED;
        private static volatile MarkerRegionColour DownMarkerRegionColour = MarkerRegionColour.NOT_DETERMINED;
*/

        private static volatile CenterstageMarkerPosition position = CenterstageMarkerPosition.NOT_DETERMINED;
        private static volatile MarkerColour colour = MarkerColour.NOT_DETERMINED;

        private static void inputToHSV(Mat input)
        {
            Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
        }
        @Override
        public void init(Mat firstFrame)
        {

            /*
             * We need to call this in order to make sure the 'Cb'
             * object is initialized, so that the submats we make
             * will still be linked to it on subsequent frames. (If
             * the object were to only be initialized in processFrame,
             * then the submats would become delinked because the backing
             * buffer would be re-allocated the first time a real frame
             * was crunched)
             */
            inputToHSV(firstFrame);

            /*
             * Submats are a persistent reference to a region of the parent
             * buffer. Any changes to the child affect the parent, and the
             * reverse also holds true.
             */
            /*regionUp_HSV   = HSV.submat(new Rect(regionUp_pointA,   regionUp_pointB)  );
            regionDown_HSV = HSV.submat(new Rect(regionDown_pointA, regionDown_pointB));
*/
            regionLeft_HSV   = HSV.submat(new Rect(regionLeft_pointA,   regionLeft_pointB)  );
            regionCenter_HSV = HSV.submat(new Rect(regionCenter_pointA, regionCenter_pointB));
            regionRight_HSV = HSV.submat(new Rect(regionRight_pointA, regionRight_pointB));

        }

        @Override
        public Mat processFrame(Mat input)
        {

            inputToHSV(input);

            regionLeft_HSV_mean   = Core.mean(regionLeft_HSV  );
            regionCenter_HSV_mean = Core.mean(regionCenter_HSV);
            regionRight_HSV_mean = Core.mean(regionRight_HSV);

            // draw rectangles

            Imgproc.rectangle(
                    input,
                    regionLeft_pointA,
                    regionLeft_pointB,
                    GREEN,
                    2);
            Imgproc.rectangle(
                    input,
                    regionCenter_pointA,
                    regionCenter_pointB,
                    GREEN,
                    2);
            Imgproc.rectangle(
                    input,
                    regionRight_pointA,
                    regionRight_pointB,
                    GREEN,
                    2);



            //to do : check every channel for activation
                if(((regionLeft_HSV_mean.val[0] <= MAX_HSL_RED_SC.val[0]) && (regionLeft_HSV_mean.val[0] >= MIN_HSL_RED_SC.val[0])))
                {
                    if((regionLeft_HSV_mean.val[1] <= MAX_HSL_RED_SC.val[1]) && (regionLeft_HSV_mean.val[1] >= MIN_HSL_RED_SC.val[1])) {
                        if((regionLeft_HSV_mean.val[2] <= MAX_HSL_RED_SC.val[2]) && (regionLeft_HSV_mean.val[2] >= MIN_HSL_RED_SC.val[2])) {
                            position = CenterstageMarkerPosition.LEFT;
                            colour = MarkerColour.RED;
                            Imgproc.rectangle(
                                    input,
                                    regionLeft_pointA,
                                    regionLeft_pointB,
                                    CYAN,
                                    -1);
                        }
                    }
                }



                if(((regionCenter_HSV_mean.val[0] <= MAX_HSL_RED_SC.val[0]) && (regionCenter_HSV_mean.val[0] >= MIN_HSL_RED_SC.val[0])))
                {
                    if(((regionCenter_HSV_mean.val[1] <= MAX_HSL_RED_SC.val[1]) && (regionCenter_HSV_mean.val[1] >= MIN_HSL_RED_SC.val[1]))){
                        if(((regionCenter_HSV_mean.val[2] <= MAX_HSL_RED_SC.val[2]) && (regionCenter_HSV_mean.val[2] >= MIN_HSL_RED_SC.val[2]))){
                            position = CenterstageMarkerPosition.CENTER;
                            colour = MarkerColour.RED;
                            Imgproc.rectangle(
                                    input,
                                    regionCenter_pointA,
                                    regionCenter_pointB,
                                    CYAN,
                                    -1);
                        }
                    }
                }




                if(((regionRight_HSV_mean.val[0] <= MAX_HSL_RED_SC.val[0]) && (regionRight_HSV_mean.val[0] >= MIN_HSL_RED_SC.val[0])))
                {
                    if(((regionRight_HSV_mean.val[1] <= MAX_HSL_RED_SC.val[1]) && (regionRight_HSV_mean.val[1] >= MIN_HSL_RED_SC.val[1]))){
                        if(((regionRight_HSV_mean.val[2] <= MAX_HSL_RED_SC.val[2]) && (regionRight_HSV_mean.val[2] >= MIN_HSL_RED_SC.val[2]))){
                            position = CenterstageMarkerPosition.RIGHT;
                            colour = MarkerColour.RED;
                            Imgproc.rectangle(
                                    input,
                                    regionRight_pointA,
                                    regionRight_pointB,
                                    CYAN,
                                    -1);
                        }
                    }
                }









                if(((regionLeft_HSV_mean.val[0] <= MAX_HSL_BLUE_SC.val[0]) && (regionLeft_HSV_mean.val[0] >= MIN_HSL_BLUE_SC.val[0])))
                {
                    if(((regionLeft_HSV_mean.val[1] <= MAX_HSL_BLUE_SC.val[1]) && (regionLeft_HSV_mean.val[1] >= MIN_HSL_BLUE_SC.val[1]))){
                        if(((regionLeft_HSV_mean.val[2] <= MAX_HSL_BLUE_SC.val[2]) && (regionLeft_HSV_mean.val[2] >= MIN_HSL_BLUE_SC.val[2]))){
                            position = CenterstageMarkerPosition.LEFT;
                            colour = MarkerColour.BLUE;
                            Imgproc.rectangle(
                                    input,
                                    regionLeft_pointA,
                                    regionLeft_pointB,
                                    YELLOW,
                                    -1);
                        }
                    }
                }



                if(((regionCenter_HSV_mean.val[0] <= MAX_HSL_BLUE_SC.val[0]) && (regionCenter_HSV_mean.val[0] >= MIN_HSL_BLUE_SC.val[0])))
                {
                    if(((regionCenter_HSV_mean.val[1] <= MAX_HSL_BLUE_SC.val[1]) && (regionCenter_HSV_mean.val[1] >= MIN_HSL_BLUE_SC.val[1]))){
                        if(((regionCenter_HSV_mean.val[2] <= MAX_HSL_BLUE_SC.val[2]) && (regionCenter_HSV_mean.val[2] >= MIN_HSL_BLUE_SC.val[2]))){
                            position = CenterstageMarkerPosition.CENTER;
                            colour = MarkerColour.BLUE;
                            Imgproc.rectangle(
                                    input,
                                    regionCenter_pointA,
                                    regionCenter_pointB,
                                    YELLOW,
                                    -1);
                        }
                    }
                }


                if(((regionRight_HSV_mean.val[0] <= MAX_HSL_BLUE_SC.val[0]) && (regionRight_HSV_mean.val[0] >= MIN_HSL_BLUE_SC.val[0])))
                {
                    if(((regionRight_HSV_mean.val[1] <= MAX_HSL_BLUE_SC.val[1]) && (regionRight_HSV_mean.val[1] >= MIN_HSL_BLUE_SC.val[1]))){
                        if(((regionRight_HSV_mean.val[2] <= MAX_HSL_BLUE_SC.val[2]) && (regionRight_HSV_mean.val[2] >= MIN_HSL_BLUE_SC.val[2]))){
                         position = CenterstageMarkerPosition.RIGHT;
                            colour = MarkerColour.BLUE;
                            Imgproc.rectangle(
                                    input,
                                    regionRight_pointA,
                                    regionRight_pointB,
                                    YELLOW,
                                    -1);
                        }
                    }
                }



            if((position != CenterstageMarkerPosition.RIGHT) && (position != CenterstageMarkerPosition.LEFT) && (position != CenterstageMarkerPosition.CENTER))
            {
                position = CenterstageMarkerPosition.NOT_DETERMINED;
            }
            if((colour != MarkerColour.BLUE) && (colour != MarkerColour.RED))
            {
                colour = MarkerColour.NOT_DETERMINED;
            }


            return input;

        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public final CenterstageMarkerPosition getAnalysis()
        {
            return position;
        }


        public final MarkerColour getColour()
        {
            return colour;
        }
    }
}
