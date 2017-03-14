package science.itaintrocket.pomfshare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AddHostDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.d("ayy lmao", "test?");
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setMessage("If you fill this out wrong the app will probably crash")
				.setTitle("Add Host")
				.setView(inflater.inflate(R.layout.dialog_add_host, null))
				.setPositiveButton("Add", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						HostDbHelper helper = new HostDbHelper(getActivity().getApplicationContext());
						SQLiteDatabase db = helper.getWritableDatabase();
						ContentValues values = new ContentValues();

						EditText nameEdit = (EditText)((AlertDialog)dialog).findViewById(R.id.host_name);
						EditText urlEdit = (EditText)((AlertDialog)dialog).findViewById(R.id.host_url);
						EditText descEdit = (EditText)((AlertDialog)dialog).findViewById(R.id.host_description);
						RadioGroup typeRG = (RadioGroup)((AlertDialog)dialog).findViewById(R.id.host_api_type);

						Host.Type type = null;

						switch(typeRG.getCheckedRadioButtonId()) {
							case R.id.pomf_api:
								type = Host.Type.POMF;
								break;
							case R.id.uguu_api:
								type = Host.Type.UGUU;
								break;
						}

						if(type == null) {
							Log.e("ayy lmao", "no host type selected rip");
							// TODO: show some error
						}

						values.put(HostDbContract.HostTable.COL_HOST_NAME, nameEdit.getText().toString());
						values.put(HostDbContract.HostTable.COL_HOST_URL, urlEdit.getText().toString());
						values.put(HostDbContract.HostTable.COL_HOST_DESC, descEdit.getText().toString());
						values.put(HostDbContract.HostTable.COL_HOST_API, type.name());

						db.insert(HostDbContract.HostTable.TABLE_NAME, null, values);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// This entire method can maybe be removed?
					}
				});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}