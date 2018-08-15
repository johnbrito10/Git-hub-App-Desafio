package com.example.professor.githubpesquisalistview;


import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Converter;

public class StringUtil implements Converter<String> {

    @Override
    public String fromBody(ResponseBody body) throws IOException {
        return body.toString();
    }

    @Override
    public RequestBody toBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static String capitalize(String word){
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}