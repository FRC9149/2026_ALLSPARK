package frc.robot.commands;

import frc.robot.subsystems.Release;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class Command_4_release extends Command {
  //TODO we are able to release the release, but we can't lock it again.
  //I don't think that we should make this a command for 2 reasons.
  //A. we could just do something like Trigger.onTrue(new InstantCommand(Release::release, Release));
  //  Although I think we should move it to climber where it would be Trigger.onTrue(new InstantCommand(Climber::release, Climber));
  //B. I think we should move it to climber
  //  The release is just so that the climber doesn't move
  // we can release the climber then move then lock it again in that one command
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
  public void initialize() {subsystem.release();}



  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
