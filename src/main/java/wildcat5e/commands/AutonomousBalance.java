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
    private boolean initPitch0 = true;

    public AutonomousBalance(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override public void execute() {
        new DriveDistance(1, 5.0, drivetrain).execute();
        currentPitch = drivetrain.gyro.getAngleY();
        if (initPitch0) {
            pitch0 = currentPitch;
            initPitch0 = false;
            System.out.println(pitch0);
        }
        hasClimbed |= climbing();
        System.out.println(hasClimbed + " climbing: " + climbing() + " currentPitch: " + (currentPitch) + " delta: " + (currentPitch-pitch0));
    
    }

   

    boolean climbing() {
        return !balanced() && currentPitch > pitch0;
    }

    boolean descending() {
        return !balanced() && currentPitch < pitch0;
    }

    boolean balanced() {
        return Math.abs(currentPitch - pitch0) <= 8.0d;
    }

    @Override public void end(boolean interrupted) {}

    @Override public boolean isFinished() {
        return hasClimbed && balanced();
    }
}
