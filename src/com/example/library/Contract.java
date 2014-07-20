package com.example.library;

import android.net.Uri;

public class Contract {

	public static final String TABLE_MUNICIPIOS = "municipios";

	public static final String KEY_MUNICIPIO_ID = "municipio_id";

	public static final String KEY_MUNICIPIO_NOMBRE = "municipio_nombre";

	public static final String SELECTION_BY_NOMBRE_MUNICIPIO = Contract.KEY_MUNICIPIO_NOMBRE + " = ";

	public static final String SELECTION_BY_ID_MUNICIPIO = Contract.KEY_MUNICIPIO_ID + " = ";

	public static final String MUNICIPIOS_ID_ORDER = Contract.KEY_MUNICIPIO_ID + " DESC";

	// Configuración base de datos
	public static final String DATABASE_NAME = "playeando_DB";

	public static final int DATABASE_VERSION = 2;

	// Content provider
	public static final String AUTHORITY = "com.example.opendata.MyContentProvider";

	public static final Uri MUNICIPIOS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_MUNICIPIOS);

	public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/MyContentProvider.data.text";

	public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/MyContentProvider.data.text";

	public static final int MUNICIPIOS = 1;

	public static final int MUNICIPIOS_ID = 2;

	// Municipios

	public static final String[] MUNICIPIOS_ARRAY = { "Adeje", "Arafo", "Arico", "Arona", "Buenavista del Norte",
			"Candelaria", "El Rosario", "El Sauzal", "El Tanque", "Fasnia", "Garachico", "Granadilla de Abona",
			"Guía de Isora", "Güímar", "Icod de los Vinos", "La Guancha", "La Matanza de Acentejo", "La Orotava",
			"La Victoria de Acentejo", "Los Realejos", "Los Silos", "Puerto de la Cruz", "San Cristóbal de La Laguna",
			"San Juan de la Rambla", "San Miguel de Abona", "Santa Cruz de Tenerife", "Santa Úrsula",
			"Santiago del Teide", "Tacoronte", "Tegueste", "Vilaflor" };

	// ListView municipios
	public static final String LISTVIEW_POSITION = "listview_position";

}
