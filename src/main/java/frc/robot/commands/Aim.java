package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Aiming;

public class Aim extends Command{
  //TODO format (or don't idc)
  
  //also suppresswarnings isn't that great and also does nothing here
      private final Aiming subsystem;


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
    private boolean up;
    private double height = 0;
    
 public Aim(Aiming subsystem, boolean up){//double aimHeight) {
  this.subsystem = subsystem;
  this.up = up;
    //Use addRequirements() here to declare subsystem dependencies.
  addRequirements(subsystem);
 } 


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //subsystem.setHeight(aimHeight);
    height = subsystem.getHeight();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    height += (up ? 1 : -1) * 0.0035;
    height = MathUtil.clamp(height, 0, 0.85);
    
    subsystem.setHeight(height);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; 
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }  

 
}
