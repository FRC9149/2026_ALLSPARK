package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Led extends SubsystemBase {

      /** Called once at the beginning of the robot program. 
           * @return */
          
    // PWM port 9
    // Must be a PWM header, not MXP or DIO
    AddressableLED m_led = new AddressableLED(9);

    public Led() {
    // Reuse buffer
        // Default to a length of 60, start empty output
        // Length is expensive to set, so only set it once, then just update data
        AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(158);
    m_led.setLength(m_ledBuffer.getLength());
            
    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();
  }
  public void color(int r, int g, int b) {

    AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(158);

    // Set the color
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
       m_ledBuffer.setRGB(i, r, g, b);
    }

    m_led.setData(m_ledBuffer);

  }
  public void gradient(int r, int g, int b){

  }
}
