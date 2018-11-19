package no.kh498.bnw.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import no.kh498.bnw.BnW;
import no.kh498.bnw.game.Player;
import no.kh498.bnw.game.PlayerHandler;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;

/**
 * @author karl henrik
 * <p>
 * {@inheritDoc}
 */
public class InputListener implements InputProcessor {

    private int windowedHeight = -1;
    private int windowedWidth = -1;

    private static final boolean ZOOM_ENABLE = false;
    private static float totalZoom = 1;

    private static final int MIN_MOVE_AMOUNT = 2;

    private static final float MIN_ZOOM = 0.15f;
    private static final float MAX_ZOOM = 3.0f;

    /** Higher means lower zoom speed */
    private static final int ZOOM_SPEED = 5;

    @Override
    public boolean keyDown(final int keycode) {
        if (Input.Keys.ESCAPE == keycode) {
            Gdx.app.exit();
            return true;
        }
        else if (Input.Keys.F == keycode && Gdx.graphics.supportsDisplayModeChange()) {

            if (windowedHeight == -1 && windowedWidth == -1) {
                windowedHeight = Gdx.graphics.getHeight();
                windowedWidth = Gdx.graphics.getWidth();
            }

            if (!Gdx.graphics.isFullscreen()) {
                BnW.updateResolution(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            else {
                BnW.updateResolution(windowedWidth, windowedHeight);
                Gdx.graphics.setWindowedMode(windowedWidth, windowedHeight);
            }
            BnW.getGameHandler().getWorldHandler().centerWorld();
            return true;
        }
        else if (Input.Keys.N == keycode) {
            BnW.getGameHandler().getWorldHandler().nextWorld();
            return true;
        }
        else if (Input.Keys.R == keycode) {
            BnW.getGameHandler().getWorldHandler().unload();
            BnW.getGameHandler().getWorldHandler().load();
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
        else if (Input.Keys.F2 == keycode) {
            BnW.printControls = !BnW.printControls;
            return true;
        }
        else if (Input.Keys.E == keycode) {
            if (BnW.gameOver) {
                return false;
            }
            BnW.getGameHandler().endTurn();
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
        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return changeHex();
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }


    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        final float x = -Gdx.input.getDeltaX() * getTotalZoom();
        final float y = -Gdx.input.getDeltaY() * getTotalZoom();

        //Make the little movements when clicking fast less noticeable
        if (Math.abs(x) < MIN_MOVE_AMOUNT * getTotalZoom() && Math.abs(y) < MIN_MOVE_AMOUNT * getTotalZoom()) {
            return false;
        }
        BnW.moveCamera(x, y);
        return true;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final int amount) {
        if (!ZOOM_ENABLE) {
            changeHex();
            return true;
        }
        if (totalZoom <= MIN_ZOOM) {
            if (amount == -1) {
                totalZoom = MIN_ZOOM;
                return false;
            }
        }
        else if (totalZoom >= MAX_ZOOM) {
            if (amount == 1) {
                totalZoom = MAX_ZOOM;
                return false;
            }
        }
        //amount is if we zoom in or out
        //(totalZoom / (totalZoom + 1)) Makes the zooming become (ish) constant (look at e^-x and x/(x+1) in a graph)
        // 5 is a zoom speed modifier, higher means less zoom pr scroll
        totalZoom += amount * (totalZoom / (totalZoom + 1)) / ZOOM_SPEED;
        BnW.getCamera().zoom = totalZoom;
        System.out.println("totalZoom = " + totalZoom);
        return true;
    }


    private boolean changeHex() {
        if (BnW.gameOver) {
            return false;
        }
        final Hexagon<HexagonData> hex = HexUtil.getCursorHexagon();
        PlayerHandler ph = BnW.getGameHandler().getPlayerHandler();
        if (hex != null && !ph.getCurrentPlayer().isAI()) {
            ph.makeMove(hex);

            if(ph.getMovesLeft() == 0 || (ph.getMovesLeft() == Player.REINFORCE_COST && ph.getHighlighted().stream().noneMatch(hexagon -> HexUtil.getData(hex).type.hasNext()))){
                System.out.println("Auto ended with '" + ph.getMovesLeft()+" moves left");
                ph.endTurn();
            }
            return true;
        }
        return false;
    }

    public static float getTotalZoom() {
        return totalZoom;
    }

    public static void resetTotalZoom() {
        BnW.getCamera().zoom = totalZoom = 1f;
    }
}
