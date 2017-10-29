package no.kh498.bnw.hex;

/**
 * @author karl henrik
 */
public class Orientation {


    public final float f0;
    public final float f1;
    public final float f2;
    public final float f3;
    public final float b0;
    public final float b1;
    public final float b2;
    public final float b3;
    public final float start_angle;

    public Orientation(final double f0, final double f1, final double f2, final double f3, final double b0,
                       final double b1, final double b2, final double b3, final double start_angle) {
        this.f0 = (float) f0;
        this.f1 = (float) f1;
        this.f2 = (float) f2;
        this.f3 = (float) f3;
        this.b0 = (float) b0;
        this.b1 = (float) b1;
        this.b2 = (float) b2;
        this.b3 = (float) b3;
        this.start_angle = (float) start_angle;
    }
}
