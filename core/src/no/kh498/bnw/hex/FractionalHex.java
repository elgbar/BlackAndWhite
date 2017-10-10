package no.kh498.bnw.hex;

import java.util.ArrayList;

/**
 * @author karl henrik
 */
public class FractionalHex {

    public FractionalHex(final double q, final double r, final double s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public final double q;
    public final double r;
    public final double s;

    public static Hex hexRound(final FractionalHex h) {
        int q = (int) (Math.round(h.q));
        int r = (int) (Math.round(h.r));
        int s = (int) (Math.round(h.s));
        final double q_diff = Math.abs(q - h.q);
        final double r_diff = Math.abs(r - h.r);
        final double s_diff = Math.abs(s - h.s);
        if (q_diff > r_diff && q_diff > s_diff) {
            q = -r - s;
        }
        else if (r_diff > s_diff) {
            r = -q - s;
        }
        else {
            s = -q - r;
        }
        return new Hex(q, r, s);
    }


    public static FractionalHex hexLerp(final FractionalHex a, final FractionalHex b, final double t) {
        return new FractionalHex(a.q * (1 - t) + b.q * t, a.r * (1 - t) + b.r * t, a.s * (1 - t) + b.s * t);
    }


    public static ArrayList<Hex> hexDrawLine(final Hex a, final Hex b) {
        final int N = Hex.distance(a, b);
        final FractionalHex a_nudge = new FractionalHex(a.q + 0.000001, a.r + 0.000001, a.s - 0.000002);
        final FractionalHex b_nudge = new FractionalHex(b.q + 0.000001, b.r + 0.000001, b.s - 0.000002);
        final ArrayList<Hex> results = new ArrayList<Hex>() {{
        }};
        final double step = 1.0 / Math.max(N, 1);
        for (int i = 0; i <= N; i++) {
            results.add(FractionalHex.hexRound(FractionalHex.hexLerp(a_nudge, b_nudge, step * i)));
        }
        return results;
    }

}
