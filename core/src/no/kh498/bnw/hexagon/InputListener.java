package no.kh498.bnw.hexagon;

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
            }

            if (!Gdx.graphics.isFullscreen()) {
                BnW.updateResolution(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            else {
                BnW.updateResolution(this.windowedWidth, this.windowedHeight);
                Gdx.graphics.setWindowedMode(this.windowedWidth, this.windowedHeight);
            }
        }
        else if (Input.Keys.N == keycode) {
            BnW.getGame().getWorldHandler().nextWorld();
        }
        else if (Input.Keys.F3 == keycode) {
            BnW.printDebug = !BnW.printDebug;
        }
        else if (Input.Keys.F1 == keycode) {
            BnW.printHelp = !BnW.printHelp;
        }
        return false;
    }


    @Override
    public boolean keyUp(final int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {

        switch (character) {
            case 'e':
                BnW.getGame().endTurn();
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
//        return changeHex(screenX, screenY);
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

    private boolean changeHex(final int screenX, final int screenY) {
        //noinspection unchecked
        final Hexagon<HexagonData> hex = HexUtil.getHexagon(screenX, screenY);
        if (hex != null) {
            BnW.getGame().getPlayerHandler().makeMove(hex);
            return true;
        }
        return false;
    }
}
