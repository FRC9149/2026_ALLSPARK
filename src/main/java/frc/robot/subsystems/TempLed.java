package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class TempLed {
    AddressableLED led = new AddressableLED(2);
    AddressableLEDBuffer buffer = new AddressableLEDBuffer(300);

    public void setup() {
        led.setLength(buffer.getLength());
        led.start();

        for(int i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, 0, 0, 255);
        }
    }

    public void setBlue() {
        

        led.setData(buffer);
    }
}
