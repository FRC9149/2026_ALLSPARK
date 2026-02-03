package frc.robot.commands;

import frc.robot.subsystems.Release;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class UNRelease extends Command {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final Release subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public UNRelease(Release subsystem) {
    this.subsystem = subsystem;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {subsystem.lock();}



  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
