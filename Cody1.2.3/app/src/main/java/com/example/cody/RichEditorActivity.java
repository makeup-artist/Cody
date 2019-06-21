package com.example.cody;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinalwb.are.AREditor;
import com.chinalwb.are.spans.AreImageSpan;
import com.chinalwb.are.strategies.ImageStrategy;
import com.chinalwb.are.strategies.VideoStrategy;
import com.chinalwb.are.styles.toolitems.styles.ARE_Style_Image;
import com.example.cody.entity.MsgBean;
import com.example.cody.entity.UserLoginBean.UsermainBean;
import com.example.cody.entity.UserPost.PostBean;
import com.example.cody.net.CommonOkHttpClient;
import com.example.cody.net.OkHttp3Util;
import com.example.cody.net.exception.OkHttpException;
import com.example.cody.net.listener.DisposeDataHandle;
import com.example.cody.net.listener.DisposeDataListener;
import com.example.cody.net.request.CommonRequest;
import com.example.cody.net.request.HeaderParams;
import com.example.cody.net.request.RequestParams;
import com.example.cody.net.response.CommonJsonCallback;
import com.example.cody.utils.DemoUtil;
import com.example.cody.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.cody.TextViewActivity.HTML_TEXT;
import static com.example.cody.utils.Constants.URL_LOGIN;
import static com.example.cody.utils.Constants.URL_POST_IMG;
import static com.example.cody.utils.Constants.URL_POST_POST;
import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;
public class RichEditorActivity extends AppCompatActivity implements ImageStrategy {


    @BindView(R.id.action_show_tv)
    TextView actionShowTv;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.action_save)
    TextView actionSave;
    @BindView(R.id.title)
    EditText mtitle;
    @BindView(R.id.areditor)
    AREditor areditor;
    private SharedPreferencesUtil sp;
    private String token;
    private String title;
    private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_are__full_bottom);
        ButterKnife.bind(this);
        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
        initView();
        token = sp.getToken();
    }

    private AREditor arEditor;

    private VideoStrategy mVideoStrategy = new VideoStrategy() {
        @Override
        public String uploadVideo(Uri uri) {
            try {
                Thread.sleep(3000); // Do upload here
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "http://www.xx.com/x.mp4";
        }

        @Override
        public String uploadVideo(String videoPath) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "http://www.xx.com/x.mp4";
        }
    };


    private void initView() {
        this.arEditor = this.findViewById(R.id.areditor);
        this.arEditor.setVideoStrategy(mVideoStrategy);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.arEditor.onActivityResult(requestCode, resultCode, data);
    }



    @OnClick({R.id.action_show_tv, R.id.post, R.id.action_save})
    public void onViewClicked(View view) {
        String html = "";
        switch (view.getId()) {
            case R.id.action_show_tv:
                html = "";
                html = this.arEditor.getHtml();
                Intent intent = new Intent(this, TextViewActivity.class);
                intent.putExtra(HTML_TEXT, html);
                startActivity(intent);
                break;
            case R.id.post:
                Map params = new HashMap();
                Map harams = new HashMap();
                title = mtitle.getText().toString();
                token = sp.getToken();
                params.put("title",title);
                params.put("content", this.arEditor.getHtml());
                harams.put("Authorization",token);
                Log.d("QAQ",title+this.arEditor.getHtml()+token);
                CommonOkHttpClient
                        .sendRequest(CommonRequest.createPostRequest(URL_POST_POST, params,new HeaderParams(harams)), new CommonJsonCallback(new DisposeDataHandle(PostBean.class, new DisposeDataListener() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                PostBean testBean = (PostBean) responseObj;
                                Log.d("QAQ","123"+testBean.getMsg());
                                if (testBean.getCode() == 200) {
                                    Toast.makeText(MyApplication.getContext(), "已成功发布", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(MyApplication.getContext(), "发布失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Object reasonObj) {
                                OkHttpException testBean = (OkHttpException) reasonObj;
                                Log.d("QAQ","222"+testBean.getEmsg().toString());
                                Log.d("QAQ",reasonObj.toString());
                                Toast.makeText(MyApplication.getContext(), "网络请求错误了QAQ", Toast.LENGTH_SHORT).show();
                            }
                        })));
                break;
            case R.id.action_save:
                html = this.arEditor.getHtml();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    DemoUtil.saveHtml(this, html);
                }
                break;
        }
    }

    @Override
    public void uploadAndInsertImage(Uri uri, ARE_Style_Image areStyleImage) {
        new UploadImageTask(areStyleImage).executeOnExecutor(THREAD_POOL_EXECUTOR, uri);
    }


    private static class UploadImageTask extends AsyncTask<Uri, Integer, String> {

        WeakReference<ARE_Style_Image> areStyleImage;
        private ProgressDialog dialog;
        UploadImageTask(ARE_Style_Image styleImage) {
            this.areStyleImage = new WeakReference<>(styleImage);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (dialog == null) {
                dialog = ProgressDialog.show(
                        areStyleImage.get().getEditText().getContext(),
                        "",
                        "Uploading image. Please wait...",
                        true);
            } else {
                dialog.show();
            }
        }

        @Override
        protected String doInBackground(Uri... uris) {
            Log.d("QAQ", "doInBackground: ");
            String string = null;
            if (uris != null && uris.length > 0) {

                File file = new File(String.valueOf(uris));
                HashMap<String, String> map = new HashMap<>();
                map.put("uid", "4123");

                OkHttp3Util.uploadFile(URL_POST_IMG, file, "dash.jpg", map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("+++", "onFailure: " + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        Log.e("---", "onResponse: " + response.body().string());
                        if (response.isSuccessful()) {
                                    Toast.makeText(MyApplication.getContext(), "此时上传成功!!", Toast.LENGTH_SHORT).
                                            show(); }
                            //此时上传成功  获取用户信息
                    }
                });


                // Returns the image url on server here
                return "https://avatars0.githubusercontent.com/u/1758864?s=460&v=4";
            }
            return "www.baidu.com";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog != null) {
                dialog.dismiss();
            }
            if (areStyleImage.get() != null) {
                areStyleImage.get().insertImage(s, AreImageSpan.ImageType.URL);
            }
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
