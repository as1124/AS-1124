package com.primeton.mobile.wechat.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * PWorld大会, 移动APP的server端
 * 
 * Servlet implementation class PWorldServlet
 */
@WebServlet(name = "pworldApp", urlPatterns = { "/pworldApp/*" })
public class PWorldServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PWorldServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String path = request.getRequestURI();
		response.setCharacterEncoding("UTF-8");
		
		try{
			String KEY_ALGORITHM = "AES";
			String content = "9CQ+6M7be34uO7gfh20sNxxHHn1k8jyQ91psS+hSlyo=";
			String encryptKey = "8Q6NyAsL6OZ0odaWwrPVzWYOs3mBugX7";
			Provider[] arg1 = Security.getProviders();
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), KEY_ALGORITHM);  
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");  
            cipher.init(Cipher.ENCRYPT_MODE, key);  
            byte[] encryptedData = cipher.doFinal(content.getBytes("UTF-8"));
            System.out.println(encryptedData);
        }catch (Exception e){
        	e.printStackTrace();
        }
		
		String[] segments = path.split("/");
		
		// 统一使用一个servlet, URL规则：http://[host]:[port]/application/servlet/category/action
		if(segments.length < 4){
			JSONObject errJson = new JSONObject();
			errJson.put("errCode", "404");
			errJson.put("errMsg", "request url can't be found");
			PrintWriter pw = response.getWriter();
			pw.write(errJson.toJSONString());
			pw.flush();
			return;
		}
		
	}

}
