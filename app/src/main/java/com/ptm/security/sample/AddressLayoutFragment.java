package com.ptm.security.sample;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;
import com.ptm.security.Model.Address;
import com.ptm.security.Model.Project_addres;
import com.ptm.security.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.ptm.security.activities.Details2.AddressBookList;
import static com.ptm.security.activities.Details2.AddressHeadingKeys;
import static com.ptm.security.activities.Details2.AddressHeadings;
import static com.ptm.security.activities.Details2.ProjectList;
import static com.ptm.security.util.Globals.SORTING_URL;
import static com.ptm.security.util.Globals.global_ctx;
import static com.ptm.security.util.Globals.pstPrms;
import static com.ptm.security.util.Globals.showAlert;
import static com.ptm.security.util.Globals.showAlertIntfacesOpt;


public class AddressLayoutFragment extends Fragment {
    public static LinkedAdaptiveTableAdapter addressTableAdapter;
    public static AdaptiveTableLayout addressTableLayout;
    public static AddressLayoutFragment newInstance() {
        AddressLayoutFragment fragment = new AddressLayoutFragment();
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
        View view= inflater.inflate(R.layout.fragment_address_layout, container, false);
        Log.d("TAG","AddressList"+AddressBookList);
        addressTableLayout = view.findViewById(R.id.tableLayout);

        initAddressAdapter();
        return view;
    }
    public static void initAddressAdapter() {
        addressTableAdapter = new AddressLinkedTableAdapter(global_ctx, AddressBookList.size(), Address.class.getDeclaredMethods().length / 2);
        addressTableAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int row, int column) {

                for(int i=0;i<row;i++){
                    for(int j=i;j<column;j++){
                        String itemData = "-"; // skip headers
                        Address address=AddressBookList.get(row);
                        switch (column) {
                            case 1:
                                itemData = address.getName();
                                break;
                            case 2:
                                itemData = address.getCompany();
                                break;
                            case 3:
                                itemData = address.getEmail();
                                break;
                            case 4:
                                itemData = address.getMobile_number();
                                break;
                            case 5:
                                itemData = address.getAddress();
                                break;
                            default:
                                break;

                        }

                        ViewGroup viewGroup = null;
                        AlertDialog.Builder builder = new AlertDialog.Builder(global_ctx);
                        //ViewGroup viewGroup = context.findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(global_ctx).inflate(R.layout.text_description_layout, viewGroup, false);
                        TextView textdescription=dialogView.findViewById(R.id.text_desc);
                        builder.setView(dialogView);
                        final AlertDialog alertDialog = builder.create();
                        if(itemData.equalsIgnoreCase("")){
                            alertDialog.dismiss();
                        }else{
                            alertDialog.show();
                            textdescription.setText(itemData);



                        }

                    }
                }
            }

            @Override
            public void onRowHeaderClick(int row) {

            }

            @Override
            public void onColumnHeaderClick(int column) {
                showAlertIntfacesOpt("Ascending", "Descending", "Please Select Sorting Order for " + AddressHeadings.get(column - 1), a -> callSortAddressAPI(AddressHeadingKeys.get(column - 1), a + ""));
            }

            @Override
            public void onLeftTopHeaderClick() {

            }
        });
        addressTableLayout.setAdapter(addressTableAdapter);

    }
    public static void callSortAddressAPI(String short_name, String sort) {
        HashMap<String, String> values = new HashMap<>();
        values.put("type", "address_book");
        values.put("column", short_name);
        values.put("sort", sort);
        pstPrms(true, SORTING_URL, values, (status, parse, msg, response) -> {
            if (!status) showAlert(true, msg);
            else {
                AddressBookList.clear();
                Address addressModel = new Address();
                addressModel.setName("Name");
                addressModel.setCompany("Company");
                addressModel.setEmail("Email");
                addressModel.setMobile_number("Mobile Number");
                addressModel.setAddress("Address");
                AddressBookList.add(addressModel);
                try {
                    JSONArray jsonArray2 = response.getJSONArray("address_book");

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                        Address model = new Address();
                        model.setName(jsonObject.optString("name"));
//                        model.setDesignation(jsonObject.optString("designation"));

                        model.setCompany(jsonObject.optString("company"));
                        model.setEmail(jsonObject.optString("email"));
                        model.setMobile_number(jsonObject.optString("mobile_number"));
                        model.setAddress(jsonObject.optString("address"));
//                        model.setPrj_lead(jsonObject.optString("prj_lead"));
//                        model.setStatus(jsonObject.optString("status"));
//                        model.setReg_date(jsonObject.optString("reg_date"));
//                        model.setSNo(String.valueOf(i+1));

                        AddressBookList.add(model);
                    }
                    initAddressAdapter();
                    addressTableAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}