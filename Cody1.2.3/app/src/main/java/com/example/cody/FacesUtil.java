package com.example.cody;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.megvii.facepp.api.bean.CommonRect;
import com.megvii.facepp.api.bean.Face;
import com.megvii.facepp.api.bean.FaceLandmark;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLException;

/*
 *@author：peanut
 *@finish time:2019.4.18
 * notation:人脸关键点识别api
 */
public class FacesUtil {
    //人脸检测
    public static String facesDetect(String image_url){
        String str = "Failed";
//        File file = new File("你的本地图片路径");
//        byte[] buff = getBytesFromFile(file);
        String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", "8h-KM_8IiizK7I1-nTek-fAD8cIhgSuc");
        map.put("api_secret", "Wd2DoAUc2MGtTXrNU-3lG-5McJUhYbeC");
        map.put("image_url",image_url);
        map.put("return_landmark", "2");
        map.put("return_attributes", "gender");
        //byteMap.put("image_file", buff);
        try{
            byte[] bacd = post(url, map, byteMap);
            str = new String(bacd);
            System.out.println("request succeed");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String facesDetect(byte[] imageByte){
        String str = "Failed";
        byte[] buff = imageByte;
        String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", "8h-KM_8IiizK7I1-nTek-fAD8cIhgSuc");
        map.put("api_secret", "Wd2DoAUc2MGtTXrNU-3lG-5McJUhYbeC");
        map.put("return_attributes", "gender");
        map.put("return_landmark", "2");
        byteMap.put("image_file", buff);
        try{
            byte[] bacd = post(url, map, byteMap);
            str = new String(bacd);
            System.out.println("request succeed");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();

    //http请求post
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("post")
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conne;
        URL url1 = new URL(url);
        OutputStream outputStream;
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        outputStream = conne.getOutputStream();
        DataOutputStream obos = new DataOutputStream(outputStream);
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private static String encode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8");
    }
    //File转换为Byte
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }
    public Face getFacePoint(String result){
        Face face = new Face();
        CommonRect commonRect = new CommonRect();
        FaceLandmark faceLandmark = new FaceLandmark();

        //返回的json数据转为JSON对象
        JSONObject json = JSON.parseObject(result);
        //获取JsonArray(faces)
        JSONArray jsonArray = json.getJSONArray("faces");
        //获取JsonArray中的第一个人脸信息
        JSONObject subJson = jsonArray.getJSONObject(0);
        //face_token
        face.setFace_token(subJson.getString("face_token"));

        //face_rectangle
        JSONObject subJsonRec = subJson.getJSONObject("face_rectangle");
        commonRect.setTop(subJsonRec.getIntValue("top"));
        commonRect.setWidth(subJsonRec.getIntValue("width"));
        commonRect.setHeight(subJsonRec.getIntValue("height"));
        commonRect.setLeft(subJsonRec.getIntValue("left"));
        face.setFace_rectangle(commonRect);
        System.out.println(commonRect.toString());
        JSONObject subJsonLandmark = subJson.getJSONObject("landmark");

        //mouthPoint
        FaceLandmark.Point point1 = new FaceLandmark.Point();
        point1.setX(subJsonLandmark.getJSONObject("mouth_left_corner").getIntValue("x"));
        point1.setY(subJsonLandmark.getJSONObject("mouth_left_corner").getIntValue("y"));
        faceLandmark.setMouth_left_corner(point1);

        FaceLandmark.Point point2 = new FaceLandmark.Point();
        point2.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_bottom").getIntValue("x"));
        point2.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_bottom").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_bottom(point2);

        FaceLandmark.Point point3 = new FaceLandmark.Point();
        point3.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_left_contour1").getIntValue("x"));
        point3.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_left_contour1").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_left_contour1(point3);

        FaceLandmark.Point point4 = new FaceLandmark.Point();
        point4.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_left_contour2").getIntValue("x"));
        point4.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_left_contour2").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_left_contour2(point4);

        FaceLandmark.Point point5 = new FaceLandmark.Point();
        point5.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_left_contour3").getIntValue("x"));
        point5.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_left_contour3").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_left_contour3(point5);

        FaceLandmark.Point point6 = new FaceLandmark.Point();
        point6.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_right_contour1").getIntValue("x"));
        point6.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_right_contour1").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_right_contour1(point6);

        FaceLandmark.Point point7 = new FaceLandmark.Point();
        point7.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_right_contour2").getIntValue("x"));
        point7.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_right_contour2").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_right_contour2(point7);

        FaceLandmark.Point point8 = new FaceLandmark.Point();
        point8.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_right_contour3").getIntValue("x"));
        point8.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_right_contour3").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_right_contour3(point8);

        FaceLandmark.Point point9 = new FaceLandmark.Point();
        point9.setX(subJsonLandmark.getJSONObject("mouth_lower_lip_top").getIntValue("x"));
        point9.setY(subJsonLandmark.getJSONObject("mouth_lower_lip_top").getIntValue("y"));
        faceLandmark.setMouth_lower_lip_top(point9);

        FaceLandmark.Point point10 = new FaceLandmark.Point();
        point10.setX(subJsonLandmark.getJSONObject("mouth_right_corner").getIntValue("x"));
        point10.setY(subJsonLandmark.getJSONObject("mouth_right_corner").getIntValue("y"));
        faceLandmark.setMouth_right_corner(point10);

        FaceLandmark.Point point11 = new FaceLandmark.Point();
        point11.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_bottom").getIntValue("x"));
        point11.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_bottom").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_bottom(point11);

        FaceLandmark.Point point12 = new FaceLandmark.Point();
        point12.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour1").getIntValue("x"));
        point12.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour1").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_left_contour1(point12);;

        FaceLandmark.Point point13 = new FaceLandmark.Point();
        point13.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour2").getIntValue("x"));
        point13.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour2").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_left_contour2(point13);
        FaceLandmark.Point point14 = new FaceLandmark.Point();
        point14.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour3").getIntValue("x"));
        point14.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour3").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_left_contour3(point14);

        FaceLandmark.Point point18 = new FaceLandmark.Point();
        point18.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour1").getIntValue("x"));
        point18.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour1").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_right_contour1(point18);

        FaceLandmark.Point point15 = new FaceLandmark.Point();
        point15.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour2").getIntValue("x"));
        point15.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour2").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_right_contour2(point15);

        FaceLandmark.Point point16 = new FaceLandmark.Point();
        point16.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour3").getIntValue("x"));
        point16.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour3").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_right_contour3(point16);

        FaceLandmark.Point point17 = new FaceLandmark.Point();
        point17.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_top").getIntValue("x"));
        point17.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_top").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_top(point17);

        FaceLandmark.Point point1a = new FaceLandmark.Point();
        point1a.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour4").getIntValue("x"));
        point1a.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour4").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_left_contour4(point1a);

        FaceLandmark.Point point1b = new FaceLandmark.Point();
        point1a.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour4").getIntValue("x"));
        point1a.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour4").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_right_contour4(point1b);

        //eyePoint
        FaceLandmark.Point point19 = new FaceLandmark.Point();
        point19.setX(subJsonLandmark.getJSONObject("left_eye_bottom").getIntValue("x"));
        point19.setY(subJsonLandmark.getJSONObject("left_eye_bottom").getIntValue("y"));
        faceLandmark.setLeft_eye_bottom(point19);

        FaceLandmark.Point point20 = new FaceLandmark.Point();
        point20.setX(subJsonLandmark.getJSONObject("left_eye_center").getIntValue("x"));
        point20.setY(subJsonLandmark.getJSONObject("left_eye_center").getIntValue("y"));
        faceLandmark.setLeft_eye_center(point20);

        FaceLandmark.Point point21 = new FaceLandmark.Point();
        point21.setX(subJsonLandmark.getJSONObject("left_eye_left_corner").getIntValue("x"));
        point21.setY(subJsonLandmark.getJSONObject("left_eye_left_corner").getIntValue("y"));
        faceLandmark.setLeft_eye_left_corner(point21);

        FaceLandmark.Point point22 = new FaceLandmark.Point();
        point22.setX(subJsonLandmark.getJSONObject("left_eye_lower_left_quarter").getIntValue("x"));
        point22.setY(subJsonLandmark.getJSONObject("left_eye_lower_left_quarter").getIntValue("y"));
        faceLandmark.setLeft_eye_lower_left_quarter(point22);

        FaceLandmark.Point point23 = new FaceLandmark.Point();
        point23.setX(subJsonLandmark.getJSONObject("left_eye_lower_right_quarter").getIntValue("x"));
        point23.setY(subJsonLandmark.getJSONObject("left_eye_lower_right_quarter").getIntValue("y"));
        faceLandmark.setLeft_eye_lower_right_quarter(point23);

        FaceLandmark.Point point24 = new FaceLandmark.Point();
        point24.setX(subJsonLandmark.getJSONObject("left_eye_pupil").getIntValue("x"));
        point24.setY(subJsonLandmark.getJSONObject("left_eye_pupil").getIntValue("y"));
        faceLandmark.setLeft_eye_pupil(point24);

        FaceLandmark.Point point25 = new FaceLandmark.Point();
        point25.setX(subJsonLandmark.getJSONObject("left_eye_right_corner").getIntValue("x"));
        point25.setY(subJsonLandmark.getJSONObject("left_eye_right_corner").getIntValue("y"));
        faceLandmark.setLeft_eye_right_corner(point25);

        FaceLandmark.Point point26 = new FaceLandmark.Point();
        point26.setX(subJsonLandmark.getJSONObject("left_eye_top").getIntValue("x"));
        point26.setY(subJsonLandmark.getJSONObject("left_eye_top").getIntValue("y"));
        faceLandmark.setLeft_eye_top(point26);

        FaceLandmark.Point point27 = new FaceLandmark.Point();
        point27.setX(subJsonLandmark.getJSONObject("left_eye_upper_left_quarter").getIntValue("x"));
        point27.setY(subJsonLandmark.getJSONObject("left_eye_upper_left_quarter").getIntValue("y"));
        faceLandmark.setLeft_eye_upper_left_quarter(point27);

        FaceLandmark.Point point28 = new FaceLandmark.Point();
        point28.setX(subJsonLandmark.getJSONObject("left_eye_upper_right_quarter").getIntValue("x"));
        point28.setY(subJsonLandmark.getJSONObject("left_eye_upper_right_quarter").getIntValue("y"));
        faceLandmark.setLeft_eye_upper_right_quarter(point28);

        FaceLandmark.Point point29 = new FaceLandmark.Point();
        point29.setX(subJsonLandmark.getJSONObject("right_eye_bottom").getIntValue("x"));
        point29.setY(subJsonLandmark.getJSONObject("right_eye_bottom").getIntValue("y"));
        faceLandmark.setRight_eye_bottom(point29);

        FaceLandmark.Point point30 = new FaceLandmark.Point();
        point30.setX(subJsonLandmark.getJSONObject("right_eye_center").getIntValue("x"));
        point30.setY(subJsonLandmark.getJSONObject("right_eye_center").getIntValue("y"));
        faceLandmark.setRight_eye_center(point30);

        FaceLandmark.Point point31 = new FaceLandmark.Point();
        point31.setX(subJsonLandmark.getJSONObject("right_eye_left_corner").getIntValue("x"));
        point31.setY(subJsonLandmark.getJSONObject("right_eye_left_corner").getIntValue("y"));
        faceLandmark.setRight_eye_left_corner(point31);

        FaceLandmark.Point point32 = new FaceLandmark.Point();
        point32.setX(subJsonLandmark.getJSONObject("right_eye_lower_left_quarter").getIntValue("x"));
        point32.setY(subJsonLandmark.getJSONObject("right_eye_lower_left_quarter").getIntValue("y"));
        faceLandmark.setRight_eye_lower_left_quarter(point32);

        FaceLandmark.Point point33 = new FaceLandmark.Point();
        point33.setX(subJsonLandmark.getJSONObject("right_eye_lower_right_quarter").getIntValue("x"));
        point33.setY(subJsonLandmark.getJSONObject("right_eye_lower_right_quarter").getIntValue("y"));
        faceLandmark.setRight_eye_lower_right_quarter(point33);

        FaceLandmark.Point point34 = new FaceLandmark.Point();
        point34.setX(subJsonLandmark.getJSONObject("right_eye_pupil").getIntValue("x"));
        point34.setY(subJsonLandmark.getJSONObject("right_eye_pupil").getIntValue("y"));
        faceLandmark.setRight_eye_pupil(point34);

        FaceLandmark.Point point35 = new FaceLandmark.Point();
        point35.setX(subJsonLandmark.getJSONObject("right_eye_right_corner").getIntValue("x"));
        point35.setY(subJsonLandmark.getJSONObject("right_eye_right_corner").getIntValue("y"));
        faceLandmark.setRight_eye_right_corner(point35);

        FaceLandmark.Point point36 = new FaceLandmark.Point();
        point36.setX(subJsonLandmark.getJSONObject("right_eye_top").getIntValue("x"));
        point36.setY(subJsonLandmark.getJSONObject("right_eye_top").getIntValue("y"));
        faceLandmark.setRight_eye_top(point36);

        FaceLandmark.Point point37 = new FaceLandmark.Point();
        point37.setX(subJsonLandmark.getJSONObject("right_eye_upper_left_quarter").getIntValue("x"));
        point37.setY(subJsonLandmark.getJSONObject("right_eye_upper_left_quarter").getIntValue("y"));
        faceLandmark.setRight_eye_upper_left_quarter(point37);

        FaceLandmark.Point point38 = new FaceLandmark.Point();
        point38.setX(subJsonLandmark.getJSONObject("right_eye_upper_right_quarter").getIntValue("x"));
        point38.setY(subJsonLandmark.getJSONObject("right_eye_upper_right_quarter").getIntValue("y"));
        faceLandmark.setRight_eye_upper_right_quarter(point38);
        //contourPoint

        //眉毛的点
        FaceLandmark.Point point39 = new FaceLandmark.Point();
        point39.setX(subJsonLandmark.getJSONObject("left_eyebrow_left_corner").getIntValue("x"));
        point39.setY(subJsonLandmark.getJSONObject("left_eyebrow_left_corner").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_left_corner(point39);

        FaceLandmark.Point point40 = new FaceLandmark.Point();
        point40.setX(subJsonLandmark.getJSONObject("left_eyebrow_lower_left_quarter").getIntValue("x"));
        point40.setY(subJsonLandmark.getJSONObject("left_eyebrow_lower_left_quarter").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_lower_left_quarter(point40);

        FaceLandmark.Point point41 = new FaceLandmark.Point();
        point41.setX(subJsonLandmark.getJSONObject("left_eyebrow_lower_middle").getIntValue("x"));
        point41.setY(subJsonLandmark.getJSONObject("left_eyebrow_lower_middle").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_lower_middle(point41);

        FaceLandmark.Point point42 = new FaceLandmark.Point();
        point42.setX(subJsonLandmark.getJSONObject("left_eyebrow_lower_right_quarter").getIntValue("x"));
        point42.setY(subJsonLandmark.getJSONObject("left_eyebrow_lower_right_quarter").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_lower_right_quarter(point42);

        FaceLandmark.Point point43 = new FaceLandmark.Point();
        point43.setX(subJsonLandmark.getJSONObject("left_eyebrow_lower_right_corner").getIntValue("x"));
        point43.setY(subJsonLandmark.getJSONObject("left_eyebrow_lower_right_corner").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_lower_right_corner(point43);

        FaceLandmark.Point point44 = new FaceLandmark.Point();
        point44.setX(subJsonLandmark.getJSONObject("left_eyebrow_upper_left_quarter").getIntValue("x"));
        point44.setY(subJsonLandmark.getJSONObject("left_eyebrow_upper_left_quarter").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_upper_left_quarter(point44);

        FaceLandmark.Point point45 = new FaceLandmark.Point();
        point45.setX(subJsonLandmark.getJSONObject("left_eyebrow_upper_middle").getIntValue("x"));
        point45.setY(subJsonLandmark.getJSONObject("left_eyebrow_upper_middle").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_upper_middle(point45);

        FaceLandmark.Point point46 = new FaceLandmark.Point();
        point46.setX(subJsonLandmark.getJSONObject("left_eyebrow_upper_right_corner").getIntValue("x"));
        point46.setY(subJsonLandmark.getJSONObject("left_eyebrow_upper_right_corner").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_upper_right_corner(point46);

        FaceLandmark.Point point47 = new FaceLandmark.Point();
        point47.setX(subJsonLandmark.getJSONObject("left_eyebrow_upper_right_quarter").getIntValue("x"));
        point47.setY(subJsonLandmark.getJSONObject("left_eyebrow_upper_right_quarter").getIntValue("y"));
        faceLandmark.setLeft_eyebrow_upper_right_quarter(point47);

        FaceLandmark.Point point48 = new FaceLandmark.Point();
        point48.setX(subJsonLandmark.getJSONObject("right_eyebrow_right_corner").getIntValue("x"));
        point48.setY(subJsonLandmark.getJSONObject("right_eyebrow_right_corner").getIntValue("y"));
        faceLandmark.setRight_eyebrow_right_corner(point48);

        FaceLandmark.Point point49 = new FaceLandmark.Point();
        point49.setX(subJsonLandmark.getJSONObject("right_eyebrow_lower_left_quarter").getIntValue("x"));
        point49.setY(subJsonLandmark.getJSONObject("right_eyebrow_lower_left_quarter").getIntValue("y"));
        faceLandmark.setRight_eyebrow_lower_left_quarter(point49);

        FaceLandmark.Point point50 = new FaceLandmark.Point();
        point50.setX(subJsonLandmark.getJSONObject("right_eyebrow_lower_middle").getIntValue("x"));
        point50.setY(subJsonLandmark.getJSONObject("right_eyebrow_lower_middle").getIntValue("y"));
        faceLandmark.setRight_eyebrow_lower_middle(point50);

        FaceLandmark.Point point51 = new FaceLandmark.Point();
        point51.setX(subJsonLandmark.getJSONObject("right_eyebrow_lower_right_quarter").getIntValue("x"));
        point51.setY(subJsonLandmark.getJSONObject("right_eyebrow_lower_right_quarter").getIntValue("y"));
        faceLandmark.setRight_eyebrow_lower_right_quarter(point51);

        FaceLandmark.Point point52 = new FaceLandmark.Point();
        point52.setX(subJsonLandmark.getJSONObject("right_eyebrow_lower_left_corner").getIntValue("x"));
        point52.setY(subJsonLandmark.getJSONObject("right_eyebrow_lower_left_corner").getIntValue("y"));
        faceLandmark.setRight_eyebrow_lower_left_corner(point52);

        FaceLandmark.Point point53 = new FaceLandmark.Point();
        point53.setX(subJsonLandmark.getJSONObject("right_eyebrow_upper_left_quarter").getIntValue("x"));
        point53.setY(subJsonLandmark.getJSONObject("right_eyebrow_upper_left_quarter").getIntValue("y"));
        faceLandmark.setRight_eyebrow_upper_left_quarter(point53);

        FaceLandmark.Point point54 = new FaceLandmark.Point();
        point54.setX(subJsonLandmark.getJSONObject("right_eyebrow_upper_middle").getIntValue("x"));
        point54.setY(subJsonLandmark.getJSONObject("right_eyebrow_upper_middle").getIntValue("y"));
        faceLandmark.setRight_eyebrow_upper_middle(point54);

        FaceLandmark.Point point55 = new FaceLandmark.Point();
        point55.setX(subJsonLandmark.getJSONObject("right_eyebrow_upper_left_corner").getIntValue("x"));
        point55.setY(subJsonLandmark.getJSONObject("right_eyebrow_upper_left_corner").getIntValue("y"));
        faceLandmark.setRight_eyebrow_upper_left_corner(point55);

        FaceLandmark.Point point56 = new FaceLandmark.Point();
        point56.setX(subJsonLandmark.getJSONObject("right_eyebrow_upper_right_quarter").getIntValue("x"));
        point56.setY(subJsonLandmark.getJSONObject("right_eyebrow_upper_right_quarter").getIntValue("y"));
        faceLandmark.setRight_eyebrow_upper_right_quarter(point56);
        //nosePoint
        face.setLandmark(faceLandmark);
        return face;
    }
    //人脸关键点坐标获取
    public Face getFacesInfo(String url){
        String result;
        //api网络请求
        result =  facesDetect(url);

        return getFacePoint(result);
    }

    public Face getFacesInfo(byte[] imageByte){
        String result;
        //api网络请求
        result =  facesDetect(imageByte);
        System.out.println(result);
        return getFacePoint(result);
    }
}