/**
 *This library was generated from 'http://localhost/aghisna/libgram.php?source=java&optional=true'
 *Repository Library : https://github.com/Aghisna12/Telegram-Library-Generator
 *Date : 2020-10-14 19:58:13

 *Library Name : Telegram
 *Language Code : java
 *Credits : Aghisna12
 */

package com.aghisna12.telebot;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class Telegram
{

	/**
	 *initialize field
	 */
	public String token;
	public String urlApi;
	public URL urlLongPoll;
	public HttpURLConnection httpLongPoll;
	public InputStream inputStreamLongPoll;
	public BufferedReader bufferedReaderLongPoll;

	/**
	 *initialize constructor
	 */
	Telegram(String token)
	{
		this.token = token;
		this.urlApi = "https://api.telegram.org/bot";
	}

	public void clearLongPoll()
	{
		try
		{
			if (this.inputStreamLongPoll != null)
			{
				this.inputStreamLongPoll.close();
			}
			if (this.bufferedReaderLongPoll != null)
			{
				this.bufferedReaderLongPoll.close();
			}
			if (this.httpLongPoll != null)
			{
				this.httpLongPoll.disconnect();
			}
		}
		catch (IOException e)
		{
			Log.d("ErrorBot : ", e.getMessage());
		}
	}

	/**
	 *httpurlconnection
	 */
	public String httpLongPoll(int offset)
	{
		try
		{
			StringBuffer stringBuffer = new StringBuffer();
			if (this.token == null)
			{
				return "Bot Token is required";
			}
			if (this.urlLongPoll == null)
			{
				this.urlLongPoll = new URL(this.urlApi + this.token + "/getUpdates");
			}
			if (this.httpLongPoll == null)
			{
				this.httpLongPoll = (HttpsURLConnection) urlLongPoll.openConnection();
				this.httpLongPoll.setReadTimeout(10000);
				this.httpLongPoll.setDoOutput(true);
				this.httpLongPoll.setDoInput(true);
				this.httpLongPoll.setConnectTimeout(15000);
				this.httpLongPoll.setRequestMethod("POST");
			}
			if (this.urlLongPoll != null && this.httpLongPoll != null)
			{
				this.httpLongPoll = (HttpsURLConnection) this.urlLongPoll.openConnection();
				this.httpLongPoll.setRequestMethod("POST");
				this.httpLongPoll.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				OutputStream os = this.httpLongPoll.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
				writer.write("offset=" + String.valueOf(offset));
				writer.flush();
				writer.close();
				os.close();
				this.inputStreamLongPoll = this.httpLongPoll.getInputStream();
				if (this.inputStreamLongPoll != null)
				{
					this.bufferedReaderLongPoll = new BufferedReader(new InputStreamReader(this.inputStreamLongPoll));
					if (bufferedReaderLongPoll != null)
					{
						String line = "";
						while ((line = bufferedReaderLongPoll.readLine()) != null)
						{
							stringBuffer.append(line).append("\n");
						}
						return stringBuffer.toString();
					}
				}
			}
		}
		catch (ProtocolException e1)
		{
			Log.d("ErrorBot e1 : ", e1.getMessage());
		}
		catch (MalformedURLException e2)
		{
			Log.d("ErrorBot e2 : ", e2.getMessage());
		}
		catch (IOException e3)
		{
			Log.d("ErrorBot e3 : ", e3.getMessage());
		}
		this.clearLongPoll();
		return "null";
	}

	/**
	 *httpurlconnection
	 */
	public String httpRequest(final String urls, final String request_method, final String parameter)
	{
		try
		{
			URL url = new URL(urls);
			HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setConnectTimeout(15000);
			httpURLConnection.setRequestMethod(request_method);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if (request_method == "POST" && parameter != "")
			{
				OutputStream os = httpURLConnection.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
				writer.write(parameter);
				writer.flush();
				writer.close();
				os.close();
			}
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String line = "";

			while ((line = bufferedReader.readLine()) != null)
			{
				stringBuffer.append(line).append("\n");
			}
			inputStream.close();
			bufferedReader.close();
			httpURLConnection.disconnect();
			return stringBuffer.toString();
		}
		catch (ProtocolException e1)
		{
			return "ErrorBot e1 : " + e1.getMessage();
		}
		catch (MalformedURLException e2)
		{
			return "ErrorBot e2 : " + e2.getMessage();
		}
		catch (IOException e3)
		{
			return "ErrorBot e3 : " + e3.getMessage();
		}
	}

	/**
	 *request api telegram
	 */
	public String requestApi(String method, HashMap<String, Object> data)
	{
		String hasil = "";
		if (this.token == null)
		{
			return "Bot Token is required";
		}
		if (method == "")
		{
			return "Method is required";
		}
		else
		{
			try
			{
				String parameter = "";
				if (data != null)
				{
					boolean pertama = false;
					for (Object o : ((HashMap) data).entrySet())
					{
						if (o != null)
						{
							Map.Entry entry = (Map.Entry) o;
							if (entry != null)
							{
								Object key = entry.getKey();
								Object value = entry.getValue();
								if (key != null && value != null)
								{
									if (!pertama)
									{
										pertama = true;
										parameter = parameter + key.toString() + "=" + URLEncoder.encode(value.toString(), "UTF-8");
									}
									else
									{
										parameter = parameter + "&" + key.toString() + "=" + URLEncoder.encode(value.toString(), "UTF-8");
									}
								}
							}
						}
					}
				}
				if (parameter !=  "")
				{
					hasil = this.httpRequest(this.urlApi + this.token + "/" + method, "POST", parameter);
				}
				else
				{
					hasil = this.httpRequest(this.urlApi + this.token + "/" + method, "GET", parameter);
				}
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return hasil;
	}

	/**
	 *method getUpdate
	 */
	public String getUpdates(int offset)
	{
		return this.httpLongPoll(offset);
	}

	/**
	 *A simple method for testing your bot's auth token. Requires no parameters. Returns basic information about the bot in form of a User object.
	 */
	public String getMe()
	{
		return this.requestApi("getMe", null);
	}

	/**
	 *Use this method to send text messages. On success, the sent Message is returned.
	 */
	public String sendMessage(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Text of the message to be sent, 1-4096 characters after entities parsing
		final String text, //String

		final HashMap<String, Object> optionals
	/**
	 *Mode for parsing entities in the message text. See formatting options for more details.
	 *final String parse_mode, //String (Optional)

	 *Disables link previews for links in this message
	 *final boolean disable_web_page_preview, //boolean (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendMessage", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("text", text);
					put("optionals", optionals);
					/**
					 *put("parse_mode",parse_mode);
					 *put("disable_web_page_preview",disable_web_page_preview);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *The Bot API supports basic formatting for messages. You can use bold, italic, underlined and strikethrough text, as well as inline links and pre-formatted code in your bots' messages. Telegram clients will render them accordingly. You can use either markdown-style or HTML-style formatting.Note that Telegram clients will display an alert to the user before opening an inline link ('Open this link?' together with the full URL).Message entities can be nested, providing following restrictions are met:
	 - If two entities has common characters then one of them is fully contained inside another.
	 - bold, italic, underline and strikethrough entities can contain and to be contained in any other entities, except pre and code.
	 - All other entities can't contain each other.Links tg://user?id=<user_id> can be used to mention a user by their ID without using a username. Please note:To use this mode, pass MarkdownV2 in the parse_mode field. Use the following syntax in your message:Please note:To use this mode, pass HTML in the parse_mode field. The following tags are currently supported:Please note:This is a legacy mode, retained for backward compatibility. To use this mode, pass Markdown in the parse_mode field. Use the following syntax in your message:Please note:
	 *
	 *public String Formatting options() {
	 *}
	 */

	/**
	 *Use this method to forward messages of any kind. On success, the sent Message is returned.
	 */
	public String forwardMessage(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
		final String from_chat_id, //String

		//Message identifier in the chat specified in from_chat_id
		final int message_id //int
	)
	{
		return this.requestApi("forwardMessage", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("from_chat_id", from_chat_id);
					put("message_id", message_id);
				}});
	}

	/**
	 *Use this method to send photos. On success, the sent Message is returned.
	 */
	public String sendPhoto(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Photo to send. Pass a file_id as String to send a photo that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new photo using multipart/form-data. More info on Sending Files »
		final String photo, //String

		final HashMap<String, Object> optionals
	/**
	 *Photo caption (may also be used when resending photos by file_id), 0-1024 characters after entities parsing
	 *final String caption, //String (Optional)

	 *Mode for parsing entities in the photo caption. See formatting options for more details.
	 *final String parse_mode, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendPhoto", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("photo", photo);
					put("optionals", optionals);
					/**
					 *put("caption",caption);
					 *put("parse_mode",parse_mode);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .MP3 or .M4A format. On success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.For sending voice messages, use the sendVoice method instead.
	 */
	public String sendAudio(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Audio file to send. Pass a file_id as String to send an audio file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
		final String audio, //String

		final HashMap<String, Object> optionals
	/**
	 *Audio caption, 0-1024 characters after entities parsing
	 *final String caption, //String (Optional)

	 *Mode for parsing entities in the audio caption. See formatting options for more details.
	 *final String parse_mode, //String (Optional)

	 *Duration of the audio in seconds
	 *final int duration, //int (Optional)

	 *Performer
	 *final String performer, //String (Optional)

	 *Track name
	 *final String title, //String (Optional)

	 *Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 *final String thumb, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendAudio", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("audio", audio);
					put("optionals", optionals);
					/**
					 *put("caption",caption);
					 *put("parse_mode",parse_mode);
					 *put("duration",duration);
					 *put("performer",performer);
					 *put("title",title);
					 *put("thumb",thumb);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send general files. On success, the sent Message is returned. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public String sendDocument(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//File to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
		final String document, //String

		final HashMap<String, Object> optionals
	/**
	 *Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 *final String thumb, //String (Optional)

	 *Document caption (may also be used when resending documents by file_id), 0-1024 characters after entities parsing
	 *final String caption, //String (Optional)

	 *Mode for parsing entities in the document caption. See formatting options for more details.
	 *final String parse_mode, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendDocument", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("document", document);
					put("optionals", optionals);
					/**
					 *put("thumb",thumb);
					 *put("caption",caption);
					 *put("parse_mode",parse_mode);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
	 */
	public String sendVideo(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using multipart/form-data. More info on Sending Files »
		final String video, //String

		final HashMap<String, Object> optionals
	/**
	 *Duration of sent video in seconds
	 *final int duration, //int (Optional)

	 *Video width
	 *final int width, //int (Optional)

	 *Video height
	 *final int height, //int (Optional)

	 *Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 *final String thumb, //String (Optional)

	 *Video caption (may also be used when resending videos by file_id), 0-1024 characters after entities parsing
	 *final String caption, //String (Optional)

	 *Mode for parsing entities in the video caption. See formatting options for more details.
	 *final String parse_mode, //String (Optional)

	 *Pass True, if the uploaded video is suitable for streaming
	 *final boolean supports_streaming, //boolean (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendVideo", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("video", video);
					put("optionals", optionals);
					/**
					 *put("duration",duration);
					 *put("width",width);
					 *put("height",height);
					 *put("thumb",thumb);
					 *put("caption",caption);
					 *put("parse_mode",parse_mode);
					 *put("supports_streaming",supports_streaming);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound). On success, the sent Message is returned. Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
	 */
	public String sendAnimation(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Animation to send. Pass a file_id as String to send an animation that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation from the Internet, or upload a new animation using multipart/form-data. More info on Sending Files »
		final String animation, //String

		final HashMap<String, Object> optionals
	/**
	 *Duration of sent animation in seconds
	 *final int duration, //int (Optional)

	 *Animation width
	 *final int width, //int (Optional)

	 *Animation height
	 *final int height, //int (Optional)

	 *Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 *final String thumb, //String (Optional)

	 *Animation caption (may also be used when resending animation by file_id), 0-1024 characters after entities parsing
	 *final String caption, //String (Optional)

	 *Mode for parsing entities in the animation caption. See formatting options for more details.
	 *final String parse_mode, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendAnimation", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("animation", animation);
					put("optionals", optionals);
					/**
					 *put("duration",duration);
					 *put("width",width);
					 *put("height",height);
					 *put("thumb",thumb);
					 *put("caption",caption);
					 *put("parse_mode",parse_mode);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .OGG file encoded with OPUS (other formats may be sent as Audio or Document). On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
	 */
	public String sendVoice(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Audio file to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
		final String voice, //String

		final HashMap<String, Object> optionals
	/**
	 *Voice message caption, 0-1024 characters after entities parsing
	 *final String caption, //String (Optional)

	 *Mode for parsing entities in the voice message caption. See formatting options for more details.
	 *final String parse_mode, //String (Optional)

	 *Duration of the voice message in seconds
	 *final int duration, //int (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendVoice", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("voice", voice);
					put("optionals", optionals);
					/**
					 *put("caption",caption);
					 *put("parse_mode",parse_mode);
					 *put("duration",duration);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long. Use this method to send video messages. On success, the sent Message is returned.
	 */
	public String sendVideoNote(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers (recommended) or upload a new video using multipart/form-data. More info on Sending Files ». Sending video notes by a URL is currently unsupported
		final String video_note, //String

		final HashMap<String, Object> optionals
	/**
	 *Duration of sent video in seconds
	 *final int duration, //int (Optional)

	 *Video width and height, i.e. diameter of the video message
	 *final int length, //int (Optional)

	 *Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 *final String thumb, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendVideoNote", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("video_note", video_note);
					put("optionals", optionals);
					/**
					 *put("duration",duration);
					 *put("length",length);
					 *put("thumb",thumb);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send a group of photos or videos as an album. On success, an array of the sent Messages is returned.
	 */
	public String sendMediaGroup(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//A JSON-serialized array describing photos and videos to be sent, must include 2-10 items
		final HashMap<String, Object> media, //HashMap<String, Object>

		final HashMap<String, Object> optionals
	/**
	 *Sends the messages silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the messages are a reply, ID of the original message
	 *final int reply_to_message_id //int (Optional)
	 */
	)
	{
		return this.requestApi("sendMediaGroup", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("media", media);
					put("optionals", optionals);
					/**
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 */
				}});
	}

	/**
	 *Use this method to send point on the map. On success, the sent Message is returned.
	 */
	public String sendLocation(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Latitude of the location
		final float latitude, //float

		//Longitude of the location
		final float longitude, //float

		final HashMap<String, Object> optionals
	/**
	 *Period in seconds for which the location will be updated (see Live Locations, should be between 60 and 86400.
	 *final int live_period, //int (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendLocation", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("latitude", latitude);
					put("longitude", longitude);
					put("optionals", optionals);
					/**
					 *put("live_period",live_period);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to edit live location messages. A location can be edited until its live_period expires or editing is explicitly disabled by a call to stopMessageLiveLocation. On success, if the edited message was sent by the bot, the edited Message is returned, otherwise True is returned.
	 */
	public String editMessageLiveLocation(
		//Latitude of new location
		final float latitude, //float

		//Longitude of new location
		final float longitude, //float

		final HashMap<String, Object> optionals
	/**
	 *Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 *final String chat_id, //String (Optional)

	 *Required if inline_message_id is not specified. Identifier of the message to edit
	 *final int message_id, //int (Optional)

	 *Required if chat_id and message_id are not specified. Identifier of the inline message
	 *final String inline_message_id, //String (Optional)

	 *A JSON-serialized object for a new inline keyboard.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("editMessageLiveLocation", new HashMap<String, Object>() {{
					put("latitude", latitude);
					put("longitude", longitude);
					put("optionals", optionals);
					/**
					 *put("chat_id",chat_id);
					 *put("message_id",message_id);
					 *put("inline_message_id",inline_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to stop updating a live location message before live_period expires. On success, if the message was sent by the bot, the sent Message is returned, otherwise True is returned.
	 */
	public String stopMessageLiveLocation(
		final HashMap<String, Object> optionals
	/**
	 *Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 *final String chat_id, //String (Optional)

	 *Required if inline_message_id is not specified. Identifier of the message with live location to stop
	 *final int message_id, //int (Optional)

	 *Required if chat_id and message_id are not specified. Identifier of the inline message
	 *final String inline_message_id, //String (Optional)

	 *A JSON-serialized object for a new inline keyboard.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("stopMessageLiveLocation", new HashMap<String, Object>() {{
					put("optionals", optionals);
					/**
					 *put("chat_id",chat_id);
					 *put("message_id",message_id);
					 *put("inline_message_id",inline_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send information about a venue. On success, the sent Message is returned.
	 */
	public String sendVenue(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Latitude of the venue
		final float latitude, //float

		//Longitude of the venue
		final float longitude, //float

		//Name of the venue
		final String title, //String

		//Address of the venue
		final String address, //String

		final HashMap<String, Object> optionals
	/**
	 *Foursquare identifier of the venue
	 *final String foursquare_id, //String (Optional)

	 *Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
	 *final String foursquare_type, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendVenue", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("latitude", latitude);
					put("longitude", longitude);
					put("title", title);
					put("address", address);
					put("optionals", optionals);
					/**
					 *put("foursquare_id",foursquare_id);
					 *put("foursquare_type",foursquare_type);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send phone contacts. On success, the sent Message is returned.
	 */
	public String sendContact(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Contact's phone number
		final String phone_number, //String

		//Contact's first name
		final String first_name, //String

		final HashMap<String, Object> optionals
	/**
	 *Contact's last name
	 *final String last_name, //String (Optional)

	 *Additional data about the contact in the form of a vCard, 0-2048 bytes
	 *final String vcard, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendContact", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("phone_number", phone_number);
					put("first_name", first_name);
					put("optionals", optionals);
					/**
					 *put("last_name",last_name);
					 *put("vcard",vcard);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send a native poll. On success, the sent Message is returned.
	 */
	public String sendPoll(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Poll question, 1-255 characters
		final String question, //String

		//A JSON-serialized list of answer options, 2-10 strings 1-100 characters each
		final String options, //String

		final HashMap<String, Object> optionals
	/**
	 *True, if the poll needs to be anonymous, defaults to True
	 *final boolean is_anonymous, //boolean (Optional)

	 *Poll type, “quiz” or “regular”, defaults to “regular”
	 *final String type, //String (Optional)

	 *True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
	 *final boolean allows_multiple_answers, //boolean (Optional)

	 *0-based identifier of the correct answer option, required for polls in quiz mode
	 *final int correct_option_id, //int (Optional)

	 *Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters with at most 2 line feeds after entities parsing
	 *final String explanation, //String (Optional)

	 *Mode for parsing entities in the explanation. See formatting options for more details.
	 *final String explanation_parse_mode, //String (Optional)

	 *Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
	 *final int open_period, //int (Optional)

	 *Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600 seconds in the future. Can't be used together with open_period.
	 *final int close_date, //int (Optional)

	 *Pass True, if the poll needs to be immediately closed. This can be useful for poll preview.
	 *final boolean is_closed, //boolean (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendPoll", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("question", question);
					put("options", options);
					put("optionals", optionals);
					/**
					 *put("is_anonymous",is_anonymous);
					 *put("type",type);
					 *put("allows_multiple_answers",allows_multiple_answers);
					 *put("correct_option_id",correct_option_id);
					 *put("explanation",explanation);
					 *put("explanation_parse_mode",explanation_parse_mode);
					 *put("open_period",open_period);
					 *put("close_date",close_date);
					 *put("is_closed",is_closed);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method to send an animated emoji that will display a random value. On success, the sent Message is returned.
	 */
	public String sendDice(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		final HashMap<String, Object> optionals
	/**
	 *Emoji on which the dice throw animation is based. Currently, must be one of “🎲”, “🎯”, or “🏀”. Dice can have values 1-6 for “🎲” and “🎯”, and values 1-5 for “🏀”. Defaults to “🎲”
	 *final String emoji, //String (Optional)

	 *Sends the message silently. Users will receive a notification with no sound.
	 *final boolean disable_notification, //boolean (Optional)

	 *If the message is a reply, ID of the original message
	 *final int reply_to_message_id, //int (Optional)

	 *Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 *final HashMap<String, Object> reply_markup //HashMap<String, Object> (Optional)
	 */
	)
	{
		return this.requestApi("sendDice", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("optionals", optionals);
					/**
					 *put("emoji",emoji);
					 *put("disable_notification",disable_notification);
					 *put("reply_to_message_id",reply_to_message_id);
					 *put("reply_markup",reply_markup);
					 */
				}});
	}

	/**
	 *Use this method when you need to tell the user that something is happening on the bot's side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status). Returns True on success.Example: The ImageBot needs some time to process a request and upload the image. Instead of sending a text message along the lines of “Retrieving image, please wait…”, the bot may use sendChatAction with action = upload_photo. The user will see a “sending photo” status for the bot.We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
	 */
	public String sendChatAction(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general files, find_location for location data, record_video_note or upload_video_note for video notes.
		final String action //String
	)
	{
		return this.requestApi("sendChatAction", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("action", action);
				}});
	}

	/**
	 *Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
	 */
	public String getUserProfilePhotos(
		//Unique identifier of the target user
		final int user_id, //int

		final HashMap<String, Object> optionals
	/**
	 *Sequential number of the first photo to be returned. By default, all photos are returned.
	 *final int offset, //int (Optional)

	 *Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
	 *final int limit //int (Optional)
	 */
	)
	{
		return this.requestApi("getUserProfilePhotos", new HashMap<String, Object>() {{
					put("user_id", user_id);
					put("optionals", optionals);
					/**
					 *put("offset",offset);
					 *put("limit",limit);
					 */
				}});
	}

	/**
	 *Use this method to get basic info about a file and prepare it for downloading. For the moment, bots can download files of up to 20MB in size. On success, a File object is returned. The file can then be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken from the response. It is guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by calling getFile again.Note: This function may not preserve the original file name and MIME type. You should save the file's MIME type and name (if available) when the File object is received.
	 */
	public String getFile(
		//File identifier to get info about
		final String file_id //String
	)
	{
		return this.requestApi("getFile", new HashMap<String, Object>() {{
					put("file_id", file_id);
				}});
	}

	/**
	 *Use this method to kick a user from a group, a supergroup or a channel. In the case of supergroups and channels, the user will not be able to return to the group on their own using invite links, etc., unless unbanned first. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
	 */
	public String kickChatMember(
		//Unique identifier for the target group or username of the target supergroup or channel (in the format @channelusername)
		final String chat_id, //String

		//Unique identifier of the target user
		final int user_id, //int

		final HashMap<String, Object> optionals
	/**
	 *Date when the user will be unbanned, unix time. If user is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever
	 *final int until_date //int (Optional)
	 */
	)
	{
		return this.requestApi("kickChatMember", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("user_id", user_id);
					put("optionals", optionals);
					/**
					 *put("until_date",until_date);
					 */
				}});
	}

	/**
	 *Use this method to unban a previously kicked user in a supergroup or channel. The user will not return to the group or channel automatically, but will be able to join via link, etc. The bot must be an administrator for this to work. Returns True on success.
	 */
	public String unbanChatMember(
		//Unique identifier for the target group or username of the target supergroup or channel (in the format @username)
		final String chat_id, //String

		//Unique identifier of the target user
		final int user_id //int
	)
	{
		return this.requestApi("unbanChatMember", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("user_id", user_id);
				}});
	}

	/**
	 *Use this method to restrict a user in a supergroup. The bot must be an administrator in the supergroup for this to work and must have the appropriate admin rights. Pass True for all permissions to lift restrictions from a user. Returns True on success.
	 */
	public String restrictChatMember(
		//Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
		final String chat_id, //String

		//Unique identifier of the target user
		final int user_id, //int

		//A JSON-serialized object for new user permissions
		final HashMap<String, Object> permissions, //HashMap<String, Object>

		final HashMap<String, Object> optionals
	/**
	 *Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be restricted forever
	 *final int until_date //int (Optional)
	 */
	)
	{
		return this.requestApi("restrictChatMember", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("user_id", user_id);
					put("permissions", permissions);
					put("optionals", optionals);
					/**
					 *put("until_date",until_date);
					 */
				}});
	}

	/**
	 *Use this method to promote or demote a user in a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Pass False for all boolean parameters to demote a user. Returns True on success.
	 */
	public String promoteChatMember(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Unique identifier of the target user
		final int user_id, //int

		final HashMap<String, Object> optionals
	/**
	 *Pass True, if the administrator can change chat title, photo and other settings
	 *final boolean can_change_info, //boolean (Optional)

	 *Pass True, if the administrator can create channel posts, channels only
	 *final boolean can_post_messages, //boolean (Optional)

	 *Pass True, if the administrator can edit messages of other users and can pin messages, channels only
	 *final boolean can_edit_messages, //boolean (Optional)

	 *Pass True, if the administrator can delete messages of other users
	 *final boolean can_delete_messages, //boolean (Optional)

	 *Pass True, if the administrator can invite new users to the chat
	 *final boolean can_invite_users, //boolean (Optional)

	 *Pass True, if the administrator can restrict, ban or unban chat members
	 *final boolean can_restrict_members, //boolean (Optional)

	 *Pass True, if the administrator can pin messages, supergroups only
	 *final boolean can_pin_messages, //boolean (Optional)

	 *Pass True, if the administrator can add new administrators with a subset of their own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by him)
	 *final boolean can_promote_members //boolean (Optional)
	 */
	)
	{
		return this.requestApi("promoteChatMember", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("user_id", user_id);
					put("optionals", optionals);
					/**
					 *put("can_change_info",can_change_info);
					 *put("can_post_messages",can_post_messages);
					 *put("can_edit_messages",can_edit_messages);
					 *put("can_delete_messages",can_delete_messages);
					 *put("can_invite_users",can_invite_users);
					 *put("can_restrict_members",can_restrict_members);
					 *put("can_pin_messages",can_pin_messages);
					 *put("can_promote_members",can_promote_members);
					 */
				}});
	}

	/**
	 *Use this method to set a custom title for an administrator in a supergroup promoted by the bot. Returns True on success.
	 */
	public String setChatAdministratorCustomTitle(
		//Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
		final String chat_id, //String

		//Unique identifier of the target user
		final int user_id, //int

		//New custom title for the administrator; 0-16 characters, emoji are not allowed
		final String custom_title //String
	)
	{
		return this.requestApi("setChatAdministratorCustomTitle", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("user_id", user_id);
					put("custom_title", custom_title);
				}});
	}

	/**
	 *Use this method to set default chat permissions for all members. The bot must be an administrator in the group or a supergroup for this to work and must have the can_restrict_members admin rights. Returns True on success.
	 */
	public String setChatPermissions(
		//Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
		final String chat_id, //String

		//New default chat permissions
		final HashMap<String, Object> permissions //HashMap<String, Object>
	)
	{
		return this.requestApi("setChatPermissions", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("permissions", permissions);
				}});
	}

	/**
	 *Use this method to generate a new invite link for a chat; any previously generated link is revoked. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns the new invite link as String on success.Note: Each administrator in a chat generates their own invite links. Bots can't use invite links generated by other administrators. If you want your bot to work with invite links, it will need to generate its own link using exportChatInviteLink — after this the link will become available to the bot via the getChat method. If your bot needs to generate a new invite link replacing its previous one, use exportChatInviteLink again.
	 */
	public String exportChatInviteLink(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id //String
	)
	{
		return this.requestApi("exportChatInviteLink", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method to set a new profile photo for the chat. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
	 */
	public String setChatPhoto(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//New chat photo, uploaded using multipart/form-data
		final HashMap<String, Object> photo //HashMap<String, Object>
	)
	{
		return this.requestApi("setChatPhoto", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("photo", photo);
				}});
	}

	/**
	 *Use this method to delete a chat photo. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
	 */
	public String deleteChatPhoto(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id //String
	)
	{
		return this.requestApi("deleteChatPhoto", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method to change the title of a chat. Titles can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
	 */
	public String setChatTitle(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//New chat title, 1-255 characters
		final String title //String
	)
	{
		return this.requestApi("setChatTitle", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("title", title);
				}});
	}

	/**
	 *Use this method to change the description of a group, a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
	 */
	public String setChatDescription(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		final HashMap<String, Object> optionals
	/**
	 *New chat description, 0-255 characters
	 *final String description //String (Optional)
	 */
	)
	{
		return this.requestApi("setChatDescription", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("optionals", optionals);
					/**
					 *put("description",description);
					 */
				}});
	}

	/**
	 *Use this method to pin a message in a group, a supergroup, or a channel. The bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in the supergroup or 'can_edit_messages' admin right in the channel. Returns True on success.
	 */
	public String pinChatMessage(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id, //String

		//Identifier of a message to pin
		final int message_id, //int

		final HashMap<String, Object> optionals
	/**
	 *Pass True, if it is not necessary to send a notification to all chat members about the new pinned message. Notifications are always disabled in channels.
	 *final boolean disable_notification //boolean (Optional)
	 */
	)
	{
		return this.requestApi("pinChatMessage", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("message_id", message_id);
					put("optionals", optionals);
					/**
					 *put("disable_notification",disable_notification);
					 */
				}});
	}

	/**
	 *Use this method to unpin a message in a group, a supergroup, or a channel. The bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in the supergroup or 'can_edit_messages' admin right in the channel. Returns True on success.
	 */
	public String unpinChatMessage(
		//Unique identifier for the target chat or username of the target channel (in the format @channelusername)
		final String chat_id //String
	)
	{
		return this.requestApi("unpinChatMessage", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
	 */
	public String leaveChat(
		//Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
		final String chat_id //String
	)
	{
		return this.requestApi("leaveChat", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
	 */
	public String getChat(
		//Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
		final String chat_id //String
	)
	{
		return this.requestApi("getChat", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method to get a list of administrators in a chat. On success, returns an Array of ChatMember objects that contains information about all chat administrators except other bots. If the chat is a group or a supergroup and no administrators were appointed, only the creator will be returned.
	 */
	public String getChatAdministrators(
		//Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
		final String chat_id //String
	)
	{
		return this.requestApi("getChatAdministrators", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method to get the number of members in a chat. Returns Int on success.
	 */
	public String getChatMembersCount(
		//Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
		final String chat_id //String
	)
	{
		return this.requestApi("getChatMembersCount", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method to get information about a member of a chat. Returns a ChatMember object on success.
	 */
	public String getChatMember(
		//Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
		final String chat_id, //String

		//Unique identifier of the target user
		final int user_id //int
	)
	{
		return this.requestApi("getChatMember", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("user_id", user_id);
				}});
	}

	/**
	 *Use this method to set a new group sticker set for a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
	 */
	public String setChatStickerSet(
		//Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
		final String chat_id, //String

		//Name of the sticker set to be set as the group sticker set
		final String sticker_set_name //String
	)
	{
		return this.requestApi("setChatStickerSet", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
					put("sticker_set_name", sticker_set_name);
				}});
	}

	/**
	 *Use this method to delete a group sticker set from a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
	 */
	public String deleteChatStickerSet(
		//Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
		final String chat_id //String
	)
	{
		return this.requestApi("deleteChatStickerSet", new HashMap<String, Object>() {{
					put("chat_id", chat_id);
				}});
	}

	/**
	 *Use this method to send answers to callback queries sent from inline keyboards. The answer will be displayed to the user as a notification at the top of the chat screen or as an alert. On success, True is returned.Alternatively, the user can be redirected to the specified Game URL. For this option to work, you must first create a game for your bot via @Botfather and accept the terms. Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a parameter.
	 */
	public String answerCallbackQuery(
		//Unique identifier for the query to be answered
		final String callback_query_id, //String

		final HashMap<String, Object> optionals
	/**
	 *Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters
	 *final String text, //String (Optional)

	 *If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
	 *final boolean show_alert, //boolean (Optional)

	 *URL that will be opened by the user's client. If you have created a Game and accepted the conditions via @Botfather, specify the URL that opens your game — note that this will only work if the query comes from a callback_game button.
	 //
	 //Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a parameter.
	 *final String url, //String (Optional)

	 *The maximum amount of time in seconds that the result of the callback query may be cached client-side. Telegram apps will support caching starting in version 3.14. Defaults to 0.
	 *final int cache_time //int (Optional)
	 */
	)
	{
		return this.requestApi("answerCallbackQuery", new HashMap<String, Object>() {{
					put("callback_query_id", callback_query_id);
					put("optionals", optionals);
					/**
					 *put("text",text);
					 *put("show_alert",show_alert);
					 *put("url",url);
					 *put("cache_time",cache_time);
					 */
				}});
	}

	/**
	 *Use this method to change the list of the bot's commands. Returns True on success.
	 */
	public String setMyCommands(
		//A JSON-serialized list of bot commands to be set as the list of the bot's commands. At most 100 commands can be specified.
		final HashMap<String, Object> commands //HashMap<String, Object>
	)
	{
		return this.requestApi("setMyCommands", new HashMap<String, Object>() {{
					put("commands", commands);
				}});
	}

	/**
	 *Use this method to get the current list of the bot's commands. Requires no parameters. Returns Array of BotCommand on success.
	 */
	public String getMyCommands()
	{
		return this.requestApi("getMyCommands", null);
	}

	/**
	 *Methods and objects used in the inline mode are described in the Inline mode section.
	 *
	 *public String Inline mode methods() {
	 *}
	 */

}
