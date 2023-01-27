package wildcat5e.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import wildcat5e.subsystems.Drivetrain;

/**
 * Assumes the Romi is placed in front of a trapezoid ramp. The Romi should climb the ramp and stop on top when
 * balanced.
 */
public class AutonomousBalance extends CommandBase {
    private final Drivetrain drivetrain;

    private double pitch0       = Float.NaN;
    private double currentPitch = Float.NaN;
    private boolean hasClimbed = false;

    public AutonomousBalance(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override public void execute() {
        new DriveDistance(0.5, 1.0, drivetrain).execute();
        currentPitch = drivetrain.gyro.getAngleY();
        if (pitch0 == Float.NaN) {
            pitch0 = currentPitch;
        }
        hasClimbed |= climbing();
    }

    boolean climbing() {
        return !balanced() && currentPitch > pitch0;
    }

    boolean descending() {
        return !balanced() && currentPitch < pitch0;
    }

    boolean balanced() {
        return Math.abs(currentPitch - pitch0) <= 5.0d;
    }

    @Override public void end(boolean interrupted) {}

    @Override public boolean isFinished() {
        return hasClimbed && balanced();
    }
}
