package com.ptm.security.sample;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;
import com.ptm.security.Adapter.ProjectImageAdapter;
import com.ptm.security.Model.ProjectImageListModel;
import com.ptm.security.Model.Project_addres;
import com.ptm.security.R;

import com.ptm.security.util.CircleImageView;
import com.ptm.security.util.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.ptm.security.activities.Details2.CompaniesHeadings;
import static com.ptm.security.activities.Details2.ProjectHeadingKeys;
import static com.ptm.security.activities.Details2.ProjectHeadings;
import static com.ptm.security.activities.Details2.ProjectImagList;
import static com.ptm.security.activities.Details2.ProjectList;
import static com.ptm.security.util.Globals.SORTING_URL;
import static com.ptm.security.util.Globals.global_ctx;
import static com.ptm.security.util.Globals.pstPrms;
import static com.ptm.security.util.Globals.showAlert;
import static com.ptm.security.util.Globals.showAlertIntfacesOpt;

public class TableLayoutFragment
        extends Fragment {
    public static LinkedAdaptiveTableAdapter projectTableAdapter;
    public static AdaptiveTableLayout projectTableLayout;

    public static TableLayoutFragment newInstance() {
        TableLayoutFragment fragment = new TableLayoutFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_total_layout, container, false);
        projectTableLayout = view.findViewById(R.id.tableLayout);
        initAdapter();
        return view;
    }

    public static void initAdapter() {
        projectTableAdapter = new SampleLinkedTableAdapter(global_ctx, ProjectList.size(),Project_addres.class.getDeclaredMethods().length / 2);
        projectTableAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int row, int column) {

                for(int i=0;i<row;i++){
                    for(int j=i;j<column;j++){
                        String itemData = "-"; // skip headers
                        Project_addres projObj = ProjectList.get(row);
                        switch (column) {
                            case 1:
                                itemData = projObj.getShort_name();

                                break;
                            case 2:
                                itemData = projObj.getCustomer_contact();
                                break;
                            case 3:
                                itemData = projObj.getPrj_location();
                                break;
                            case 4:
                                itemData = projObj.getCat_description();
                                break;
                            case 5:
                                itemData = projObj.getPrj_description();
                                break;
                            case 6:
                                itemData = projObj.getPrj_status();
                                break;
                            case 7:
                                itemData = projObj.getStatus_date();
                                break;
                            case 8:
                                itemData = projObj.getReqst_date();
                                break;
                            case 9:
                                itemData = projObj.getPrj_lead();
                                break;
                            case 10:
                                itemData = projObj.getReminder_date();
                                break;
                            case 11:
                                itemData=projObj.getImage();
                                break;
          /*  case 12:
                itemData = projObj.getAbout_info();
                break;
            case 13:
                itemData = projObj.getName();
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
                            ProjectImageAdapter projectImageAdapter= new ProjectImageAdapter(global_ctx,ProjectImagList);
                            recyclerView.setAdapter(projectImageAdapter);
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
                showAlertIntfacesOpt("Ascending", "Descending", "Please Select Sorting Order for " + ProjectHeadings.get(column - 1), a -> callSortAPI(ProjectHeadingKeys.get(column - 1), a + ""));
            }

            @Override
            public void onLeftTopHeaderClick() {

            }
        });
        projectTableLayout.setAdapter(projectTableAdapter);
    }

    public static void callSortAPI(String short_name, String sort) {
        HashMap<String, String> values = new HashMap<>();
        values.put("type", "projects");
        values.put("column", short_name);
        values.put("sort", sort);
        pstPrms(true, SORTING_URL, values, (status, parse, msg, response) -> {
            if (!status) showAlert(true, msg);
            else {
                ProjectList.clear();
                Project_addres headingModel = new Project_addres();
                headingModel.setShort_name("Short Name");
                headingModel.setName("Customers");
                headingModel.setPrj_location("Location");
                headingModel.setCat_description("Category");
                headingModel.setPrj_description("Description");
                headingModel.setPrj_status("Status");
                headingModel.setAbout_info("Activity");
                headingModel.setReqst_date("Request Date");
                headingModel.setPrj_lead("Project Lead");
                headingModel.setReminder_date("Reminders");
                headingModel.setHeading_image("Files");

                ProjectList.add(headingModel);
                try {
                    JSONArray jsonArray1 = response.getJSONArray("projects");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        Project_addres model = new Project_addres();
                        model.setShort_name(jsonObject.optString("short_name"));
                        model.setPrj_location(jsonObject.optString("prj_location"));
                        model.setCat_description(jsonObject.optString("cat_description"));
                        model.setPrj_description(jsonObject.optString("prj_description"));
                        model.setReqst_date(jsonObject.optString("reqst_date"));
                        model.setStatus_date(jsonObject.optString("status_date"));
                        model.setReminder_date(jsonObject.optString("reminder_date"));
                        model.setPrj_status(jsonObject.optString("prj_status"));
                        model.setCustomer_contact(jsonObject.optString("customer_contact"));
                        model.setPrj_lead(jsonObject.optString("prj_lead"));
                        model.setImage("https://cdn5.vectorstock.com/i/1000x1000/42/34/green-progress-upload-download-symbol-vector-23994234.jpg");

                        JSONArray imageArray=jsonObject.optJSONArray("image");
                        assert imageArray != null;
                        for (int j = 0; j < imageArray.length(); j++){
                            ProjectImagList.clear();
                            ProjectImageListModel projectImageListModel= new ProjectImageListModel();
                            projectImageListModel.setProjectImage(imageArray.getString(j));
//                            model.setImage(imageArray.getString(0));
                            ProjectImagList.add(projectImageListModel);
                        }
                        ProjectList.add(model);
                    }
                    initAdapter();
                    projectTableAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}