package com.example.opendata;

import java.util.ArrayList;

import com.example.library.Contract;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MunicipiosFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<String> municipios = new ArrayList<String>();
		Cursor cursorMunicipios = getActivity().getContentResolver().query(Contract.MUNICIPIOS_URI, null, null, null,
				null);
		while (!cursorMunicipios.isLast()) {
			municipios.add(cursorMunicipios.getString(1));
			cursorMunicipios.moveToNext();
		}
		ListAdapter myListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				municipios);
		setListAdapter(myListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_municipios, container, false);
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getActivity(), PlayaActivity.class);
		intent.putExtra(Contract.LISTVIEW_POSITION, position);
		startActivity(intent);
	}
}
