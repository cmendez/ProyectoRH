package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.administracion.VisualizarInfoColaboradoFragment;
import pe.edu.pucp.proyectorh.model.Modulo;
import pe.edu.pucp.proyectorh.reclutamiento.EvaluacionPostulanteFragment;
import pe.edu.pucp.proyectorh.reportes.*;
import pe.edu.pucp.proyectorh.objetivos.*;
import pe.edu.pucp.proyectorh.utils.Constante;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Actividad principal
 * 
 * @author Cesar
 * 
 */
public class MainActivity extends FragmentActivity implements
		MenuFragment.Callbacks {

	public static int NAVEGACION = 1;
	private boolean dosPaneles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opcion_list);

		if (findViewById(R.id.opcion_detail_container) != null) {
			dosPaneles = true;
			((MenuFragment) getSupportFragmentManager().findFragmentById(
					R.id.opcion_list)).setActivateOnItemClick(true);
		}
		ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(11, 58, 23))); //color web original verde olivo
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(11, 100, 23)));
		bar.setTitle("RH++");
	}

	@Override
	public void onItemSelected(String id) {
		if (dosPaneles) {
			if (Modulo.MODULO_ACTUAL == Constante.MI_INFORMACION) {

				if (id.equals("1")) { // Informaci�n personal
					VisualizarInfoColaboradoFragment fragment = new VisualizarInfoColaboradoFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();

				}
				if (id.equals("2")) {// Mi equipo de trabajo

				}
				if (id.equals("3")) {// Mis contactos

				}
				if (id.equals("4")) {// Mi agenda

				}

			} else if ((Modulo.MODULO_ACTUAL == Constante.RECLUTAMIENTO)
					&& ("4".equals(id))) {
				EvaluacionPostulanteFragment fragment = new EvaluacionPostulanteFragment();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}

			else if ((Modulo.MODULO_ACTUAL == Constante.REPORTES)
					&& ("4".equals(id))) {
				ReporteObjetivosBSCPrincipal fragment = new ReporteObjetivosBSCPrincipal();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}

			else if (Modulo.MODULO_ACTUAL == Constante.EVALUACION_360) {
				if (id.equals("1")) { // Mis evaluaciones

				}
				if (id.equals("2")) {// Rol evaluadores

				}
				if (id.equals("3")) {// Mis subordinados

				}
			} else if (Modulo.MODULO_ACTUAL == Constante.OBJETIVOS) {
				if (id.equals("1")) { // Objetivos Empresa
					ObjetivosEmpresa fragment = new ObjetivosEmpresa();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("2")) {// Mis Objetivos

				}
				if (id.equals("3")) {// Objetivos Subordinados

				}
				if (id.equals("4")) {// Registrar Avance

				}
				if (id.equals("5")) {// Mis Avances

				}
				if (id.equals("6")) {// Monitoreo

				}
			} else {
				Bundle arguments = new Bundle();
				arguments.putString(DetalleFragment.ARG_ITEM_ID, id);
				DetalleFragment fragment = new DetalleFragment();
				fragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}
		} else {
			Intent detailIntent = new Intent(this, DetalleActivity.class);
			detailIntent.putExtra(DetalleFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Cerrar sesi�n");
			builder.setMessage("�Est� seguro que desea cerrar sesi�n?");
			builder.setCancelable(false);
			builder.setNegativeButton("Cancelar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.setPositiveButton("Aceptar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							cerrarSesion();
						}
					});
			builder.create();
			builder.show();
			return false;
		case R.id.help:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
	}

	protected void cerrarSesion() {
		NAVEGACION = 1;
		finish();
	}
}
