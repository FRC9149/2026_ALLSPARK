package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;


public class ClimbToLevel extends Command{


    private final Climber subsystem;
    private int level;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
 public ClimbToLevel(Climber subsystem, int level) { 
  this.subsystem = subsystem;
  this.level = level;
    //Use addRequirements() here to declare subsystem dependencies.
  addRequirements(subsystem);
 }

  // Called when the command is initially scheduled.
@Override
public void initialize() {
  double current = subsystem.getHeight();
  double target = subsystem.getTargetHeight(level); // we’ll add this

}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     
     


     
    subsystem.moveToHeight(level);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return subsystem.atHeight(level);
  }  
}
