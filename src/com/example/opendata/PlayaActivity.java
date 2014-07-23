package com.example.opendata;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.library.Contract;
import com.example.library.DownloadAemetXmlTask;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.text.format.Time;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.widget.TextView;

public class PlayaActivity extends Activity {
	private String response = "";

	private int temp_max, temp_min;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playa);

		int position = getIntent().getIntExtra(Contract.LISTVIEW_POSITION, 0);

		TextView nombreMunicipio = (TextView) findViewById(R.id.nombre_municipio);
		TextView temperaturaMunicipio = (TextView) findViewById(R.id.temperatura_municipio);

		Cursor cursor = getContentResolver().query(Contract.MUNICIPIOS_URI, null,
				Contract.SELECTION_BY_ID_MUNICIPIO + (position + 1), null, null);

		// Descargar información meteorológica para municipio
		DownloadAemetXmlTask httpClient = new DownloadAemetXmlTask();
		httpClient.execute(Contract.URL_AEMET + cursor.getString(2) + Contract.XML_EXTENSION);
		try {
			response = httpClient.get();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Parse AEMET Response to XML document for Android

		XmlPullParser parser = Xml.newPullParser();
		readForecast(parser);
		nombreMunicipio.setText("Municipio " + cursor.getString(1));
		temperaturaMunicipio.setText("Temperatura máxima: " + temp_max + ", mínima: " + temp_min);

	}

	private void readForecast(XmlPullParser parser) {
		// Get current date
		Time time = new Time(Time.getCurrentTimezone());
		time.setToNow();
		String date = time.format("%Y-%m-%d");
		String forecastDate = "";

		try {
			int eventType = parser.getEventType();
			parser.setInput(new StringReader(response));
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					System.out.println("Start document");
				}
				else if (eventType == XmlPullParser.START_TAG) {
					if (parser.getName().equals("dia")) {
						forecastDate = parser.getAttributeValue(0);
					}
					if (forecastDate.equals(date)) {
						System.out.println(parser.getName());
						if (parser.getName().equals("temperatura")) {
							parser.nextTag();
							if (parser.getName().equals("maxima")) {
								parser.next();
								temp_max = Integer.parseInt(parser.getText());
							}
							// End Tag "maxima"
							parser.nextTag();
							// Start Tag "minima"
							parser.nextTag();
							if (parser.getName().equals("minima")) {
								parser.next();
								temp_min = Integer.parseInt(parser.getText());
							}
							break;
						}
					}
				}
				else if (eventType == XmlPullParser.END_TAG) {
					// System.out.println("End tag " + parser.getName());
				}
				else if (eventType == XmlPullParser.TEXT) {
					System.out.println("Text " + parser.getText());
				}
				eventType = parser.next();
			}
		}
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playa, menu);
		return true;
	}

}
