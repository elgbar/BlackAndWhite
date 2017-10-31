package no.kh498.bnw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import no.kh498.bnw.BnW;
import org.codetome.hexameter.core.api.Hexagon;

/**
 * @author karl henrik
 * <p>
 * {@inheritDoc}
 */
@SuppressWarnings("NonJREEmulationClassesInClientCode")
public class InputListener implements InputProcessor {

    private int windowedHeight = -1;
    private int windowedWidth = -1;

    private int fullscreenHeight = -1;
    private int fullscreenWidth = -1;

    @Override
    public boolean keyDown(final int keycode) {
        if (Input.Keys.ESCAPE == keycode) {
            Gdx.app.exit();
            return true;
        }
        else if (Input.Keys.F == keycode && Gdx.graphics.supportsDisplayModeChange()) {

            if (this.windowedHeight == -1 && this.windowedWidth == -1) {
                this.windowedHeight = Gdx.graphics.getHeight();
                this.windowedWidth = Gdx.graphics.getWidth();
                this.fullscreenHeight = Gdx.graphics.getDisplayMode().height;
                this.fullscreenWidth = Gdx.graphics.getDisplayMode().width;
                System.out.println(this.windowedWidth + "x" + this.windowedHeight + "\n" + this.fullscreenWidth + "x" +
                                   this.fullscreenHeight);
            }


            if (!Gdx.graphics.isFullscreen()) {
                BnW.updateResolution(this.fullscreenWidth, this.fullscreenHeight);
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            else {
                BnW.updateResolution(this.windowedWidth, this.windowedHeight);
                Gdx.graphics.setWindowedMode(this.windowedWidth, this.windowedHeight);
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {
        int ordinal;
        switch (character) {
            case 'c':
                ordinal = BnW.color.ordinal() + 1;
                if (ordinal >= HexColor.values().length) {
                    ordinal = 0;
                }

                BnW.color = HexColor.values()[ordinal];
                return true;
            case 't':
                ordinal = BnW.type.ordinal() + 1;
                if (ordinal >= HexType.values().length) {
                    ordinal = 0;
                }
                BnW.type = HexType.values()[ordinal];
                return true;
            default:
                return false;
        }

    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return changeHex(screenX, screenY);
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return changeHex(screenX, screenY);
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final int amount) {
        return false;
    }

    private boolean changeHex(final int screenX, final int screenY) {
        final Hexagon<HexagonData> hex =
            (Hexagon<HexagonData>) BnW.getGrid().getByPixelCoordinate(screenX, screenY).orElse(null);
        if (hex != null) {
//            System.out.println(
//                "Processed: " + screenX + ", " + screenY + " | hex to coord: " + hex.getCenterX() + ", " +
//                hex.getCenterY());

            final HexagonData data = hex.getSatelliteData().orElse(new HexagonData());
            data.color = BnW.color;
            data.type = BnW.type;
            hex.setSatelliteData(data);
            return true;
        }
//        System.out.println("not processed: " + screenX + ", " + screenY);
        return false;
    }
}
