package com.kostenko.webmydictionary.translators.yandex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.webmydictionary.translators.TranslatorAPI;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service(value = "translatorAPI")
public class YandexAPI implements TranslatorAPI<TranslationResponse> {
    private static final Logger LOG = LoggerFactory.getLogger(YandexAPI.class);
    private static final String API_KEY_ONE = "trnsl.1.1.20150522T195930Z.010ab143cb4576f2.568a0965d1a708e331f7af72fc325e30861ff083";
    private static final String API_KEY_TWO = "trnsl.1.1.20150609T180905Z.f13a08e37aa237d0.1d49c5f38dcf5917e5e41950eb2882b27a3b97c8";
    private static final String API_KEY_THREE = "trnsl.1.1.20160512T204421Z.b829b32f4cea989b.3fb210f3bcb6f16c61e92b993ed9d99a5fb9e561";
    private static final List<String> API_KEYS = new ArrayList<>(3);
    private static int index = 0;

    static {
        API_KEYS.add(API_KEY_ONE);
        API_KEYS.add(API_KEY_TWO);
        API_KEYS.add(API_KEY_THREE);
    }

    /*https://translate.yandex.net/api/v1.5/tr.json/translate?key=<API-key>&text=<text>&lang=<direction like en-ru>&[format=<plain or html>]**/
    private final String URL_LINK = "https://translate.yandex.net/api/v1.5/tr.json/translate";//?key=%s&text=%s&lang=%s-%s&format=plain";

    @Override
    public TranslationResponse translate(String from, String to, String message) {
        TranslationResponse result = null;
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(URL_LINK);
        httpPost.setHeader("User-Agent", "Mozilla/5.0");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key", API_KEY_ONE));
        params.add(new BasicNameValuePair("lang", String.format("%1$s-%2$s", from, to)));
        params.add(new BasicNameValuePair("text", message));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        HttpResponse httpResponse;
        try {
            httpResponse = client.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(br.readLine(), TranslationResponse.class);
        } catch (IOException e) {
            LOG.error("Something wrong with request to yandex api or Json mapping", e);
            result = null;
        }
        return getResultFromResponse(result);
    }

    private TranslationResponse getResultFromResponse(TranslationResponse result) {
        if (result != null) {
            int resultCode = result.getCode();
            Statuses status = Statuses.getStatus(resultCode);
            if (status != Statuses.OK) {
                result.setErrorCode(String.format("Api returns next error (statusCode: %s, ErrorMessage: %s)",
                        status.getCode(), status.getMessage()));
            }
        }
        return result;
    }

    private synchronized static String getApiKey() {
        index = (index++) % (API_KEYS.size());
        LOG.debug("Current API key index is: " + index);
        String key = API_KEYS.get(index);
        LOG.debug("API Key is: " + key);
        return key;
    }
}