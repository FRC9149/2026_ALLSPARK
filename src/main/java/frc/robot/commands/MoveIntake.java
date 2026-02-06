package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LowerIntake;

public class MoveIntake extends Command{
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final LowerIntake subsystem;
  private boolean intake_retracted;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public MoveIntake(LowerIntake subsystem, boolean intake_retracted) {
    this.subsystem = subsystem;
    this.intake_retracted = intake_retracted;
     // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
        if (intake_retracted) {
            subsystem.intakeDown();
        }
        else {
            subsystem.intakeUp();

        }
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
