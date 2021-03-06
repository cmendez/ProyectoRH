package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ObjetivosExpandableAdapter extends BaseExpandableListAdapter {
	
	private ArrayList<ObjetivosBSC> groups;
	 
    private ArrayList<ArrayList<ObjetivosBSC>> children;

    private ArrayList<Integer> contadorImpresionHijos;
    
    TableLayout lay;
    
	private Context contexto;
	boolean flagMostrar;
	boolean primeraVez=true;
	ObjetivosExpandableAdapter este = this;

	public ObjetivosExpandableAdapter(Context contexto, ArrayList<ObjetivosBSC> groups, ArrayList<ArrayList<ObjetivosBSC>> children) {
        this.contexto = contexto;
        this.groups = groups;
        this.children = children;
		contadorImpresionHijos = new ArrayList<Integer>();
		
		for(int i=0;i<groups.size();i++){
			contadorImpresionHijos.add(0);
		}
	}
	
	
	
	public void actualizaHijos(ArrayList<ArrayList<ObjetivosBSC>> children){
		this.children = children;
	}
	 

	@Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    @Override
    public ObjetivosBSC getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }
    
    public void eliminaHijo(int groupPosition, int childPosition){
    	children.get(groupPosition).remove(childPosition);
    }
    public void aumentaHijo(int groupPosition){
    	ObjetivosBSC objetivo = new ObjetivosBSC();
    	children.get(groupPosition).add(objetivo);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

   
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {

    	System.out.println("vere gpos="+groupPosition+ " y cpos="+childPosition + " isLastChild="+isLastChild);
    	
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.objetivos_expandablelist_child, null);           
        }
        
  	      	System.out.println("agregara para papa="+groups.get(groupPosition).Nombre+ " el child = "+children.get(groupPosition).get(childPosition).Nombre);
		    
		    ObjetivosBSC objBSC = getChild(groupPosition, childPosition);
		    String szNombre ="";
		    String szPeso = "";
		    if(objBSC != null){
				szNombre=objBSC.Nombre;
				szPeso = Integer.toString(objBSC.Peso);
			}
				
			EditText descripObj = (EditText) convertView.findViewById(R.id.nombreObj);
			descripObj.setText(szNombre);
			descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,85));
			//descripObj
			
		    EditText peso = (EditText) convertView.findViewById(R.id.pesoObj);
		    peso.setText(szPeso);
		    peso.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
        
		    Button btnEliminar = (Button) convertView.findViewById(R.id.buttonMenos);
		    btnEliminar.setText("X");
		    btnEliminar.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		    btnEliminar.setOnClickListener(new OnClickListener() {
				  @Override
				  public void onClick(View v) {
					  System.out.println("--> elimina");
					  eliminaHijo(groupPosition, childPosition);
					  este.notifyDataSetChanged();
				  }
			});
		    
		    this.notifyDataSetChanged();
		    Button btnAumentar = (Button) convertView.findViewById(R.id.buttonMas);
		    btnAumentar.setText("+");
		    btnAumentar.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		    btnAumentar.setOnClickListener(new OnClickListener() {
				  @Override
				  public void onClick(View v) {
					  System.out.println("--> aumenta");
					  aumentaHijo(groupPosition);
					  este.notifyDataSetChanged();
				  }
			});
		    if(!isLastChild){
		    	btnAumentar.setVisibility(View.INVISIBLE);
		    }else{
		    	btnAumentar.setVisibility(View.VISIBLE);
		    }
		    
		    convertView.setFocusable(false);
	    return convertView;       
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public ObjetivosBSC getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	String group = getGroup(groupPosition).Nombre;
    	System.out.println("groupView de ="+group +" isExp="+isExpanded);    	

    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelistview_group, null);
            convertView.setBackgroundColor(Color.parseColor("#2EFE9A"));
        }

        TextView grouptxt = (TextView) convertView.findViewById(R.id.TextViewGroup);
        grouptxt.setText(group);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
    
	class TableFila extends TableRow {
		int flagUlt = 0;
		
		public TableFila(Context context) {
			super(context);
		}		
	}
 
	
	public TableFila agregaFila(ObjetivosBSC objBSC, final int flagUltimo){
		final TableFila fila = new TableFila(contexto);
		fila.flagUlt=flagUltimo;
		String szNombre ="";
		String szPeso ="";
		//String szCreador=LoginActivity.getUsuario().getUsername();
		
		if(objBSC != null){
			szNombre=objBSC.Nombre;
			szPeso = Integer.toString(objBSC.Peso);
			//szCreador = LoginActivity.getUsuario().getUsername(); //objBSC.CreadorID;
		}
		
		fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

	    EditText descripObj = new EditText(contexto);
	    descripObj.setInputType(InputType.TYPE_CLASS_TEXT);

	  
	    descripObj.setText(szNombre);
	    descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,85));
	    fila.addView(descripObj);
		
	    EditText peso = new EditText(contexto);
	    peso.setInputType(InputType.TYPE_CLASS_NUMBER);
	    peso.setText(szPeso);
	    peso.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
	    fila.addView(peso);
	    
	    Button eliminarObj = new Button(contexto);
	    eliminarObj.setText("X");
	    eliminarObj.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  System.out.println("--> elimina");
				  fila.removeAllViews();
			  }
		});
	    fila.addView(eliminarObj);	
	    
	    /**BOTON AUMENTAR - INICIO**/
	    final Button aumentarObj = new Button(contexto);
	    aumentarObj.setText("+");
	    aumentarObj.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {	
				  aumentarObj.setVisibility(View.INVISIBLE); //elimina el boton
				  TableFila fila = agregaFila(null,1);
				  System.out.println("--> aumenta");
				  lay.addView(fila);
			  }
		});
	    fila.addView(aumentarObj);
	    
	    if(fila.flagUlt!=1){
	    	aumentarObj.setVisibility(View.INVISIBLE); //elimina el boton		    	
	    }
	    
	    /**BORON AUMENTAR - FIN**/
	    System.out.println("retorna fila");
	return fila;
}

}
