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
import com.ptm.security.Adapter.EquipmentImageAdapter;
import com.ptm.security.Adapter.ProjectImageAdapter;
import com.ptm.security.Model.Equipment;
import com.ptm.security.Model.EquipmentImageListModel;
import com.ptm.security.Model.Project_addres;
import com.ptm.security.R;
import com.ptm.security.util.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.ptm.security.activities.Details2.EquipmentHeadingKeys;
import static com.ptm.security.activities.Details2.EquipmentHeadings;
import static com.ptm.security.activities.Details2.EquipmentImageList;
import static com.ptm.security.activities.Details2.EquipmentList;
import static com.ptm.security.activities.Details2.ProjectImagList;
import static com.ptm.security.activities.Details2.ProjectList;
import static com.ptm.security.util.Globals.IMAGES_EQUIPMENT;
import static com.ptm.security.util.Globals.SORTING_URL;
import static com.ptm.security.util.Globals.global_ctx;
import static com.ptm.security.util.Globals.pstPrms;
import static com.ptm.security.util.Globals.showAlert;
import static com.ptm.security.util.Globals.showAlertIntfacesOpt;


public class EquipmentLayoutFragment extends Fragment {

    public static LinkedAdaptiveTableAdapter equipmentTableAdapter;
    public static AdaptiveTableLayout equipmentTableLayout;

    public static EquipmentLayoutFragment newInstance() {
        EquipmentLayoutFragment fragment = new EquipmentLayoutFragment();

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
        View view= inflater.inflate(R.layout.fragment_equipment_layout, container, false);
        equipmentTableLayout = view.findViewById(R.id.tableLayout);
        initEquipmentAdapter();
        return view;
    }
    public static void initEquipmentAdapter() {
        equipmentTableAdapter = new EquipmentLinkedTableAdapter(global_ctx, EquipmentList.size(), Equipment.class.getDeclaredMethods().length / 2);
        equipmentTableAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int row, int column) {
                final String[] okFileExtensions = new String[] {"jpg", "png", "gif", "jpeg"};

                for(int i=0;i<row;i++){
                    for(int j=i;j<column;j++){
                        String itemData = "-"; // skip headers

                        Equipment equipment=EquipmentList.get(row);

                        switch (column) {

                            case 1:
                                itemData = equipment.getVendor_name();
                                break;
                            case 2:
                                itemData = equipment.getCategory();
                                break;
                            case 3:
                                itemData = equipment.getDescription();
                                break;
                            case 4:
                                itemData = equipment.getContact();
                                break;
                            case 5:
                                itemData = equipment.getCountry();
                                break;
                            case 6:
                                itemData = equipment.getImage();
                                break;
                            case 7:
                                itemData = equipment.getPrice();
                                break;
                            case 8:
                                itemData = equipment.getFiles();
                                break;
                            case 9:
                                itemData = equipment.getShort_name();
                                break;
                            default:
                                break;

                        }

                        ViewGroup viewGroup = null;
                        AlertDialog.Builder builder = new AlertDialog.Builder(global_ctx);
                        //ViewGroup viewGroup = context.findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(global_ctx).inflate(R.layout.text_description_layout, viewGroup, false);
                        TextView textdescription=dialogView.findViewById(R.id.text_desc);
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
                            EquipmentImageAdapter equipmentImageAdapter= new EquipmentImageAdapter(global_ctx,EquipmentImageList);
                            recyclerView.setAdapter(equipmentImageAdapter);
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
                showAlertIntfacesOpt("Ascending", "Descending", "Please Select Sorting Order for " + EquipmentHeadings.get(column - 1), a -> callSortEquipmentAPI(EquipmentHeadingKeys.get(column - 1), a + ""));
            }
            @Override
            public void onLeftTopHeaderClick() {

            }
        });
        equipmentTableLayout.setAdapter(equipmentTableAdapter);

    }
    public static void callSortEquipmentAPI(String short_name, String sort) {
        HashMap<String, String> values = new HashMap<>();
        values.put("type", "equipment");
        values.put("column", short_name);
        values.put("sort", sort);
        pstPrms(true, SORTING_URL, values, (status, parse, msg, response) -> {
            if (!status) showAlert(true, msg);
            else {
                EquipmentList.clear();
                Equipment equipmentmodel = new Equipment();
                equipmentmodel.setVendor_name("Vendor Name");
                equipmentmodel.setCategory("Category");
                equipmentmodel.setDescription("Description");
                equipmentmodel.setContact("Contact");
                equipmentmodel.setCountry("Country");
                equipmentmodel.setHeadingImage("Picture");
                equipmentmodel.setPrice(" Estim Price");
                equipmentmodel.setFiles("Files");
                equipmentmodel.setShort_name("Projects");

                EquipmentList.add(equipmentmodel);
                try {
                    JSONArray jsonArray3 = response.getJSONArray("equipment");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        JSONObject jsonObject = jsonArray3.getJSONObject(i);
                        com.ptm.security.Model.Equipment model = new com.ptm.security.Model.Equipment();
                        model.setVendor_name(jsonObject.optString("vendor_name"));
                        model.setCategory(jsonObject.optString("category"));
                        model.setDescription(jsonObject.optString("description"));
                        model.setContact(jsonObject.optString("contact"));
                        model.setCountry(jsonObject.optString("country"));
                        if(!jsonObject.optString("image").equalsIgnoreCase("")){
                            model.setImage(IMAGES_EQUIPMENT+jsonObject.optString("image"));

                        }
                        model.setPrice(jsonObject.optString("price"));
                        model.setShort_name(jsonObject.optString("short_name"));
                        model.setFiles("https://cdn5.vectorstock.com/i/1000x1000/42/34/green-progress-upload-download-symbol-vector-23994234.jpg");

//                        model.setSNo(String.valueOf(i+1));
                        JSONArray jsonArray =jsonObject.getJSONArray("files");
                        for (int j = 0; j < jsonArray.length(); j++){
                            EquipmentImageList.clear();
                            EquipmentImageListModel equipmentImageListModel= new EquipmentImageListModel();
                            equipmentImageListModel.setEquipmentImage(jsonArray.getString(j));
//                            model.setFiles(jsonArray.getString(0));
                            EquipmentImageList.add(equipmentImageListModel);
                        }
                        EquipmentList.add(model);
                    }
                    initEquipmentAdapter();
                    equipmentTableAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }



}