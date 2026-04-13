package frc.robot.subsystems;

import java.util.regex.Pattern;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.robocats.LED.LedStrip;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LowerIntake extends SubsystemBase{
    private final SparkMax intake2m = new SparkMax(9, MotorType.kBrushless);//was 12
    private final SparkMax intake3m = new SparkMax(12, MotorType.kBrushless);
    private final RelativeEncoder encoder = intake2m.getEncoder();
    private final RelativeEncoder encoder2 = intake3m.getEncoder();
    LedStrip subsystem;
    // private final SparkClosedLoopController controller = intake2m.getClosedLoopController();
    // private static final double IN_POS = 0.0;
    // private static final double OUT_POS = 0.8;
  
    // Closed loop adjusts number to find the correct spot for PIDs.
    // Ex. You want to throw a baseball at 30 mph. First throw is 36, so you soften your throw to 28. 
    // You strengthen it again back to 30 mph
    // - Hugo
    // no but close enough - El

    public LowerIntake(LedStrip ledstrip) {
        this.subsystem = ledstrip;

        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig config2 = new SparkMaxConfig();
        
        config.idleMode(IdleMode.kBrake);
        config2.idleMode(IdleMode.kBrake).inverted(true);
        // config.closedLoop.pid(0.5, 0, 0);
        config.encoder.positionConversionFactor(60/24);
        config2.encoder.positionConversionFactor(60/24);

        intake3m.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters); 
        intake2m.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters); 

        encoder.setPosition(0);
        encoder2.setPosition(0);
    }
   
    public void intakeDown() {
    //    controller.setSetpoint(OUT_POS, ControlType.kPosition);
        intake2m.set(0.1);
        intake3m.set(0.1);

        subsystem.BoringSolidColorsNavy();
    }
    
    public void intakeUp() {
        // controller.setSetpoint(IN_POS, ControlType.kPosition);
        intake2m.set(-0.05);
        intake3m.set(-0.05);
    }

    public void just_run_the_motor_man(double speed) {
        intake2m.set(speed);
        intake3m.set(speed);
    }

    public void stop() {
        intake2m.set(0);
        intake3m.set(0);
    }
            SparkMaxConfig config3 = new SparkMaxConfig();
            SparkMaxConfig config4 = new SparkMaxConfig();
            boolean obiWanKenobi = false;
    @Override
    public void periodic() {

        subsystem.BoringSolidColorsIvyGreen();


            
            if (DriverStation.isDisabled()){
            
        
        config3.idleMode(IdleMode.kCoast);
        config4.idleMode(IdleMode.kCoast).inverted(true);

        
        }
        else {
           
        
            config3.idleMode(IdleMode.kBrake);
         config4.idleMode(IdleMode.kBrake).inverted(true);
         

        }
        if (DriverStation.isDisabled() != obiWanKenobi) {
            intake3m.configure(config3, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters); 
                intake2m.configure(config4, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                
                obiWanKenobi = DriverStation.isDisabled();
        }


        //if (!DriverStation.isDisabled()) {
        //    obiWanKenobi = is cool;
//
        //}
       
        SmartDashboard.putNumber("Lower intake encoder", encoder.getPosition());
        SmartDashboard.putNumber("2nd Lower intake encoder", encoder2.getPosition());
    }
}