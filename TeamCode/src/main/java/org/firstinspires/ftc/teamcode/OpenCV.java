package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class OpenCV extends OpMode {
    OpenCvWebcam happywebcam = null;

    @Override
    public void init() {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam1");
        int cameraMoniterViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMoniterViewId", "id", hardwareMap.appContext.getPackageName());
        happywebcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMoniterViewId);

        happywebcam.setPipeline(new examplePipeline());

        happywebcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            public void onOpened() {
                happywebcam.startStreaming(640,360, OpenCvCameraRotation.UPRIGHT);
            }

            public void onError(int errorCode) {

            }
        });
    }

    @Override
    public void loop() {

    }

    class examplePipeline extends OpenCvPipeline{
        Mat YCbCr = new Mat();
        Mat leftCrop;
        Mat rightCrop;
        double leftavgfin;
        double rightavgfin;
        Mat outPut = new Mat();
        Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, YCbCr,Imgproc.COLOR_RGB2YCrCb);
            telemetry.addLine("pipeline running");

            //possibly 3 in future for object recognition
            Rect leftRect = new Rect(1, 1, 639, 719);
            Rect rightRect = new Rect(640, 1, 639, 719);

            input.copyTo(outPut);
            Imgproc.rectangle(outPut, leftRect, rectColor, 2);
            Imgproc.rectangle(outPut, rightRect, rectColor, 2);

            leftCrop = YCbCr.submat(leftRect);
            rightCrop = YCbCr.submat(rightRect);

            Core.extractChannel(leftCrop, leftCrop, 2);
            Core.extractChannel(rightCrop, rightCrop, 2);

            Scalar leftavg = Core.mean(leftCrop);
            Scalar rightavg = Core.mean(leftCrop);

            leftavgfin = leftavg.val[0];
            rightavgfin = rightavg.val[0];

            if (leftavgfin > rightavgfin) {
                telemetry.addLine("left wins!");
            }
            else {
                telemetry.addLine("right wins!");
            }

            return(outPut);
        }
    }
}