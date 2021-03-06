package pe.edu.pucp.proyectorh.reclutamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.SolicitudOfertaLaboral;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PostularOfertaLaboral extends Fragment {

	private View rootView;
	private View layoutVacio;
	private ListView listaSolicitudes;
	private ArrayAdapter<String> solicitudesAdapter;
	private ArrayList<SolicitudOfertaLaboral> solicitudes = null;
	private ArrayList<String> puestosSolicitudes = null;
	private static final String OPERACION_VALIDA = "true";
	private static final String OPERACION_INVALIDA = "false";
	private int IDOfertaLaboral;
	private Button postularButton;

	private int posicionLista = -1;
	private SolicitudOfertaLaboral solicitud;
	boolean espera = true;

	public PostularOfertaLaboral() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.postular_oferta_laboral,
				container, false);

		customizarEstilos(getActivity(), rootView);
		llamarServicioObtenerOfertasLaborales(LoginActivity.getUsuario()
				.getID(), "Aprobado");

		return rootView;
	}

	private void customizarEstilos(Context context, View view) {
		try {
			if (view instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) view;
				for (int i = 0; i < vg.getChildCount(); i++) {
					View child = vg.getChildAt(i);
					customizarEstilos(context, child);
				}
			} else if (view instanceof TextView) {
				((TextView) view).setTypeface(Typeface.createFromAsset(
						context.getAssets(), EstiloApp.FORMATO_LETRA_APP));
			}
		} catch (Exception e) {
		}
	}

	private void llamarServicioObtenerOfertasLaborales(String ID,
			String estadoOferta) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.ObtenerOfertasParaPostulacion
					+ "?colaboradorID=" + ID + "&estadoOfertaLaboral="
					+ estadoOferta;
			System.out.println("pagina: " + request);
			new deserializarJSON(this.getActivity()).execute(request);
		}
	}

	public class deserializarJSON extends AsyncCall {

		public deserializarJSON(Activity activity) {
			super(activity);
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("result: " + result);
			probarDeserializacionJSON(result);
			ocultarMensajeProgreso();
		}
	}

	public void probarDeserializacionJSON(String str) {
		String result;
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		if (str != "")
			result = str;
		else
			result = "{\"success\":true,\"solicitudes\":[{\"solicitudID\":1,\"area\":\"TI\",\"cargo\":\"Practicante\",\"fechaRequerimiento\":\"01/05/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Teodoro Santos\",\"comentarios\":\"se requiere practicante para soporte\",\"observacion\":null,\"estado\":\"pendiente\"},{\"solicitudID\":2,\"area\":\"Marketing\",\"cargo\":\"Asistente\",\"fechaRequerimiento\":\"22/03/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Karla River\",\"comentarios\":\"Se requiere asistente de investigaci�n\",\"observacion\":null,\"estado\":\"pendiente\"},{\"solicitudID\":3,\"area\":\"Contabilidad\",\"cargo\":\"Analista\",\"fechaRequerimiento\":\"13/11/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Sergio Fuentes\",\"comentarios\":\"se requiere analista con o sin experiencia\",\"observacion\":null,\"estado\":\"pendiente\"}]}";
		System.out.println("Recibido: " + result.toString());
		// deserializando el json parte por parte
		try {
			JSONObject jsonObject = new JSONObject(result);
			// System.out.println("result: " + result);
			String respuesta = jsonObject.getString("success");
			if (respuesta.equals("true")) {
				// if (procesaRespuesta(respuesta)) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				JSONArray listaOfertasLaborales = (JSONArray) data
						.get("ofertasLaborales");

				solicitudes = new ArrayList<SolicitudOfertaLaboral>();
				puestosSolicitudes = new ArrayList<String>();
				JSONObject solicitudObject;
				for (int i = 0; i < listaOfertasLaborales.length(); i++) {
					solicitudObject = listaOfertasLaborales.getJSONObject(i);

					solicitud = new SolicitudOfertaLaboral(
							solicitudObject.getInt("ID"),
							solicitudObject.getString("Area"),
							solicitudObject.getString("Puesto"),
							solicitudObject.getInt("NumeroVacantes"),
							solicitudObject.getInt("SueldoTentativo"),
							formatoFecha.parse(solicitudObject
									.getString("FechaRequerimiento")),
							formatoFecha.parse(solicitudObject
									.getString("FechaFinRequerimiento")),
							solicitudObject.getString("ModoSolicitud"),
							solicitudObject.getString("Responsable"),
							solicitudObject.getString("Descripcion"),
							solicitudObject.getString("Comentarios"));

					solicitudes.add(solicitud);
					puestosSolicitudes.add(solicitud.getPuesto());
				}
				mostrarSolicitudes();
			} else {
				String message = jsonObject.getString("message");
				if (message.startsWith("Error en la BD:")){
					mostrarErrorComunicacion("Problema en el servidor");
				} else {
					mostrarErrorComunicacion(message);	
				}
			}
		} catch (JSONException e) {
			System.out.println("entre al catch1");
			System.out.println(e.toString());
			mostrarErrorComunicacion("El servicio solicitado no est� disponible en el servidor");
		} catch (NullPointerException ex) {
			System.out.println("entre al catch2");
			System.out.println(ex.toString());
			mostrarErrorComunicacion("El servicio solicitado no est� disponible en el servidor");
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("entre al catch3");
			System.out.println(e.toString());
			mostrarErrorComunicacion("El servicio solicitado no est� disponible en el servidor");
		} catch (Exception ex2) {
			System.out.println("entre al catch4");
			System.out.println(ex2.toString());
			mostrarErrorComunicacion("El servicio solicitado no est� disponible en el servidor");
		}

	}

	private void mostrarSolicitudes() {
		this.listaSolicitudes = (ListView) rootView
				.findViewById(R.id.reclut_lista_solicit_of_laboral);
		this.postularButton = (Button) this.rootView
				.findViewById(R.id.reclu_btn_Validar);

		// System.out.println("solicitudes != NULL");
		/*
		 * this.solicitudesAdapter = new
		 * ArrayAdapter<String>(this.getActivity(),
		 * android.R.layout.simple_list_item_1, puestosSolicitudes);
		 */

		this.solicitudesAdapter = new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_list_item_1, puestosSolicitudes) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView,
						parent);
				view.setTypeface(Typeface.createFromAsset(getActivity()
						.getAssets(), EstiloApp.FORMATO_LETRA_APP));
				return view;
			}
		};

		listaSolicitudes.setAdapter(solicitudesAdapter);
		listaSolicitudes
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
							View childView, int position, long id) {
						// position tiene la posicion de la vista en el
						// adapter
						mostrarSolicitudSeleccionada(solicitudes.get(position));
						// obtenemos el id de la solicitud seleccionada
						// IDOfertaLaboral =
						// solicitudes.get(position).getSolicitudID();

						IDOfertaLaboral = solicitudes.get(position).getID();
						posicionLista = position;
					}
				});

		postularButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ((solicitudes.size() > 0) && (posicionLista != -1)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Postular a Oferta Laboral");
					builder.setMessage("�Desea postular a la oferta laboral?");
					builder.setCancelable(false);
					builder.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.setPositiveButton("Aceptar",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (posicionLista != -1) {

										// comunicarle al ws que se postulo
										// a la oferta
										// laboral

										enviarPostulacionOfertaLaboral(
												LoginActivity.getUsuario()
														.getID(),
												IDOfertaLaboral);

									}
									dialog.cancel();
								}

							});
					builder.create();
					builder.show();
				}
			}
		});

	}

	private void mostrarErrorComunicacion(String mensaje) {
		try
		{
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		builder.setTitle("Error de servicio");
		builder.setMessage(mensaje);
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
		}
		catch(Exception e)
		{}
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (OPERACION_VALIDA.equals(respuestaServidor)) {
			return true;
			/*
			 * } else if (OPERACION_INVALIDA.equals(respuestaServidor)) { // Se
			 * muestra mensaje de usuario invalido AlertDialog.Builder builder =
			 * new AlertDialog.Builder(this .getActivity());
			 * builder.setTitle("Login inv�lido"); builder.setMessage(
			 * "Combinaci�n de usuario y/o contrase�a incorrectos.");
			 * builder.setCancelable(false); builder.setPositiveButton("Ok",
			 * null); builder.create(); builder.show(); return false;
			 */
		} else {
			// Se muestra mensaje de error
			AlertDialog.Builder builder = new AlertDialog.Builder(
					this.getActivity());
			builder.setTitle("Problema en el servidor");
			builder.setMessage("Hay un problema en el servidor.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		}
	}

	protected void mostrarSolicitudSeleccionada(
			SolicitudOfertaLaboral solicitudOfertaLaboral) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		TextView area = (TextView) rootView
				.findViewById(R.id.reclut_area_input);
		area.setText(solicitudOfertaLaboral.getArea() == "null" ? " - "
				: solicitudOfertaLaboral.getArea());

		TextView puesto = (TextView) rootView
				.findViewById(R.id.reclut_cargo_input);
		TextView puestolabel = (TextView) rootView
				.findViewById(R.id.reclut_cargo_label);

		if (solicitudOfertaLaboral.getPuesto() != null) {
			int cantidadCaracteresPuestoOferta = solicitudOfertaLaboral
					.getPuesto().length();
			if (cantidadCaracteresPuestoOferta >= 35) {
				puesto.setHeight(60);
				puestolabel.setHeight(60);
			} else {
				// puesto.setLayoutParams(new
				// LayoutParams(LayoutParams.WRAP_CONTENT,
				// LayoutParams.WRAP_CONTENT));
				// puestolabel.setLayoutParams(new
				// LayoutParams(LayoutParams.WRAP_CONTENT,
				// LayoutParams.WRAP_CONTENT));
				puesto.setHeight(30);
				puestolabel.setHeight(30);
			}
			System.out.println(cantidadCaracteresPuestoOferta);
		}

		puesto.setText(solicitudOfertaLaboral.getPuesto() == "null" ? " - "
				: solicitudOfertaLaboral.getPuesto());

		TextView nrovacantes = (TextView) rootView
				.findViewById(R.id.reclut_nro_vacantes_input);
		nrovacantes
				.setText(solicitudOfertaLaboral.getNroVacantes() == 0 ? " 0 "
						: String.valueOf(solicitudOfertaLaboral
								.getNroVacantes()));

		TextView sueldotentativo = (TextView) rootView
				.findViewById(R.id.reclut_sueldo_tentativo_input);
		sueldotentativo
				.setText(solicitudOfertaLaboral.getSueldoTentativo() == 0 ? " S/. 0 "
						: String.valueOf("S/. "
								+ solicitudOfertaLaboral.getSueldoTentativo()));

		TextView fechaRequerimiento = (TextView) rootView
				.findViewById(R.id.reclut_fecha_input);
		if (solicitudOfertaLaboral.getFechaRequerimiento() != null) {
			fechaRequerimiento.setText(formatoFecha
					.format(solicitudOfertaLaboral.getFechaRequerimiento()));
		} else {
			fechaRequerimiento.setText("");
		}

		TextView fechaLimiteSolicitud = (TextView) rootView
				.findViewById(R.id.reclut_fecha_limite_input);
		if (solicitudOfertaLaboral.getFechaLimiteSolicitud() != null) {
			fechaLimiteSolicitud.setText(formatoFecha
					.format(solicitudOfertaLaboral.getFechaLimiteSolicitud()));
		} else {
			fechaLimiteSolicitud.setText("");
		}

		TextView modoPublicacion = (TextView) rootView
				.findViewById(R.id.reclut_modo_input);
		modoPublicacion
				.setText(solicitudOfertaLaboral.getModoPublicacion() == "null" ? " - "
						: solicitudOfertaLaboral.getModoPublicacion());

		TextView responsable = (TextView) rootView
				.findViewById(R.id.reclut_responsable_input);
		responsable
				.setText(solicitudOfertaLaboral.getResponsable() == "null" ? " - "
						: solicitudOfertaLaboral.getResponsable());

		TextView descripcion = (TextView) rootView
				.findViewById(R.id.reclut_descripcion_input);
		String comment = "* sin descripci�n disponible *";
		if ((solicitudOfertaLaboral.getDescripcion() == null)
				|| (solicitudOfertaLaboral.getDescripcion() == "null"))
			descripcion.setText(" - ");
		else if (solicitudOfertaLaboral.getDescripcion().isEmpty()
				|| (solicitudOfertaLaboral.getDescripcion() == "")
				|| (solicitudOfertaLaboral.getDescripcion().length() <= 0))
			descripcion.setText(comment);
		else
			descripcion.setText(solicitudOfertaLaboral.getDescripcion());

	}

	private void enviarPostulacionOfertaLaboral(String IDusuario,
			int IDofertaLaboral) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.EnviarPostulacionOfertaLaboral
					+ "?colaboradorID=" + IDusuario + "&ofertaLaboralID="
					+ IDofertaLaboral;
			System.out.println("pagina: " + request);
			new enviarMensajeWS().execute(request);
		}
	}

	public class enviarMensajeWS extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("result: " + result);
			confirmacionPostulacion(result);
		}
	}

	public void confirmacionPostulacion(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			System.out.println("result: " + result);
			String respuesta = jsonObject.getString("success");
			// si no pudo actualizar, mostramos mensaje de error y volvemos a
			// mostrar todas las solicitudes pendientes
			if (respuesta == "true") {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						this.getActivity());
				builder.setTitle("Mensaje de Confirmaci�n");
				builder.setMessage("Operaci�n exitosa");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();

				SolicitudOfertaLaboral nueva = new SolicitudOfertaLaboral();
				mostrarSolicitudSeleccionada(nueva);
				puestosSolicitudes.remove(posicionLista);
				solicitudes.remove(posicionLista);
				solicitudesAdapter.notifyDataSetChanged();
				posicionLista = -1; // volvemos a colocar el posicion en -1
				// llamarServicioObtenerOfertasLaborales("Aprobado");
			} else if (respuesta == "false") {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						this.getActivity());
				builder.setTitle("Problema en el servidor");
				builder.setMessage("Hay un problema en el servidor.");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
				posicionLista = -1; // volvemos a colocar el posicion en -1
				SolicitudOfertaLaboral nueva = new SolicitudOfertaLaboral();
				mostrarSolicitudSeleccionada(nueva);
				llamarServicioObtenerOfertasLaborales(LoginActivity
						.getUsuario().getID(), "Aprobado");
			}
		} catch (JSONException e) {
			System.out.println("entre al catch1");
			System.out.println(e.toString());
			mostrarErrorComunicacion("El servicio solicitado no est� disponible en el servidor");
		} catch (NullPointerException ex) {
			System.out.println("entre al catch2");
			System.out.println(ex.toString());
			mostrarErrorComunicacion("El servicio solicitado no est� disponible en el servidor");
		} catch (Exception ex2) {
			System.out.println("entre al catch3");
			System.out.println(ex2.toString());
			mostrarErrorComunicacion("El servicio solicitado no est� disponible en el servidor");
		}
	}
}
