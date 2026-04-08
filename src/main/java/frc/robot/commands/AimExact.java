package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Aiming;

public class AimExact extends Command{
  //TODO format (or don't idc)
  
  //also suppresswarnings isn't that great and also does nothing here
    private final Aiming subsystem;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
    
    private double height = 0;
    
 public AimExact(Aiming subsystem, double height){//double aimHeight) {
  this.subsystem = subsystem;
  this.height = height;
    //Use addRequirements() here to declare subsystem dependencies.
  addRequirements(subsystem);
 } 


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //subsystem.setHeight(aimHeight);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println("L");
    // subsystem.setHeight(aimHeight);
  
    height = MathUtil.clamp(height, 0, 0.8);
    
    subsystem.setHeight(height);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true; 
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}  

 
}
