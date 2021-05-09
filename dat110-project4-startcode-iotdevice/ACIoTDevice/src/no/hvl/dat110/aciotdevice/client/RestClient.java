package no.hvl.dat110.aciotdevice.client;


import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static no.hvl.dat110.aciotdevice.client.Configuration.HOST;
import static no.hvl.dat110.aciotdevice.client.Configuration.PORT;

import java.io.IOException;

public class RestClient {

	    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	    public static final String URL = "http://" + HOST + ":" + PORT;
	    private static final String LOGPATH = "/accessdevice/log/";
	    private static final String CODEPATH = "/accessdevice/code/";
	    private static final Gson gson = new Gson();
	    private final OkHttpClient client;


	    
	    public RestClient() {
	        this.client = new OkHttpClient();
	    }

	    public void doPostAccessLog(String message) {

	        RequestBody body = RequestBody.create(JSON, gson.toJson(new AccessMessage(message)));
	        Request req = new Request.Builder().url(URL + LOGPATH).post(body).build();

	        try {
	            Response resp = client.newCall(req).execute();
	            String json = resp.body().string();
	            System.out.println("json from doPostAccessLog: " + json);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public AccessCode doGetAccessCode() {

	        Request req = new Request.Builder().url(URL + CODEPATH).get().build();
	        try {
	            Response resp = client.newCall(req).execute();
	            String json = resp.body().string();
	            System.out.println("json from doGetAccessCode: " + json);
	            return gson.fromJson(json, AccessCode.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
}
