package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;




public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, resp) -> {
  		  resp.type("application/json");
  		});

		get("/accessdevice/hello", (req, resp) -> {
			
		 	Gson gs = new Gson();
		 	
		 	return gs.toJson("IoT Access Control Device");
		});
		post("/accessdevice/log/", (req, resp) -> {
				
				Gson gs = new Gson();
				AccessMessage message = gs.fromJson(req.body(), AccessMessage.class);
				
				int id = accesslog.add(message.getMessage());
				AccessEntry entry = new AccessEntry(id, message.getMessage());
				
				return gs.toJson(entry);
		});

		get("/accessdevice/log/", (req, resp) -> {

			return accesslog.toJson();
		});

		get("/accessdevice/log/:id", (req, resp) -> {
			Gson gs = new Gson();
			int id = Integer.parseInt(req.params("id"));
			AccessEntry entry = accesslog.get(id);

			return gs.toJson(entry);
		});

		put("/accessdevice/code/", (req, resp) -> {
			Gson gs = new Gson();
			AccessCode codes = gs.fromJson(req.body(), AccessCode.class);
			accesscode.setAccesscode((codes.getAccesscode()));
			return gs.toJson(codes);
		});

		get("/accessdevice/code/", (req, resp) -> {
			Gson gs = new Gson();
			return gs.toJson(accesscode);
		});

		delete("/accessdevice/log/", (req, resp) -> {

			accesslog.clear();

			return accesslog.toJson();
		});

	}
    
}
