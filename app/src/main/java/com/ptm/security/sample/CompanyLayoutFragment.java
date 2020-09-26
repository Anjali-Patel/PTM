package com.ptm.security.sample;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;
import com.ptm.security.Adapter.CompanyImageAdapter;
import com.ptm.security.Adapter.ProjectImageAdapter;
import com.ptm.security.Model.Company;
import com.ptm.security.Model.CompanyImageListModel;
import com.ptm.security.Model.Project_addres;
import com.ptm.security.R;
import com.ptm.security.util.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.ptm.security.activities.Details2.CompaniesHeadingKeys;
import static com.ptm.security.activities.Details2.CompaniesHeadings;
import static com.ptm.security.activities.Details2.CompaniesList;
import static com.ptm.security.activities.Details2.CompanyImagList;
import static com.ptm.security.activities.Details2.ProjectImagList;
import static com.ptm.security.activities.Details2.ProjectList;
import static com.ptm.security.util.Globals.SORTING_URL;
import static com.ptm.security.util.Globals.global_ctx;
import static com.ptm.security.util.Globals.pstPrms;
import static com.ptm.security.util.Globals.showAlert;
import static com.ptm.security.util.Globals.showAlertIntfacesOpt;


public class CompanyLayoutFragment extends Fragment {
    public static LinkedAdaptiveTableAdapter companyTableAdapter;
    public static AdaptiveTableLayout companyTableLayout;

    public static CompanyLayoutFragment newInstance() {
        CompanyLayoutFragment fragment = new CompanyLayoutFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_company_layout, container, false);
        companyTableLayout = view.findViewById(R.id.tableLayout);

        initCompaniesAdapter();
        return view;
    }
    public static void initCompaniesAdapter() {
        companyTableAdapter = new CompanyLinkedTableAdapter(global_ctx, CompaniesList.size(), Company.class.getDeclaredMethods().length / 2);
        companyTableAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int row, int column) {
                final String[] okFileExtensions = new String[] {"jpg", "png", "gif", "jpeg"};

                for(int i=0;i<row;i++){
                    for(int j=i;j<column;j++){
                        String itemData = "-"; // skip headers
                        Company company=CompaniesList.get(row);

                        switch (column) {

                            case 1:
                                itemData = company.getCompany_name();
                                break;
                            case 2:
                                itemData = company.getCountry();
                                break;
                            case 3:
                                itemData = company.getDescription();
                                break;
                            case 4:
                                itemData = company.getHomepage();
                                break;
                            case 5:
                                itemData = company.getContact();
                                break;
                            case 6:
                                itemData = company.getFiles();
                                break;
                            case 7:
                                itemData = company.getShort_name();
                                break;
          /*  case 8:
                itemData = company.getFiles();
                break;
            case 9:
                itemData = company.getShort_name();
                break;*/
                            default:
                                break;

                        }

                        ViewGroup viewGroup = null;
                        AlertDialog.Builder builder = new AlertDialog.Builder(global_ctx);
                        //ViewGroup viewGroup = context.findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(global_ctx).inflate(R.layout.text_description_layout, viewGroup, false);
                        TextView textdescription=dialogView.findViewById(R.id.text_desc);
                        TextView cancel=dialogView.findViewById(R.id.cancel);
                        RecyclerView recyclerView=dialogView.findViewById(R.id.imge_list);

                        builder.setView(dialogView);
                        final AlertDialog alertDialog = builder.create();
                        String[] exts = itemData.split("[.]");
                        String ext = "";
                        if (exts.length > 0) {
                            ext = exts[exts.length - 1];
                        }

                        if(ext.equalsIgnoreCase("jpeg") ||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("png")||ext.equalsIgnoreCase("txt")||ext.equalsIgnoreCase("xls")){

                            recyclerView.setVisibility(View.VISIBLE);
                            CompanyImageAdapter companyImageAdapter= new CompanyImageAdapter(global_ctx,CompanyImagList);
                            recyclerView.setAdapter(companyImageAdapter);
                            alertDialog.show();

                        }else {
                            textdescription.setVisibility(View.VISIBLE);
                            textdescription.setText(itemData);
                            alertDialog.show();


                        }


                    }
                }
            }

            @Override
            public void onRowHeaderClick(int row) {
//                showAlert(true,"This is done");

            }
            @Override
            public void onColumnHeaderClick(int column) {
                showAlertIntfacesOpt("Ascending", "Descending", "Please Select Sorting Order for " + CompaniesHeadings.get(column - 1), a -> callSortCompaniesAPI(CompaniesHeadingKeys.get(column - 1), a + ""));
            }

            @Override
            public void onLeftTopHeaderClick() {
            }
        });
        companyTableLayout.setAdapter(companyTableAdapter);

    }

    public static void callSortCompaniesAPI(String short_name, String sort) {
        HashMap<String, String> values = new HashMap<>();
        values.put("type", "companies");
        values.put("column", short_name);
        values.put("sort", sort);
        pstPrms(true, SORTING_URL, values, (status, parse, msg, response) -> {
            if (!status) showAlert(true, msg);
            else {
                CompaniesList.clear();
                Company companyModel = new Company();
                companyModel.setCompany_name("CompanyName");
                companyModel.setCountry("Country");
                companyModel.setDescription("Description");
                companyModel.setHomepage("Homepage");
                companyModel.setContact("Contact");
                companyModel.setHeading_image("Files");
                companyModel.setShort_name("Projects");
                CompaniesList.add(companyModel);
                try {
                    JSONArray jsonArray3 = response.getJSONArray("companies");

                    for (int i = 0; i < jsonArray3.length(); i++) {
                        JSONObject jsonObject = jsonArray3.getJSONObject(i);
                        Company model = new Company();
                        model.setCompany_name(jsonObject.optString("company_name"));
                        model.setCountry(jsonObject.optString("country"));
//                        model.setField_activity(jsonObject.optString("field_activity"));
                        model.setDescription(jsonObject.optString("description"));
//                        model.setReference(jsonObject.optString("reference"));
                        model.setHomepage(jsonObject.optString("homepage"));
                        model.setContact(jsonObject.optString("contact"));

                        model.setShort_name(jsonObject.optString("short_name"));
                        model.setFiles("https://cdn5.vectorstock.com/i/1000x1000/42/34/green-progress-upload-download-symbol-vector-23994234.jpg");




//                        model.setSNo(String.valueOf(i+1));
                        JSONArray jsonArray =jsonObject.getJSONArray("files");
                        for (int j = 0; j < jsonArray.length(); j++){
                            CompanyImagList.clear();
                            CompanyImageListModel companyImageListModel= new CompanyImageListModel();
                            companyImageListModel.setImage_name(jsonArray.getString(j));
//                            model.setFiles(jsonArray.getString(0));
                            CompanyImagList.add(companyImageListModel);
                        }
                        CompaniesList.add(model);
                    }

                    initCompaniesAdapter();
                    companyTableAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}