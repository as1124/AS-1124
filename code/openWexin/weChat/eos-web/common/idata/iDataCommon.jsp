<%@page pageEncoding="UTF-8"%>
<%@page import="org.codehaus.jettison.json.JSONObject"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<html>
<head>
	<%!
	private static byte buf[] = {
	        -84, -19, 0, 5, 115, 114, 0, 30, 99, 111, 
	        109, 46, 115, 117, 110, 46, 99, 114, 121, 112, 
	        116, 111, 46, 112, 114, 111, 118, 105, 100, 101, 
	        114, 46, 68, 69, 83, 75, 101, 121, 107, 52, 
	        -100, 53, -38, 21, 104, -104, 2, 0, 1, 91, 
	        0, 3, 107, 101, 121, 116, 0, 2, 91, 66, 
	        120, 112, 117, 114, 0, 2, 91, 66, -84, -13, 
	        23, -8, 6, 8, 84, -32, 2, 0, 0, 120, 
	        112, 0, 0, 0, 8, -88, -101, -56, 50, 87, 
	        52, -29, -94
	};

	public static javax.crypto.SecretKey newDESKey() {
	    try {
	        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(buf));
	        javax.crypto.SecretKey key = (javax.crypto.SecretKey)ois.readObject();
	        ois.close();
	        return key;
	    } catch(java.io.IOException e) {
	        return null;
	    } catch(ClassNotFoundException e) {
	        return null;
	    }
	}
	
    public static String deDES(String source) {
        try {
			javax.crypto.SecretKey key = newDESKey();
			//½âÃÜ
		    javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("DES");
					c = javax.crypto.Cipher.getInstance("DES");
					c.init(javax.crypto.Cipher.DECRYPT_MODE, key);
		            byte[] cipherByte = hex2byte(source);
					byte[] clearByte = c.doFinal(cipherByte);
		            return new String(clearByte);
        } catch (Exception e) {
					e.printStackTrace();
		}
        return null;
    }

    public static byte[] hex2byte(String s) {
        byte[] result = new byte[s.length() / 2];
        String hs = "";
        int b = 0;
        for (int i = 0; i < s.length(); i+=2) {
            hs = s.substring(i, i+2);
            b =  Integer.parseInt(hs, 16);
            result[i / 2] = (byte) b;
        }
        return result;
    }

    public static String enDES(String source) {
    	try{
			javax.crypto.SecretKey key = newDESKey();
			//¼ÓÃÜ
			javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("DES");
				c = javax.crypto.Cipher.getInstance("DES");
				c.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
				byte[] clearByte = c.doFinal(source.getBytes());
	      return "%$*^@%"+byte2hex(clearByte);
        }catch (Exception e) {
					e.printStackTrace();
		}
        return null;
	}

	public static String byte2hex(byte[] b) { 
	     String hs = ""; 
	     String stmp = ""; 
	     for (int n = 0; n < b.length; n++) { 
	         stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); 
	         if (stmp.length() == 1) 
	             hs = hs + "0" + stmp; 
	         else 
	             hs = hs + stmp; 
	     } 
	     return hs.toUpperCase(); 
	} 
%>

<%
	String _iDataContext=request.getContextPath();
	java.io.File file=new java.io.File (application.getRealPath("/common/idata/idata.json")) ;
	if(file.exists()){
		String fileStr=FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObj = new JSONObject(fileStr);
		String password=jsonObj.getString("password");
		if(password!=null&&!password.startsWith("%$*^@%")){
			password=enDES(password);
			jsonObj.put("password", password);
			FileUtils.writeStringToFile(file, jsonObj.toString(1).replaceAll("\\\\",""));
		}
	}
%>
	

<script type="text/javascript">
	var _iDataContext='<%=_iDataContext %>';
</script>
<script type="text/javascript" src="<%=_iDataContext%>/common/idata/idata.js"></script>
</head>
<body>
</body>
</html>