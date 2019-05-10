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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.megvii.facepp.api.bean.Face;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cody.BitmapUtil.toByteArray;


public class makeUpActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
    private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap
    private ImageView mImageView;
    private Button takePhoto;
    //private Uri imageUri;// 拍照时的图片uri

    public static final int TAKE_PHOTO =1;
    public static final  int CHOOSE_PHOTO =2;
    public static final int CROP_PHOTO = 3;

    @BindView(R.id.response)
    TextView txtResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        setContentView(R.layout.fragment_photo);
        ButterKnife.bind(this);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xiaofu);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageBitmap(bitmap);
        FacesUtil facesUtil = new FacesUtil();
        FaceMakeUpUtils faceMakeUpUtils = new FaceMakeUpUtils();
        Face face = facesUtil.getFacesInfo("http://image1607.oss-cn-qingdao.aliyuncs.com/xiaofu.jpeg");
        bitmap = faceMakeUpUtils.mouthRendering(bitmap,face);
        bitmap = faceMakeUpUtils.drawEyePoint(bitmap,face);
        bitmap = faceMakeUpUtils.drawEyebrowPoint(bitmap,face);
        mImageView.setImageBitmap(bitmap);
        takePhoto= (Button)findViewById(R.id.button_take_photo);
        mImageView = (ImageView)findViewById(R.id.imageView);

        setListeners();// 设置监听
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
                                break;
                            // 调用系统图库
                            case 1:
                                // 创建Intent，用于打开手机本地图库选择图片
                                Intent intent1 = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // 启动intent打开本地图库
                                startActivityForResult(intent1,CHOOSE_PHOTO);
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
                            Bitmap bitmap = decodeUriAsBitmap(imageUri);
                            // 展示图片
                            byte []imageByte = toByteArray(bitmap);
                            FacesUtil facesUtil = new FacesUtil();
                            Face face = facesUtil.getFacesInfo(imageByte);
                            FaceMakeUpUtils faceMakeUpUtils = new FaceMakeUpUtils();
                            bitmap = faceMakeUpUtils.mouthRendering(bitmap,face);
                            //bitmap = faceMakeUpUtils.drawEyePoint(bitmap,face);
                            //bitmap = faceMakeUpUtils.drawEyebrowPoint(bitmap,face);
                            mImageView.setImageBitmap(bitmap);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}