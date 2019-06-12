package com.example.cody;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cgfay.caincamera.R;
import com.cgfay.caincamera.activity.MusicMergeActivity;
import com.cgfay.caincamera.activity.VideoGifMakeActivity;
import com.cgfay.cameralibrary.engine.PreviewEngine;
import com.cgfay.cameralibrary.engine.model.AspectRatio;
import com.cgfay.cameralibrary.engine.model.GalleryType;
import com.cgfay.cameralibrary.listener.OnGallerySelectedListener;
import com.cgfay.cameralibrary.listener.OnPreviewCaptureListener;
import com.cgfay.filterlibrary.glfilter.resource.FilterHelper;
import com.cgfay.filterlibrary.glfilter.resource.MakeupHelper;
import com.cgfay.filterlibrary.glfilter.resource.ResourceHelper;
import com.cgfay.imagelibrary.activity.ImageEditActivity;
import com.cgfay.scan.engine.MediaScanEngine;
import com.cgfay.scan.listener.OnCaptureListener;
import com.cgfay.scan.listener.OnMediaSelectedListener;
import com.cgfay.scan.loader.impl.GlideMediaLoader;
import com.cgfay.scan.model.MimeType;
import com.cgfay.utilslibrary.utils.PermissionUtils;
import com.cgfay.video.activity.VideoCutActivity;

import java.util.List;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 0;

    private boolean mOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        initResources();
    }

    private void checkPermissions() {
        boolean cameraEnable = PermissionUtils.permissionChecking(this,
                Manifest.permission.CAMERA);
        boolean storageWriteEnable = PermissionUtils.permissionChecking(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean recordAudio = PermissionUtils.permissionChecking(this,
                Manifest.permission.RECORD_AUDIO);
        if (!cameraEnable || !storageWriteEnable || !recordAudio) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    }, REQUEST_CODE);
        }
    }

    private void initView() {
        findViewById(R.id.btn_camera_beauty).setOnClickListener(this);
        findViewById(R.id.btn_camera_makeup).setOnClickListener(this);
        findViewById(R.id.btn_face_info).setOnClickListener(this);
        findViewById(R.id.btn_edit_music_merge).setOnClickListener(this);
        findViewById(R.id.btn_edit_gif_make).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOnClick = false;
    }

    @Override
    public void onClick(View v) {
        if (mOnClick) {
            return;
        }
        mOnClick = true;
        int i = v.getId();
        if (i == R.id.btn_camera_beauty) {
            previewCamera();
        } else if (i == R.id.btn_camera_makeup) {
            startActivity(new Intent(this, makeUpActivity.class));
        } else if (i == R.id.btn_face_info) {
            startActivity(new Intent(this, FaceInfoActivity.class));
        } else if (i == R.id.btn_edit_music_merge) {
            musicMerge();
        } else if (i == R.id.btn_edit_gif_make) {
            videoConvertGif();
        }
    }

    /**
     * 初始化动态贴纸、滤镜等资源
     */
    private void initResources() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResourceHelper.initAssetsResource(StartActivity.this);
                FilterHelper.initAssetsFilter(StartActivity.this);
                MakeupHelper.initAssetsMakeup(StartActivity.this);
            }
        }).start();
    }

    /**
     * 打开预览页面
     */
    private void previewCamera() {
        PreviewEngine.from(this)
                .setCameraRatio(AspectRatio.Ratio_16_9)
                .showFacePoints(false)
                .showFps(true)
                .setGalleryListener(new OnGallerySelectedListener() {
                    @Override
                    public void onGalleryClickListener(GalleryType type) {
                        scanMedia(type == GalleryType.ALL);
                    }
                })
                .setPreviewCaptureListener(new OnPreviewCaptureListener() {
                    @Override
                    public void onMediaSelectedListener(String path, GalleryType type) {
                        if (type == GalleryType.PICTURE) {
                            Intent intent = new Intent(StartActivity.this, ImageEditActivity.class);
                            intent.putExtra(ImageEditActivity.PATH, path);
                            startActivity(intent);
                        } else if (type == GalleryType.VIDEO) {
                            Intent intent = new Intent(StartActivity.this, VideoCutActivity.class);
                            intent.putExtra(VideoCutActivity.PATH, path);
                            startActivity(intent);
                        }
                    }
                })
                .startPreview();
    }

    /**
     * 扫描媒体库
     */
    private void scanMedia(boolean enableGif) {
        scanMedia(enableGif, true, true);
    }

    /**
     * 扫描媒体库
     * @param enableGif
     * @param enableImage
     * @param enableVideo
     */
    private void scanMedia(boolean enableGif, boolean enableImage, boolean enableVideo) {
        MediaScanEngine.from(this)
                .setMimeTypes(MimeType.ofAll())
                .ImageLoader(new GlideMediaLoader())
                .spanCount(4)
                .showCapture(true)
                .showImage(enableImage)
                .showVideo(enableVideo)
                .enableSelectGif(enableGif)
                .setCaptureListener(new OnCaptureListener() {
                    @Override
                    public void onCapture() {
                        previewCamera();
                    }
                })
                .setMediaSelectedListener(new OnMediaSelectedListener() {
                    @Override
                    public void onSelected(List<Uri> uriList, List<String> pathList, boolean isVideo) {
                        if (isVideo) {
                            Intent intent = new Intent(StartActivity.this, VideoCutActivity.class);
                            intent.putExtra(VideoCutActivity.PATH, pathList.get(0));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(StartActivity.this, ImageEditActivity.class);
                            intent.putExtra(ImageEditActivity.PATH, pathList.get(0));
                            startActivity(intent);
                        }
                    }
                })
                .scanMedia();
    }

    private void musicMerge() {
        MediaScanEngine.from(this)
                .setMimeTypes(MimeType.ofAll())
                .ImageLoader(new GlideMediaLoader())
                .spanCount(4)
                .showCapture(true)
                .showImage(false)
                .showVideo(true)
                .enableSelectGif(false)
                .setCaptureListener(new OnCaptureListener() {
                    @Override
                    public void onCapture() {
                        previewCamera();
                    }
                })
                .setMediaSelectedListener(new OnMediaSelectedListener() {
                    @Override
                    public void onSelected(List<Uri> uriList, List<String> pathList, boolean isVideo) {
                        if (isVideo) {
                            Intent intent = new Intent(StartActivity.this, MusicMergeActivity.class);
                            intent.putExtra(MusicMergeActivity.PATH, pathList.get(0));
                            startActivity(intent);
                        }
                    }
                })
                .scanMedia();
    }

    /**
     * 视频转GIF
     * 备注：目前so没有把gif的encoder 和 muxer编译进去，这里没做完整的测试
     */
    private void videoConvertGif() {
        MediaScanEngine.from(this)
                .setMimeTypes(MimeType.ofAll())
                .ImageLoader(new GlideMediaLoader())
                .spanCount(4)
                .showCapture(true)
                .showImage(false)
                .showVideo(true)
                .enableSelectGif(false)
                .setCaptureListener(new OnCaptureListener() {
                    @Override
                    public void onCapture() {
                        previewCamera();
                    }
                })
                .setMediaSelectedListener(new OnMediaSelectedListener() {
                    @Override
                    public void onSelected(List<Uri> uriList, List<String> pathList, boolean isVideo) {
                        if (isVideo) {
                            Intent intent = new Intent(StartActivity.this, VideoGifMakeActivity.class);
                            intent.putExtra(VideoGifMakeActivity.PATH, pathList.get(0));
                            startActivity(intent);
                        }
                    }
                })
                .scanMedia();
    }
}
