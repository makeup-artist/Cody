package com.example.cody.maskTexture;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.example.cody.R;
import com.example.cody.utils.ResReadUtils;
import com.example.cody.utils.ShaderUtils;
import com.example.cody.utils.TextureUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TextureRender implements GLSurfaceView.Renderer {

    protected static final String VERTEX_SHADER = "" +
            "attribute vec4 vPosition;  \n" +
            "attribute vec2 vCoordinate;    \n" +
            "uniform mat4 vMatrix;  \n" +

            "varying vec2 aCoordinate;  \n" +

            "void main(){   \n"+
            "    gl_Position=vMatrix*vPosition; \n"+
            "    aCoordinate=vCoordinate;   \n"+
            "}  \n";
//            "#version 300 es                                            \n" +
//            "layout (location = 0) in vec4 vPosition;                   \n" +
//            "layout (location = 1) in vec2 aTextureCoord;               \n" +
//            "uniform mat4 u_Matrix;                                     \n" +
//            "out vec2 vTexCoord;                                        \n" +
//            "void main() {                                              \n" +
//            "    gl_Position  = u_Matrix * vPosition;                   \n" +
//            "    gl_PointSize = 10.0;                                   \n" +
//            "    vTexCoord = aTextureCoord;                             \n" +
//            "}                                                          \n";

    protected static final String FRAGMENT_SHADER = "" +
            "precision mediump float;   \n"+

            "uniform sampler2D vTexture;    \n"+
            "varying vec2 aCoordinate;  \n"+

            "void main(){   \n"+
            "    gl_FragColor=texture2D(vTexture,aCoordinate);  \n"+
            "}  \n";


//            "#version 300 es                                             \n" +
//            "precision mediump float;                                   \n" +
//            "uniform sampler2D uTextureUnit;                             \n" +
//            "in vec2 vTexCoord;                                         \n" +
//            "out vec4 vFragColor;                                        \n" +
//            "void main() {                                                \n" +
//            "     vFragColor = texture(uTextureUnit,vTexCoord);           \n" +
//            "}                                                            \n";

    private static final String TAG = "TextureRenderer";

    private final FloatBuffer vertexBuffer, mTexVertexBuffer;

    private final ShortBuffer mVertexIndexBuffer;

    private Context mContext;

    private int mProgram;

    private int textureId;

    private Bitmap mBitmap;


    /**
     * 顶点坐标
     * (x,y,z)
     */
    private float[] POSITION_VERTEX = new float[]{
            0f, 0f, 0f,     //顶点坐标V0
            1f, 1f, 0f,     //顶点坐标V1
            -1f, 1f, 0f,    //顶点坐标V2
            -1f, -1f, 0f,   //顶点坐标V3
            1f, -1f, 0f     //顶点坐标V4
    };

    /**
     * 纹理坐标
     * (s,t)
     */
    private static final float[] TEX_VERTEX = {
            0.5f, 0.5f, //纹理坐标V0
            1f, 0f,     //纹理坐标V1
            0f, 0f,     //纹理坐标V2
            0f, 1.0f,   //纹理坐标V3
            1f, 1.0f    //纹理坐标V4
    };

    /**
     * 索引
     */
    private static final short[] VERTEX_INDEX = {
            0, 1, 2,  //V0,V1,V2 三个顶点组成一个三角形
            0, 2, 3,  //V0,V2,V3 三个顶点组成一个三角形
            0, 3, 4,  //V0,V3,V4 三个顶点组成一个三角形
            0, 4, 1   //V0,V4,V1 三个顶点组成一个三角形
    };

    public TextureRender(Context context) {
        mContext = context;

        //分配内存空间,每个浮点型占4字节空间
        vertexBuffer = ByteBuffer.allocateDirect(POSITION_VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        //传入指定的坐标数据
        vertexBuffer.put(POSITION_VERTEX);
        vertexBuffer.position(0);

        mTexVertexBuffer = ByteBuffer.allocateDirect(TEX_VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(TEX_VERTEX);
        mTexVertexBuffer.position(0);

        mVertexIndexBuffer = ByteBuffer.allocateDirect(VERTEX_INDEX.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(VERTEX_INDEX);
        mVertexIndexBuffer.position(0);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置背景颜色
        GLES30.glClearColor(1.0f, 0.0f, 0.0f, 0.5f);
        //编译
        final int vertexShaderId = ShaderUtils.compileVertexShader(VERTEX_SHADER);
        final int fragmentShaderId = ShaderUtils.compileFragmentShader(FRAGMENT_SHADER);
        //链接程序片段
        mProgram = ShaderUtils.linkProgram(vertexShaderId, fragmentShaderId);

        //加载纹理
        textureId = TextureUtils.loadTexture(mContext, R.drawable.leyebrow);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
//        GLES20.glUniformMatrix4fv(glHMatrix,1,false,mMVPMatrix,0);
//        GLES20.glEnableVertexAttribArray(glHPosition);
//        GLES20.glEnableVertexAttribArray(glHCoordinate);
//        GLES20.glUniform1i(glHTexture, 0);
//        textureId=TextureUtils.loadTexture(this,R.drawable.leyebrow);
//        //传入顶点坐标
//        GLES20.glVertexAttribPointer(glHPosition,2,GLES20.GL_FLOAT,false,0,POSITION_VERTEX);
//        //传入纹理坐标
//        GLES20.glVertexAttribPointer(glHCoordinate,2,GLES20.GL_FLOAT,false,0,TEX_VERTEX);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
    }
}
