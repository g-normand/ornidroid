package fr.ornidroid.ui.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.ornidroid.R;
import fr.ornidroid.ui.activity.MultiCriteriaSearchActivity;
import fr.ornidroid.ui.multicriteriasearch.MultiCriteriaSearchFieldType;
import fr.ornidroid.ui.multicriteriasearch.SpinnerIconSelector;

/**
 * The Class MyCustomAdapter. This class allows to print icons and text in a
 * spinner
 * 
 * @see http 
 *      ://android-er.blogspot.fr/2010/12/custom-arrayadapter-for-spinner-with
 *      .html
 */
public class MyCustomAdapter extends ArrayAdapter<String> {

	/** The activity. */
	private final MultiCriteriaSearchActivity activity;
	/** The items list. */
	private final List<String> itemsList;

	private final MultiCriteriaSearchFieldType selectFieldType;

	/**
	 * Instantiates a new my custom adapter.
	 * 
	 * @param pActivity
	 *            the activity
	 * @param textViewResourceId
	 *            the text view resource id
	 * @param pItemsList
	 *            the items list
	 * @param selectFieldType
	 *            the select field type
	 */
	public MyCustomAdapter(final MultiCriteriaSearchActivity pActivity,
			final int textViewResourceId, final List<String> pItemsList,
			final MultiCriteriaSearchFieldType pSelectFieldType) {
		super(pActivity, textViewResourceId, pItemsList);
		this.activity = pActivity;
		this.itemsList = pItemsList;
		this.selectFieldType = pSelectFieldType;

	}

	/**
	 * Gets the custom view.
	 * 
	 * @param position
	 *            the position
	 * @param convertView
	 *            the convert view
	 * @param parent
	 *            the parent
	 * @return the custom view
	 */
	public View getCustomView(final int position, final View convertView,
			final ViewGroup parent, boolean dropDownStyle) {

		final LayoutInflater inflater = this.activity.getLayoutInflater();
		View row;
		ImageView icon;
		if (dropDownStyle) {
			row = inflater.inflate(R.layout.row_spinner_icons_dropdown_list,
					parent, false);
			final TextView label = (TextView) row
					.findViewById(R.id.spinner_text_dropdown_list);
			label.setText(this.itemsList.get(position));

			icon = (ImageView) row
					.findViewById(R.id.spinner_icon_dropdown_list);
		} else {
			row = inflater.inflate(R.layout.row_spinner_icons, parent, false);
			final TextView label = (TextView) row
					.findViewById(R.id.spinner_text);
			label.setText(this.itemsList.get(position));

			icon = (ImageView) row.findViewById(R.id.spinner_icon);
		}

		int idRes = 0;
		switch (this.selectFieldType) {
		case BEAK_FORM:
			final int beakFormId = this.activity.getOrnidroidService()
					.getBeakFormId(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromBeakFormId(beakFormId);
			break;
		case COUNTRY:
			final String countrycode = this.activity.getOrnidroidService()
					.getCountryCode(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromCountryCode(countrycode);
			break;
		default:
			break;

		}
		icon.setImageResource(idRes);
		return row;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getDropDownView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getDropDownView(final int position, final View convertView,
			final ViewGroup parent) {
		View v = getCustomView(position, convertView, parent, true);
		v.setBackgroundResource(R.color.mcs_custom_spinner_items_dropdown_background);

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		return getCustomView(position, convertView, parent, false);
	}
}