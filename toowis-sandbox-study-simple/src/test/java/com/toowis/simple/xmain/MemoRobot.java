package com.toowis.simple.xmain;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MemoRobot {
    public static void main(String[] args) throws AWTException {
        // 메모장 켜놓고...
        Robot r = new Robot();
        r.mouseMove(100, 100);
        r.delay(100);

        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(100);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection("내용"), null);
        
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        
        r.keyRelease(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);
    }
}
