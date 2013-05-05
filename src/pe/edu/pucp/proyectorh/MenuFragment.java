package pe.edu.pucp.proyectorh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.edu.pucp.proyectorh.model.Modulo;
import pe.edu.pucp.proyectorh.model.Modulo.ModuloItem;
import pe.edu.pucp.proyectorh.utils.ExpandableListFragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;

/**
 * Fragmento que contiene la lista de opciones
 * 
 * @author Cesar
 * 
 */
public class MenuFragment extends ExpandableListFragment {

	private Callbacks mCallbacks = menuOpcionesCallbacks;
	private SimpleExpandableListAdapter mAdapter;
	private static final String ID = "Id";
	private static final String NAME = "Name";
	private static final String IS_EVEN = "Is even";
	private ExpandableListView elv;
	View lastColored;

	public interface Callbacks {
		public void onItemSelected(String id);
	}

	private static Callbacks menuOpcionesCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	public MenuFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

		// Se agregan las funcionalidades de RH++
		for (ModuloItem modulo : Modulo.MODULOS) {
			Map<String, String> curGroupMap = new HashMap<String, String>();
			groupData.add(curGroupMap);
			curGroupMap.put(ID, modulo.getId());
			curGroupMap.put(NAME, modulo.getNombre());
		}

		for (int i = 0; i < 7; i++) {
			List<Map<String, String>> children = new ArrayList<Map<String, String>>();
			List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
			switch (i + 1) {
			case 1:
				submodulos = Modulo.obtenerFuncionalidadesMiInformacion();
				break;
			case 2:
				submodulos = Modulo.obtenerFuncionalidadesAdministracion();
				break;
			case 3:
				submodulos = Modulo.obtenerFuncionalidadesReclutamiento();
				break;
			case 4:
				submodulos = Modulo.obtenerFuncionalidadesEvaluacion360();
				break;
			case 5:
				submodulos = Modulo.obtenerFuncionalidadesObjetivos();
				break;
			case 6:
				submodulos = Modulo.obtenerFuncionalidadesLineaDeCarrera();
				break;
			case 7:
				submodulos = Modulo.obtenerFuncionalidadesReportes();
				break;
			}

			for (ModuloItem modulo : submodulos) {
				Map<String, String> curChildMap = new HashMap<String, String>();
				children.add(curChildMap);
				curChildMap.put(ID, modulo.getId());
				curChildMap.put(NAME, modulo.getNombre());
			}
			childData.add(children);
		}

		mAdapter = new SimpleExpandableListAdapter(getActivity()
				.getApplicationContext(), groupData,
				R.layout.custom_simple_expandable_list_item_1, new String[] {
						NAME, IS_EVEN }, new int[] { R.id.text1, R.id.text2 },
				childData, R.layout.custom_simple_expandable_list_item_2,
				new String[] { NAME, IS_EVEN }, new int[] { R.id.text1,
						R.id.text2 });

		setListAdapter(mAdapter);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		elv = getExpandableListView();
		elv.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				System.out.println("Grupo " + (groupPosition + 1));
				return false;
			}
		});
		elv.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				System.out.println("Grupo " + (groupPosition + 1) + "/ Hijo "
						+ (childPosition + 1));
				// cambiar color de hijo elegido
				if (lastColored != null) {
					lastColored.setBackgroundColor(Color.TRANSPARENT);
					lastColored.invalidate();
				}
				lastColored = v;
				// bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(11, 58,
				// 23))); //color web original verde olivo
				v.setBackgroundDrawable(new ColorDrawable(Color
						.rgb(11, 100, 23)));
				Modulo.MODULO_ACTUAL = groupPosition + 1;
				Modulo.MODULOS_MOSTRADOS_ACTUAL = obtenerSubModulosSegunId(groupPosition);
				mCallbacks.onItemSelected(String.valueOf(childPosition + 1));
				return false;
			}
		});
	}

	protected List<ModuloItem> obtenerSubModulosSegunId(int i) {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		switch (i + 1) {
		case 1:
			submodulos = Modulo.obtenerFuncionalidadesMiInformacion();
			break;
		case 2:
			submodulos = Modulo.obtenerFuncionalidadesAdministracion();
			break;
		case 3:
			submodulos = Modulo.obtenerFuncionalidadesReclutamiento();
			break;
		case 4:
			submodulos = Modulo.obtenerFuncionalidadesEvaluacion360();
			break;
		case 5:
			submodulos = Modulo.obtenerFuncionalidadesObjetivos();
			break;
		case 6:
			submodulos = Modulo.obtenerFuncionalidadesLineaDeCarrera();
			break;
		case 7:
			submodulos = Modulo.obtenerFuncionalidadesReportes();
			break;
		}
		return submodulos;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = menuOpcionesCallbacks;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	@Override
	public void onListItemClick(ExpandableListView l, View v, int position,
			long id) {
		System.out.println("Evento activado");
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		System.out.println("Evento activado");
		return true;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		elv = getExpandableListView();
	}
}