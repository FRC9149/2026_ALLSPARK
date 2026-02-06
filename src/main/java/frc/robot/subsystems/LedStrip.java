package frc.robot.subsystems;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;

public class LedStrip {
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;

    /**
     * @param port The pwm port that the LED Strip is connected to
     * @param length How many LEDs are on the strip
     */
    public LedStrip(int port, int length) {
        led = new AddressableLED(port);
        ledBuffer = new AddressableLEDBuffer(length);
        led.setLength(length);
    }

    /** Sets a led to a certain color
     * @param index The index of the led you want to change
     * @param r The red value 0-255
     * @param g The Green value 0-255
     * @param b The Blue value 0-255
     */
    public void setLed(int index, int r, int g, int b) {
        ledBuffer.setRGB(index, r, g, b);
        led.setData(ledBuffer);
    }
    /** Takes in a BiConsumer that gets applied to all the led pixels.
     * @param f The BiConsumer that takes in the index of the pixel and the rgb values of the pixel
     */
    public void foreach(BiConsumer<Integer, int[]> f) {
        //for all the led's in the class
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            //consume the index and rgb value of the current led
            f.accept(
                i, 
                //new array of each color
                new int[]{
                    ledBuffer.getRed(i), 
                    ledBuffer.getGreen(i), 
                    ledBuffer.getBlue(i)
                }
            );
        }
    }

    /** Applys a pattern to the led buffer
     * @param pattern The led pattern you wish to apply
     */
    public void applyLEDPattern(LEDPattern pattern) {
        pattern.applyTo(ledBuffer);
        led.setData(ledBuffer);
    }

    /** stops output to the led strip*/
    public void stop() { led.stop();}

    public void setAll(int r, int g, int b) {}
    public void setRainbow() {}
    // TODO find a better name for this
    // should make a line that moves up the led strip
    //ex:
    // _____====__________
    // _________====______
    // _____________====__
    // ...
    public void setLoading() {}
}
