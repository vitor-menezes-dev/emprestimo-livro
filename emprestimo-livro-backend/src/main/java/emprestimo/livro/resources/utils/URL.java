package emprestimo.livro.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	public static List<Integer> decodeIntList(String s) {
		if (s == null || s.isEmpty())
			return null;
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

	public static String decodeParam(String s) {
		if (s == null)
			return null;
		try {
			return URLDecoder.decode(s, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<String> decodeStringList(String s) {
		if (s.isEmpty())
			return null;
		return Arrays.asList(s.split(",")).stream().map(x -> decodeParam(x)).collect(Collectors.toList());
	}

}
