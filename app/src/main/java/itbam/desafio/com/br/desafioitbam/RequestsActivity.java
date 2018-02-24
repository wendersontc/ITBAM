package itbam.desafio.com.br.desafioitbam;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import itbam.desafio.com.br.desafioitbam.adapter.RepositorioAdapter;
import itbam.desafio.com.br.desafioitbam.adapter.RequestsAdapter;
import itbam.desafio.com.br.desafioitbam.models.RepositoriosModel;
import itbam.desafio.com.br.desafioitbam.models.RequestsModel;
import itbam.desafio.com.br.desafioitbam.utils.ConstantsUtil;
import itbam.desafio.com.br.desafioitbam.utils.HttpUtil;
import itbam.desafio.com.br.desafioitbam.utils.RenderImageUtil;

public class RequestsActivity  extends AppCompatActivity{

    private JSONObject objectJson;
    private String stringJson;
    private RequestQueue rq;
    private ImageLoader imageLoader;
    private String requests = null;

    private Integer page = 2;
    private RequestsAdapter reqAdapter;
    private ArrayList<RequestsModel> reqModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests);

        rq = Volley.newRequestQueue(RequestsActivity.this);
        imageLoader = new RenderImageUtil().CacheImageLoader(rq);

        final Bundle extras = getIntent().getExtras();
        requests = (String) extras.getSerializable("requests");

        reqModels = new ArrayList<>();
        reqAdapter = new RequestsAdapter(reqModels, imageLoader, this);

        Log.d("req", "---------------- this is response : " + requests);

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.loadmore_recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reqAdapter);

        firstLoadData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        loadMore();
                    }
                }
            }
        });
    }

    private void firstLoadData() {
        HttpUtil.getRequests(this.requests + "?page=2&per_page=5",null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody); // for UTF-8 encoding
                Gson gson = new Gson();

                RequestsModel[] repo = gson.fromJson(str,RequestsModel[].class);
                for (int i = 0; i < repo.length; i++) {

                    reqModels.add(new RequestsModel(repo[i].getTitle(),
                            repo[i].getBody(), ""+repo[i].getHtml_url(),
                            repo[i].getCreated_at(), repo[i].getUser()));
                    reqAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String str = new String(responseBody); // for UTF-8 encoding
                Log.d("erro", "---------------- this is error : " + str);
            }
        });

    }

    private void loadMore(){
        HttpUtil.getRequests(this.requests + "?page="+(page++)+"&per_page=5",null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody); // for UTF-8 encoding
                Gson gson = new Gson();

                RequestsModel[] repo = gson.fromJson(str,RequestsModel[].class);
                for (int i = 0; i < repo.length; i++) {

                    reqModels.add(new RequestsModel(repo[i].getTitle(),
                            repo[i].getBody(), ""+repo[i].getHtml_url(),
                            repo[i].getCreated_at(), repo[i].getUser()));
                    reqAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String str = new String(responseBody); // for UTF-8 encoding
                Log.d("erro", "---------------- this is error : " + str);
            }
        });
    }
}
