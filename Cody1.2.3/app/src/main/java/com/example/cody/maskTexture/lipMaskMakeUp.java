//package com.example.cody.maskTexture;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.opengl.GLES20;
//import android.opengl.GLSurfaceView;
//import android.opengl.GLUtils;
//
//import com.megvii.facepp.api.bean.Face;
//
//import javax.microedition.khronos.opengles.GL10;
//
//public class lipMaskMakeUp extends GLSurfaceView {
//    private static final String vertexMatrixShaderCode =
//            "attribute vec4 vPosition;\n" +
//                    "attribute vec2 vCoordinate;\n" +
//                    "varying vec2 aCoordinate;\n" +
//                    "void main(){\n" +
//                    "    gl_Position=vPosition;\n" +
//                    "    aCoordinate=vCoordinate;\n" +
//                    "}";
//
//
//    private static final String fragmentShaderCode =
//            "precision mediump float;\n" +
//                    "uniform sampler2D vTexture;\n" +
//                    "varying vec2 aCoordinate;\n" +
//                    "void main(){\n" +
//                    "    gl_FragColor=texture2D(vTexture,aCoordinate);\n" +
//                    "}";
//
//    private float []mVertices;
//    private float []mVerticesMask;
//    private short []index;
//    private Bitmap mBitmap;
//    private Face face;
//    public lipMaskMakeUp(Context context) {
//        super(context);
//    }
//    public lipMaskMakeUp(Context context,Bitmap bm,Face fa){
//        super(context);
//        mBitmap = bm;
//        face = fa;
//    }
//    private void updateVertices(){
//        float Points[] = {
//                face.getLandmark().getMouth_left_corner().getX(),face.getLandmark().getMouth_left_corner().getY(),
//                face.getLandmark().getMouth_upper_lip_left_contour2().getX(),face.getLandmark().getMouth_upper_lip_left_contour2().getY(),
//                face.getLandmark().getMouth_upper_lip_left_contour1().getX(),face.getLandmark().getMouth_upper_lip_left_contour1().getY(),
//                face.getLandmark().getMouth_upper_lip_top().getX(),face.getLandmark().getMouth_upper_lip_top().getY(),
//                face.getLandmark().getMouth_upper_lip_right_contour1().getX(),face.getLandmark().getMouth_upper_lip_right_contour1().getY(),
//                face.getLandmark().getMouth_upper_lip_right_contour2().getX(),face.getLandmark().getMouth_upper_lip_right_contour2().getY(),
//                face.getLandmark().getMouth_right_corner().getX(),face.getLandmark().getMouth_right_corner().getY(),
//                face.getLandmark().getMouth_lower_lip_right_contour2().getX(),face.getLandmark().getMouth_lower_lip_right_contour2().getY(),
//                face.getLandmark().getMouth_lower_lip_right_contour3().getX(),face.getLandmark().getMouth_lower_lip_right_contour3().getY(),
//                face.getLandmark().getMouth_lower_lip_bottom().getX(),face.getLandmark().getMouth_lower_lip_bottom().getY(),
//                face.getLandmark().getMouth_lower_lip_left_contour3().getX(),face.getLandmark().getMouth_lower_lip_left_contour3().getY(),
//                face.getLandmark().getMouth_lower_lip_left_contour2().getX(),face.getLandmark().getMouth_lower_lip_left_contour2().getY(),
//                face.getLandmark().getMouth_lower_lip_left_contour1().getX(),face.getLandmark().getMouth_lower_lip_left_contour1().getY(),
//                face.getLandmark().getMouth_upper_lip_left_contour3().getX(),face.getLandmark().getMouth_upper_lip_left_contour3().getY(),
//                face.getLandmark().getMouth_lower_lip_top().getX(),face.getLandmark().getMouth_lower_lip_top().getY(),
//                face.getLandmark().getMouth_upper_lip_bottom().getX(),face.getLandmark().getMouth_upper_lip_bottom().getY(),
//                face.getLandmark().getMouth_upper_lip_right_contour3().getX(),face.getLandmark().getMouth_upper_lip_right_contour3().getY(),
//                face.getLandmark().getMouth_lower_lip_right_contour1().getX(),face.getLandmark().getMouth_lower_lip_right_contour1().getY(),
//                face.getLandmark().getMouth_upper_lip_left_contour4().getX(),face.getLandmark().getMouth_upper_lip_left_contour4().getY(),
//                face.getLandmark().getMouth_upper_lip_right_contour4().getX(),face.getLandmark().getMouth_upper_lip_right_contour4().getY()
//        };
//    }
//    private int createTexture() {
//        int[] texture = new int[1];
//        if (mBitmap != null && !mBitmap.isRecycled()) {
//            //生成纹理
//            GLES20.glGenTextures(1, texture, 0);
//            //生成纹理
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
//            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
//            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
//            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
//            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
//            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
//            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
//            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
//            //根据以上指定的参数，生成一个2D纹理
//            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
//            return texture[0];
//        }
//        return 0;
//    }
//    @Override
//    public void onDrawFrame(GL10 gl) {
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//        GLES20.glUseProgram(mProgram);
//        GLES20.glEnableVertexAttribArray(glHPosition);
//        GLES20.glEnableVertexAttribArray(glHCoordinate);
//        GLES20.glUniform1i(glHTexture, 0);
//        textureId = createTexture();
//        //传入顶点坐标
//        GLES20.glVertexAttribPointer(glHPosition, 2, GLES20.GL_FLOAT, false, 0, bPos);
//        //传入纹理坐标
//        GLES20.glVertexAttribPointer(glHCoordinate, 2, GLES20.GL_FLOAT, false, 0, bCoord);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
//    }
//}
