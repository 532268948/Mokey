package com.example.lib_common.music;

/**
 * @author : 叶天华
 * @project: Monkey
 * @date : 2018/12/23
 * @time : 14:50
 * @email : 15869107730@163.com
 * @note :
 */
public class ProxyConfig {
    final static public String LOCAL_IP_ADDRESS = "127.0.0.1";
    final static public int HTTP_PORT = 80;

    final static public String HTTP_BODY_END = "\r\n\r\n";
    final static public String HTTP_RESPONSE_BEGIN = "HTTP/";
    final static public String HTTP_REQUEST_BEGIN = "GET ";
    final static public String HTTP_REQUEST_LINE1_END = " HTTP/";

    static public class ProxyRequest {
        public String body;
        public long rangePosition;
    }

    static public class ProxyResponse {
        public byte[] body;
        public byte[] other;
        public long startPosition;
        public long endPosition;
    }
}
