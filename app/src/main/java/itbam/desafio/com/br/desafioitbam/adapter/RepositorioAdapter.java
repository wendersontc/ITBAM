package itbam.desafio.com.br.desafioitbam.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import itbam.desafio.com.br.desafioitbam.R;
import itbam.desafio.com.br.desafioitbam.RequestsActivity;
import itbam.desafio.com.br.desafioitbam.models.RepositoriosModel;

public class RepositorioAdapter extends  RecyclerView.Adapter<RepositorioAdapter.MyViewHolder> {

    private ArrayList<RepositoriosModel> repsModel;
    private ImageLoader imageloader;
    private Context mcontext;

    public RepositorioAdapter(ArrayList<RepositoriosModel> repsModel,
                              ImageLoader il, Context context) {
        this.repsModel = repsModel;
        this.imageloader = il;
        this.mcontext = context;
    }


    @Override
    public RepositorioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositorioAdapter.MyViewHolder holder, int position) {
        holder.title.setText(repsModel.get(position).getFull_name());
        holder.description.setText(repsModel.get(position).getDescription());
        holder.nome.setText(repsModel.get(position).getOwner().getLogin());
        holder.forks.setText("Forks : " + repsModel.get(position).getForks());
        holder.stars.setText("Stars : " + repsModel.get(position).getStargazers_count());
        imageloader.get(repsModel.get(position).getOwner().getAvatar_url(),imageloader.getImageListener(holder.imagem,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
    }

    @Override
    public int getItemCount() {
        return repsModel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView title;
        private TextView description;
        private TextView nome;
        private TextView forks;
        private TextView stars;
        private ImageView imagem;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textViewItem1);
            description  = (TextView) view.findViewById(R.id.textViewItem2);
            nome = (TextView) view.findViewById(R.id.textViewItem3);
            imagem = (ImageView) view.findViewById(R.id.image_view);
            forks = (TextView) view.findViewById(R.id.textViewItem4);
            stars = (TextView) view.findViewById(R.id.textViewItem5);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            System.out.println("Elemento " + getAdapterPosition() + " clicado.");
            Bundle bundle = new Bundle();
            if(!repsModel.isEmpty()){
                bundle.putString("requests", String.valueOf(repsModel.get(getAdapterPosition()).getPulls_url()).substring(0,
                        String.valueOf(repsModel.get(getAdapterPosition()).getPulls_url()).length() - 9));
                bundle.putString("name", String.valueOf(repsModel.get(getAdapterPosition()).getOwner().getLogin()));
                Intent intent = new Intent(mcontext, RequestsActivity.class);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }

        }
    }
}
