package frc.robot.subsystems;

import java.util.Map;
import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
    //TODO
    Once we are done with writing this class, we should move it to the library.
    I moved the class to the main project so that it is easier to write code. 
    The implementation will be the same, just different imports

    LEDPatterns seem to be the easiet way to program complex patterns while simple or smaller patterns will be used with setLED
*/
public class LedStrip extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;

     private LEDPattern activePattern = LEDPattern.solid(edu.wpi.first.wpilibj.util.Color.kGreen);

    /**
     * @param port The pwm port that the LED Strip is connected to
     * @param length How many LEDs are on the strip
     */

    //------------------------base stuff--------------------------------------
    public LedStrip(int port, int length) {
        led = new AddressableLED(port);
        ledBuffer = new AddressableLEDBuffer(length);
        led.setLength(length);

        led.start();
    }

     public void applyActiveLEDPattern(LEDPattern newPattern) {
        this.activePattern = newPattern;
    }

    //@Override
    //public void periodic() {
    //    // Runs every 20ms to update the animation frames
    //    activePattern.applyTo(ledBuffer);
    //    led.setData(ledBuffer);
    //}
    
    //------------------------------------more stuff------------------------------------------

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

    public void setAll(int r, int g, int b) {

        for (int index = 0; index < ledBuffer.getLength(); index++) {
            ledBuffer.setRGB(index, r, g, b);

        }
        led.setData(ledBuffer);
    }
    public void setRainbow() {}
    // todo find a better name for this
    // should make a line that moves up the led strip
    //ex:
    // _____====__________
    // _________====______
    // _____________====__
    // ...
    public void setLoading() {}

    public void sethalf(){

        LEDPattern steps = LEDPattern.steps(Map.of(0, Color.kWhite, 0.5, Color.kBlue));

// Apply the LED pattern to the data buffer
steps.applyTo(ledBuffer);

// Write the data to the LED strip
led.setData(ledBuffer);
      


    }
}
