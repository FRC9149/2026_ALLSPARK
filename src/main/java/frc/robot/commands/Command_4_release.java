package frc.robot.commands;

import frc.robot.subsystems.Release;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class Command_4_release extends Command {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final Release subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Command_4_release(Release subsystem) {
    this.subsystem = subsystem;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {subsystem.release();}
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {subsystem.release();}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
