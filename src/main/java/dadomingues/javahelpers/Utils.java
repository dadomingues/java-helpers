package dadomingues.javahelpers;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Utils {

    public static LocalDateTime toDate(String input, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(input);
    }

    public static String toDateString(String input, String format) {
        return toDate(input, format).toString();
    }

    public static String now() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String now(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String nowFhhmm() {
        return now("HHmm");
    }

    public static String nowFhhmm(char sep) {
        return now("HH"+sep+"mm");
    }

    public static String nowFhhmmss() {
        return now("HHmmss");
    }

    public static String nowFhhmmss(char sep) {
        return now("HH"+sep+"mm"+sep+"ss");
    }

    public static String nowFaaaammdd() {
        return now("yyyyMMdd");
    }

    public static String nowFaaaammdd(char sep) {
        return now("yyyy"+sep+"MM"+sep+"dd");
    }

    public static String nowFddmmaaaa() {
        return now("yyyyMMdd");
    }

    public static String nowFddmmaaaa(char sep) {
        return now("dd"+sep+"MM"+sep+"yyyy");
    }

    public static String aaaammddToString(String input, String format) {
        return toDateString(input, format);
    }

    public static int[] stringToIntArray(String str) {
        int[] num = new int[str.length()];
        for (int i=0; i<str.length(); i++ ) {
            num[i] = (int) str.charAt(i)-48;
        }
        return num;
    }

    public static char intToChar(int value) {
        return (char) (48 + value);
    }


	public static String UrlDecode(String valor) {
		try {
			return URLDecoder.decode(valor, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// https://stackoverflow.com/questions/2993649/how-to-normalize-a-url-in-java

    public static String canonicalizeUrl(final String taintedURL) throws MalformedURLException
    {
        final URL url;
        try {
            url = new URI(taintedURL).normalize().toURL();
        } catch (URISyntaxException e) {
            throw new MalformedURLException(e.getMessage());
        }

        final String path = url.getPath().replace("/$", "");
        final SortedMap<String, String> params = createParameterMap(url.getQuery());
        final int port = url.getPort();
        final String queryString;

        if (params != null) {
            // Some params are only relevant for user tracking, so remove the most commons ones.
            for (Iterator<String> i = params.keySet().iterator(); i.hasNext();) {
                final String key = i.next();
                if (key.startsWith("utm_") || key.contains("session")) i.remove();
            }
            queryString = "?" + canonicalizeQueryString(params);
        } else {
            queryString = "";
        }

        /*
        try {
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            String canonical = uri.toString();
        } catch (URISyntaxException e) {
            throw new MalformedURLException(e.getMessage());
        }
        */

        String portNumber = (port != -1 && port != 80) ? ":"+port : "";

        final StringBuffer sb = new StringBuffer();
        sb.append(url.getProtocol());
        sb.append("://");
        sb.append(url.getHost());
        sb.append(portNumber);
        sb.append(path);
        sb.append(queryString);

        return sb.toString();

    }

    /**
     * Takes a query string, separates the constituent name-value pairs, and
     * stores them in a SortedMap ordered by lexicographical order.
     * @return Null if there is no query string.
     */
    private static SortedMap<String, String> createParameterMap(final String queryString)
    {
        if (queryString == null || queryString.isEmpty()) return null;

        final String[] pairs = queryString.split("&");
        final Map<String, String> params = new HashMap<String, String>(pairs.length);

        for (final String pair : pairs) {
            if (pair.length() < 1) continue;

            String[] tokens = pair.split("=", 2);
            for (int j = 0; j < tokens.length; j++) {
                try {
                    tokens[j] = URLDecoder.decode(tokens[j], "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
            switch (tokens.length) {
                case 1:
                    if (pair.charAt(0) == '=') {
                        params.put("", tokens[0]);
                    } else {
                        params.put(tokens[0], "");
                    }
                    break;
                case 2:
                    params.put(tokens[0], tokens[1]);
                    break;
            }
        }

        return new TreeMap<String, String>(params);
    }


    /**
     * Canonicalize the query string.
     *
     * @param sortedParamMap Parameter name-value pairs in lexicographical order.
     * @return Canonical form of query string.
     */
    private static String canonicalizeQueryString(final SortedMap<String, String> sortedParamMap)
    {
        if (sortedParamMap == null || sortedParamMap.isEmpty()) return "";

        final StringBuffer sb = new StringBuffer(350);
        final Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet().iterator();

        while (iter.hasNext()) {
            final Map.Entry<String, String> pair = iter.next();
            sb.append(queryEncode(pair.getKey()));
            sb.append('=');
            sb.append(queryEncode(pair.getValue()));
            if (iter.hasNext()) sb.append('&');
        }

        return sb.toString();
    }

    /**
     * Percent-encode values according the RFC 3986. The built-in Java URLEncoder does not encode
     * according to the RFC, so we make the extra replacements.
     *
     * @param string Decoded string.
     * @return Encoded string per RFC 3986.
     */
    private static String queryEncode(final String string, final boolean rfc3986) {
        try {
            if (!rfc3986) {
                return queryEncode(string);
            }
            return URLEncoder.encode(string, "UTF-8")
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    private static String queryEncode(final String string)
    {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }


	public static String compactJson(final String json) {
		String compacted = "";
		String[] split = json.split("\"");
		List<String> list = Arrays.asList(split);
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			String item = iterator.next();
			if(item.length() > 30){
				String substring = item.substring(0,30);
				compacted += substring + "...";
			} else {
				compacted += item;
			}
			if(iterator.hasNext()){
				compacted += "\"";
			}
		}
		return compacted;
	}

}
