package com.example.demo.wxTemplateMsg.winUtil;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

//import org.opensaml.xmlsec.signature.X509Certificate;

/***
 * http工具类
 *
 * @author Administrator
 *
 */
public class HttpClientUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws ClientProtocolException, IOException {
		Map mapParams = new HashMap();
		// 应用ID,您的APPID。
		// 'app_id' => "2016073000124682",

		// 商户私钥，您的原始格式RSA私钥
		// 'merchant_private_key' =>
		// "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMt4uPLEQlilM/oNDweVk0a6hLwLU5bQzLDpTEIMZbJMq8bBCxFkzmjEcXoJqQzxfjqrvnqz8W7dI4gpXFk/L44OjPu53d1nTb03WMXlgQ7euOt/y+61teXLTqjGJ/KR1CQ74xi6N3xxcWR6O1p+Tr2BO0kKVfycj7FMxZpZ9VaHAgMBAAECgYBM2ab5QnlQo+0Sx7XihnMe5rnVkG2c6/Yz55n+5FHQ2zaSFj2I70sqtO+rSVTJZ7jmjhGepLJOzkVqWzzfFIdv8mts89cjEjTJM+tdb4SeOaRCyWnrY66T1fcyCpQHEyPITQo6uFQBYue/gv9OAJX8H10RwwxObWS5/P+9gAeZYQJBAPSYiexRc9JvJ3JEfKr7jzHuvffKfhIhk67Jd3K0V2hoG9QdSyq3Ksv/tkRlen0BOaSUiwBknrkmqdO8EPVOGRECQQDU9VSC2JK6wR2t8sDGa2scrUG2VM9zKwA/V5LAbSqgdkRcXr7hFU0OJZwFYlz7K4X6fI4nQHrBptxzwVUwL7YXAkBPmZTJI3jd8u6TcVZhDpz7UwbfrTZ6EBNPvqDw41/OypLD5QShUhrLaNyYRa4nIE7yEKVr61L8TmjWT031hwUBAkEAzFZqtPJiOFg8xj/7wWgo6udD+lP+ih9kqWK6KXGWSUmx5n/y3760pTJpNrFfTpJAiE0jiX5RS6d0OI3107q0GQJABzFqNfpsngZ1vtcuoGUH7jIJ2karFtTSbOBRUXAntO0clQHgGGaRhrolXIb9yy3dfzz0HKWbsrXRa0umGHoV0Q==",

		// 异步通知地址
		// 'notify_url' => "http://localhost/zfb/notify_url.php",

		// 同步跳转
		// 'return_url' => "http://localhost/zfb/return_url.php",

		// 编码格式
		// 'charset' => "UTF-8",

		// 支付宝网关
		// 'gatewayUrl' =>
		// "https://openapi.alipaydev.com/gateway.do",//"https://openapi.alipay.com/gateway.do",

		// 支付宝公钥
		// 'alipay_public_key' =>
		// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB",

		mapParams.put("app_id", "2016073000124682");
		mapParams.put("method", "alipay.trade.wap.pay");
		mapParams.put("format", "JSON");
		mapParams.put("return_url", "");
		mapParams.put("notify_url", "");

		mapParams.put("charset", "utf-8");
		mapParams.put("sign_type", "RSA");
		mapParams.put("sign",
				"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMt4uPLEQlilM/oNDweVk0a6hLwLU5bQzLDpTEIMZbJMq8bBCxFkzmjEcXoJqQzxfjqrvnqz8W7dI4gpXFk/L44OjPu53d1nTb03WMXlgQ7euOt/y+61teXLTqjGJ/KR1CQ74xi6N3xxcWR6O1p+Tr2BO0kKVfycj7FMxZpZ9VaHAgMBAAECgYBM2ab5QnlQo+0Sx7XihnMe5rnVkG2c6/Yz55n+5FHQ2zaSFj2I70sqtO+rSVTJZ7jmjhGepLJOzkVqWzzfFIdv8mts89cjEjTJM+tdb4SeOaRCyWnrY66T1fcyCpQHEyPITQo6uFQBYue/gv9OAJX8H10RwwxObWS5/P+9gAeZYQJBAPSYiexRc9JvJ3JEfKr7jzHuvffKfhIhk67Jd3K0V2hoG9QdSyq3Ksv/tkRlen0BOaSUiwBknrkmqdO8EPVOGRECQQDU9VSC2JK6wR2t8sDGa2scrUG2VM9zKwA/V5LAbSqgdkRcXr7hFU0OJZwFYlz7K4X6fI4nQHrBptxzwVUwL7YXAkBPmZTJI3jd8u6TcVZhDpz7UwbfrTZ6EBNPvqDw41/OypLD5QShUhrLaNyYRa4nIE7yEKVr61L8TmjWT031hwUBAkEAzFZqtPJiOFg8xj/7wWgo6udD+lP+ih9kqWK6KXGWSUmx5n/y3760pTJpNrFfTpJAiE0jiX5RS6d0OI3107q0GQJABzFqNfpsngZ1vtcuoGUH7jIJ2karFtTSbOBRUXAntO0clQHgGGaRhrolXIb9yy3dfzz0HKWbsrXRa0umGHoV0Q==");
		mapParams.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapParams.put("version", "1.0");

		JSONObject content = new JSONObject();

		// 具体描述
		content.put("body", "这是一个描述");
		// 商品的标题/交易标题/订单标题/订单关键字等。
		content.put("subject", "测试标题");
		// 商户网站唯一订单号
		content.put("out_trade_no", "APPS52773620190939898131");
		// 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		content.put("timeout_express", "90m");
		content.put("total_amount", "0.1");
		content.put("seller_id", "");
		content.put("auth_token", "");
		content.put("", "");
		content.put("", "");
		content.put("", "");
		content.put("", "");
		content.put("", "");
		content.put("", "");

		mapParams.put("biz_content", content.toJSONString());

		post("https://openapi.alipay.com/gateway.do", mapParams);
	}

	/**
	 * 发送get请求
	 *
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		String content = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			response.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					content = EntityUtils.toString(entity);
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: " + content);
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 发送post请求
	 *
	 * @param url
	 * @param mapParams
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String post(String url, Map<String, String> mapParams) {
		String content = "";
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60).setConnectTimeout(1000 * 60)
				.build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);

		CloseableHttpResponse response = null;
		try{
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (mapParams != null) {
			Set<Entry<String, String>> entrySet = mapParams.entrySet();
			for (Entry<String, String> entry : entrySet) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(uefEntity);
		System.out.println("executing request " + httpPost.getURI());
			response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			content = EntityUtils.toString(entity, "UTF-8");
			System.out.println("--------------------------------------");
			System.out.println("Status: " + response.getStatusLine());
			System.out.println("Response content: " + content);
			System.out.println("--------------------------------------");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		// 关闭连接,释放资源
		try {
			response.close();
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		}
		return content;
	}
	public static String postJson(String url,String json) {
		String content = "";
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60).setConnectTimeout(1000 * 60)
				.build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {

		httpPost.setEntity(new StringEntity(String.valueOf(json), Charset.forName("UTF-8")));
		System.out.println("executing request " + httpPost.getURI());
		httpPost.setHeader("Content-Type", "application/json;charset=utf-8");

			response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			content = EntityUtils.toString(entity, "UTF-8");
			System.out.println("--------------------------------------");
			System.out.println("Status: " + response.getStatusLine());
			System.out.println("Response content: " + content);
			System.out.println("--------------------------------------");
		}
		// 关闭连接,释放资源
		response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {

					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return content;
	}

	public static String postSSl(String url, Map<String, String> mapParams) throws ClientProtocolException, IOException {
		String content = "";
		// 创建默认的httpClient实例.
		DefaultHttpClient httpclient =  new DefaultHttpClient();

		  enableSSL(httpclient);

		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60).setConnectTimeout(1000 * 60)
				.build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);

		CloseableHttpResponse response = null;

		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (mapParams != null) {
			Set<Entry<String, String>> entrySet = mapParams.entrySet();
			for (Entry<String, String> entry : entrySet) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(uefEntity);
		System.out.println("executing request " + httpPost.getURI());
			response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			content = EntityUtils.toString(entity, "UTF-8");
			System.out.println("--------------------------------------");
			System.out.println("Status: " + response.getStatusLine());
			System.out.println("Response content: " + content);
			System.out.println("--------------------------------------");
		}
		// 关闭连接,释放资源
		response.close();
		httpclient.close();
		return content;
	}

	/**
	 * 发送POST请求，得到二进制文件流
	 *
	 * @param url
	 * @param mapParams
	 * @return byte[]
	 * @throws IOException
	 * @throws @throws
	 *             ClientProtocolException
	 */
	public static byte[] postFile(String url, Map<String, String> mapParams) throws IOException {
		byte[] byteArray = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60).setConnectTimeout(1000 * 60)
				.build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);

		CloseableHttpResponse response = null;

		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (mapParams != null) {
			Set<Entry<String, String>> entrySet = mapParams.entrySet();
			for (Entry<String, String> entry : entrySet) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(uefEntity);
		// System.out.println("executing request " + httpPost.getURI());
		response = httpclient.execute(httpPost);// --------------
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			byteArray = EntityUtils.toByteArray(entity);
		}
		// 关闭连接,释放资源
		try {
			response.close();
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArray;
	}

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 *
	 * @param imgFilePath
	 * @return String
	 */
	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 *
	 * @param imgStr
	 * @param imgFilePath
	 * @return boolean
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public static void enableSSL(DefaultHttpClient httpclient) {
        try {

            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{
                new X509TrustManager(){

                    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                        throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                        throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

					@Override
					public void checkClientTrusted(
							java.security.cert.X509Certificate[] chain,
							String authType)
							throws java.security.cert.CertificateException {
					}

					@Override
					public void checkServerTrusted(
							java.security.cert.X509Certificate[] chain,
							String authType)
							throws java.security.cert.CertificateException {
					}}
            }, new java.security.SecureRandom());
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme https = new Scheme("https", sf, 443);
            httpclient.getConnectionManager().getSchemeRegistry().register(https);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
