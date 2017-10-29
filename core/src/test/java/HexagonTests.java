import no.kh498.bnw.hex.FractionalHex;
import no.kh498.bnw.hex.Hex;
import no.kh498.bnw.hex.Layout;
import no.kh498.bnw.hex.OffsetCoord;
import no.kh498.bnw.hex.util.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author karl henrik
 */

public class HexagonTests {

    @Test
    public static void equalHex(final String name, final Hex a, final Hex b) {
        Assertions.assertTrue(a.q == b.q && a.s == b.s && a.r == b.r);
    }

    @Test
    public static void equalOffsetcoord(final String name, final OffsetCoord a, final OffsetCoord b) {
        Assertions.assertTrue(a.col == b.col && a.row == b.row);
    }

    @Test
    public static void equalInt(final String name, final int a, final int b) {
        Assertions.assertTrue(a == b);
    }


    public static void equalHexArray(final String name, final ArrayList<Hex> a, final ArrayList<Hex> b) {
        HexagonTests.equalInt(name, a.size(), b.size());
        for (int i = 0; i < a.size(); i++) {
            HexagonTests.equalHex(name, a.get(i), b.get(i));
        }
    }


    public static void testHexArithmetic() {
        HexagonTests.equalHex("hex_add", new Hex(4, -10, 6), Hex.add(new Hex(1, -3, 2), new Hex(3, -7, 4)));
        HexagonTests.equalHex("hex_subtract", new Hex(-2, 4, -2), Hex.subtract(new Hex(1, -3, 2), new Hex(3, -7, 4)));
    }


    public static void testHexDirection() {
        HexagonTests.equalHex("hex_direction", new Hex(0, -1, 1), Hex.direction(2));
    }


    public static void testHexNeighbor() {
        HexagonTests.equalHex("hex_neighbor", new Hex(1, -3, 2), Hex.neighbor(new Hex(1, -2, 1), 2));
    }


    public static void testHexDiagonal() {
        HexagonTests.equalHex("hex_diagonal", new Hex(-1, -1, 2), Hex.diagonalNeighbor(new Hex(1, -2, 1), 3));
    }


    public static void testHexDistance() {
        HexagonTests.equalInt("hex_distance", 7, Hex.distance(new Hex(3, -7, 4), new Hex(0, 0, 0)));
    }


    public static void testHexRotateRight() {
        HexagonTests.equalHex("hex_rotate_right", Hex.rotateRight(new Hex(1, -3, 2)), new Hex(3, -2, -1));
    }


    public static void testHexRotateLeft() {
        HexagonTests.equalHex("hex_rotate_left", Hex.rotateLeft(new Hex(1, -3, 2)), new Hex(-2, -1, 3));
    }


    public static void testHexRound() {
        final FractionalHex a = new FractionalHex(0, 0, 0);
        final FractionalHex b = new FractionalHex(1, -1, 0);
        final FractionalHex c = new FractionalHex(0, -1, 1);
        HexagonTests.equalHex("hex_round 1", new Hex(5, -10, 5), FractionalHex
            .hexRound(FractionalHex.hexLerp(new FractionalHex(0, 0, 0), new FractionalHex(10, -20, 10), 0.5)));
        HexagonTests.equalHex("hex_round 2", FractionalHex.hexRound(a),
                              FractionalHex.hexRound(FractionalHex.hexLerp(a, b, 0.499)));
        HexagonTests.equalHex("hex_round 3", FractionalHex.hexRound(b),
                              FractionalHex.hexRound(FractionalHex.hexLerp(a, b, 0.501)));
        HexagonTests.equalHex("hex_round 4", FractionalHex.hexRound(a), FractionalHex.hexRound(
            new FractionalHex(a.q * 0.4 + b.q * 0.3 + c.q * 0.3, a.r * 0.4 + b.r * 0.3 + c.r * 0.3,
                              a.s * 0.4 + b.s * 0.3 + c.s * 0.3)));
        HexagonTests.equalHex("hex_round 5", FractionalHex.hexRound(c), FractionalHex.hexRound(
            new FractionalHex(a.q * 0.3 + b.q * 0.3 + c.q * 0.4, a.r * 0.3 + b.r * 0.3 + c.r * 0.4,
                              a.s * 0.3 + b.s * 0.3 + c.s * 0.4)));
    }


    public static void testHexLinedraw() {
        HexagonTests.equalHexArray("hex_linedraw", new ArrayList<Hex>() {{
            add(new Hex(0, 0, 0));
            add(new Hex(0, -1, 1));
            add(new Hex(0, -2, 2));
            add(new Hex(1, -3, 2));
            add(new Hex(1, -4, 3));
            add(new Hex(1, -5, 4));
        }}, FractionalHex.hexDrawLine(new Hex(0, 0, 0), new Hex(1, -5, 4)));
    }


    public static void testLayout() {
        final Hex h = new Hex(3, 4, -7);
        final Layout flat = new Layout(Layout.FLAT, new Point(10, 15), new Point(35, 71));
        HexagonTests.equalHex("layout", h, FractionalHex.hexRound(Layout.pixelToHex(flat, Layout.hexToPixel(flat, h))));
        final Layout pointy = new Layout(Layout.POINTY, new Point(10, 15), new Point(35, 71));
        HexagonTests
            .equalHex("layout", h, FractionalHex.hexRound(Layout.pixelToHex(pointy, Layout.hexToPixel(pointy, h))));
    }


    public static void testConversionRoundtrip() {
        final Hex a = new Hex(3, 4, -7);
        final OffsetCoord b = new OffsetCoord(1, -3);
        HexagonTests.equalHex("conversion_roundtrip even-q", a, OffsetCoord
            .qOffsetToCube(OffsetCoord.EVEN, OffsetCoord.qOffsetFromCube(OffsetCoord.EVEN, a)));
        HexagonTests.equalOffsetcoord("conversion_roundtrip even-q", b, OffsetCoord
            .qOffsetFromCube(OffsetCoord.EVEN, OffsetCoord.qOffsetToCube(OffsetCoord.EVEN, b)));
        HexagonTests.equalHex("conversion_roundtrip odd-q", a, OffsetCoord
            .qOffsetToCube(OffsetCoord.ODD, OffsetCoord.qOffsetFromCube(OffsetCoord.ODD, a)));
        HexagonTests.equalOffsetcoord("conversion_roundtrip odd-q", b, OffsetCoord
            .qOffsetFromCube(OffsetCoord.ODD, OffsetCoord.qOffsetToCube(OffsetCoord.ODD, b)));
        HexagonTests.equalHex("conversion_roundtrip even-r", a, OffsetCoord
            .rOffsetToCube(OffsetCoord.EVEN, OffsetCoord.rOffsetFromCube(OffsetCoord.EVEN, a)));
        HexagonTests.equalOffsetcoord("conversion_roundtrip even-r", b, OffsetCoord
            .rOffsetFromCube(OffsetCoord.EVEN, OffsetCoord.rOffsetToCube(OffsetCoord.EVEN, b)));
        HexagonTests.equalHex("conversion_roundtrip odd-r", a, OffsetCoord
            .rOffsetToCube(OffsetCoord.ODD, OffsetCoord.rOffsetFromCube(OffsetCoord.ODD, a)));
        HexagonTests.equalOffsetcoord("conversion_roundtrip odd-r", b, OffsetCoord
            .rOffsetFromCube(OffsetCoord.ODD, OffsetCoord.rOffsetToCube(OffsetCoord.ODD, b)));
    }


    public static void testOffsetFromCube() {
        HexagonTests.equalOffsetcoord("offset_from_cube even-q", new OffsetCoord(1, 3),
                                      OffsetCoord.qOffsetFromCube(OffsetCoord.EVEN, new Hex(1, 2, -3)));
        HexagonTests.equalOffsetcoord("offset_from_cube odd-q", new OffsetCoord(1, 2),
                                      OffsetCoord.qOffsetFromCube(OffsetCoord.ODD, new Hex(1, 2, -3)));
    }


    public static void testOffsetToCube() {
        HexagonTests.equalHex("offset_to_cube even-", new Hex(1, 2, -3),
                              OffsetCoord.qOffsetToCube(OffsetCoord.EVEN, new OffsetCoord(1, 3)));
        HexagonTests.equalHex("offset_to_cube odd-q", new Hex(1, 2, -3),
                              OffsetCoord.qOffsetToCube(OffsetCoord.ODD, new OffsetCoord(1, 2)));
    }

    @Test
    public static void testAll() {
        HexagonTests.testHexArithmetic();
        HexagonTests.testHexDirection();
        HexagonTests.testHexNeighbor();
        HexagonTests.testHexDiagonal();
        HexagonTests.testHexDistance();
        HexagonTests.testHexRotateRight();
        HexagonTests.testHexRotateLeft();
        HexagonTests.testHexRound();
        HexagonTests.testHexLinedraw();
        HexagonTests.testLayout();
        HexagonTests.testConversionRoundtrip();
        HexagonTests.testOffsetFromCube();
        HexagonTests.testOffsetToCube();
        System.out.println("Everything okay!");
    }


    public static void main(final String[] args) {
        HexagonTests.testAll();
    }


    public static void complain(final String name) {
        System.out.println("FAIL " + name);
    }
}
