package com.example.cody;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cgfay.cameralibrary.widget.ShutterButton;
import com.example.cody.adapter.DemoAdapter;
import com.example.cody.entity.BaseEntity;
import com.example.cody.utils.FaceMakeUpUtils;
import com.example.cody.utils.FacesUtils;
import com.megvii.facepp.api.bean.Face;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.example.cody.BitmapUtils.decodeUriAsBitmap;
import static com.example.cody.BitmapUtils.toByteArray;


public class makeUpActivity extends AppCompatActivity {
    private Bitmap mbitmap;
    private Face mFace;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/take_photo_image.jpg";
    Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap
    private ImageView mImageView;
    private ShutterButton takePhoto;
    private Button previewMakeup;
    private Button generateInfo;

    private RecyclerView mRecyclerView;
    private List<BaseEntity> mEntityList;

    public static final int TAKE_PHOTO =1;
    public static final  int CHOOSE_PHOTO =2;
    public static final int CROP_PHOTO = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        setContentView(R.layout.fragment_photo);
        ButterKnife.bind(this);
        initView();  //初始化视图
        initData(); // 初始化数据
        initRecyclerView(); //初始化recycleView
        startCamera(); //启动相机
        setListeners();// 设置监听

    }
    private void initView(){
        mImageView = (ImageView) findViewById(R.id.imageView);
        takePhoto= (ShutterButton)findViewById(R.id.button_take_photo);
        previewMakeup = (Button)findViewById(R.id.button_make_up);
        mImageView = (ImageView)findViewById(R.id.imageView);
        generateInfo = (Button)findViewById(R.id.button_generate_info);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_makeup);
    }
    private void initData(){
        mEntityList = new ArrayList<BaseEntity>();
        BaseEntity baseEntity1 = new BaseEntity();
        baseEntity1.setText("口红");
        baseEntity1.setImage(R.drawable.mouth);
        mEntityList.add(baseEntity1);
        BaseEntity baseEntity2 = new BaseEntity();
        baseEntity2.setText("美瞳");
        baseEntity2.setImage(R.drawable.eyebeauty);
        mEntityList.add(baseEntity2);
        BaseEntity baseEntity3 = new BaseEntity();
        baseEntity3.setText("眉毛");
        baseEntity3.setImage(R.drawable.eyebrow);
        mEntityList.add(baseEntity3);
        BaseEntity baseEntity4 = new BaseEntity();
        baseEntity4.setText("眼影");
        baseEntity4.setImage(R.drawable.eyeshadow);
        mEntityList.add(baseEntity4);
        BaseEntity baseEntity5 = new BaseEntity();
        baseEntity5.setText("眼线");
        baseEntity5.setImage(R.drawable.eyeline);
        mEntityList.add(baseEntity5);
        BaseEntity baseEntity6 = new BaseEntity();
        baseEntity6.setText("发色");
        baseEntity6.setImage(R.drawable.haircolor);
        mEntityList.add(baseEntity6);
    }
    //初始化口红数据
    private void initMouseData(){
        mEntityList = new ArrayList<BaseEntity>();
        BaseEntity baseEntity1 = new BaseEntity();
        baseEntity1.setText("赤红");
        baseEntity1.setImage(R.drawable.chihong);
        mEntityList.add(baseEntity1);
        BaseEntity baseEntity2 = new BaseEntity();
        baseEntity2.setText("燕脂");
        baseEntity2.setImage(R.drawable.yanzhi);
        mEntityList.add(baseEntity2);
        BaseEntity baseEntity3 = new BaseEntity();
        baseEntity3.setText("猩猩红");
        baseEntity3.setImage(R.drawable.xingxinghong);
        mEntityList.add(baseEntity3);
        BaseEntity baseEntity4 = new BaseEntity();
        baseEntity4.setText("红");
        baseEntity4.setImage(R.drawable.hong);
        mEntityList.add(baseEntity4);
        BaseEntity baseEntity5 = new BaseEntity();
        baseEntity5.setText("红曳");
        baseEntity5.setImage(R.drawable.hongye);
        mEntityList.add(baseEntity5);
        BaseEntity baseEntity6 = new BaseEntity();
        baseEntity6.setText("绯");
        baseEntity6.setImage(R.drawable.fei);
        mEntityList.add(baseEntity6);
        BaseEntity baseEntity7 = new BaseEntity();
        baseEntity7.setText("苏红");
        baseEntity7.setImage(R.drawable.suhong);
        mEntityList.add(baseEntity7);
        BaseEntity baseEntity8 = new BaseEntity();
        baseEntity8.setText("莓");
        baseEntity8.setImage(R.drawable.mei);
        mEntityList.add(baseEntity8);
        BaseEntity baseEntity9 = new BaseEntity();
        baseEntity9.setText("中红");
        baseEntity9.setImage(R.drawable.zhonghong);
        mEntityList.add(baseEntity9);
        BaseEntity baseEntity10 = new BaseEntity();
        baseEntity10.setText("银朱");
        baseEntity10.setImage(R.drawable.yinzhu);
        mEntityList.add(baseEntity10);
        BaseEntity baseEntity11 = new BaseEntity();
        baseEntity11.setText("红华");
        baseEntity11.setImage(R.drawable.honghua);
        mEntityList.add(baseEntity11);
        BaseEntity baseEntity12 = new BaseEntity();
        baseEntity12.setText("桑染");
        baseEntity12.setImage(R.drawable.sangran);
        mEntityList.add(baseEntity12);
        BaseEntity baseEntity13 = new BaseEntity();
        baseEntity13.setText("蓝色");
        baseEntity13.setImage(R.drawable.g_like);
        mEntityList.add(baseEntity13);
        BaseEntity baseEntity14 = new BaseEntity();
        baseEntity14.setText("绿色");
        baseEntity14.setImage(R.drawable.g_like);
        mEntityList.add(baseEntity14);
        BaseEntity baseEntity15 = new BaseEntity();
        baseEntity15.setText("紫色");
        baseEntity15.setImage(R.drawable.g_like);
        mEntityList.add(baseEntity15);
    }
    //初始化眼影数据
    private void initEyeShadowData(){
        mEntityList = new ArrayList<BaseEntity>();
        BaseEntity baseEntity1 = new BaseEntity();
        baseEntity1.setText("口红");
        baseEntity1.setImage(R.drawable.mouth);
        mEntityList.add(baseEntity1);
        BaseEntity baseEntity2 = new BaseEntity();
        baseEntity2.setText("美瞳");
        baseEntity2.setImage(R.drawable.eyebeauty);
        mEntityList.add(baseEntity2);
        BaseEntity baseEntity3 = new BaseEntity();
        baseEntity3.setText("眉毛");
        baseEntity3.setImage(R.drawable.eyebrow);
        mEntityList.add(baseEntity3);
        BaseEntity baseEntity4 = new BaseEntity();
        baseEntity4.setText("眼影");
        baseEntity4.setImage(R.drawable.eyeshadow);
        mEntityList.add(baseEntity4);
    }
    //初始化美瞳数据
    private void initEyeBeautyData(){
        mEntityList = new ArrayList<BaseEntity>();
        BaseEntity baseEntity1 = new BaseEntity();
        baseEntity1.setText("口红");
        baseEntity1.setImage(R.drawable.mouth);
        mEntityList.add(baseEntity1);
        BaseEntity baseEntity2 = new BaseEntity();
        baseEntity2.setText("美瞳");
        baseEntity2.setImage(R.drawable.eyebeauty);
        mEntityList.add(baseEntity2);
        BaseEntity baseEntity3 = new BaseEntity();
        baseEntity3.setText("眉毛");
        baseEntity3.setImage(R.drawable.eyebrow);
        mEntityList.add(baseEntity3);
        BaseEntity baseEntity4 = new BaseEntity();
        baseEntity4.setText("眼影");
        baseEntity4.setImage(R.drawable.eyeshadow);
        mEntityList.add(baseEntity4);
    }
    //初始化眼线数据
    private void initEyeLineData(){
        mEntityList = new ArrayList<BaseEntity>();
        BaseEntity baseEntity1 = new BaseEntity();
        baseEntity1.setText("口红");
        baseEntity1.setImage(R.drawable.mouth);
        mEntityList.add(baseEntity1);
        BaseEntity baseEntity2 = new BaseEntity();
        baseEntity2.setText("美瞳");
        baseEntity2.setImage(R.drawable.eyebeauty);
        mEntityList.add(baseEntity2);
        BaseEntity baseEntity3 = new BaseEntity();
        baseEntity3.setText("眉毛");
        baseEntity3.setImage(R.drawable.eyebrow);
        mEntityList.add(baseEntity3);
        BaseEntity baseEntity4 = new BaseEntity();
        baseEntity4.setText("眼影");
        baseEntity4.setImage(R.drawable.eyeshadow);
        mEntityList.add(baseEntity4);
    }
    //初始化眉型数据
    private void initEyebrowData(){
        mEntityList = new ArrayList<BaseEntity>();
        BaseEntity baseEntity1 = new BaseEntity();
        baseEntity1.setText("口红");
        baseEntity1.setImage(R.drawable.mouth);
        mEntityList.add(baseEntity1);
        BaseEntity baseEntity2 = new BaseEntity();
        baseEntity2.setText("美瞳");
        baseEntity2.setImage(R.drawable.eyebeauty);
        mEntityList.add(baseEntity2);
        BaseEntity baseEntity3 = new BaseEntity();
        baseEntity3.setText("眉毛");
        baseEntity3.setImage(R.drawable.eyebrow);
        mEntityList.add(baseEntity3);
        BaseEntity baseEntity4 = new BaseEntity();
        baseEntity4.setText("眼影");
        baseEntity4.setImage(R.drawable.eyeshadow);
        mEntityList.add(baseEntity4);
    }
    //初始化发色数据
    private void initHairColorData(){
        mEntityList = new ArrayList<BaseEntity>();
        BaseEntity baseEntity1 = new BaseEntity();
        baseEntity1.setText("口红");
        baseEntity1.setImage(R.drawable.mouth);
        mEntityList.add(baseEntity1);
        BaseEntity baseEntity2 = new BaseEntity();
        baseEntity2.setText("美瞳");
        baseEntity2.setImage(R.drawable.eyebeauty);
        mEntityList.add(baseEntity2);
        BaseEntity baseEntity3 = new BaseEntity();
        baseEntity3.setText("眉毛");
        baseEntity3.setImage(R.drawable.eyebrow);
        mEntityList.add(baseEntity3);
        BaseEntity baseEntity4 = new BaseEntity();
        baseEntity4.setText("眼影");
        baseEntity4.setImage(R.drawable.eyeshadow);
        mEntityList.add(baseEntity4);
    }
    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(makeUpActivity.this);
        //水平布局
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(manager);
        // 设置adapter
        DemoAdapter adapter = new DemoAdapter(makeUpActivity.this, mEntityList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new DemoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(makeUpActivity.this, "选择了" + mEntityList.get(position).getText(), Toast.LENGTH_SHORT).show();
                makeUpchooser(mEntityList.get(position).getText());
                Bitmap bitmap = drawFace(mFace,mEntityList.get(position).getText());
                mImageView.setImageBitmap(bitmap);
            }
        });
    }
    /**
     * 设置监听
     */
    private void setListeners() {

        // 展示图片按钮点击事件
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoOrSelectPicture();// 拍照或者调用图库
            }
        });
        previewMakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                initRecyclerView();
            }
        });
        generateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void takePhotoOrSelectPicture() {
        CharSequence[] items = {"拍照","相册"};// 裁剪items选项
        // 弹出对话框提示用户拍照或者是通过本地图库选择图片
        new AlertDialog.Builder(makeUpActivity.this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            // 选择了拍照
                            case 0:
                                startCamera();
                                break;
                            // 调用系统图库
                            case 1:
                                startAlbum();
                                break;
                        }
                    }
                }).show();
    }
    /**
     * 调用startActivityForResult方法启动一个intent后，
     * 可以在该方法中拿到返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:// 拍照
                if(resultCode == RESULT_OK){
                    File cropImage = new File(Environment.getExternalStorageDirectory(), "take_photo_image.jpg");
                    Uri cropImgUri = Uri.fromFile(cropImage);
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(cropImgUri, "image/*");
                    intent.putExtra("crop", "true");
                    //  设置裁剪图片的宽高
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 960);
                    intent.putExtra("outputY", 960);
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra("return-data", false);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    intent.putExtra("noFaceDetection", true); // no face detection
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CHOOSE_PHOTO:// 系统图库
                if(resultCode == RESULT_OK){
                    // 获取图库所选图片的uri
                    Uri uri = data.getData();
                    // 创建intent用于裁剪图片
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    intent.putExtra("crop", "true");
                    //  设置裁剪图片的宽高
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 960);
                    intent.putExtra("outputY", 960);
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra("return-data", false);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    intent.putExtra("noFaceDetection", true); // no face detection
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:// 裁剪后展示图片
                if(resultCode == RESULT_OK){
                    try{
                        // 展示拍照后裁剪的图片
                        if(imageUri != null){
                            // 根据文件流解析生成Bitmap对象
                            BitmapUtils bitmapUtils = new BitmapUtils(this);
                            mbitmap = BitmapUtils.decodeUriAsBitmap(imageUri);
                            // 展示图片
                            byte []imageByte = toByteArray(mbitmap);
                            FacesUtils facesUtils = new FacesUtils();
                            mFace= facesUtils.getFacesInfo(imageByte);
                            mImageView.setImageBitmap(mbitmap);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void makeUpchooser(String index){

        switch(index){
            case "口红":
                initMouseData();
                break;
            case "美瞳":
                initEyeBeautyData();
                break;
            case "眼线":
                initEyeLineData();
                break;
            case "眼影":
                initEyeShadowData();
                break;
            case "眉毛":
                initEyebrowData();
                break;
            case "发色":
                initHairColorData();
                break;
            default:
                break;
        }
        initRecyclerView();
    }
    private  void startCamera(){
        // 创建文件保存拍照的图片
        File takePhotoImage = new File(Environment.getExternalStorageDirectory(), "take_photo_image.jpg");
        try {
            // 文件存在，删除文件
            if(takePhotoImage.exists()){
                takePhotoImage.delete();
            }
            // 根据路径名自动的创建一个新的空文件
            takePhotoImage.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        // 获取图片文件的uri对象
        imageUri = Uri.fromFile(takePhotoImage);
        // 创建Intent，用于启动手机的照相机拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定输出到文件uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        // 启动intent开始拍照
        startActivityForResult(intent, TAKE_PHOTO);
    }
    private void startAlbum(){
        // 创建Intent，用于打开手机本地图库选择图片
        Intent intent1 = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // 启动intent打开本地图库
        startActivityForResult(intent1,CHOOSE_PHOTO);
    }
    private Bitmap drawFace(Face face,String index){
        if (face==null)
            return mbitmap;
        Bitmap bitmap = null;
        FaceMakeUpUtils faceMakeUpUtils = new FaceMakeUpUtils(this,face);
        switch(index){
            case "赤红":
                faceMakeUpUtils.setARGB(120,128,29,30);
                break;
            case "银朱":
                faceMakeUpUtils.setARGB(120,175,27,51);
                break;
            case "猩猩红":
                faceMakeUpUtils.setARGB(120,176,33,25);
                break;
            case "桑染":
                faceMakeUpUtils.setARGB(120,99,24,31);
                break;
            case "中红":
                faceMakeUpUtils.setARGB(120,188,47,55);
                break;
            case "莓":
                faceMakeUpUtils.setARGB(120,185,38,44);
                break;
            case "燕脂":
                faceMakeUpUtils.setARGB(120,154,34,36);
                break;
            case "红华":
                faceMakeUpUtils.setARGB(120,165,33,28);
                break;
            case "苏红":
                faceMakeUpUtils.setARGB(120,155,56,76);
                break;
            case "绯":
                faceMakeUpUtils.setARGB(120,198,76,64);
                break;
            case "红曳":
                faceMakeUpUtils.setARGB(120,165,41,52);
                break;
            case "红":
                faceMakeUpUtils.setARGB(120,171,34,46);
                break;
            case "绿色":
                faceMakeUpUtils.setARGB(120,14,216,50);
                break;
            case "蓝色":
                faceMakeUpUtils.setARGB(120,12,32,185);
                break;
            case "紫色":
                faceMakeUpUtils.setARGB(120,117,11,169);
                break;
            default:
                return mbitmap;
        }
        bitmap = faceMakeUpUtils.mouthRendering(mbitmap);
        return bitmap;
    }
}