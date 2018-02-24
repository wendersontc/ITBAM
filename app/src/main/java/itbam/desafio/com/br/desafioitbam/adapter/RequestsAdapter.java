package itbam.desafio.com.br.desafioitbam.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import itbam.desafio.com.br.desafioitbam.models.RequestsModel;

public class RequestsAdapter extends  RecyclerView.Adapter<RequestsAdapter.MyViewHolder> {

    private ArrayList<RequestsModel> repsModel;
    private ImageLoader imageloader;
    private Context mcontext;

    public RequestsAdapter(ArrayList<RequestsModel> repsModel,
                              ImageLoader il, Context context) {
        this.repsModel = repsModel;
        this.imageloader = il;
        this.mcontext = context;
    }


    @Override
    public RequestsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requests, parent, false));
    }

    @Override
    public void onBindViewHolder(RequestsAdapter.MyViewHolder holder, int position) {
        holder.title.setText(repsModel.get(position).getTitle());
        holder.nome.setText(repsModel.get(position).getUser().getLogin());
        holder.description.setText(repsModel.get(position).getBody());
        imageloader.get(repsModel.get(position).getUser().getAvatar_url(),imageloader.getImageListener(holder.imagem,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
    }

    @Override
    public int getItemCount() {
        return repsModel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView title;
        private TextView description;
        private TextView nome;
        private ImageView imagem;

        MyViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.textViewItem1);
            description  = (TextView) view.findViewById(R.id.textViewItem2);
            title = (TextView) view.findViewById(R.id.textViewItem3);
            imagem = (ImageView) view.findViewById(R.id.image_view);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            System.out.println("Elemento " + getAdapterPosition() + " clicado.");
            Uri uri = Uri.parse((String) repsModel.get(getAdapterPosition()).getHtml_url());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mcontext.startActivity(intent);
        }
    }
}
