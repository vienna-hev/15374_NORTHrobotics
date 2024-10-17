package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class OpenCV extends OpMode {
    OpenCvWebcam happywebcam = null;

    @Override
    public void init() {
        WebcamName webcamName = HardwareMap.get(WebcamName.class, "webcam1");


    }
}
