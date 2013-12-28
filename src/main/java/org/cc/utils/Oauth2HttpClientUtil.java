package org.cc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Oauth2HttpClientUtil {
	private static Integer default_connTimeout = 3000;
	private static Integer default_readTimeout = 3000;
	
	public static String getMethod(String url,Integer connTimeout,Integer readTimeout) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		if(connTimeout!=null){
			default_connTimeout = connTimeout;
		}
		if(readTimeout !=null){
			default_readTimeout = readTimeout;
		}
        try {  
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = 
            		RequestConfig.custom().setConnectionRequestTimeout(default_connTimeout)
            		.setConnectTimeout(default_readTimeout).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpGet);  
            try {  
                //http请求头状态码
                int status = response.getStatusLine().getStatusCode();
                //TODO 不成功报错
                if(status!= HttpStatus.SC_OK){
                	
                }
                HttpEntity entity = response.getEntity();  
                if (entity != null) {
                    result = EntityUtils.toString(entity);//tostring方法读取实体内容，利用编码规则转换成String，默认规则是IS0-8859-1  
                }
                EntityUtils.consume(entity);//确保实体内容被消费，并且关闭内容流  
            } finally {  
                response.close();  
            }  
        } finally {  
            httpclient.close();  
        }
        return result;
	}
	
	public static String postMethod(String url,List<NameValuePair> param,Integer connTimeout,Integer readTimeout) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		if(connTimeout!=null){
			default_connTimeout = connTimeout;
		}
		if(readTimeout !=null){
			default_readTimeout = readTimeout;
		}
		try { 
			HttpPost httpPost = new HttpPost(url);
	        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(param);  
	        httpPost.setEntity(ent);
	        RequestConfig requestConfig = 
            		RequestConfig.custom().setConnectionRequestTimeout(default_connTimeout)
            		.setConnectTimeout(default_readTimeout).build();//设置请求和传输超时时间
	        httpPost.setConfig(requestConfig);
	        
	        CloseableHttpResponse response = httpclient.execute(httpPost);  
	        try {  
	        	//http请求头状态码
                int status = response.getStatusLine().getStatusCode();
                //TODO 不成功报错
                if(status!= HttpStatus.SC_OK){
                	
                }
		        HttpEntity entity = response.getEntity();  
		        if (entity != null) {  
		        	result = EntityUtils.toString(entity);  
		        }  
		        EntityUtils.consume(entity);  
	        } finally {  
	        	response.close();  
	        } 
		} finally {  
            httpclient.close();  
        }
		return result;
	}
	
	/**
	 * 下载图片
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void getMethodPicture(String url,String filePath,Integer connTimeout,Integer readTimeout) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		if(connTimeout!=null){
			default_connTimeout = connTimeout;
		}
		if(readTimeout !=null){
			default_readTimeout = readTimeout;
		}
        try {  
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = 
            		RequestConfig.custom().setConnectionRequestTimeout(default_connTimeout)
            		.setConnectTimeout(default_readTimeout).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpGet);  
            try {  
                //http请求头状态码
                int status = response.getStatusLine().getStatusCode();
                //TODO 不成功报错
                if(status!= HttpStatus.SC_OK){
                	
                }
                HttpEntity entity = response.getEntity();
                InputStream input = null; 
                if (entity != null) {
                     try {  
                         input = entity.getContent();  
                         File file = new File(filePath);  
                         FileOutputStream output = FileUtils.openOutputStream(file);  
                         try {  
                             IOUtils.copy(input, output);  
                         } finally {  
                             IOUtils.closeQuietly(output);  
                         }  
                     } finally {  
                         IOUtils.closeQuietly(input);  
                     }
                }
            } finally {  
                response.close();  
            }  
        } finally {  
            httpclient.close();  
        }
	}
	
}
