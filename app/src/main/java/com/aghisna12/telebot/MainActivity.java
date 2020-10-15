package com.aghisna12.telebot;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity 
{

	public long waktuDelay = 1000;
	public LinearLayout pesanLogScroll;
	public boolean diStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LinearLayout layoutUtama = new LinearLayout(this);
		ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutUtama.setLayoutParams(layoutParam);
		layoutUtama.setOrientation(LinearLayout.VERTICAL);
		layoutUtama.setGravity(Gravity.CENTER | Gravity.TOP);
		LinearLayout layoutCredits = new LinearLayout(this);
		layoutCredits.setLayoutParams(layoutParam);
		layoutCredits.setGravity(Gravity.CENTER);
		TextView credits = new TextView(this);
		credits.setGravity(Gravity.CENTER);
		credits.setText("Local Server Bot Sederhana\nBy @Aghisna12\n");
		//credits.setTextColor(Color.BLUE);
		credits.setTextSize(18.0f);
		layoutCredits.addView(credits);
		layoutUtama.addView(layoutCredits);
		final EditText editToken = new EditText(this);
		editToken.setHint("Masukan Bot Token...");
		layoutUtama.addView(editToken);
		LinearLayout layoutTombol = new LinearLayout(this);
		layoutTombol.setLayoutParams(layoutParam);
		layoutTombol.setGravity(Gravity.CENTER);
		final Button tombolMulai = new Button(this);
		tombolMulai.setText("Mulai");
		final Button tombolStop = new Button(this);
		tombolStop.setVisibility(View.GONE);
		tombolStop.setText("Stop");
		final Button tombolHapusLog = new Button(this);
		tombolHapusLog.setVisibility(View.GONE);
		tombolHapusLog.setText("Hapus Log");
		tombolMulai.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					tombolMulai.setVisibility(View.GONE);
					tombolStop.setVisibility(View.VISIBLE);
					tombolHapusLog.setVisibility(View.VISIBLE);
					MainActivity.this.startBot(editToken.getText().toString());
				}
			});
		tombolStop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					MainActivity.this.diStop = true;
					tombolMulai.setVisibility(View.VISIBLE);
					tombolStop.setVisibility(View.GONE);
					tombolHapusLog.setVisibility(View.GONE);
				}
			});
		tombolHapusLog.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					MainActivity.this.pesanLogScroll.removeAllViews();
				}
			});
		layoutTombol.addView(tombolMulai);
		layoutTombol.addView(tombolStop);
		layoutTombol.addView(tombolHapusLog);
		layoutUtama.addView(layoutTombol);
		pesanLogScroll = new LinearLayout(this);
		pesanLogScroll.setLayoutParams(layoutParam);
		pesanLogScroll.setOrientation(LinearLayout.VERTICAL);
		pesanLogScroll.setPadding(5, 5, 5, 5);
		LinearLayout layoutLog = new LinearLayout(this);
		layoutLog.setLayoutParams(layoutParam);
		layoutLog.setGravity(Gravity.CENTER);
		TextView log = new TextView(this);
		log.setGravity(Gravity.CENTER);
		log.setText("Pesan Log\n");
		log.setTextColor(Color.WHITE);
		log.setTextSize(18.0f);
		layoutLog.addView(log);
		pesanLogScroll.addView(layoutLog);
		ScrollView logScroll = new ScrollView(this);
		//logScroll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		logScroll.setBackgroundColor(Color.BLACK);
		logScroll.addView(pesanLogScroll);
		layoutUtama.addView(logScroll);
		setContentView(layoutUtama);
    }

	public void startBot(String token)
	{
		final Telegram tg = new Telegram(token);
		new Thread(new Runnable() {
				@Override
				public void run()
				{
					android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
					if (tg != null)
					{
						final String me = tg.getMe();
						MainActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run()
								{
									TextView logMulai = new TextView(MainActivity.this);
									logMulai.setText("Memulai Bot : " + me);
									logMulai.setTextColor(Color.YELLOW);
									MainActivity.this.pesanLogScroll.addView(logMulai);
								}
							});
						int lastOffset = 0;
						String curUpdates = "";
						while (!MainActivity.this.diStop)
						{
							try
							{
								final String updates = tg.getUpdates(lastOffset);
								if (curUpdates.length() != updates.length())
								{
									curUpdates = updates;
									JSONObject jsonUpdates = new JSONObject(updates);
									if (jsonUpdates.has("result")) 
									{
										JSONArray arrayUpdates = (JSONArray)jsonUpdates.getJSONArray("result");
										if (arrayUpdates.length() > 0)
										{
											for (int i = 0; i < arrayUpdates.length(); i++)
											{
												JSONObject objectUpdates = (JSONObject) arrayUpdates.get(i);
												if (objectUpdates.has("update_id"))
												{
													int update_id = (int)objectUpdates.get("update_id");
													if (update_id > 0)
													{
														lastOffset = update_id + 1;
													}
													if (objectUpdates.has("message"))
													{
														JSONObject messageObject = (JSONObject)objectUpdates.getJSONObject("message");
														if (messageObject.has("chat") && messageObject.has("text"))
														{
															JSONObject chatObject = (JSONObject)messageObject.getJSONObject("chat");
															int chat_id = (int)chatObject.get("id");
															String message_text = (String)messageObject.get("text");
															final String respon = tg.sendMessage(String.valueOf(chat_id), message_text, null);
															MainActivity.this.runOnUiThread(new Runnable() {
																	@Override
																	public void run()
																	{
																		TextView logTerima = new TextView(MainActivity.this);
																		logTerima.setText("Receive : " + updates);
																		logTerima.setTextColor(Color.RED);
																		MainActivity.this.pesanLogScroll.addView(logTerima);
																		TextView logKirim = new TextView(MainActivity.this);
																		logKirim.setText("Request : " + respon);
																		logKirim.setTextColor(Color.BLUE);
																		MainActivity.this.pesanLogScroll.addView(logKirim);
																	}
																});
														}
													}
												}
											}
										}
									}
								}
								Thread.sleep(waktuDelay);
							}
							catch (InterruptedException it)
							{
								Log.e("ErrorBot it : ", it.getMessage());
							}
							catch (JSONException je)
							{
								Log.e("ErrorBot je : ", je.getMessage());
							}
						}
					}
				}
			}).start();
	}
}
