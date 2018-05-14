package com.rorrim.mang.smartmirror.Interface;

public interface Connectable {
    String GET_METHOD = "GET";
    String POST_METHOD = "POST";
    String CHARSET = "UTF-8";
    String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    /** Time out for Connection **/
    int connectTimeout = 5 * 1000;
    /** Timeout when reading from the input stream after establishing a connection to the resource */
    int readTimeout = 10 * 1000;

    String baseURL = "http://203.252.166.206:5000";
}
