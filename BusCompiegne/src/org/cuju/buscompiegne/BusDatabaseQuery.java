package org.cuju.buscompiegne;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class BusDatabaseQuery {

	static JSONArray data_cached = null;

	public static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is, "UTF-8")
				.useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static JSONArray getJSon(Activity act) {
		if (data_cached != null) {
			return data_cached;
		}
		InputStream input;
		try {
			input = act.getAssets().open("data.json");
			data_cached = new JSONArray(convertStreamToString(input));
			return data_cached;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> stations_list(Activity act){
		return stations_list(act,-1);
	}
	public static ArrayList<String> stations_list(Activity act,int dir){
		return stations_list(act,null,-1);
	}
	public static ArrayList<String> stations_list(Activity act,String line_name,int dir) {
		ArrayList<String> list = new ArrayList<String>();
		JSONArray data = getJSon(act);
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject line = (JSONObject) data.get(i);
				if(line_name == null || line_name.equals(line.getString("name"))){
					if(dir == -1){
						stations(0, list, line);
						stations(1, list, line);
					}
					else{
						stations(dir,list,line);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return removeDuplicates(list);
	}

	private static void stations(int dir, ArrayList<String> list,JSONObject line) throws JSONException {
		JSONArray rows = line.getJSONArray("dirs").getJSONArray(dir);
		for (int j = 0; j < rows.length(); j++) {
			String name = (rows.getJSONArray(j).getString(0)).split(",")[0];
			list.add(name);
		}
	}
	
	static ArrayList<String> removeDuplicates(List<String> l) {
		Set<String> set = new HashSet<String>(l);
		return new ArrayList<String>(set);
	}
	public static ArrayList<String> timetable(Activity act, String station_name,int dir) {
		ArrayList<String> list = new ArrayList<String>();
		JSONArray data = getJSon(act);
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject line = (JSONObject) data.get(i);
				String line_name = line.getString("name");
				JSONArray rows = line.getJSONArray("dirs").getJSONArray(dir);
				for (int j = 0; j < rows.length(); j++) {
					String[] table =(rows.getJSONArray(j).getString(0)).split(",");
					String name = table[0];
					if(name.equalsIgnoreCase(station_name)){
						for(int z = 1; z < table.length;z++){
							if(table[z].length() > 2){
								list.add(table[z] + " - " + line_name);
							}
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/* Return every time where you can take the bus from station A to get to station B */
	public static ArrayList<String> search(Activity act, String stationA, String stationB){
		ArrayList<String> list = new ArrayList<String>();
		JSONArray data = getJSon(act);
		for (int line_i = 0; line_i < data.length(); line_i++) {
			try {
				JSONObject line = (JSONObject) data.get(line_i);
				String line_name = line.getString("name");
				for(int dir = 0; dir < 2;dir++){
					JSONArray rows = line.getJSONArray("dirs").getJSONArray(dir);
					String[] stAarr = null;
					int stAarr_index = rows.length();
					String[] stBarr = null;
					for (int j = 0; j < rows.length(); j++) {
						String[] table =(rows.getJSONArray(j).getString(0)).split(",");
						String name = table[0];
						if(name.equalsIgnoreCase(stationA)){
							stAarr = table;
							stAarr_index = j;
						}
						if(name.equalsIgnoreCase(stationB) && j >= stAarr_index){
							stBarr = table;
						}
					}
					if (stAarr != null && stBarr != null){
						for (int i = 1; i < stAarr.length;i++){
						String a = stAarr[i];
						String b = stBarr[i];
						if( a.length() >  2 && b.length() > 2){
							list.add(a + " > " + b + " ( "+line_name+" )");
							}
						}
					}
				}
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		if(list.isEmpty()){
			list.add("Aucun résultat");
		}
		return list;
	}
}
