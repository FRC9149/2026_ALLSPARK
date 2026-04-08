package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;


public class RetractClimber extends Command{

  private final Climber subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
 public RetractClimber(Climber subsystem) {
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
    subsystem.retract();
  }

  // Called once the command ends or is interrupted.
   @Override
  public void end(boolean interrupted) {
    subsystem.stop();
  }

    @Override
  public boolean isFinished() {
    return subsystem.atMinHeight();
  }
    
}
