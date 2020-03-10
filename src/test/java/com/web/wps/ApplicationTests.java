package com.web.wps;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import org.junit.Test;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class ApplicationTests {

	@Test
	public void test01(){
		UUID randomUUID = UUID.randomUUID();
		String uuid = randomUUID.toString().replace("-","");
		System.out.println(uuid);
	}

	public static void main(String[] args) {
		String ak = "";
		String sk = "";
		String ep = "";
		String process = "";
		String bucketName = "";
		String objectKey = "";
		URL url =  getUrl(process, ak , sk ,ep ,bucketName, objectKey);
		System.out.println(url.toString());
	}

	private static URL getUrl(String process, String ak, String sk,String ep, String bucketName, String objectKey) {
		OSSClient client = new OSSClient(ep,ak, sk);
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectKey);
		getObjectRequest.setProcess(process);
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectKey);
		request.setProcess(process);
		request.setExpiration(new Date(new Date().getTime() + 3600 * 1000));
		return client.generatePresignedUrl(request);
	}

}
