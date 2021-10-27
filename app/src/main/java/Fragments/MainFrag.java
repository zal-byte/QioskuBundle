package Fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zadev.qiosku.Helper.Server;
import com.zadev.qiosku.Helper.Snacc;
import com.zadev.qiosku.MainActivity;
import com.zadev.qiosku.R;
import com.zadev.qiosku.ViewModel.MProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.MainProductAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFrag extends Fragment {

    ArrayList<MProduct> mProduct = new ArrayList<>();
    RecyclerView main_recyclerview;
    Server server;
    Snacc snacc;

    public MainFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFrag newInstance(String param1, String param2) {
        MainFrag fragment = new MainFrag();

        return fragment;
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        server = new Server();
        snacc = new Snacc(((MainActivity) getActivity()));

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        main_recyclerview = view.findViewById(R.id.main_recyclerview);
        main_recyclerview.setLayoutManager(new GridLayoutManager(((MainActivity) getActivity()), 2));
//        main_recyclerview.setLayoutManager(new LinearLayoutManager(((MainActivity) getActivity())));

        fetchData();


        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchData() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String res) {
                super.onPostExecute(res);
            }

            @Override
            protected String doInBackground(Void... voids) {
                String res = "";
                try {
                    StringRequest sr = new StringRequest(Request.Method.GET, server.api, response -> {
                        parseResponse(response);
                    }, error -> {
                        snacc.Snackme(error.getMessage(), false);
                    });
                    RequestQueue queue = Volley.newRequestQueue(((MainActivity) getActivity()));
                    queue.add(sr);
                } catch (Exception ex) {
                    System.out.println("FetchProductException_> " + ex.getMessage());
                }
                return res;
            }
        }.execute();
    }

    private void parseResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                if( object.getBoolean("status") == true )
                {
                    MProduct product = new MProduct();
                    product.setP_id(object.getString("p_id"));
                    product.setP_name(object.getString("p_name"));
                    product.setP_image(object.getString("p_image"));
                    product.setP_description(object.getString("p_description"));
                    product.setP_quantity(object.getString("p_quantity"));
                    product.setP_price(object.getString("p_price"));

                    mProduct.add(product);
                }else
                {
                    snacc.Snackme(object.getString("msg"), false);
                }
            }

            if( mProduct.size() > 0)
            {
                setAdapter();
            }

        } catch (Exception e) {
            snacc.Snackme(e.getMessage(), false);
        }
    }
    private void setAdapter()
    {
        MainProductAdapter adapter = new MainProductAdapter(((MainActivity) getActivity()), mProduct);
        main_recyclerview.setAdapter(adapter);
    }

}