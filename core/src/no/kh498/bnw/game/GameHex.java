package no.kh498.bnw.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import no.kh498.bnw.hex.Hex;
import no.kh498.bnw.hex.Layout;
import no.kh498.bnw.hex.util.Point;

/**
 * @author karl henrik
 */
public class GameHex {

    private final HexType type;
    private final HexColor color;
    private final Hex hex;
    private final Point center;

    GameHex(final Hex hex, final Layout layout, final HexType type, final HexColor color) {
        this.hex = hex;
        this.center = Point.abs(Layout.hexToPixel(layout, this.hex));
        this.type = type;
        this.color = color;
    }

    public void render(final Batch batch, final Layout layout) {
        batch.setColor(this.color.getShade());

        final float lradX = layout.size.x;
        final float lradY = layout.size.y;

        final float radX = Board.GET_HEX_RAD().x;
        final float radY = Board.GET_HEX_RAD().y;

        System.out.println("Center hex: " + this.center + " | X rad Hex: " + radX + " | Y rad Hex: " + radY);
        final float height = layout.size.y;
        final float width = layout.size.x;

        final float x = this.center.x - height / 2;
        final float y = this.center.y - width / 2;
        System.out.println("drawing sprite at " + x + ", " + y + " width: " + width + " height: " + height);
        batch.draw(this.type.getTexture(), x + height, y + width, height * 2, width * 2);
    }
}
