package itbam.desafio.com.br.desafioitbam;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

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
import itbam.desafio.com.br.desafioitbam.models.RepositoriosModel;
import itbam.desafio.com.br.desafioitbam.utils.ConstantsUtil;
import itbam.desafio.com.br.desafioitbam.utils.HttpUtil;
import itbam.desafio.com.br.desafioitbam.utils.RenderImageUtil;

public class MainActivity extends AppCompatActivity {

    private JSONObject objectJson;
    private String stringJson;
    private RequestQueue rq;
    private ImageLoader imageLoader;

    private Integer page = 1;
    private RepositorioAdapter reposAdapter;
    private ArrayList<RepositoriosModel> repsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq = Volley.newRequestQueue(MainActivity.this);
        imageLoader = new RenderImageUtil().CacheImageLoader(rq);

        repsModel = new ArrayList<>();
        reposAdapter = new RepositorioAdapter(repsModel, imageLoader, this);

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.loadmore_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reposAdapter);

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
        HttpUtil.get("search/repositories?q=java&page="+page+"&per_page="+ ConstantsUtil.LOAD_LIMIT +"&sort=stars",null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody); // for UTF-8 encoding
                Log.d("reponse", "---------------- this is response : " + str);
                Gson gson = new Gson();
                try {
                    objectJson = new JSONObject(str);
                    stringJson = objectJson.getString("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RepositoriosModel[] repo = gson.fromJson(stringJson,RepositoriosModel[].class);
                for (int i = 0; i < repo.length; i++) {

                        repsModel.add(new RepositoriosModel(repo[i].getDescription(),
                                repo[i].getOwner(), repo[i].getFull_name(),
                                repo[i].getStargazers_count(), repo[i].getForks(),repo[i].getPulls_url()));
                        reposAdapter.notifyDataSetChanged();
                }

                //RepositoriosModel[] repo = gson.fromJson(stringJson,RepositoriosModel[].class);
                //RepositoriosAdapter adapter = new RepositoriosAdapter(MainActivity.this, R.layout.list_item_fragment, repo, imageLoader);
                //listaView.setAdapter(adapter);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String str = new String(responseBody); // for UTF-8 encoding
                Log.d("erro", "---------------- this is error : " + str);
            }
        });

    }

    private void loadMore(){

        HttpUtil.get("search/repositories?q=java&page="+(page++)+"&per_page="+ ConstantsUtil.LOAD_LIMIT +"&sort=stars",null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody); // for UTF-8 encoding
                Log.d("reponse", "---------------- this is response : " + str);
                Gson gson = new Gson();
                try {
                    objectJson = new JSONObject(str);
                    stringJson = objectJson.getString("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RepositoriosModel[] repo = gson.fromJson(stringJson,RepositoriosModel[].class);
                for (int i = 0; i < repo.length; i++) {

                    repsModel.add(new RepositoriosModel(repo[i].getDescription(),
                            repo[i].getOwner(), repo[i].getFull_name(),
                            repo[i].getStargazers_count(), repo[i].getForks(), repo[i].getPulls_url()));
                    reposAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String str = new String(responseBody); // for UTF-8 encoding
                Log.d("erro", "---------------- this is error : " + str);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
