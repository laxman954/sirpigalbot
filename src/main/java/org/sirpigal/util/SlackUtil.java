package org.sirpigal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlackUtil {
	public static Map<String, String> slackMarkUpMap = new HashMap<String, String>() {
		{
			put("<strong>", "*");
			put("</strong>", "*");
			put("<b>", "*");
			put("</b>", "*");
			put("<br />", "\n");
			put("<em>", "_");
			put("</em>", "_");
			put("<i>", "_");
			put("</i>", "_");
			put("<strike>", "~");
			put("</strike>", "~");
			put("<code>", "`");
			put("</code>", "`");
			put("<pre>", "```");
			put("</pre>", "```");
		}
	};

	public static String htmlToSlacify(String htmlMarkup) {
		String regexp = "<strong>|</strong>|<b>|</b>|<em>|</em>|<i>|</i>|<strike>|</strike>|<code>|</code>|<pre>|</pre>|<br />";
		StringBuffer slackMarkup = new StringBuffer();
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(htmlMarkup);
		while (m.find())
			m.appendReplacement(slackMarkup, slackMarkUpMap.get(m.group()));
		m.appendTail(slackMarkup);
		return slackMarkup.toString();

	}
}
