package org.sirpigal.slack.bot;

import static org.sirpigal.util.SirpigalUtil.getSirpigalContactDetails;
import static org.sirpigal.util.SirpigalUtil.getTopNew;
import static org.sirpigal.util.SirpigalUtil.getXMLFromUrl;
import static org.sirpigal.util.SlackUtil.htmlToSlacify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.socket.WebSocketSession;
import org.w3c.dom.Document;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;

/**
 * @author lperumalm
 * @version 1.0.0 4-Oct-2016
 */
@Component
public class SirpigalBot extends Bot {

	private static final Logger logger = LoggerFactory.getLogger(SirpigalBot.class);

	/**
	 * Slack token from application.properties file. You can get your slack
	 * token next <a href="https://my.slack.com/services/new/bot">creating a new
	 * bot</a>.
	 */
	@Value("${slackBotToken}")
	private String slackToken;

	@Override
	public Bot getSlackBot() {
		return this;
	}

	@Override
	public String getSlackToken() {
		byte[] token = Base64Utils.decodeFromString(slackToken);
		return new String(token);
	}

	/**
	 * Invoked when the bot receives a direct mention (@sirpigalbot: message) or
	 * a direct message. NOTE: These two event types are added by jbot to make
	 * your task easier, Slack doesn't have any direct way to determine these
	 * type of events.
	 *
	 * @param session
	 * @param event
	 */
	@Controller(events = { EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE })
	public void onReceiveDM(WebSocketSession session, Event event) {
		reply(session, event, new Message("Hi, I am " + slackService.getCurrentUser().getName()));
	}

	/**
	 * Invoked when an item is pinned in the channel.
	 *
	 * @param session
	 * @param event
	 */
	@Controller(events = EventType.PIN_ADDED)
	public void onPinAdded(WebSocketSession session, Event event) {
		reply(session, event, new Message("Thanks for the pin! You can find all pinned items under channel details."));
	}

	@Controller(pattern = "(help)", next = "selectOption")
	public void sirpigalHelp(WebSocketSession session, Event event) {
		startConversation(event, "selectOption");
		reply(session, event, new Message(getHelpDetails()));

	}

	private String getHelpDetails() {
		StringBuilder message = new StringBuilder();
		message.append("Please Select Any one of the below option \n");
		message.append("1.Latest News").append("\n");
		message.append("2.Contact").append("\n");
		message.append("3.Blood Group Availability").append("\n");
		message.append("4.Send Queries").append("\n");
		return message.toString();
	}

	@Controller
	public void selectOption(WebSocketSession session, Event event) {
		String replyMessage = getMessageByOption(event.getText()) + "\n\n Enter *help* to continue..";
		reply(session, event, new Message(replyMessage));
		stopConversation(event);
	}

	public String getMessageByOption(String text) {
		String message = null;
		switch (text) {
		case "1":
			message = getLatestNews();
			break;
		case "2":
			message = getContactDetails();
			break;
		case "3":
			break;
		case "4":
			break;
		default:
			message = "INVALID";
			break;
		}
		return message;
	}

	public String getContactDetails() {
		return getSirpigalContactDetails();
	}

	public String getLatestNews() {
		Document doc = getXMLFromUrl("http://www.sirpigal.org/admin_bet/Download/sirpigal_post_details.xml");
		return htmlToSlacify(getTopNew(doc));
	}

}
