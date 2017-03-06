package why777s.WebSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
@SuppressWarnings( { "rawtypes", "unchecked" })
public class KeywordFilter {
	private HashMap keysMap = new HashMap();
	private int matchType = 1;

	public KeywordFilter() {

	}

	public void addKeywords(List<String> keywords) {
		for (int i = 0; i < keywords.size(); i++) {
			String key = keywords.get(i).trim();
			HashMap nowhash = null;
			nowhash = keysMap;
			for (int j = 0; j < key.length(); j++) {
				char word = key.charAt(j);
				Object wordMap = nowhash.get(word);
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap<String, String> newWordHash = new HashMap<String, String>();
					newWordHash.put("isEnd", "0");
					nowhash.put(word, newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1) {
					nowhash.put("isEnd", "1");
				}
			}
		}
	}

	public void clearKeywords() {
		keysMap.clear();
	}



	private int checkKeyWords(String txt, int begin, int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = 0;
		for (int i = begin; i < l; i++) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}



	public Set<String> getTxtKeyWords(String txt) {
		Set set = new HashSet();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				i += len;
			} else {
				i++;
			}
		}
		txt = null;
		return set;
	}

	public String getFilerWords(String txt) {
		int l = txt.length();
		String filterWords=txt;
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				filterWords=filterWords.replaceAll(txt.substring(i, i + len),"*");
				i += len;
			} else {
				i++;
			}
		}
		return filterWords;
	}



	public boolean isContentKeyWords(String txt) {
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		txt = null;
		return false;
	}


	public int getMatchType() {
		return matchType;
	}


	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}


	public String addKeyWordFilter (String txt)throws IOException{
		KeywordFilter filter = new KeywordFilter();
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("sb");
		keywords.add("sv");

//		BufferedReader br = new BufferedReader(new FileReader("b.txt"));
//        String line = null;
//        while ((line = br.readLine()) != null) {
//            keywords.add(line.toUpperCase());
//        }
//        br.close();
        filter.addKeywords(keywords);
//		String txt = "一个SB,VS,SV,sv";
//		boolean boo = filter.isContentKeyWords(txt);
//		System.out.println(boo);
//		Set set = filter.getTxtKeyWords(txt);
		return filter.getFilerWords(txt);
	}
}