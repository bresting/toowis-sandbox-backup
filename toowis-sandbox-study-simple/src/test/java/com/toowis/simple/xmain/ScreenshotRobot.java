package com.toowis.simple.xmain;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ScreenshotRobot {
    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        String format = "png";
        
        String fileName = "C:\\90.download\\sample." + format;
        Rectangle captureRect = new Rectangle(0, 0, 800, 600);
        BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
        ImageIO.write(screenFullImage, format, new File(fileName));
    }
}
