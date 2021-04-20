package io.javahome.isthesiteup.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlCheckController { // Controllers should be put under the directory of Application

    private final String IS_SITE_UP = "Site is up!";
    private final String IS_SITE_DOWN = "Site is down!";
    private final String INCONRRECT_URL = "URL is incorrect!";

    @GetMapping("/check")
    public String getUrlStatusMessage(@RequestParam String url) {

        String returnMessage = "";
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCodeCategory = conn.getResponseCode() / 100;
            System.out.println(responseCodeCategory + ":" + url + conn.getResponseCode());
            if (responseCodeCategory == 2 || responseCodeCategory == 3) {
                returnMessage = IS_SITE_UP;
            } else {
                returnMessage = IS_SITE_DOWN;
            }
        } catch (MalformedInputException e) {
            returnMessage = INCONRRECT_URL;
        } catch (IOException e) {
            returnMessage = IS_SITE_DOWN;
        }

        return returnMessage;
    }
}
