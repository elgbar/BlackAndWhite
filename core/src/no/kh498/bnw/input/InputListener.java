package no.kh498.bnw.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import no.kh498.bnw.BnW;
import no.kh498.bnw.hexagon.HexUtil;
import no.kh498.bnw.hexagon.HexagonData;
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
            return true;
        }
        else if (Input.Keys.N == keycode) {
            BnW.getGame().getWorldHandler().nextWorld();
            return true;
        }
        else if (Input.Keys.F3 == keycode) {
            BnW.printDebug = !BnW.printDebug;
            return true;
        }
        else if (Input.Keys.F1 == keycode) {
            BnW.printHelp = !BnW.printHelp;
            return true;
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

    private float changedX = 0;
    private float changedY = 0;

    public float getChangedX() {
        return this.changedX;
    }

    public float getChangedY() {
        return this.changedY;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        final float x = -Gdx.input.getDeltaX();
        final float y = -Gdx.input.getDeltaY();
        moveCamera(x, y);

        return true;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final int amount) {
        return false;
    }

    public void moveCamera(final float deltaX, final float deltaY) {
        this.changedX += deltaX;
        this.changedY += deltaY;

        BnW.getCamera().translate(deltaX, deltaY);
    }

    private boolean changeHex(final int screenX, final int screenY) {
        //noinspection unchecked
        final Hexagon<HexagonData> hex =
            HexUtil.getHexagon(screenX + (int) this.changedX, screenY + (int) this.changedY);
        if (hex != null) {
            BnW.getGame().getPlayerHandler().makeMove(hex);
            return true;
        }
        return false;
    }
}
