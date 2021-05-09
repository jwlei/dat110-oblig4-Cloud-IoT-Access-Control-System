package no.hvl.dat110.ac.restservice;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class AccessLog {
	

		private AtomicInteger cid;
		protected ConcurrentHashMap<Integer, AccessEntry> log;

		public AccessLog() {
			this.log = new ConcurrentHashMap<Integer, AccessEntry>();
			cid = new AtomicInteger(0);
		}
		
		public int add(String message) {

			int id = cid.getAndIncrement();
			AccessEntry entry = new AccessEntry(id, message);
			log.put(id, entry);
			return id;
		}

		public AccessEntry get(int id) {

			return log.get(id);

		}

		public void clear() {
			log.clear();
		}

		public String toJson() {			
			Gson gs = new Gson();
			String js = null;
			ConcurrentHashMap<Integer, AccessEntry> clone = new ConcurrentHashMap<Integer, AccessEntry>(log);
			
			js += "[";
			if (!log.isEmpty()) {
				Iterator<Entry<Integer, AccessEntry>> it = clone.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, AccessEntry> entry = it.next();
					js += gs.toJson(entry.getValue());
					it.remove();
					if (it.hasNext()) {
						js += ",";
					}
				}
				
			}
			js += "]";

			return js;
		}
}
