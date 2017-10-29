package no.kh498.bnw.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import no.kh498.bnw.hex.Hex;
import no.kh498.bnw.hex.Layout;
import no.kh498.bnw.hex.util.Point;

import java.util.ArrayList;

/**
 * @author karl henrik
 */
public class Board {

    public final Layout layout;
    public final ArrayList<GameHex> hexes;

    public final int radius;


    private static Point dist;

    public static Point GET_HEX_RAD() {
        return dist;
    }

    public Board(final int hexRadius, final int height, final int width) {
        this.radius = hexRadius;
//        final Point centerOfScreen = new Point(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.layout = new Layout(Layout.FLAT, new Point(hexRadius, hexRadius), new Point(hexRadius, hexRadius));

        dist = Point.abs(Layout.hexCenterFromPoint(this.layout, new Point(0, 0)));

//        this.hexes.add(new GameHex(Layout.pixelToHex(this.layout, dist), this.layout, HexType.DIAMOND, HexColor.GRAY));
//        this.hexes.add(
//            new GameHex(Layout.pixelToHex(this.layout, new Point(0, 0)), this.layout, HexType.DIAMOND, HexColor.GRAY));
//
//        for (int y = 0; y <= Gdx.graphics.getHeight(); y += dist.y) {
//            for (int x = 0; x <= Gdx.graphics.getWidth(); x += dist.x) {
//                this.hexes.add(
//                    new GameHex(Layout.pixelToHex(this.layout, new Point(x, y)), this.layout, HexType.DIAMOND,
//                                HexColor.GRAY));
//            }
//        }

        //TODO optimize the storage
//        this.hexes = new GameHex[width * 2][height * 2];
        this.hexes = new ArrayList<>();

//        for (int y = 0; y < width; y++) {
//            for (int x = 0; x < height; x++) {
//                this.hexes[y][x] = new GameHex(new Hex(x, y), this.layout, HexType.DIAMOND, HexColor.GRAY);
//            }
//        }
        for (int q = 0; q < width; q++) {
            final int q_offset = q >> 1; // or (int) Math.floor(q / 2)
            for (int r = -q_offset; r < height - q_offset; r++) {
                this.hexes.add(new GameHex(new Hex(q, r), this.layout, HexType.DIAMOND, HexColor.GRAY));
            }
        }
        System.out.println("hexes = " + this.hexes.size());
    }

    public void render(final Batch batch) {
        for (final GameHex gp : this.hexes) {
            gp.render(batch, this.layout);
        }
    }
}
