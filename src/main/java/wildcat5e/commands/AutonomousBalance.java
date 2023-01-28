package wildcat5e.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import wildcat5e.subsystems.Drivetrain;

import static wildcat5e.commands.AutonomousBalance.State.CHARGING;
import static wildcat5e.commands.AutonomousBalance.State.CLIMBING;
import static wildcat5e.commands.AutonomousBalance.State.DESCENDING;
import static wildcat5e.commands.AutonomousBalance.State.FIELD;
import static wildcat5e.commands.AutonomousBalance.State.INITIALIZING;

/**
 * Assumes the Romi is placed in front of a trapezoid ramp. The Romi should climb the ramp and stop on top when
 * balanced.
 */
public class AutonomousBalance extends CommandBase {
    enum State {INITIALIZING, FIELD, CLIMBING, CHARGING, DESCENDING}

    final Drivetrain    drivetrain;
    final DriveDistance forward;
    final DriveDistance backward;

    State  state = INITIALIZING;
    double startingPitch;
    double currentPitch;

    public AutonomousBalance(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.forward = new DriveDistance(1, 5.0, drivetrain);
        this.backward = new DriveDistance(-1, 5.0, drivetrain);
    }

    @Override public void execute() {
        currentPitch = drivetrain.gyro.getAngleY();
        state = nextState();
        System.out.printf("%s delta %2.2f%n", state, currentPitch - startingPitch);
    }

    State nextState() {
        switch (state) {
            case INITIALIZING:
                startingPitch = currentPitch;
                return FIELD;
            case FIELD:
                return balanced() ? FIELD : climbing() ? CLIMBING : DESCENDING;
            case CLIMBING:
                forward.execute();
                return balanced() ? CHARGING : climbing() ? CLIMBING : DESCENDING;
            case DESCENDING:
                backward.execute();
                return balanced() ? CHARGING : climbing() ? CLIMBING : DESCENDING;
            case CHARGING:
            default:
                return balanced() ? CHARGING : climbing() ? CLIMBING : DESCENDING;
        }
    }

    boolean climbing() {
        return !balanced() && currentPitch > startingPitch;
    }

    boolean balanced() {
        return Math.abs(currentPitch - startingPitch) <= 8.0d;
    }

    @Override public boolean isFinished() {
        return state == CHARGING;
    }
}
