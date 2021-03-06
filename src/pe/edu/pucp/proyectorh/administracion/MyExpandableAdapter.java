package pe.edu.pucp.proyectorh.administracion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> groups;

	private ArrayList<ArrayList<ArrayList<String>>> children;

	private Context context;

	public MyExpandableAdapter(Context context, ArrayList<String> groups,
			ArrayList<ArrayList<ArrayList<String>>> children) {
		this.context = context;
		this.groups = groups;
		this.children = children;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public ArrayList<String> getChild(int groupPosition, int childPosition) {
		return children.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		String child = getChild(groupPosition,
				childPosition).get(0);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.expandablelistview_child, null);
		}

		TextView childtxt = (TextView) convertView
				.findViewById(R.id.TextViewChild01);

		childtxt.setText(child);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return children.get(groupPosition).size();
	}

	@Override
	public String getGroup(int groupPosition) {
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
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		String group = getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.expandablelistview_group, null);
		}

		TextView grouptxt = (TextView) convertView
				.findViewById(R.id.TextViewGroup);

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

}
