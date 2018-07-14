package pe.com.smartfinance.viewcontrollers.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Category;
import pe.com.smartfinance.models.Tag;

/*public class ExpensesExpandableListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Category>> listCategoryChild;
    private HashMap<String, List<Tag>> listTagChild;
    private HashMap<String, List<String>> listDateChild;

    public ExpensesExpandableListAdapter(Context context, List<String> listDataHeader,
                                         HashMap<String, List<Category>> listCategoryChild, HashMap<String, List<Tag>> listTagChild,
                                         HashMap<String, List<String>> listDateChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listCategoryChild = listCategoryChild;
        this.listTagChild = listTagChild;
        this.listDateChild = listDateChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        listCategoryChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
       listTagChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
       return listDateChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_expenses_child, null);
        }

        TextView listItemTextView = (TextView) view.findViewById(R.id.listChildExpensesTextView);

        listItemTextView.setText(childText);
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View view, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_expenses_header, null);
        }

        TextView listHeaderTextView = (TextView) view.findViewById(R.id.listHeaderExpensesTextView);
        listHeaderTextView.setTypeface(null, Typeface.BOLD);
        listHeaderTextView.setText(headerTitle);

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}*/
