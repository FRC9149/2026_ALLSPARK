package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Led;

public class Leds extends Command{
    private final Led m_subsystem;
    public int r, g, b;
    public Leds(Led color, int r, int g, int b) {
        m_subsystem = color;
        this.r = r;
        this.g = g;
        this.b = b;
        addRequirements(m_subsystem);
    }

    @Override
    public void execute() {

    }

    @Override
    public void initialize() {
        m_subsystem.color(r, g, b);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
