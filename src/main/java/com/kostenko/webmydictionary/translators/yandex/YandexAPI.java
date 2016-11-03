package com.kostenko.webmydictionary.translators.yandex;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kostenko.webmydictionary.configuration.AppConfigLoader;
import com.kostenko.webmydictionary.configuration.AppConfigLoader.Property;
import com.kostenko.webmydictionary.translators.TranslatorAPI;
import com.kostenko.webmydictionary.translators.yandex.domain.TranslationResponse;
import com.kostenko.webmydictionary.translators.yandex.domain.YandexResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service(value = "translatorAPI")
public class YandexAPI implements TranslatorAPI<YandexResponse> {
    private static final String translatorTechnology = "Translated by «Yandex.Translator» service "; //TODO: move to the property file for internationalization
    private static final String dictionaryTechnology = "Implemented by «Yandex.Dictionary» service "; //TODO: move to the property file for internationalization
    private final List<String> technologies = new ArrayList<>();
    private final String urlDictionary;
    private final String urlTranslator;
    private String apiKeyTranslation;
    private String apiKeyDictionary;


    @Autowired
    public YandexAPI(AppConfigLoader appConfigLoader) {
        checkNotNull(appConfigLoader, "AppConfigLoader can't be null");
        apiKeyTranslation = appConfigLoader.getProperty(Property.YANDEX_API_TRANSLATION_KEY);
        urlTranslator = appConfigLoader.getProperty(Property.YANDEX_API_TRANSLATION_URL);
        apiKeyDictionary = appConfigLoader.getProperty(Property.YANDEX_API_DICTIONARY_KEY);
        urlDictionary = appConfigLoader.getProperty(Property.YANDEX_API_DICTIONARY_URL);
        technologies.add(translatorTechnology);
        technologies.add(dictionaryTechnology);
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
            log.debug(translationDictionaryResult.toString());
        } catch (IOException e) {
            log.error("Something wrong with request to yandex api or Json mapping", e);
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
        params.add(new BasicNameValuePair("key", apiKeyTranslation));
        params.add(new BasicNameValuePair("lang", String.format("%1$s-%2$s", from, to)));
        params.add(new BasicNameValuePair("text", text));
        HttpPost httpPost = prepareHttpPost(params, urlTranslator);
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
            log.error("Something wrong with request to yandex api or Json mapping", e);
        }
        return stringBuilder;
    }

    private String translateThroughDictionary(String from, String to, String text) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("key", apiKeyDictionary));
        params.add(new BasicNameValuePair("lang", String.format("%1$s-%2$s", from, to)));
        params.add(new BasicNameValuePair("text", text));
        params.add(new BasicNameValuePair("ui", to));
        HttpPost httpPost = prepareHttpPost(params, urlDictionary);
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
}