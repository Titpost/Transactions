package edu.billing.controller;

import org.springframework.http.HttpHeaders;

public class Controller {

    protected final static HttpHeaders responseHeaders = new HttpHeaders();

    static {
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Allow-Headers", "*");
        responseHeaders.set("Access-Control-Max-Age", "3600");
    }
}
