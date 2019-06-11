package com.example.cody.maskTexture;

import android.content.Context;

public class MyGLSurfaceView extends android.opengl.GLSurfaceView {
    private final TextureRender mTextureRenderer;

    public MyGLSurfaceView(Context context){
        super(context);

        //创建OpenGL ES 2.0的环境
        setEGLContextClientVersion(2);

        mTextureRenderer = new TextureRender(context);

        //真正的绘制工作在GLSurfaceView.Renderer中
        setRenderer(mTextureRenderer);

        //为了性能，设置这个属性，只有数据改变的时候才重新渲染，根据需要设置
        setRenderMode(MyGLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
