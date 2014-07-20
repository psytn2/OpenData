package com.example.opendata;

import com.example.library.Contract;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	private ViewPager viewPager;

	private TabsPagerAdapter mAdapter;

	private ActionBar actionBar;

	// Tab titles
	private String[] tabs = { "Mapa", "Municipios", "Favoritos" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Inicialización
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Actualizar municipios base de datos
		ContentValues values = new ContentValues();
		for (String municipio : Contract.MUNICIPIOS_ARRAY) {
			values.put(Contract.KEY_MUNICIPIO_NOMBRE, municipio);
			if (getContentResolver().query(Contract.MUNICIPIOS_URI, null,
					Contract.SELECTION_BY_NOMBRE_MUNICIPIO + '"' + municipio + '"', null, null).getCount() < 1)
				getContentResolver().insert(Contract.MUNICIPIOS_URI, values);
		}

		// Añadir pestañas
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}

		/**
		 * cambiar la selección de pestaña cuando se cambia de pestaña desplazando el dedo
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
