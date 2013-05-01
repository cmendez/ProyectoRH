package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.model.Modulo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

/**
 * Fragmento de contenido de las funcionalidades
 * 
 * @author Cesar
 * 
 */
public class OpcionDetailFragment extends Fragment {

	public static final String ARG_ITEM_ID = "item_id";

	Modulo.ModuloItem tituloFuncionalidad;

	public OpcionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			System.out.println("-------Seleccionado Id:"
					+ String.valueOf((Integer.parseInt((getArguments()
							.getString(ARG_ITEM_ID))) - 1)));
			tituloFuncionalidad = Modulo.MODULOS_MOSTRADOS_ACTUAL.get(Integer
					.parseInt(getArguments().getString(ARG_ITEM_ID)) - 1);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View rootView;
		if (tituloFuncionalidad != null) {
				rootView = inflater.inflate(R.layout.fragment_opcion_detail,
						container, false);
				((TextView) rootView.findViewById(R.id.opcion_detail))
				.setText(tituloFuncionalidad.nombre);
		}else{
			rootView = inflater.inflate(R.layout.fragment_opcion_detail,
					container, false);
		}
		return rootView;
	}
}
