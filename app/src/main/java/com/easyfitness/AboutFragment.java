package com.easyfitness;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.easyfitness.DAO.DatabaseHelper;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;

public class AboutFragment extends Fragment {
    private String name;
    private int id;
    private MainActivity mActivity = null;

    private final View.OnClickListener clickLicense = v -> {

        String name = null;
        String url = null;
        String copyright = null;
        License license = null;

        switch (v.getId()) {








        }

        final Notice notice = new Notice(name, url, copyright, license);
        new LicensesDialog.Builder(getMainActivity())
                .setNotices(notice)
                .build()
                .show();
    };

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static AboutFragment newInstance(String name, int id) {
        AboutFragment f = new AboutFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putInt("id", id);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_about, container, false);

        TextView mpDBVersionTextView = view.findViewById(R.id.database_version);
        mpDBVersionTextView.setText(Integer.toString(DatabaseHelper.DATABASE_VERSION));






        // Inflate the layout for this fragment
        return view;
    }

    public MainActivity getMainActivity() {
        return this.mActivity;
    }

}

