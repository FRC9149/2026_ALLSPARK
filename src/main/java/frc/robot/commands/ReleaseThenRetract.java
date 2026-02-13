package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Release;


public class ReleaseThenRetract extends SequentialCommandGroup {
  public ReleaseThenRetract(Release release, Climber climber, int FREEME) {
    addCommands(
        new Command_4_release(release),   // unlock first
        new WaitCommand(0.25),         // optional: let servo move
        new RetractClimber(climber),   // then go down
        new WaitCommand(0.25),
        new UNRelease(release),
        new WaitCommand(0.25)

    );
  }
}