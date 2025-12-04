package com.example.savetrip.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class CurrencyConverter {

    private static final String TAG = "CurrencyConverter";
    private String apiKey;
    private Context context;

    public Map<String, Double> latestRates = new HashMap<>();
    private String baseCurrency;

    public interface ConversionCallback {
        void onSuccess(double convertedAmount);
        void onFailure(Exception e);
    }

    public CurrencyConverter(Context context, String apiKey) {
        this.context = context;
        this.apiKey = apiKey;
    }

    // Ambil latest rates dari API
    public void fetchLatestRates(String base, Double totalAmount, String[] symbols, Runnable onSuccess, Runnable onFailure) {
        this.baseCurrency = base;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < symbols.length; i++) {
            sb.append(symbols[i]);
            if (i < symbols.length - 1) sb.append(",");
        }

        String url = "http://api.exchangeratesapi.io/v1/convert?access_key=" + apiKey +
                "&from=" + base + "&to=" + sb.toString() + "&amount= " + totalAmount;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject rates = response.getJSONObject("rates");
                            latestRates.clear();
                            for (String key : symbols) {
                                latestRates.put(key, rates.getDouble(key));
                            }
                            onSuccess.run();
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON parsing error", e);
                            onFailure.run();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error", error);
                        onFailure.run();
                    }
                });

        queue.add(request);
    }

    // Konversi menggunakan latestRates yang sudah diambil
    public void convert(double amount, String targetCurrency, ConversionCallback callback) {
        if (targetCurrency.equals(baseCurrency)) {
            callback.onSuccess(amount);
            return;
        }

        Double rate = latestRates.get(targetCurrency);
        if (rate != null) {
            callback.onSuccess(amount * rate);
        } else {
            callback.onFailure(new Exception("Rate not available for " + targetCurrency));
        }
    }

    // Format jumlah ke currency
    public static String format(double amount, String currencyCode) {
        Locale locale;
        switch (currencyCode) {
            case "USD": locale = Locale.US; break;
            case "EUR": locale = Locale.FRANCE; break;
            case "SGD": locale = new Locale("en", "SG"); break;
            case "IDR": default: locale = new Locale("id", "ID"); break;
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(amount);
    }

    public void fetchAllRates(String base, Runnable onSuccess, Runnable onFailure) {
        this.baseCurrency = base;

        String url = "https://api.exchangeratesapi.io/v1/latest?access_key=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject rates = response.getJSONObject("rates");
                        latestRates.clear();
                        Iterator<String> keys = rates.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            latestRates.put(key, rates.getDouble(key));
                        }
                        onSuccess.run();
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error", e);
                        onFailure.run();
                    }
                },
                error -> {
                    Log.e(TAG, "Volley error", error);
                    onFailure.run();
                });

        queue.add(request);
    }

}
