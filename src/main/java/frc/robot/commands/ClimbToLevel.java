package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Release;


public class ClimbToLevel extends Command{


    private final Climber subsystem;
    private int level;
    private final Release subsystem2;
    private boolean goingDown = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
 public ClimbToLevel(Climber subsystem, Release subsystem2, int level) { 
  this.subsystem2 = subsystem2;
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

  goingDown = target < current;
}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     if (goingDown) {
      new ReleaseThenRetract(subsystem2, subsystem, level)

     }
     
     
     // do nothing if trying to go down
     //TODO why? why don't we just say that if we want to go down it just calls subsystem.retract();

     
    subsystem.moveToHeight(level, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.hold();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return goingDown || subsystem.atHeight(level);
    
      }
}
