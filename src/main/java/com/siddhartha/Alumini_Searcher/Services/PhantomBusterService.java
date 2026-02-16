package com.siddhartha.Alumini_Searcher.Services;
import com.siddhartha.Alumini_Searcher.config.phantomproperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
@Service
public class PhantomBusterService {

    private final OkHttpClient client = new OkHttpClient();

    public String fetchAlumniFromPhantom() {

        try {
            Request request = new Request.Builder()
                    .url("https://api.phantombuster.com/api/v2/agents/fetch?id=7579262188291333")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("X-Phantombuster-Key", "Qi448NiYzV6P40LB7ugZA8ML9rP4ceKUQ3xZUp0lL3A")
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Phantom API failed: " + response.code());
            }

            return response.body().string();

        } catch (Exception e) {
            throw new RuntimeException("Error fetching Phantom data", e);
        }
    }
}
