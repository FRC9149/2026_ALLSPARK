package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class Command_4_intake extends Command{
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final Intake subsystem;
  private double speed;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Command_4_intake(Intake subsystem, double speed) {
    this.subsystem = subsystem;
    this.speed = speed;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {subsystem.intake(speed);}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {subsystem.intake(1);}
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
