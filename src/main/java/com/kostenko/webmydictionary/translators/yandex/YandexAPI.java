package com.kostenko.webmydictionary.translators.yandex;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kostenko.webmydictionary.translators.TranslatorAPI;
import com.kostenko.webmydictionary.translators.yandex.domain.TranslationResponse;
import com.kostenko.webmydictionary.translators.yandex.domain.YandexResponse;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "translatorAPI")
public class YandexAPI implements TranslatorAPI<YandexResponse> {
    private static final Logger LOG = LoggerFactory.getLogger(YandexAPI.class);
    private static final String API_KEY_ONE = "trnsl.1.1.20150522T195930Z.010ab143cb4576f2.568a0965d1a708e331f7af72fc325e30861ff083"; //TODO: move to the property file
    private static final String API_KEY_TWO = "trnsl.1.1.20150609T180905Z.f13a08e37aa237d0.1d49c5f38dcf5917e5e41950eb2882b27a3b97c8"; //TODO: move to the property file
    private static final String API_KEY_THREE = "trnsl.1.1.20160512T204421Z.b829b32f4cea989b.3fb210f3bcb6f16c61e92b993ed9d99a5fb9e561"; //TODO: move to the property file
    private static final String API_KEY_DICTIONARY = "dict.1.1.20160523T184419Z.57f3846f4ebe2a46.14118f8be02cc0ebb7c49576bb500c225cd9d52e"; //TODO: move to the property file
    private static final List<String> API_KEYS = new ArrayList<>(3);
    private static final String URL_DICTIONARY = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup"; //TODO: move to the property file
    private static final String URL_TRANSLATOR = "https://translate.yandex.net/api/v1.5/tr.json/translate"; //TODO: move to the property file
    private static final String translatorTechnology = "Translated by «Yandex.Translator» service "; //TODO: move to the property file for internationalization
    private static final String dictionaryTechnology = "Implemented by «Yandex.Dictionary» service "; //TODO: move to the property file for internationalization
    private static final List<String> technologies = new ArrayList<>();

    static {
        technologies.add(translatorTechnology);
        technologies.add(dictionaryTechnology);
    }

    private static int index = 0;

    static {
        API_KEYS.add(API_KEY_ONE);
        API_KEYS.add(API_KEY_TWO);
        API_KEYS.add(API_KEY_THREE);
    }

    @Override
    public YandexResponse translate(String from, String to, String message) {
        TranslationResponse translationResponse = null;
        Map<String, Object> translationDictionaryResult;
        try {
            String translatorResult = translateThroughTranslator(from, to, message);
            String dictionaryResult = translateThroughDictionary(from, to, message);
            ObjectMapper objectMapper = new ObjectMapper();
            translationResponse = objectMapper.readValue(translatorResult, TranslationResponse.class);
            JsonNode jsonNode = objectMapper.readTree(dictionaryResult);
            if (jsonNode instanceof ObjectNode) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("head");
            }
            String str = jsonNode.toString();
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
            };
            translationDictionaryResult = objectMapper.readValue(str, typeRef);
            LOG.debug(translationDictionaryResult.toString());
        } catch (IOException e) {
            LOG.error("Something wrong with request to yandex api or Json mapping", e);
            translationDictionaryResult = new HashMap<>();
        }
        YandexResponse response = getYandexResponse(translationResponse, translationDictionaryResult);
        return getResultFromResponse(response);
    }

    private YandexResponse getYandexResponse(TranslationResponse translationResponse, Map<String, Object> dictionaryResponse) {
        YandexResponse response = null;
        if (translationResponse != null && dictionaryResponse != null) {
            response = new YandexResponse(translationResponse.getCode(),
                    translationResponse.getLang(),
                    translationResponse.getText().toString(),
                    dictionaryResponse,
                    technologies,
                    translationResponse.getErrorCode());
        }
        return response;
    }

    private String translateThroughTranslator(String from, String to, String text) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key", getApiKey()));
        params.add(new BasicNameValuePair("lang", String.format("%1$s-%2$s", from, to)));
        params.add(new BasicNameValuePair("text", text));
        HttpPost httpPost = prepareHttpPost(params, URL_TRANSLATOR);
        StringBuilder stringBuilder = getResponseString(httpPost);
        return stringBuilder.toString();
    }

    private HttpPost prepareHttpPost(List<NameValuePair> params, String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", "Mozilla/5.0");
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        return httpPost;
    }

    private StringBuilder getResponseString(HttpPost httpPost) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpResponse httpResponse = client.execute(httpPost);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"))) {
                String temp;
                while ((temp = br.readLine()) != null) {
                    stringBuilder.append(temp);
                }
            }
        } catch (IOException e) {
            LOG.error("Something wrong with request to yandex api or Json mapping", e);
        }
        return stringBuilder;
    }

    private String translateThroughDictionary(String from, String to, String text) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key", API_KEY_DICTIONARY));
        params.add(new BasicNameValuePair("lang", String.format("%1$s-%2$s", from, to)));
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("ui", to));
        HttpPost httpPost = prepareHttpPost(params, URL_DICTIONARY);
        StringBuilder stringBuilder = getResponseString(httpPost);
        return stringBuilder.toString();
    }

    private YandexResponse getResultFromResponse(YandexResponse result) {
        if (result != null) {
            int resultCode = result.getCode();
            Statuses status = Statuses.getStatus(resultCode);
            if (status != Statuses.OK) {
                result.setErrorCode(String.format("Api returns next error (statusCode: %s, ErrorMessage: %s)",
                        status.code, status.message));
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