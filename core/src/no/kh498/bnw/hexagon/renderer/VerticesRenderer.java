package no.kh498.bnw.hexagon.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import no.kh498.bnw.BnW;

public class VerticesRenderer implements Disposable {

    //@formatter:off //TODO read this from file?
    private static final String VERT_SHADER =
        "attribute vec2 a_position;\n" +
        "attribute vec4 a_color;\n" +
        "uniform mat4 u_projTrans;\n" +
        "varying vec4 vColor;\n" +
        "void main() {\n" +
        "	vColor = a_color;\n" +
        "	gl_Position =  u_projTrans * vec4(a_position.xy, 0.0, 1.0);\n" +
        "}";

    private static final String FRAG_SHADER =
        "#ifdef GL_ES\n" +
        "precision mediump float;\n" +
        "#endif\n" +
        "varying vec4 vColor;\n" +
        "void main() {\n" +
        "	gl_FragColor = vColor;\n" +
        "}";
    //@formatter:on

    private static ShaderProgram createMeshShader() {
        ShaderProgram.pedantic = false;
        final ShaderProgram shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
        final String log = shader.getLog();
        if (!shader.isCompiled()) {
            throw new GdxRuntimeException(log);
        }
        if (log != null && log.length() != 0) {
            System.out.println("Shader Log: " + log);
        }
        return shader;
    }

    private final Mesh mesh;
    private final ShaderProgram shader;

    //Position attribute - (x, y)
    private static final int POSITION_COMPONENTS = 2;

    //Color attribute - (r, g, b, a)
    private static final int COLOR_COMPONENTS = 1;

    //Total number of components for all attributes
    private static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

    //The maximum number of triangles our mesh will hold
    private static final int MAX_TRIS = 384; // each hexagon has 4 to 6 vertices, and there will be a least 64 hexagons

    //The maximum number of vertices our mesh will hold
    private static final int MAX_VERTS = MAX_TRIS * 3;

    //The array which holds all the data, interleaved like so:
    //    x, y, r, g, b, a
    //    x, y, r, g, b, a,
    //    x, y, r, g, b, a,
    //    ... etc ...
    private final float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];

    //The index position
    private int idx = 0;

    public VerticesRenderer() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        this.mesh = new Mesh(true, MAX_VERTS, 0,
                             new VertexAttribute(VertexAttributes.Usage.Position, POSITION_COMPONENTS, "a_position"),
                             new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "a_color"));

        this.shader = createMeshShader();
    }

    public void flush() {
        //if we've already flushed
        if (this.idx == 0) {
            return;
        }

        //sends our vertex data to the mesh
        this.mesh.setVertices(this.verts);

        //no need for depth...
//        Gdx.gl.glDepthMask(false);

        //enable blending, for alpha

        //number of vertices we need to render
        final int vertexCount = (this.idx / NUM_COMPONENTS);

        //update the camera with our Y-up coordiantes
//        this.cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //start the shader before setting any uniforms
        this.shader.begin();

        //update the projection matrix so our triangles are rendered in 2D
        this.shader.setUniformMatrix("u_projTrans", BnW.getCamera().combined);

        //render the mesh
        this.mesh.render(this.shader, GL20.GL_TRIANGLES, 0, vertexCount);

        this.shader.end();

        //re-enable depth to reset states to their default
//        Gdx.gl.glDepthMask(true);

        //reset index to zero
        this.idx = 0;
    }

    /**
     * @param cBit
     *     Color bit
     * @param verts
     *     Vertices to add
     */
    public void drawTriangle(final float cBit, final float[] verts) {
        //we don't want to hit any index out of bounds exception...
        //so we need to flush the batch if we can't store any more verts
        if (this.idx == this.verts.length) {
            flush();
        }

        //now we push the vertex data into our array
        //we are assuming (0, 0) is lower left, and Y is up

        //bottom left vertex
        this.verts[this.idx++] = verts[0]; //Position x
        this.verts[this.idx++] = verts[1]; //Position y
        this.verts[this.idx++] = cBit;     //Color

        //top left vertex
        this.verts[this.idx++] = verts[2];
        this.verts[this.idx++] = verts[3];
        this.verts[this.idx++] = cBit;

        //bottom right vertex
        this.verts[this.idx++] = verts[4];
        this.verts[this.idx++] = verts[5];
        this.verts[this.idx++] = cBit;
    }


    @Override
    public void dispose() {
        this.mesh.dispose();
        this.shader.dispose();
    }

}