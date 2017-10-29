package no.kh498.bnw.game;

import com.badlogic.gdx.InputProcessor;
import no.kh498.bnw.BnW;
import org.codetome.hexameter.core.api.Hexagon;

/**
 * @author karl henrik
 * <p>
 * {@inheritDoc}
 */
public class InputListener implements InputProcessor {

    @Override
    public boolean keyDown(final int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {
        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        final Hexagon<HexagonData> hex =
            (Hexagon<HexagonData>) BnW.getGrid().getByPixelCoordinate(screenX, screenY).orElse(null);
        if (hex != null) {
            System.out.println(
                "Processed: " + screenX + ", " + screenY + " | hex to coord: " + hex.getCenterX() + ", " +
                hex.getCenterY());
            
            final HexagonData data = hex.getSatelliteData().orElse(new HexagonData());
            data.color = HexColor.WHITE;
            hex.setSatelliteData(data);
            return true;
        }
        System.out.println("not processed: " + screenX + ", " + screenY);
        return false;
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final int amount) {
        return false;
    }
}
