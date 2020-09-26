package com.ptm.security.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import com.ptm.security.Model.Address;
import com.ptm.security.Model.Company;
import com.ptm.security.Model.Equipment;
import com.ptm.security.Model.CompanyImageListModel;
import com.ptm.security.Model.EquipmentImageListModel;
import com.ptm.security.Model.ProjectImageListModel;
import com.ptm.security.Model.Project_addres;
import com.ptm.security.R;
import com.ptm.security.sample.AddressLayoutFragment;
import com.ptm.security.sample.CompanyLayoutFragment;
import com.ptm.security.sample.EquipmentLayoutFragment;
import com.ptm.security.sample.TableLayoutFragment;
import com.ptm.security.util.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ptm.security.sample.AddressLayoutFragment.initAddressAdapter;
import static com.ptm.security.sample.CompanyLayoutFragment.initCompaniesAdapter;
import static com.ptm.security.sample.EquipmentLayoutFragment.initEquipmentAdapter;
import static com.ptm.security.sample.TableLayoutFragment.initAdapter;
import static com.ptm.security.util.Globals.DETAILS_URL;
import static com.ptm.security.util.Globals.IMAGES_EQUIPMENT;
import static com.ptm.security.util.Globals.SEARCH_URL;
import static com.ptm.security.util.Globals.hideKeyboard;
import static com.ptm.security.util.Globals.pstPrms;
import static com.ptm.security.util.Globals.showAlert;
import static com.ptm.security.util.Globals.showKeyboard;
import static com.ptm.security.util.Globals.toast;

public class Details2 extends BaseActivity {
    public static List<CompanyImageListModel> CompanyImagList;
    public static List<ProjectImageListModel> ProjectImagList;
    public static List<EquipmentImageListModel> EquipmentImageList;

    public static List<Address> AddressBookList;
    public static List<Company> CompaniesList;
    public static List<Equipment> EquipmentList;
    public static List<Project_addres> ProjectList;


    public static List<String> CompaniesHeadings;
    public static List<String> CompaniesHeadingKeys;
    public static List<String> AddressHeadings;
    public static List<String> AddressHeadingKeys;
    public static List<String> EquipmentHeadings;
    public static List<String> EquipmentHeadingKeys;
    public static List<String> ProjectHeadings;
    public static List<String> ProjectHeadingKeys;


    TabLayout tabLayout;
    ViewPager viewPager;
    public static ImageView searchBtn, searchCallBtn;
    public static RelativeLayout searchLay;
    public static EditText etSearch;
    public static ImageView clearBtn;
    public static int activeTab = 0;
//    public static String[] CompaniesHeadings = new String[]{"", "", "", "", "", "", "", "", ""};
//    public static String[] CompaniesHeadingKeys = new String[]{"", "", "", "", "", "", "", "", ""};
//    public static String[] AddressHeadings = new String[]{" Name", "Designation", "Company", "Email", "Mobile Number", "Address", "Project Lead", "Status", "Reg. Date"};
//    public static String[] AddressHeadingKeys = new String[]{"name", "designation", "company", "email", "mobile_number", "address", "prj_lead", "status", "reg_date"};
//    public static String[] EquipmentHeadings = new String[]{"Name", "Category", "Description", "Contact", "Country", "Image", "Price", "Files ", "Short Name"};
//    public static String[] EquipmentHeadingKeys = new String[]{"vendor_name", "category", "description", "contact", "country", "image", "price", "files", "short_name"};


//    public static String[] ProjectHeadings = new String[]{"", "", "", "", "Request Date", "Status Date", "Reminder Date", "Status", "Contact", "Project Lead","Image", "About Info", "Name"};
//    public static String[] ProjectHeadingKeys = new String[]{"", };
    public static TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        EquipmentImageList=new ArrayList<>();
        ProjectImagList= new ArrayList<>();
        CompanyImagList= new ArrayList<>();
        CompaniesHeadings = new ArrayList<>();
        CompaniesHeadingKeys = new ArrayList<>();
        AddressHeadings = new ArrayList<>();
        AddressHeadingKeys = new ArrayList<>();
        EquipmentHeadings = new ArrayList<>();
        EquipmentHeadingKeys = new ArrayList<>();
        ProjectHeadings = new ArrayList<>();
        ProjectHeadingKeys = new ArrayList<>();

        CompaniesHeadings.add("Company Name");   //Company Heading
        CompaniesHeadings.add("Country");
        CompaniesHeadings.add("Description");
        CompaniesHeadings.add("Homepage");
        CompaniesHeadings.add("Contact");
        CompaniesHeadings.add("Files");
        CompaniesHeadings.add("Projects");
//        CompaniesHeadings.add("Company");

/*        CompaniesHeadings.add("Field Activity");
        CompaniesHeadings.add("Reference");
        CompaniesHeadings.add("Short Name");*/

        CompaniesHeadingKeys.add("company_name");   //Company key heading
        CompaniesHeadingKeys.add("country");
        CompaniesHeadingKeys.add("description");
        CompaniesHeadingKeys.add("homepage");
        CompaniesHeadingKeys.add("contact");
        CompaniesHeadingKeys.add("files");
        CompaniesHeadingKeys.add("short_name");

/*        CompaniesHeadingKeys.add("field_activity");
        CompaniesHeadingKeys.add("reference");
     */

        ProjectHeadings.add("Short Name");   //Project Heading
        ProjectHeadings.add("Customer");
        ProjectHeadings.add("Location");
        ProjectHeadings.add("Category");
        ProjectHeadings.add("Description");
        ProjectHeadings.add("Status");
        ProjectHeadings.add("Activity");
        ProjectHeadings.add("Request Date");
        ProjectHeadings.add("Project Lead");
        ProjectHeadings.add("Reminders");
        ProjectHeadings.add("Files");
//        ProjectHeadings.add("ProjectPTM");
//        ProjectHeadings.add("Customer Contact");
//        ProjectHeadings.add("Project");

/*        ProjectHeadings.add("Image");
        ProjectHeadings.add("About Info");
        ProjectHeadings.add("Name");*/


        ProjectHeadingKeys.add("short_name");   //Project key heading
        ProjectHeadingKeys.add("customer_contact");
        ProjectHeadingKeys.add("prj_location");
        ProjectHeadingKeys.add("cat_description");
        ProjectHeadingKeys.add("prj_description");
        ProjectHeadingKeys.add("status_date");
        ProjectHeadingKeys.add("about_info");
        ProjectHeadingKeys.add("reqst_date");
        ProjectHeadingKeys.add("prj_lead");
        ProjectHeadingKeys.add("reminder_date");
        ProjectHeadingKeys.add("image");

      /*  ProjectHeadingKeys.add("prj_status");
        ProjectHeadingKeys.add("name");*/

        EquipmentHeadings.add("Vendor Name");   //Equipment Headings
        EquipmentHeadings.add("Category");
        EquipmentHeadings.add("Description");
        EquipmentHeadings.add("Contact");
        EquipmentHeadings.add("Country");
        EquipmentHeadings.add("Picture");
        EquipmentHeadings.add("Estim Price");
        EquipmentHeadings.add("Files");
        EquipmentHeadings.add("Projects");
//        EquipmentHeadings.add("Equipment");

        EquipmentHeadingKeys.add("vendor_name");   //Equipment key heading
        EquipmentHeadingKeys.add("category");
        EquipmentHeadingKeys.add("description");
        EquipmentHeadingKeys.add("contact");
        EquipmentHeadingKeys.add("country");
        EquipmentHeadingKeys.add("image");
        EquipmentHeadingKeys.add("price");
        EquipmentHeadingKeys.add("files");
        EquipmentHeadingKeys.add("short_name");

        AddressHeadings.add("Name");   //Address Headings
        AddressHeadings.add("Company");
        AddressHeadings.add("Email");
        AddressHeadings.add("Mobile Number");
        AddressHeadings.add("Address");
//        AddressHeadings.add("Address Book");


/*        AddressHeadings.add("Designation");
        AddressHeadings.add("Project Lead");
        AddressHeadings.add("Status");
        AddressHeadings.add("Reg. Date");*/

        AddressHeadingKeys.add("name");   //Address key heading
        AddressHeadingKeys.add("company");
        AddressHeadingKeys.add("email");
        AddressHeadingKeys.add("mobile_number");
        AddressHeadingKeys.add("address");

     /*   AddressHeadingKeys.add("designation");
        AddressHeadingKeys.add("prj_lead");
        AddressHeadingKeys.add("status");
        AddressHeadingKeys.add("reg_date");*/

        getDetails();
        initViews();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_details2;
    }

    private void getDetails() {
        AddressBookList = new ArrayList<>();
        CompaniesList = new ArrayList<>();
        ProjectList = new ArrayList<>();
        EquipmentList = new ArrayList<>();
//        HashMap<String, String> values = new HashMap<>();
//        values.put("search_text", "");
//        values.put("search_type", "");
        pstPrms(true, DETAILS_URL, new HashMap<>(), (status, parse, msg, response) -> {
            if (!status) showAlert(true, msg);
            else {
                AddressBookList.clear();
                CompaniesList.clear();
                ProjectList.clear();
                EquipmentList.clear();
                try {
                    JSONArray jsonArray1 = response.getJSONArray("projects");
                    JSONArray jsonArray2 = response.getJSONArray("equipment");
                    JSONArray jsonArray3 = response.getJSONArray("companies");
                    JSONArray jsonArray4 = response.getJSONArray("address_book");

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

                    Company companyModel = new Company();
                    companyModel.setCompany_name("CompanyName");
                    companyModel.setCountry("Country");
                    companyModel.setDescription("Description");
                    companyModel.setHomepage("Homepage");
                    companyModel.setContact("Contact");
                    companyModel.setHeading_image("Files");
                    companyModel.setShort_name("Projects");
                    CompaniesList.add(companyModel);

                    Address addressModel = new Address();
                    addressModel.setName("Name");
                    addressModel.setCompany("Company");
                    addressModel.setEmail("Email");
                    addressModel.setMobile_number("Mobile Number");
                    addressModel.setAddress("Address");
                    AddressBookList.add(addressModel);

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

//                        model.setAbout_info(jsonObject.optString("about_info"));
//                        model.setName(jsonObject.optString("name"));
                        JSONArray imageArray=jsonObject.optJSONArray("image");
                        assert imageArray != null;
                        for (int j = 0; j < imageArray.length(); j++){
//                            ProjectImagList.clear();
                            ProjectImageListModel projectImageListModel= new ProjectImageListModel();
                            projectImageListModel.setProjectImage(imageArray.getString(j));
//                            model.setImage(imageArray.getString(0));
                            ProjectImagList.add(projectImageListModel);
                        }

                        ProjectList.add(model);
                    }
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
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
//                        model.setFiles("files");
                        model.setShort_name(jsonObject.optString("short_name"));
                        model.setFiles("https://cdn5.vectorstock.com/i/1000x1000/42/34/green-progress-upload-download-symbol-vector-23994234.jpg");

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
                        model.setFiles("https://cdn5.vectorstock.com/i/1000x1000/42/34/green-progress-upload-download-symbol-vector-23994234.jpg");
                        model.setShort_name(jsonObject.optString("short_name"));
//                        model.setFiles("image");
//                        model.setSNo(String.valueOf(i + 1));
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



                    for (int i = 0; i < jsonArray4.length(); i++) {
                        JSONObject jsonObject = jsonArray3.getJSONObject(i);
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
//                        model.setSNo(String.valueOf(i + 1));

                        AddressBookList.add(model);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(ProjectList.size()==1){
                    showAlert(true,"No data found");
                }else{
                    initFragment();
                }
            }
        });
    }

    private void initFragment() {
        highlightTab(activeTab=0);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, TableLayoutFragment.newInstance(), TableLayoutFragment.class.getSimpleName()).commit();
    }


    private void initViews() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv1.setOnClickListener(view -> {
            activeTab = 0;
            resetSearchLayout();
            if(ProjectList.size()==1){
                showAlert(true,"No data found");
            }else{
                initFragment();
            }
           });
        tv2.setOnClickListener(view -> {
            activeTab = 1;
            resetSearchLayout();
//            initFragment();
      if(EquipmentList.size()==1){
    showAlert(true,"No data found");
     }else{
    highlightTab(activeTab);
    getSupportFragmentManager().beginTransaction().replace(R.id.container, EquipmentLayoutFragment.newInstance(), EquipmentLayoutFragment.class.getSimpleName()).commit();
      } });

        tv3.setOnClickListener(view -> {
            activeTab = 2;
            resetSearchLayout();
//            initFragment();
     if(CompaniesList.size()==1){
    showAlert(true,"No data found");
    }else{
    highlightTab(activeTab);
    getSupportFragmentManager().beginTransaction().replace(R.id.container, CompanyLayoutFragment.newInstance(), CompanyLayoutFragment.class.getSimpleName()).commit();
     } });
        tv4.setOnClickListener(view -> {
            activeTab = 3;
            resetSearchLayout();
//            initFragment();
     if(AddressBookList.size()==1){
   showAlert(true,"No data found");
    }else {
    highlightTab(activeTab);
    getSupportFragmentManager().beginTransaction().replace(R.id.container, AddressLayoutFragment.newInstance(), AddressLayoutFragment.class.getSimpleName()).commit();
    } });
//        tabLayout = findViewById(R.id.mTabLayout);
        viewPager = findViewById(R.id.viewPager);
        searchBtn = findViewById(R.id.searchBtn);
        searchCallBtn = findViewById(R.id.searchCallBtn);
        searchLay = findViewById(R.id.searchLay);
        etSearch = findViewById(R.id.etSearch);
        clearBtn = findViewById(R.id.clearBtn);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        AddressBookList = new ArrayList<>();
        CompaniesList = new ArrayList<>();
        ProjectList = new ArrayList<>();
        EquipmentList = new ArrayList<>();
        searchBtn.setOnClickListener(view -> {
            searchLay.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.GONE);
            etSearch.requestFocus();
            showKeyboard();
        });
        searchCallBtn.setOnClickListener(view -> {
            switch (activeTab) {
                case 0:
                    searchProject();
                    break;
                case 1:
                    searchEquipment();
                    break;
                case 2:
           searchCompanies() ;
                break;
                case 3:
   searchAddress();
      break;
            }
        });
        clearBtn.setOnClickListener(view -> {
                    resetSearchLayout();
                    getDetails();
                    highlightTab(activeTab=0);

                }
        );
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etSearch.setTextSize(etSearch.getText().toString().length() > 14 ? 12f : 15f);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchAddress() {
        String streditsearch = etSearch.getText().toString();
        if (streditsearch.equalsIgnoreCase("")) {
            toast("Please enter address");
        } else {
            AddressBookList.clear();
            Address addressModel = new Address();
            addressModel.setName("Name");
            addressModel.setCompany("Company");
            addressModel.setEmail("Email");
            addressModel.setMobile_number("Mobile Number");
            addressModel.setAddress("Address");
//            addressModel.setPrj_lead("Project Lead");
//            addressModel.setStatus("Status");
//            addressModel.setReg_date("Reg. Date");

            AddressBookList.add(addressModel);
            HashMap<String, String> values = new HashMap<>();
            values.put("search_text", streditsearch);
            values.put("search_type", "address_book");
            pstPrms(true, SEARCH_URL, values, (status, parse, msg, response) -> {
                if (!status) showAlert(true, msg);
                else {
                    try {

                        JSONArray jsonArray2 = response.getJSONArray("address_book");
                        if(jsonArray2.length()==0){
                            showAlert(true,"No Data Found");
                        }else{
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                                Address model = new Address();
                                model.setName(jsonObject.optString("name"));
//                                model.setDesignation(jsonObject.optString("designation"));

                                model.setCompany(jsonObject.optString("company"));
                                model.setEmail(jsonObject.optString("email"));
                                model.setMobile_number(jsonObject.optString("mobile_number"));
                                model.setAddress(jsonObject.optString("address"));
//                                model.setPrj_lead(jsonObject.optString("prj_lead"));
//                                model.setStatus(jsonObject.optString("status"));
//                                model.setReg_date(jsonObject.optString("reg_date"));

                                AddressBookList.add(model);
                            }
                            initAddressAdapter();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void searchCompanies() {
        String streditsearch = etSearch.getText().toString().trim();
        if (streditsearch.equalsIgnoreCase("")) {
            toast("Please enter company name");
        } else {
            CompaniesList.clear();
            Company companyModel = new Company();
            companyModel.setCompany_name("Company Name");
            companyModel.setCountry("Country");
            companyModel.setDescription("Description");
            companyModel.setHomepage("Homepage");
            companyModel.setContact("Contact");
            companyModel.setHeading_image("Files");
            companyModel.setShort_name("Projects");

            CompaniesList.add(companyModel);

            HashMap<String, String> values = new HashMap<>();
            values.put("search_text", streditsearch);
            values.put("search_type", "companies");
            pstPrms(true, SEARCH_URL, values, (status, parse, msg, response) -> {
                if (!status) showAlert(true, msg);
                else {
                    try {
                        JSONArray jsonArray3 = response.getJSONArray("companies");
                        if (jsonArray3.length() == 0) {
                            showAlert(true, "No Data Found");
                        } else {
                            for (int i = 0; i < jsonArray3.length(); i++) {
                                JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                Company model = new Company();
                                model.setCompany_name(jsonObject.optString("company_name"));
                                model.setCountry(jsonObject.optString("country"));
//                                model.setField_activity(jsonObject.optString("field_activity"));
                                model.setDescription(jsonObject.optString("description"));
//                                model.setReference(jsonObject.optString("reference"));
                                model.setHomepage(jsonObject.optString("homepage"));
                                model.setContact(jsonObject.optString("contact"));
                                model.setShort_name(jsonObject.optString("short_name"));
                                model.setFiles("https://cdn5.vectorstock.com/i/1000x1000/42/34/green-progress-upload-download-symbol-vector-23994234.jpg");

                                JSONArray jsonArray =jsonObject.getJSONArray("files");
                                for (int j = 0; j < jsonArray.length(); j++){
                                    CompanyImagList.clear();
                                    CompanyImageListModel companyImageListModel= new CompanyImageListModel();
                                    companyImageListModel.setImage_name(jsonArray.getString(j));
//                                    model.setFiles(jsonArray.getString(0));
                                    CompanyImagList.add(companyImageListModel);
                                }
                                CompaniesList.add(model);
                            }
                            initCompaniesAdapter();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private void searchEquipment() {
        String streditsearch = etSearch.getText().toString().trim();
        if (streditsearch.equalsIgnoreCase("")) {
            toast("Please enter equipment name");
        } else {
            EquipmentList.clear();
            com.ptm.security.Model.Equipment equipmentmodel = new com.ptm.security.Model.Equipment();
            equipmentmodel.setVendor_name("Vendor Name");
            equipmentmodel.setCategory("Category");
            equipmentmodel.setDescription("Description");
            equipmentmodel.setContact("Contact");
            equipmentmodel.setCountry("Country");
            equipmentmodel.setHeadingImage("Picture");
            equipmentmodel.setPrice("Estim Price");
            equipmentmodel.setFiles("Files");
            equipmentmodel.setShort_name("Projects");
            EquipmentList.add(equipmentmodel);
            HashMap<String, String> values = new HashMap<>();
            values.put("search_text", streditsearch);
            values.put("search_type", "equipment");
            pstPrms(true, SEARCH_URL, values, (status, parse, msg, response) -> {
                if (!status) showAlert(true, msg);
                else {
                    try {
                        JSONArray jsonArray3 = response.getJSONArray("equipment");
                        if (jsonArray3.length() == 0) {
                            showAlert(true, "No Data Found");
                        } else {
                            for (int i = 0; i < jsonArray3.length(); i++) {
                                JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                com.ptm.security.Model.Equipment model = new com.ptm.security.Model.Equipment();
                                model.setVendor_name(jsonObject.optString("vendor_name"));
                                model.setCategory(jsonObject.optString("category"));
                                model.setDescription(jsonObject.optString("description"));
                                model.setContact(jsonObject.optString("contact"));
                                model.setCountry(jsonObject.optString("country"));
                                model.setFiles("https://cdn5.vectorstock.com/i/1000x1000/42/34/green-progress-upload-download-symbol-vector-23994234.jpg");
                                if(!jsonObject.optString("image").equalsIgnoreCase("")){
                                    model.setImage(IMAGES_EQUIPMENT+jsonObject.optString("image"));

                                }
                                model.setPrice(jsonObject.optString("price"));
//                                model.setFiles("files");
                                model.setShort_name(jsonObject.optString("short_name"));
                                JSONArray jsonArray =jsonObject.getJSONArray("files");
                                for (int j = 0; j < jsonArray.length(); j++){
                                    EquipmentImageList.clear();
                                    EquipmentImageListModel equipmentImageListModel= new EquipmentImageListModel();
                                    equipmentImageListModel.setEquipmentImage(jsonArray.getString(j));
//                                    model.setFiles(jsonArray.getString(0));
                                    EquipmentImageList.add(equipmentImageListModel);
                                }


                                EquipmentList.add(model);
                            }
                            initEquipmentAdapter();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private void searchProject() {
        String streditsearch = etSearch.getText().toString().trim();
        if (streditsearch.equalsIgnoreCase("")) {
            toast("Please enter project name");
        } else {
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

//            headingModel.setReqst_date("Request Date");
//            headingModel.setStatus_date("Status Date");
//            headingModel.setHeading_image("Image");
//            headingModel.setCustomer_contact("Contact");
            ProjectList.add(headingModel);
            HashMap<String, String> values = new HashMap<>();
            values.put("search_text", streditsearch);
            values.put("search_type", "projects");
            pstPrms(true, SEARCH_URL, values, (status, parse, msg, response) -> {
                if (!status) showAlert(true, msg);
                else {
                    try {
                        JSONArray jsonArray1 = response.getJSONArray("projects");
                        if (jsonArray1.length() == 0) {
                            showAlert(true, "No data found");
                        } else {
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
//                                    ProjectImagList.clear();
                                    ProjectImageListModel projectImageListModel= new ProjectImageListModel();
                                    projectImageListModel.setProjectImage(imageArray.getString(j));
//                                    model.setImage(imageArray.getString(0));
                                    ProjectImagList.add(projectImageListModel);
                                }
                                ProjectList.add(model);
                            }
                            initAdapter();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
    private void highlightTab(int i) {
        tv1.setBackgroundResource(R.drawable.roundrect_nopad);
        tv2.setBackgroundResource(R.drawable.roundrect_nopad);
        tv3.setBackgroundResource(R.drawable.roundrect_nopad);
        tv4.setBackgroundResource(R.drawable.roundrect_nopad);
        switch (activeTab) {
            case 0:
                tv1.setBackgroundResource(R.drawable.roundrect_nopad_select);
                break;
            case 1:
                tv2.setBackgroundResource(R.drawable.roundrect_nopad_select);
                break;
            case 2:
                tv3.setBackgroundResource(R.drawable.roundrect_nopad_select);
                break;
            case 3:
                tv4.setBackgroundResource(R.drawable.roundrect_nopad_select);
                break;
        }

    }

    public static void resetSearchLayout() {
        searchBtn.setVisibility(View.VISIBLE);
        searchLay.setVisibility(View.GONE);
        etSearch.setText("");
        hideKeyboard();
    }
}
