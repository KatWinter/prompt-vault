package me.winterbelle.prompvault.utils.helpers.security;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class InputSanitizer {

    public String sanitizePlainText(String input) {
        if (input == null) {
            return null;
        }

        return Jsoup.clean(input, Safelist.none()).trim();
    }
}