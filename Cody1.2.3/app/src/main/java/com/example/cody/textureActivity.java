package com.example.cody;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cody.maskTexture.MyGLSurfaceView;
import com.example.cody.maskTexture.TextureRender;

import butterknife.BindView;
import butterknife.ButterKnife;

public class textureActivity extends AppCompatActivity implements SurfaceTexture.OnFrameAvailableListener{

    private GLSurfaceView mGLView;
    private GLSurfaceView mGLSurfaceView;
    private boolean mRendererSet = false;

    public SurfaceTexture mSurfaceTexture;

    public static Camera camera;
    private int camera_status = 1;
    @BindView(R.id.gl_surface)
    GLSurfaceView mCameraGlsurfaceView;
    public TextureRender mRenderer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);
        ButterKnife.bind(this);
        mCameraGlsurfaceView.setEGLContextClientVersion(2);//在setRenderer()方法前调用此方法
        mRenderer = new TextureRender(this);
        mCameraGlsurfaceView.setRenderer(mRenderer);
        mCameraGlsurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mCameraGlsurfaceView.requestRender();
    }
}
