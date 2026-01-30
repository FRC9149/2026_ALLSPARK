package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Aiming;
import frc.robot.subsystems.Climber;

public class Aim extends Command{
      private final Aiming subsystem;
      

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
 public Aim(Aiming subsystem) {
  this.subsystem = subsystem;
  
    //Use addRequirements() here to declare subsystem dependencies.
  addRequirements(subsystem);
 }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
    
}
