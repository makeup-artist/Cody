package com.example.cody.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.megvii.facepp.api.bean.Face;

import java.math.BigInteger;


/*
 * @auther:peanut
 * @finish time:2019.4.18
 * notation:人脸妆扮处理集
 */
public class FaceMakeUpUtils{
    private int WidthScale = 960;
    private int HeightScale = 960;
    private Face face;
    private Context mContext;
    private int a;
    private int r;
    private int g;
    private int b;
    //Getter and Setter
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setARGB(int a,int r,int g,int b){
        setA(a);
        setR(r);
        setB(b);
        setG(g);
    }

    public void setFace(Face f){face = f;}

    public Face getFace(){return face; }
    public FaceMakeUpUtils(Face f){
        face = f;
    }
    public FaceMakeUpUtils(Context c, Face f){
        mContext = c;
        face = f;
    }
    public void mouthMaskTexture(Context context, Bitmap bitmap){
        if(face==null)
            return ;
        //取出人脸关键点的坐标数据
        float lipPoints[] = {
                face.getLandmark().getMouth_left_corner().getX(),face.getLandmark().getMouth_left_corner().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour2().getX(),face.getLandmark().getMouth_upper_lip_left_contour2().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour1().getX(),face.getLandmark().getMouth_upper_lip_left_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_top().getX(),face.getLandmark().getMouth_upper_lip_top().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour1().getX(),face.getLandmark().getMouth_upper_lip_right_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour2().getX(),face.getLandmark().getMouth_upper_lip_right_contour2().getY(),
                face.getLandmark().getMouth_right_corner().getX(),face.getLandmark().getMouth_right_corner().getY(),
                face.getLandmark().getMouth_lower_lip_right_contour2().getX(),face.getLandmark().getMouth_lower_lip_right_contour2().getY(),
                face.getLandmark().getMouth_lower_lip_right_contour3().getX(),face.getLandmark().getMouth_lower_lip_right_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_bottom().getX(),face.getLandmark().getMouth_lower_lip_bottom().getY(),
                face.getLandmark().getMouth_lower_lip_left_contour3().getX(),face.getLandmark().getMouth_lower_lip_left_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_left_contour2().getX(),face.getLandmark().getMouth_lower_lip_left_contour2().getY(),
                face.getLandmark().getMouth_lower_lip_left_contour1().getX(),face.getLandmark().getMouth_lower_lip_left_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour3().getX(),face.getLandmark().getMouth_upper_lip_left_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_top().getX(),face.getLandmark().getMouth_lower_lip_top().getY(),
                face.getLandmark().getMouth_upper_lip_bottom().getX(),face.getLandmark().getMouth_upper_lip_bottom().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour3().getX(),face.getLandmark().getMouth_upper_lip_right_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_right_contour1().getX(),face.getLandmark().getMouth_lower_lip_right_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour4().getX(),face.getLandmark().getMouth_upper_lip_left_contour4().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour4().getX(),face.getLandmark().getMouth_upper_lip_right_contour4().getY()
        };
    }
    //人脸关键部位上色：眉型效果
    public Bitmap eyebrowRendering(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setARGB(a,r,g,b);
//        Bitmap eyeBmL = BitmapFactory.decodeResource(mResources,R.drawable.leyebrow);
//        Bitmap eyeBmR = BitmapFactory.decodeResource(mResources,R.drawable.reyebrow);
        //左眉毛点集
        float lpoints[] = {
                face.getLandmark().getLeft_eyebrow_left_corner().getX(),face.getLandmark().getLeft_eyebrow_left_corner().getY(),
                face.getLandmark().getLeft_eyebrow_upper_left_quarter().getX(),face.getLandmark().getLeft_eyebrow_upper_left_quarter().getY(),
                face.getLandmark().getLeft_eyebrow_upper_middle().getX(),face.getLandmark().getLeft_eyebrow_upper_middle().getY(),
                face.getLandmark().getLeft_eyebrow_upper_right_quarter().getX(),face.getLandmark().getLeft_eyebrow_upper_right_quarter().getY(),
                face.getLandmark().getLeft_eyebrow_upper_right_corner().getX(),face.getLandmark().getLeft_eyebrow_upper_right_corner().getY(),
                face.getLandmark().getLeft_eyebrow_lower_right_corner().getX(),face.getLandmark().getLeft_eyebrow_lower_right_corner().getY(),
                face.getLandmark().getLeft_eyebrow_lower_right_quarter().getX(),face.getLandmark().getLeft_eyebrow_lower_right_quarter().getY(),
                face.getLandmark().getLeft_eyebrow_lower_middle().getX(),face.getLandmark().getLeft_eyebrow_lower_middle().getY(),
                face.getLandmark().getLeft_eyebrow_lower_left_quarter().getX(),face.getLandmark().getLeft_eyebrow_lower_left_quarter().getY()
        };
        //右眉毛点集
        float rpoints[] = {
                face.getLandmark().getRight_eyebrow_right_corner().getX(),face.getLandmark().getRight_eyebrow_right_corner().getY(),
                face.getLandmark().getRight_eyebrow_upper_right_quarter().getX(),face.getLandmark().getRight_eyebrow_upper_right_quarter().getY(),
                face.getLandmark().getRight_eyebrow_upper_middle().getX(),face.getLandmark().getRight_eyebrow_upper_middle().getY(),
                face.getLandmark().getRight_eyebrow_upper_left_quarter().getX(),face.getLandmark().getRight_eyebrow_upper_left_quarter().getY(),
                face.getLandmark().getRight_eyebrow_upper_left_corner().getX(),face.getLandmark().getRight_eyebrow_upper_left_corner().getY(),
                face.getLandmark().getRight_eyebrow_lower_left_corner().getX(),face.getLandmark().getRight_eyebrow_lower_left_corner().getY(),
                face.getLandmark().getRight_eyebrow_lower_left_quarter().getX(),face.getLandmark().getRight_eyebrow_lower_left_quarter().getY(),
                face.getLandmark().getRight_eyebrow_lower_middle().getX(),face.getLandmark().getRight_eyebrow_lower_middle().getY(),
                face.getLandmark().getRight_eyebrow_lower_right_quarter().getX(),face.getLandmark().getRight_eyebrow_lower_right_quarter().getY()
        };
        //像素适应
        for (int i=0;i<17;i=i+2){
            lpoints[i] = (lpoints[i])*bm.getWidth()/WidthScale;
            lpoints[i+1] = (lpoints[i+1])*bm.getHeight()/HeightScale;
            rpoints[i] = (rpoints[i])*bm.getWidth()/WidthScale;
            rpoints[i+1] = (rpoints[i+1])*bm.getHeight()/HeightScale;
        }
        //眉型缩放
        for(int i=2;i<7;i=i+2){
            lpoints[17-i+2] = lpoints[17-i+2] + (lpoints[i+1]-lpoints[17-i+2])/3;
            rpoints[17-i+2] = rpoints[17-i+2] + (rpoints[i+1]-rpoints[17-i+2])/3;
        }
        Path rpath = new Path();
        rpath.moveTo(rpoints[0],rpoints[1]);
        for (int i=2;i<17;i=i+2){
            rpath.lineTo(rpoints[i],rpoints[i+1]);
        }
        Path lpath = new Path();
        lpath.moveTo(lpoints[0],lpoints[1]);
        for (int i=2;i<17;i=i+2){
            lpath.lineTo(lpoints[i],lpoints[i+1]);
        }
        canvas.drawPath(rpath,paint);
        canvas.drawPath(lpath,paint);
        return bmp;
    }
    //人脸关键部位勾勒：eyebrow
    public Bitmap drawEyebrowPoint(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        //画布和笔
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);//笔色
        paint.setStrokeWidth(5);//笔宽
        //左眉毛点集
        float lpoints[] = {
                face.getLandmark().getLeft_eyebrow_left_corner().getX(),face.getLandmark().getLeft_eyebrow_left_corner().getY(),
                face.getLandmark().getLeft_eyebrow_upper_left_quarter().getX(),face.getLandmark().getLeft_eyebrow_upper_left_quarter().getY(),
                face.getLandmark().getLeft_eyebrow_upper_middle().getX(),face.getLandmark().getLeft_eyebrow_upper_middle().getY(),
                face.getLandmark().getLeft_eyebrow_upper_right_quarter().getX(),face.getLandmark().getLeft_eyebrow_upper_right_quarter().getY(),
                face.getLandmark().getLeft_eyebrow_upper_right_corner().getX(),face.getLandmark().getLeft_eyebrow_upper_right_corner().getY(),
                face.getLandmark().getLeft_eyebrow_lower_right_corner().getX(),face.getLandmark().getLeft_eyebrow_lower_right_corner().getY(),
                face.getLandmark().getLeft_eyebrow_lower_right_quarter().getX(),face.getLandmark().getLeft_eyebrow_lower_right_quarter().getY(),
                face.getLandmark().getLeft_eyebrow_lower_middle().getX(),face.getLandmark().getLeft_eyebrow_lower_middle().getY(),
                face.getLandmark().getLeft_eyebrow_lower_left_quarter().getX(),face.getLandmark().getLeft_eyebrow_lower_left_quarter().getY()
        };
        //右眉毛点集
        float rpoints[] = {
                face.getLandmark().getRight_eyebrow_right_corner().getX(),face.getLandmark().getRight_eyebrow_right_corner().getY(),
                face.getLandmark().getRight_eyebrow_upper_right_quarter().getX(),face.getLandmark().getRight_eyebrow_upper_right_quarter().getY(),
                face.getLandmark().getRight_eyebrow_upper_middle().getX(),face.getLandmark().getRight_eyebrow_upper_middle().getY(),
                face.getLandmark().getRight_eyebrow_upper_left_quarter().getX(),face.getLandmark().getRight_eyebrow_upper_left_quarter().getY(),
                face.getLandmark().getRight_eyebrow_upper_left_corner().getX(),face.getLandmark().getRight_eyebrow_upper_left_corner().getY(),
                face.getLandmark().getRight_eyebrow_lower_left_corner().getX(),face.getLandmark().getRight_eyebrow_lower_left_corner().getY(),
                face.getLandmark().getRight_eyebrow_lower_left_quarter().getX(),face.getLandmark().getRight_eyebrow_lower_left_quarter().getY(),
                face.getLandmark().getRight_eyebrow_lower_middle().getX(),face.getLandmark().getRight_eyebrow_lower_middle().getY(),
                face.getLandmark().getRight_eyebrow_lower_right_quarter().getX(),face.getLandmark().getRight_eyebrow_lower_right_quarter().getY()
        };
        //像素适应
        for (int i=0;i<17;i=i+2){
            lpoints[i] = (lpoints[i])*bm.getWidth()/WidthScale;
            lpoints[i+1] = (lpoints[i+1])*bm.getHeight()/HeightScale;
            rpoints[i] = (rpoints[i])*bm.getWidth()/WidthScale;
            rpoints[i+1] = (rpoints[i+1])*bm.getHeight()/HeightScale;
        }
        for(int i=2;i<7;i=i+2){
            lpoints[17-i+2] = lpoints[17-i+2] + (lpoints[i+1]-lpoints[17-i+2])/3;
            rpoints[17-i+2] = rpoints[17-i+2] + (rpoints[i+1]-rpoints[17-i+2])/3;
        }
        //描点
        canvas.drawPoints(lpoints,paint);
        canvas.drawPoints(rpoints,paint);
        //连线
        paint.setStrokeWidth(1);//笔宽 线宽
        for(int i=0;i<15;i+=2){
            canvas.drawLine(lpoints[i],lpoints[i+1],lpoints[i+2],lpoints[i+3],paint);
            canvas.drawLine(rpoints[i],rpoints[i+1],rpoints[i+2],rpoints[i+3],paint);
        }
        canvas.drawLine(lpoints[0],lpoints[1],lpoints[16],lpoints[17],paint);
        canvas.drawLine(rpoints[0],rpoints[1],rpoints[16],rpoints[17],paint);
        return bmp;
    }
    //人脸关键部位上色：眼影效果
    public Bitmap eyeRendering(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bmp);
        //coding......
        return bmp;
    }
    //人脸关键部位勾勒：眼睛
    public Bitmap drawEyePoint(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        //画布和笔
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);//笔色
        paint.setStrokeWidth(5);//笔宽
        //左眼点集
        float lpoints[] = {
                face.getLandmark().getLeft_eye_left_corner().getX(),face.getLandmark().getLeft_eye_left_corner().getY(),
                face.getLandmark().getLeft_eye_upper_left_quarter().getX(),face.getLandmark().getLeft_eye_upper_left_quarter().getY(),
                face.getLandmark().getLeft_eye_top().getX(),face.getLandmark().getLeft_eye_top().getY(),
                face.getLandmark().getLeft_eye_upper_right_quarter().getX(),face.getLandmark().getLeft_eye_upper_right_quarter().getY(),
                face.getLandmark().getLeft_eye_right_corner().getX(),face.getLandmark().getLeft_eye_right_corner().getY(),
                face.getLandmark().getLeft_eye_lower_right_quarter().getX(),face.getLandmark().getLeft_eye_lower_right_quarter().getY(),
                face.getLandmark().getLeft_eye_bottom().getX(),face.getLandmark().getLeft_eye_bottom().getY(),
                face.getLandmark().getLeft_eye_lower_left_quarter().getX(),face.getLandmark().getLeft_eye_lower_left_quarter().getY()
        };
        //右眼点集
        float rpoints[] = {
                face.getLandmark().getRight_eye_left_corner().getX(),face.getLandmark().getRight_eye_left_corner().getY(),
                face.getLandmark().getRight_eye_upper_left_quarter().getX(),face.getLandmark().getRight_eye_upper_left_quarter().getY(),
                face.getLandmark().getRight_eye_top().getX(),face.getLandmark().getRight_eye_top().getY(),
                face.getLandmark().getRight_eye_upper_right_quarter().getX(),face.getLandmark().getRight_eye_upper_right_quarter().getY(),
                face.getLandmark().getRight_eye_right_corner().getX(),face.getLandmark().getRight_eye_right_corner().getY(),
                face.getLandmark().getRight_eye_lower_right_quarter().getX(),face.getLandmark().getRight_eye_lower_right_quarter().getY(),
                face.getLandmark().getRight_eye_bottom().getX(),face.getLandmark().getRight_eye_bottom().getY(),
                face.getLandmark().getRight_eye_lower_left_quarter().getX(),face.getLandmark().getRight_eye_lower_left_quarter().getY()
        };
        //像素适应
        for (int i=0;i<15;i=i+2){
            lpoints[i] = (lpoints[i])*bm.getWidth()/WidthScale;
            lpoints[i+1] = (lpoints[i+1])*bm.getHeight()/HeightScale;
            rpoints[i] = (rpoints[i])*bm.getWidth()/WidthScale;
            rpoints[i+1] = (rpoints[i+1])*bm.getHeight()/HeightScale;
        }
        //描点
        canvas.drawPoints(lpoints,paint);
        canvas.drawPoints(rpoints,paint);
        //连线
        paint.setStrokeWidth(1);//笔宽 线宽
        for(int i=0;i<13;i+=2){
            canvas.drawLine(lpoints[i],lpoints[i+1],lpoints[i+2],lpoints[i+3],paint);
            canvas.drawLine(rpoints[i],rpoints[i+1],rpoints[i+2],rpoints[i+3],paint);
        }
        canvas.drawLine(lpoints[0],lpoints[1],lpoints[14],lpoints[15],paint);
        canvas.drawLine(rpoints[0],rpoints[1],rpoints[14],rpoints[15],paint);
        return bmp;
    }

    //人脸关键部位上色：口红效果
    public Bitmap mouthRendering(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(20);//笔宽
        //上色_上嘴唇
        Path patha = new Path();
        paint.setARGB(a,r,g,b);

        patha.moveTo(face.getLandmark().getMouth_left_corner().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_left_corner().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_left_contour2().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_left_contour2().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_left_contour1().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_left_contour1().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_top().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_top().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_right_contour1().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_right_contour1().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_right_contour2().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_right_contour2().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_right_corner().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_right_corner().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_right_contour3().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_right_contour3().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_bottom().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_bottom().getY()*bm.getHeight()/HeightScale);
        patha.lineTo(face.getLandmark().getMouth_upper_lip_left_contour3().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_upper_lip_left_contour3().getY()*bm.getHeight()/HeightScale);
        patha.close();
        canvas.drawPath(patha,paint);
        //下嘴唇
        Path pathb = new Path();
        pathb.moveTo(face.getLandmark().getMouth_left_corner().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_left_corner().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_lower_lip_left_contour1().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_left_contour1().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_lower_lip_top().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_top().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_lower_lip_right_contour1().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_right_contour1().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_right_corner().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_right_corner().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_lower_lip_right_contour2().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_right_contour2().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo( face.getLandmark().getMouth_lower_lip_right_contour3().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_right_contour3().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_lower_lip_bottom().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_bottom().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_lower_lip_left_contour3().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_left_contour3().getY()*bm.getHeight()/HeightScale);
        pathb.lineTo(face.getLandmark().getMouth_lower_lip_left_contour2().getX()*bm.getWidth()/WidthScale,face.getLandmark().getMouth_lower_lip_left_contour2().getY()*bm.getHeight()/HeightScale);
        pathb.close();
        canvas.drawPath(pathb,paint);
        return bmp;
    }
    //人脸关键部位勾勒：嘴唇
    public Bitmap drawMouthPoint(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        //画布和笔
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);//笔色
        paint.setStrokeWidth(5);//笔宽
        //取出人脸关键点的坐标数据
        float point[] = {
                face.getLandmark().getMouth_left_corner().getX(),face.getLandmark().getMouth_left_corner().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour2().getX(),face.getLandmark().getMouth_upper_lip_left_contour2().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour1().getX(),face.getLandmark().getMouth_upper_lip_left_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_top().getX(),face.getLandmark().getMouth_upper_lip_top().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour1().getX(),face.getLandmark().getMouth_upper_lip_right_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour2().getX(),face.getLandmark().getMouth_upper_lip_right_contour2().getY(),
                face.getLandmark().getMouth_right_corner().getX(),face.getLandmark().getMouth_right_corner().getY(),
                face.getLandmark().getMouth_lower_lip_right_contour2().getX(),face.getLandmark().getMouth_lower_lip_right_contour2().getY(),
                face.getLandmark().getMouth_lower_lip_right_contour3().getX(),face.getLandmark().getMouth_lower_lip_right_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_bottom().getX(),face.getLandmark().getMouth_lower_lip_bottom().getY(),
                face.getLandmark().getMouth_lower_lip_left_contour3().getX(),face.getLandmark().getMouth_lower_lip_left_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_left_contour2().getX(),face.getLandmark().getMouth_lower_lip_left_contour2().getY(),
                face.getLandmark().getMouth_lower_lip_left_contour1().getX(),face.getLandmark().getMouth_lower_lip_left_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour3().getX(),face.getLandmark().getMouth_upper_lip_left_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_top().getX(),face.getLandmark().getMouth_lower_lip_top().getY(),
                face.getLandmark().getMouth_upper_lip_bottom().getX(),face.getLandmark().getMouth_upper_lip_bottom().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour3().getX(),face.getLandmark().getMouth_upper_lip_right_contour3().getY(),
                face.getLandmark().getMouth_lower_lip_right_contour1().getX(),face.getLandmark().getMouth_lower_lip_right_contour1().getY(),
                face.getLandmark().getMouth_upper_lip_left_contour4().getX(),face.getLandmark().getMouth_upper_lip_left_contour4().getY(),
                face.getLandmark().getMouth_upper_lip_right_contour4().getX(),face.getLandmark().getMouth_upper_lip_right_contour4().getY()
        };
        //根据像素进行Bitmap的大小适应
        for (int i=0;i<36;i=i+2){
            point[i] = (point[i])*bm.getWidth()/WidthScale;
            point[i+1] = (point[i+1])*bm.getHeight()/HeightScale;
            System.out.println(point[i]);
        }
        //描点
        canvas.drawPoints(point,paint);
        paint.setStrokeWidth(1);
        //连线
        for(int i=0;i<33;i+=2){
            if(i!=22)
                canvas.drawLine(point[i],point[i+1],point[i+2],point[i+3],paint);
        }
        canvas.drawLine(point[0],point[1],point[24],point[25],paint);
        canvas.drawLine(point[22],point[23],point[0],point[1],paint);
        canvas.drawLine(point[34],point[35],point[12],point[13],paint);
        return bmp;
    }

    //人脸关键部位勾勒：脸型轮廓
    public Bitmap drawFaceCounter(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);//笔色
        paint.setStrokeWidth(5);//笔宽
        //取出人脸关键点的坐标数据
        float points[] = {
                face.getLandmark().getContour_left1().getX(),face.getLandmark().getContour_left1().getY(),
                face.getLandmark().getContour_left2().getX(),face.getLandmark().getContour_left2().getY(),
                face.getLandmark().getContour_left3().getX(),face.getLandmark().getContour_left3().getY(),
                face.getLandmark().getContour_left4().getX(),face.getLandmark().getContour_left4().getY(),
                face.getLandmark().getContour_left5().getX(),face.getLandmark().getContour_left5().getY(),
                face.getLandmark().getContour_left6().getX(),face.getLandmark().getContour_left6().getY(),
                face.getLandmark().getContour_left7().getX(),face.getLandmark().getContour_left7().getY(),
                face.getLandmark().getContour_left8().getX(),face.getLandmark().getContour_left8().getY(),
                face.getLandmark().getContour_left9().getX(),face.getLandmark().getContour_left9().getY(),
                face.getLandmark().getContour_left10().getX(),face.getLandmark().getContour_left10().getY(),
                face.getLandmark().getContour_left11().getX(),face.getLandmark().getContour_left11().getY(),
                face.getLandmark().getContour_left12().getX(),face.getLandmark().getContour_left12().getY(),
                face.getLandmark().getContour_left13().getX(),face.getLandmark().getContour_left13().getY(),
                face.getLandmark().getContour_left14().getX(),face.getLandmark().getContour_left14().getY(),
                face.getLandmark().getContour_left15().getX(),face.getLandmark().getContour_left15().getY(),
                face.getLandmark().getContour_left16().getX(),face.getLandmark().getContour_left16().getY(),
                face.getLandmark().getContour_chin().getX(),face.getLandmark().getContour_chin().getY(),
                face.getLandmark().getContour_right16().getX(),face.getLandmark().getContour_right16().getY(),
                face.getLandmark().getContour_right15().getX(),face.getLandmark().getContour_right15().getY(),
                face.getLandmark().getContour_right14().getX(),face.getLandmark().getContour_right14().getY(),
                face.getLandmark().getContour_right13().getX(),face.getLandmark().getContour_right13().getY(),
                face.getLandmark().getContour_right12().getX(),face.getLandmark().getContour_right12().getY(),
                face.getLandmark().getContour_right11().getX(),face.getLandmark().getContour_right11().getY(),
                face.getLandmark().getContour_right10().getX(),face.getLandmark().getContour_right10().getY(),
                face.getLandmark().getContour_right9().getX(),face.getLandmark().getContour_right9().getY(),
                face.getLandmark().getContour_right8().getX(),face.getLandmark().getContour_right8().getY(),
                face.getLandmark().getContour_right7().getX(),face.getLandmark().getContour_right7().getY(),
                face.getLandmark().getContour_right6().getX(),face.getLandmark().getContour_right6().getY(),
                face.getLandmark().getContour_right5().getX(),face.getLandmark().getContour_right5().getY(),
                face.getLandmark().getContour_right4().getX(),face.getLandmark().getContour_right4().getY(),
                face.getLandmark().getContour_right3().getX(),face.getLandmark().getContour_right3().getY(),
                face.getLandmark().getContour_right2().getX(),face.getLandmark().getContour_right2().getY(),
                face.getLandmark().getContour_right1().getX(),face.getLandmark().getContour_right1().getY()
        };
        //像素自适应
        for(int i=0;i<66;i=i+2){
            points[i] = points[i]*bm.getWidth()/WidthScale;
            points[i+1] = points[i+1]*bm.getHeight()/HeightScale;
        }
        //描点
        canvas.drawPoints(points,paint);
        //连线
        paint.setStrokeWidth(1);//笔宽 线宽
        for(int i=0;i<64;i=i+2){
            canvas.drawLine(points[i],points[i+1],points[i+2],points[i+3],paint);
        }
        return bmp;
    }

    //人脸关键部位勾勒：鼻子
    public Bitmap drawNosePoint(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);//笔色
        paint.setStrokeWidth(5);//笔宽
        //取出人脸关键点的坐标数据
        float points[] = {
                face.getLandmark().getNose_bridge1().getX(),face.getLandmark().getNose_bridge1().getY(),
                face.getLandmark().getNose_left_contour1().getX(),face.getLandmark().getNose_left_contour1().getY(),
                face.getLandmark().getNose_left_contour2().getX(),face.getLandmark().getNose_left_contour2().getY(),
                face.getLandmark().getNose_left_contour3().getX(),face.getLandmark().getNose_left_contour3().getY(),
                face.getLandmark().getNose_left_contour4().getX(),face.getLandmark().getNose_left_contour4().getY(),
                face.getLandmark().getNose_left_contour5().getX(),face.getLandmark().getNose_left_contour5().getY(),
                face.getLandmark().getNose_middle_contour().getX(),face.getLandmark().getNose_middle_contour().getY(),
                face.getLandmark().getNose_right_contour5().getX(),face.getLandmark().getNose_right_contour5().getY(),
                face.getLandmark().getNose_right_contour4().getX(),face.getLandmark().getNose_right_contour4().getY(),
                face.getLandmark().getNose_right_contour3().getX(),face.getLandmark().getNose_right_contour3().getY(),
                face.getLandmark().getNose_right_contour2().getX(),face.getLandmark().getNose_right_contour2().getY(),
                face.getLandmark().getNose_right_contour1().getX(),face.getLandmark().getNose_right_contour1().getY()
        };
        //像素自适应
        for(int i=0;i<24;i=i+2){
            points[i] = points[i]*bm.getWidth()/WidthScale;
            points[i+1] = points[i+1]*bm.getHeight()/HeightScale;
        }
        //描点
        canvas.drawPoints(points,paint);
        //连线
        paint.setStrokeWidth(1);//笔宽 线宽
        for(int i=0;i<22;i=i+2){
            canvas.drawLine(points[i],points[i+1],points[i+2],points[i+3],paint);
        }
        canvas.drawLine(points[0],points[1],points[22],points[23],paint);
        return bmp;
    }

    //facedetect
    public Bitmap drawFaceRect(Bitmap bm){
        Bitmap bmp = bm.copy(Bitmap.Config.ARGB_8888, true);
        if(face==null)
            return bm;
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);//笔色
        paint.setStrokeWidth(5);//笔宽
        //取出人脸关键点的坐标数据
        float top = face.getFace_rectangle().getTop()*bm.getWidth()/WidthScale;
        float left = face.getFace_rectangle().getLeft()*bm.getHeight()/HeightScale;
        float width = face.getFace_rectangle().getWidth()*bm.getWidth()/WidthScale;
        float height = face.getFace_rectangle().getHeight()*bm.getHeight()/HeightScale;
        //画出人脸检测矩形框
        canvas.drawLine(left,top,left+width,top,paint);
        canvas.drawLine(left,top,left,top-height,paint);
        canvas.drawLine(left+width,top,left+width,top-height,paint);
        canvas.drawLine(left,top-height,left+width,top-height,paint);
        return bmp;
    }
}
