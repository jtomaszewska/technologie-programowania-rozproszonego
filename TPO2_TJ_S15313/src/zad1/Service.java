/**
 * @author Tomaszewska Justyna S15313
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    private static final String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=%s&APPID=%s";
    private static final String weatherAppId = "76d057a60d6abd56ed36974936616945";
    private static final String exchangeRateECBUrl = "https://api.exchangeratesapi.io/latest";
    private static final String nbpRatePartA = "http://www.nbp.pl/kursy/xml/b012z190320.xml";
    private static final String nbpRatePartB = "http://www.nbp.pl/kursy/xml/a058z190322.xml";
    private String country;

    public Service(String country) {
        if (country != null)
            this.country = country;
    }

    private static Map<String, String> getAvailableCurrencies() {
        Locale[] locales = Locale.getAvailableLocales();
        Map<String, String> currencies = new TreeMap<>();
        for (Locale locale : locales) {
            try {
                currencies.put(locale.getDisplayCountry(),
                        Currency.getInstance(locale).getCurrencyCode());
            } catch (Exception e) {
                // when the locale is not supported
            }
        }
        return currencies;
    }

    /*informacja o pogodzie w formacie JSON*/
    public String getWeather(String city) {
        String httpResponse = "";
        try {
            URL url = new URL(String.format(weatherUrl, city, weatherAppId));
            httpResponse = httpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    private String httpGet(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        int status = con.getResponseCode();
        if (status != 200) return "";
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    /*kurs waluty danego kraju wobec waluty podanej jako argument*/
    public Double getRateFor(String currency_code) {
        String basicCurr = getCurrency(country);
        Map<String, Double> rates = null;
        try {
            rates = getRatesFor(basicCurr);
        } catch (IOException e) {

        }
        return rates.get(currency_code);
    }

    private Map<String, Double> getRatesFor(String basicCurr) throws IOException {
        String rateWithCountryCurrAsBasic = httpGet(new URL(exchangeRateECBUrl + "?base=" + basicCurr));
        Map<String, Double> rates = new TreeMap<>();
        Pattern pattern = Pattern.compile("\"([A-Z]{3})\":([0-9]*.[0-9]*)");
        Matcher matcher = pattern.matcher(rateWithCountryCurrAsBasic);
        while (matcher.find()) {
            rates.put(matcher.group(1), Double.parseDouble(matcher.group(2)));
        }
        return rates;
    }

    private String getCurrency(String country) {
        Map<String, String> currencies = getAvailableCurrencies();
        String curr = "PLN";
        if (currencies.keySet().contains(country)) {
            curr = currencies.get(country);
        }
        return curr;
    }

    /*kurs złotego wobec waluty kraju*/
    public Double getNBPRate() {

        String countryCurr = getCurrency(country);
        if (countryCurr.equals("PLN")) {
            return Double.valueOf(1);
        }
        String nbpRatesAllData = "";
        try {
            nbpRatesAllData = httpGet(new URL(nbpRatePartA));
            nbpRatesAllData += httpGet(new URL(nbpRatePartB));
        } catch (IOException e) {
            System.out.println("Strona NBP nieistnieje, bądź została zmieniona");
        }

        Pattern pattern = Pattern.compile("([0-9]*)</przelicznik>\\s*<kod_waluty>" + countryCurr + "</kod_waluty>\\s*<kurs_sredni>([0-9]*,[0-9]{4})");
        Matcher matcher = pattern.matcher(nbpRatesAllData);
        String nbpRate = "0";
        String scaler = "1";
        if (matcher.find()) {
            scaler = matcher.group(1);
            nbpRate = matcher.group(2);
        }
        nbpRate = nbpRate.replaceAll(",", ".");
        Double rate = Double.parseDouble(nbpRate);
        int scale = Integer.parseInt(scaler);
        Double val = BigDecimal.valueOf(rate / scale)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
        return val;
    }

    /*lista walut(kody) w european central bank api*/
    public List<String> getCurrenciesFromECB() throws IOException {
        String rates = httpGet(new URL(exchangeRateECBUrl));
        List<String> currencies = new ArrayList<>();
        Pattern pattern = Pattern.compile("[A-Z]{3}");
        Matcher matcher = pattern.matcher(rates);
        while (matcher.find()) {
            currencies.add(matcher.group(0));
        }
        return currencies;
    }
}
