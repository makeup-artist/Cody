package com.example.cody.utils;

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
public class FacesUtils {
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

        FaceLandmark.Point point105 = new FaceLandmark.Point();
        point105.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour4").getIntValue("x"));
        point105.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_left_contour4").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_left_contour4(point105);

        FaceLandmark.Point point106 = new FaceLandmark.Point();
        point106.setX(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour4").getIntValue("x"));
        point106.setY(subJsonLandmark.getJSONObject("mouth_upper_lip_right_contour4").getIntValue("y"));
        faceLandmark.setMouth_upper_lip_right_contour4(point106);

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
        FaceLandmark.Point point57 = new FaceLandmark.Point();
        point57.setX(subJsonLandmark.getJSONObject("nose_bridge1").getIntValue("x"));
        point57.setY(subJsonLandmark.getJSONObject("nose_bridge1").getIntValue("y"));
        faceLandmark.setNose_bridge1(point57);

        FaceLandmark.Point point58 = new FaceLandmark.Point();
        point58.setX(subJsonLandmark.getJSONObject("nose_bridge2").getIntValue("x"));
        point58.setY(subJsonLandmark.getJSONObject("nose_bridge2").getIntValue("y"));
        faceLandmark.setNose_bridge2(point58);

        FaceLandmark.Point point59 = new FaceLandmark.Point();
        point59.setX(subJsonLandmark.getJSONObject("nose_bridge3").getIntValue("x"));
        point59.setY(subJsonLandmark.getJSONObject("nose_bridge3").getIntValue("y"));
        faceLandmark.setNose_bridge3(point59);

        FaceLandmark.Point point60 = new FaceLandmark.Point();
        point60.setX(subJsonLandmark.getJSONObject("nose_tip").getIntValue("x"));
        point60.setY(subJsonLandmark.getJSONObject("nose_tip").getIntValue("y"));
        faceLandmark.setNose_tip(point60);

        FaceLandmark.Point point61 = new FaceLandmark.Point();
        point61.setX(subJsonLandmark.getJSONObject("nose_left_contour1").getIntValue("x"));
        point61.setY(subJsonLandmark.getJSONObject("nose_left_contour1").getIntValue("y"));
        faceLandmark.setNose_left_contour1(point61);

        FaceLandmark.Point point62 = new FaceLandmark.Point();
        point62.setX(subJsonLandmark.getJSONObject("nose_left_contour2").getIntValue("x"));
        point62.setY(subJsonLandmark.getJSONObject("nose_left_contour2").getIntValue("y"));
        faceLandmark.setNose_left_contour2(point62);

        FaceLandmark.Point point63 = new FaceLandmark.Point();
        point63.setX(subJsonLandmark.getJSONObject("nose_left_contour3").getIntValue("x"));
        point63.setY(subJsonLandmark.getJSONObject("nose_left_contour3").getIntValue("y"));
        faceLandmark.setNose_left_contour3(point63);

        FaceLandmark.Point point64 = new FaceLandmark.Point();
        point64.setX(subJsonLandmark.getJSONObject("nose_left_contour4").getIntValue("x"));
        point64.setY(subJsonLandmark.getJSONObject("nose_left_contour4").getIntValue("y"));
        faceLandmark.setNose_left_contour4(point64);

        FaceLandmark.Point point65 = new FaceLandmark.Point();
        point65.setX(subJsonLandmark.getJSONObject("nose_left_contour5").getIntValue("x"));
        point65.setY(subJsonLandmark.getJSONObject("nose_left_contour5").getIntValue("y"));
        faceLandmark.setNose_left_contour5(point65);

        FaceLandmark.Point point66 = new FaceLandmark.Point();
        point66.setX(subJsonLandmark.getJSONObject("nose_right_contour1").getIntValue("x"));
        point66.setY(subJsonLandmark.getJSONObject("nose_right_contour1").getIntValue("y"));
        faceLandmark.setNose_right_contour1(point66);

        FaceLandmark.Point point67 = new FaceLandmark.Point();
        point67.setX(subJsonLandmark.getJSONObject("nose_right_contour2").getIntValue("x"));
        point67.setY(subJsonLandmark.getJSONObject("nose_right_contour2").getIntValue("y"));
        faceLandmark.setNose_right_contour2(point67);

        FaceLandmark.Point point68 = new FaceLandmark.Point();
        point68.setX(subJsonLandmark.getJSONObject("nose_right_contour3").getIntValue("x"));
        point68.setY(subJsonLandmark.getJSONObject("nose_right_contour3").getIntValue("y"));
        faceLandmark.setNose_right_contour3(point68);

        FaceLandmark.Point point69 = new FaceLandmark.Point();
        point69.setX(subJsonLandmark.getJSONObject("nose_right_contour4").getIntValue("x"));
        point69.setY(subJsonLandmark.getJSONObject("nose_right_contour4").getIntValue("y"));
        faceLandmark.setNose_right_contour4(point69);

        FaceLandmark.Point point70 = new FaceLandmark.Point();
        point70.setX(subJsonLandmark.getJSONObject("nose_right_contour5").getIntValue("x"));
        point70.setY(subJsonLandmark.getJSONObject("nose_right_contour5").getIntValue("y"));
        faceLandmark.setNose_right_contour5(point70);

        FaceLandmark.Point point71 = new FaceLandmark.Point();
        point71.setX(subJsonLandmark.getJSONObject("nose_middle_contour").getIntValue("x"));
        point71.setY(subJsonLandmark.getJSONObject("nose_middle_contour").getIntValue("y"));
        faceLandmark.setNose_middle_contour(point71);

        //faceContour
        FaceLandmark.Point point72 = new FaceLandmark.Point();
        point72.setX(subJsonLandmark.getJSONObject("contour_left1").getIntValue("x"));
        point72.setY(subJsonLandmark.getJSONObject("contour_left1").getIntValue("y"));
        faceLandmark.setContour_left1(point72);

        FaceLandmark.Point point73 = new FaceLandmark.Point();
        point73.setX(subJsonLandmark.getJSONObject("contour_left2").getIntValue("x"));
        point73.setY(subJsonLandmark.getJSONObject("contour_left2").getIntValue("y"));
        faceLandmark.setContour_left2(point73);

        FaceLandmark.Point point74 = new FaceLandmark.Point();
        point74.setX(subJsonLandmark.getJSONObject("contour_left3").getIntValue("x"));
        point74.setY(subJsonLandmark.getJSONObject("contour_left3").getIntValue("y"));
        faceLandmark.setContour_left3(point74);

        FaceLandmark.Point point75 = new FaceLandmark.Point();
        point75.setX(subJsonLandmark.getJSONObject("contour_left4").getIntValue("x"));
        point75.setY(subJsonLandmark.getJSONObject("contour_left4").getIntValue("y"));
        faceLandmark.setContour_left4(point75);

        FaceLandmark.Point point76 = new FaceLandmark.Point();
        point76.setX(subJsonLandmark.getJSONObject("contour_left5").getIntValue("x"));
        point76.setY(subJsonLandmark.getJSONObject("contour_left5").getIntValue("y"));
        faceLandmark.setContour_left5(point76);

        FaceLandmark.Point point77 = new FaceLandmark.Point();
        point77.setX(subJsonLandmark.getJSONObject("contour_left6").getIntValue("x"));
        point77.setY(subJsonLandmark.getJSONObject("contour_left6").getIntValue("y"));
        faceLandmark.setContour_left6(point77);

        FaceLandmark.Point point78 = new FaceLandmark.Point();
        point78.setX(subJsonLandmark.getJSONObject("contour_left7").getIntValue("x"));
        point78.setY(subJsonLandmark.getJSONObject("contour_left7").getIntValue("y"));
        faceLandmark.setContour_left7(point78);

        FaceLandmark.Point point79 = new FaceLandmark.Point();
        point79.setX(subJsonLandmark.getJSONObject("contour_left8").getIntValue("x"));
        point79.setY(subJsonLandmark.getJSONObject("contour_left8").getIntValue("y"));
        faceLandmark.setContour_left8(point79);

        FaceLandmark.Point point80 = new FaceLandmark.Point();
        point80.setX(subJsonLandmark.getJSONObject("contour_left9").getIntValue("x"));
        point80.setY(subJsonLandmark.getJSONObject("contour_left9").getIntValue("y"));
        faceLandmark.setContour_left9(point80);

        FaceLandmark.Point point81 = new FaceLandmark.Point();
        point81.setX(subJsonLandmark.getJSONObject("contour_left10").getIntValue("x"));
        point81.setY(subJsonLandmark.getJSONObject("contour_left10").getIntValue("y"));
        faceLandmark.setContour_left10(point81);

        FaceLandmark.Point point82 = new FaceLandmark.Point();
        point82.setX(subJsonLandmark.getJSONObject("contour_left11").getIntValue("x"));
        point82.setY(subJsonLandmark.getJSONObject("contour_left11").getIntValue("y"));
        faceLandmark.setContour_left11(point82);

        FaceLandmark.Point point83 = new FaceLandmark.Point();
        point83.setX(subJsonLandmark.getJSONObject("contour_left12").getIntValue("x"));
        point83.setY(subJsonLandmark.getJSONObject("contour_left12").getIntValue("y"));
        faceLandmark.setContour_left12(point83);

        FaceLandmark.Point point84 = new FaceLandmark.Point();
        point84.setX(subJsonLandmark.getJSONObject("contour_left13").getIntValue("x"));
        point84.setY(subJsonLandmark.getJSONObject("contour_left13").getIntValue("y"));
        faceLandmark.setContour_left13(point84);

        FaceLandmark.Point point85 = new FaceLandmark.Point();
        point85.setX(subJsonLandmark.getJSONObject("contour_left14").getIntValue("x"));
        point85.setY(subJsonLandmark.getJSONObject("contour_left14").getIntValue("y"));
        faceLandmark.setContour_left14(point85);

        FaceLandmark.Point point86 = new FaceLandmark.Point();
        point86.setX(subJsonLandmark.getJSONObject("contour_left15").getIntValue("x"));
        point86.setY(subJsonLandmark.getJSONObject("contour_left15").getIntValue("y"));
        faceLandmark.setContour_left15(point86);

        FaceLandmark.Point point87 = new FaceLandmark.Point();
        point87.setX(subJsonLandmark.getJSONObject("contour_left16").getIntValue("x"));
        point87.setY(subJsonLandmark.getJSONObject("contour_left16").getIntValue("y"));
        faceLandmark.setContour_left16(point87);

        FaceLandmark.Point point88 = new FaceLandmark.Point();
        point88.setX(subJsonLandmark.getJSONObject("contour_right1").getIntValue("x"));
        point88.setY(subJsonLandmark.getJSONObject("contour_right1").getIntValue("y"));
        faceLandmark.setContour_right1(point88);

        FaceLandmark.Point point89 = new FaceLandmark.Point();
        point89.setX(subJsonLandmark.getJSONObject("contour_right2").getIntValue("x"));
        point89.setY(subJsonLandmark.getJSONObject("contour_right2").getIntValue("y"));
        faceLandmark.setContour_right2(point89);

        FaceLandmark.Point point90 = new FaceLandmark.Point();
        point90.setX(subJsonLandmark.getJSONObject("contour_right3").getIntValue("x"));
        point90.setY(subJsonLandmark.getJSONObject("contour_right3").getIntValue("y"));
        faceLandmark.setContour_right3(point90);

        FaceLandmark.Point point91 = new FaceLandmark.Point();
        point91.setX(subJsonLandmark.getJSONObject("contour_right4").getIntValue("x"));
        point91.setY(subJsonLandmark.getJSONObject("contour_right4").getIntValue("y"));
        faceLandmark.setContour_right4(point91);

        FaceLandmark.Point point92 = new FaceLandmark.Point();
        point92.setX(subJsonLandmark.getJSONObject("contour_right5").getIntValue("x"));
        point92.setY(subJsonLandmark.getJSONObject("contour_right5").getIntValue("y"));
        faceLandmark.setContour_right5(point92);

        FaceLandmark.Point point93 = new FaceLandmark.Point();
        point93.setX(subJsonLandmark.getJSONObject("contour_right6").getIntValue("x"));
        point93.setY(subJsonLandmark.getJSONObject("contour_right6").getIntValue("y"));
        faceLandmark.setContour_right6(point93);

        FaceLandmark.Point point94 = new FaceLandmark.Point();
        point94.setX(subJsonLandmark.getJSONObject("contour_right7").getIntValue("x"));
        point94.setY(subJsonLandmark.getJSONObject("contour_right7").getIntValue("y"));
        faceLandmark.setContour_right7(point94);

        FaceLandmark.Point point95 = new FaceLandmark.Point();
        point95.setX(subJsonLandmark.getJSONObject("contour_right8").getIntValue("x"));
        point95.setY(subJsonLandmark.getJSONObject("contour_right8").getIntValue("y"));
        faceLandmark.setContour_right8(point95);

        FaceLandmark.Point point96 = new FaceLandmark.Point();
        point96.setX(subJsonLandmark.getJSONObject("contour_right9").getIntValue("x"));
        point96.setY(subJsonLandmark.getJSONObject("contour_right9").getIntValue("y"));
        faceLandmark.setContour_right9(point96);

        FaceLandmark.Point point97 = new FaceLandmark.Point();
        point97.setX(subJsonLandmark.getJSONObject("contour_right10").getIntValue("x"));
        point97.setY(subJsonLandmark.getJSONObject("contour_right10").getIntValue("y"));
        faceLandmark.setContour_right10(point97);

        FaceLandmark.Point point98 = new FaceLandmark.Point();
        point98.setX(subJsonLandmark.getJSONObject("contour_right11").getIntValue("x"));
        point98.setY(subJsonLandmark.getJSONObject("contour_right11").getIntValue("y"));
        faceLandmark.setContour_right11(point98);

        FaceLandmark.Point point99 = new FaceLandmark.Point();
        point99.setX(subJsonLandmark.getJSONObject("contour_right12").getIntValue("x"));
        point99.setY(subJsonLandmark.getJSONObject("contour_right12").getIntValue("y"));
        faceLandmark.setContour_right12(point99);

        FaceLandmark.Point point100 = new FaceLandmark.Point();
        point100.setX(subJsonLandmark.getJSONObject("contour_right13").getIntValue("x"));
        point100.setY(subJsonLandmark.getJSONObject("contour_right13").getIntValue("y"));
        faceLandmark.setContour_right13(point100);

        FaceLandmark.Point point101 = new FaceLandmark.Point();
        point101.setX(subJsonLandmark.getJSONObject("contour_right14").getIntValue("x"));
        point101.setY(subJsonLandmark.getJSONObject("contour_right14").getIntValue("y"));
        faceLandmark.setContour_right14(point101);

        FaceLandmark.Point point102 = new FaceLandmark.Point();
        point102.setX(subJsonLandmark.getJSONObject("contour_right15").getIntValue("x"));
        point102.setY(subJsonLandmark.getJSONObject("contour_right15").getIntValue("y"));
        faceLandmark.setContour_right15(point102);

        FaceLandmark.Point point103 = new FaceLandmark.Point();
        point103.setX(subJsonLandmark.getJSONObject("contour_right16").getIntValue("x"));
        point103.setY(subJsonLandmark.getJSONObject("contour_right16").getIntValue("y"));
        faceLandmark.setContour_right16(point103);

        FaceLandmark.Point point104 = new FaceLandmark.Point();
        point104.setX(subJsonLandmark.getJSONObject("contour_chin").getIntValue("x"));
        point104.setY(subJsonLandmark.getJSONObject("contour_chin").getIntValue("y"));
        faceLandmark.setContour_chin(point104);
        //数据读入
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