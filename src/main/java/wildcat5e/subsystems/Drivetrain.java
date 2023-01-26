package wildcat5e.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import wildcat5e.sensors.RomiGyro;

public class Drivetrain extends SubsystemBase {
    public static final double countsPerRevolution = 1440.0;
    public static final double wheelDiameterInch   = 2.75591; // 70 mm

    // The Romi has the left and right motors set to PWM channels 0 and 1 respectively
    public final Spark leftMotor  = new Spark(0);
    public final Spark rightMotor = new Spark(1);

    // The Romi has onboard encoders that are hardcoded to use DIO pins 4/5 and 6/7 for the left and right
    public final Encoder leftEncoder  = new Encoder(4, 5);
    public final Encoder rightEncoder = new Encoder(6, 7);

    public final DifferentialDrive diffDrive = new DifferentialDrive(leftMotor, rightMotor);

    // Set up the RomiGyro
    public final RomiGyro gyro = new RomiGyro();

    // Set up the BuiltInAccelerometer
    public final BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();

    /** Creates a new Drivetrain. */
    public Drivetrain() {
        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        rightMotor.setInverted(true);

        // Use inches as unit for encoder distances
        leftEncoder.setDistancePerPulse((Math.PI * wheelDiameterInch) / countsPerRevolution);
        rightEncoder.setDistancePerPulse((Math.PI * wheelDiameterInch) / countsPerRevolution);
        resetEncoders();
    }

    public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
        diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
