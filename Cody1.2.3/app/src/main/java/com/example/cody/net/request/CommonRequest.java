package com.example.cody.net.request;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Create by SunnyDay on 2019/03/04
 *
 * @function 接收请求参数 为我们生成Request对象
 */
public class CommonRequest {

    private static Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json;");
    /**
     * @function post 请求
     * @param url    url
     * @param params 请求参数
     *
     */
    public static Request createPostRequest(String url, Map params, HeaderParams headerParams ) {
        String param= gson.toJson(params);
        //        FormBody.Builder builder = new FormBody.Builder();
        // 吧请求内容添加到请求体中
        RequestBody requestBody = RequestBody.create(JSON, param);

//        for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
//            builder.add(entry.getKey(), entry.getValue());
//        }
        // 构建请求体
//        FormBody formBody = builder.build();
        //构造请求头
        Request.Builder requestbuilder = new Request.Builder();
//        if(headerParams != null){
//            for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
//                requestbuilder.addHeader(entry.getKey(), entry.getValue());
//            }
//        }
        Request request = requestbuilder.addHeader("content-type", "application/json;charset:utf-8").post(requestBody).url(url).build();
        // 返回封装的Request请求
        return request;
    }
    public static Request createPostRequest(String url, Map params ) {
        return createPostRequest(url,params,null);
    }

    /**
     * @function put 请求
     * @param url    url
     * @param params 请求参数
     *
     */
    public static Request createPutRequest(String url, Map params ,HeaderParams headerParams) {
        String param= gson.toJson(params);
        //        FormBody.Builder builder = new FormBody.Builder();
        // 吧请求内容添加到请求体中
        RequestBody requestBody = RequestBody.create(JSON, param);

//        for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
//            builder.add(entry.getKey(), entry.getValue());
//        }
        // 构建请求体
//        FormBody formBody = builder.build();
        //构造请求头
        Request.Builder requestbuilder = new Request.Builder();
//        if(headerParams != null){
//            for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
//                requestbuilder.addHeader(entry.getKey(), entry.getValue());
//            }
//        }
        Request request = requestbuilder.addHeader("content-type", "application/json;charset:utf-8").put(requestBody).url(url).build();
        // 返回封装的Request请求
        return request;
    }
    public static Request createPutRequest(String url,  Map  params ) {
        return createPutRequest(url,  params,null);
    }

    /**
     * @function delete 请求
     * @param url    url
     * @param params 请求参数
     *
     */
    public static Request createDeleteRequest(String url, RequestParams params,HeaderParams headerParams) {
        FormBody.Builder builder = new FormBody.Builder();
        // 吧请求内容添加到请求体中
        for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        // 构建请求体
        FormBody formBody = builder.build();
        //构造请求头
        Request.Builder requestbuilder = new Request.Builder();
        if(headerParams != null){
            for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
                requestbuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestbuilder.url(url).delete(formBody).build();
        // 返回封装的Request请求
        return request;
    }
    public static Request createDeleteRequest(String url, RequestParams params ) {
        return createDeleteRequest(url,params,null);
    }

    /**
     * @function get 请求
     * @param url    url
     * @param params 请求参数
     *     通过url+请求参数的拼接 成我们的get请求url，在生成Request请求
     *
     *               get url 的方式  域名 ？ key = value & key = value ......
     *   有参数就拼接url再请求， 没有参数就使用给的拼接好的url
     */



    public static Request createGetRequest(String url, RequestParams params, HeaderParams headerParams){
        StringBuilder sb = new StringBuilder(url).append("?");
        if (params!=null){
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
               sb.append(entry.getKey())
                       .append("=")
                       .append(entry.getValue())
                       .append("&");
            }
            String disposedUrl = sb.toString().substring(0,sb.length()-1);// 去掉最后一个多余的&字符串
            //构造请求头
            Request.Builder requestbuilder = new Request.Builder();
            if(headerParams != null){
                for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
                    requestbuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            return requestbuilder.url(disposedUrl).get().build();
        }else{
            //构造请求头
            Request.Builder requestbuilder = new Request.Builder();
            if(headerParams != null){
                for (Map.Entry<String, String> entry : headerParams.urlParams.entrySet()) {
                    requestbuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            return requestbuilder.url(url).get().build();
        }

    }

    public static Request createGetRequest(String url, RequestParams params ) {
        return createGetRequest(url,params,null);
    }
}
