package pe.edu.pucp.proyectorh.miinformacion;

import java.util.Collections;
import java.util.Comparator;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evento;
import pe.edu.pucp.proyectorh.utils.Constante;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint({ "ValidFragment", "ValidFragment", "ValidFragment" })
public class EventoFragment extends Fragment {

	private View rootView;
	private Evento evento;

	public EventoFragment(Evento evento) {
		this.evento = evento;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.evento_layout, container, false);
		mostrarEvento();
		return rootView;
	}

	private void mostrarEvento() {
		// Datos del evento
		TextView nombreEventoText = (TextView) rootView
				.findViewById(R.id.nombre_evento);
		mostrarTexto(nombreEventoText, evento.getNombre());
		// nombreEventoText.setText(evento.getNombre());
		TextView tipoEventoText = (TextView) rootView
				.findViewById(R.id.tipo_evento_content);
		mostrarTexto(tipoEventoText, evento.getTipoEvento());
		// tipoEventoText.setText(evento.getTipoEvento());
		TextView fechaInicioText = (TextView) rootView
				.findViewById(R.id.fecha_inicio_content);
		// fechaInicioText.setText(evento.getFechaInicio());
		mostrarTexto(fechaInicioText, evento.getFechaInicio());
		TextView fechaFinText = (TextView) rootView
				.findViewById(R.id.fecha_fin_content);
		// fechaFinText.setText(evento.getFechaFin());
		mostrarTexto(fechaFinText, evento.getFechaFin());
		TextView lugarEventoText = (TextView) rootView
				.findViewById(R.id.lugar_evento_content);
		// lugarEventoText.setText(evento.getLugar());
		mostrarTexto(lugarEventoText, evento.getLugar());

		// Datos del creador
		TextView nombreCreadorText = (TextView) rootView
				.findViewById(R.id.nombre_creador_content);
		nombreCreadorText.setText(evento.getCreador().getNombres());
		TextView areaCreadorText = (TextView) rootView
				.findViewById(R.id.area_creador_content);
		areaCreadorText.setText(evento.getCreador().getArea());
		TextView puestoCreadorText = (TextView) rootView
				.findViewById(R.id.puesto_creador_content);
		puestoCreadorText.setText(evento.getCreador().getPuesto());

		ListView listaInvitados = (ListView) rootView
				.findViewById(R.id.lista_invitados_evento);
		Collections.sort(evento.getInvitados(), new Comparator<Colaborador>() {

			@Override
			public int compare(Colaborador colaborador1,
					Colaborador colaborador2) {
				return colaborador1.toString().compareTo(
						colaborador2.toString());

			}
		});
		final ArrayAdapter<Colaborador> invitadosAdapter = new ArrayAdapter<Colaborador>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				evento.getInvitados());
		listaInvitados.setAdapter(invitadosAdapter);
		listaInvitados
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							View childView, int position, long id) {
						// position tiene la posicion de la vista en el adapter
						mostrarInvitadoSeleccionado(evento.getInvitados().get(
								position));
						invitadosAdapter.notifyDataSetChanged();
					}
				});
	}

	protected void mostrarInvitadoSeleccionado(Colaborador invitado) {
		TextView nombreText = (TextView) rootView
				.findViewById(R.id.invitado_nombre_content);
		nombreText.setText(invitado.getNombreCompleto());
		TextView areaText = (TextView) rootView
				.findViewById(R.id.invitado_area_content);
		areaText.setText(invitado.getArea());
		TextView puestoText = (TextView) rootView
				.findViewById(R.id.invitado_puesto_content);
		puestoText.setText(invitado.getPuesto());
		TextView telefonoText = (TextView) rootView
				.findViewById(R.id.invitado_telefono_content);
		telefonoText.setText(invitado.getPuesto());
		TextView correoText = (TextView) rootView
				.findViewById(R.id.invitado_correo_content);
		correoText.setText(invitado.getPuesto());
	}

	private void mostrarTexto(TextView textView, String texto) {
		if (texto != null) {
			textView.setText(texto);
		} else {
			textView.setText(Constante.ESPACIO_VACIO);
		}
	}
}
