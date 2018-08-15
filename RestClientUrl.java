package com.example.professor.githubpesquisalistview;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.professor.githubpesquisalistview.Url;


//requerindo informaçôes de Usuarios do Servidor
public class RestClientUrl {


        public static Url getInfo(String mUrl){
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Url infoUser = restTemplate.getForObject(mUrl, Url.class);

            return infoUser;
        }

    }

