package no.kh498.bnw.hex;

/**
 * @author karl henrik
 */
public class Orientation {

    public Orientation(final double f0, final double f1, final double f2, final double f3, final double b0,
                       final double b1, final double b2, final double b3, final double start_angle) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.start_angle = start_angle;
    }

    public final double f0;
    public final double f1;
    public final double f2;
    public final double f3;
    public final double b0;
    public final double b1;
    public final double b2;
    public final double b3;
    public final double start_angle;
}
