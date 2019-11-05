package com.noah.solutions.system.thirdPartyInterface;

import java.net.URLEncoder;

public class Tools {
    public static void main(String[] args) {
        System.out.println(Tools.URLEncode(Tools.str2Unicode("您的验证码是1111")));
    }
    public static String URLEncode(final String inStr) {

        String output = inStr;
        if (inStr != null) {
            try {
                output = URLEncoder.encode(inStr, "UTF-8");
            } catch (java.io.UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        } else {
            output = "";
        }
        return output;
    }
    public static String str2Unicode(String strIn) {
        int i;
        String strResult = "", strTemp = "";
        for (i = 0; i < strIn.length(); i++) {
            strTemp = strIn.substring(i, i + 1);
            if (strTemp.charAt(0) == 0 && strTemp.charAt(0) <= 255) {
                strResult = strResult + strTemp;
            } else {
                strResult = strResult + "&#x"
                        + Integer.toHexString(strTemp.charAt(0)) + ";";
            }
        }
        return strResult;
    }
}

