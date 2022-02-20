package com.swastikairhub.SwastiKAirHubBackend.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.StringReader;

public class RestResponse {
    public String code;
    public String message;
    public Object data;

    public RestResponse() {
    }

    private RestResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private RestResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<Object> ok(String msg) {
        return new ResponseEntity<Object>(new RestResponse("200", msg), HttpStatus.OK);
    }


    public static ResponseEntity<Object> ok(Object data) {
//        JsonObject object = null;
//        if (data != null) {
//            object = toJson(data);
//        }
//        System.out.println("OLD: "+data);
        Object data1 = HelperUtil.checkAndCorrectNullValueOfObject(data);
//        System.out.println("NEW: "+data1);
        return new ResponseEntity<Object>(new RestResponse("200", "SUCCESS", data1), HttpStatus.OK);
    }


    public static ResponseEntity<Object> ok(Object data, String msg ) {
        Object data1 = HelperUtil.checkAndCorrectNullValueOfObject(data);
        return new ResponseEntity<>((new RestResponse("200", msg, data1)), HttpStatus.OK);
    }

    public static JsonObject toJson(Object data) {
        JsonValue value = Json.createReader(new StringReader(JsonUtil.toJsonObj(data))).read();
        return Json.createObjectBuilder().add("data", value).build();
    }

    public static class error {
        public String code;
        public String message;
        public Object errorData;

        public error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public error(String code, String message, Object errorData) {
            this.code = code;
            this.message = message;
            this.errorData = errorData;
        }
    }

}
