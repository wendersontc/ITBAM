package itbam.desafio.com.br.desafioitbam.adapter;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import itbam.desafio.com.br.desafioitbam.R;
import itbam.desafio.com.br.desafioitbam.models.RepositoriosModel;

public class RepositoriosAdapter extends ArrayAdapter<RepositoriosModel> {

    private RepositoriosModel [] mData;
    private Context mContext;
    private int mLayoutResourceId;
    private ImageLoader imageloader;

    public RepositoriosAdapter(Context context, int resource, RepositoriosModel[] repo, ImageLoader il) {
        super(context, resource, repo);
        mContext = context;
        mData = repo;
        imageloader = il;
        mLayoutResourceId = resource;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final RepositoriosAdapter.ViewHolderItem viewHolder;

        if(convertView == null){

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);

            viewHolder = new RepositoriosAdapter.ViewHolderItem();
            viewHolder.imageViewItem = (ImageView) convertView.findViewById(R.id.image_view);
            viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.textViewItem2);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (RepositoriosAdapter.ViewHolderItem) convertView.getTag();
        }

        imageloader.get(mData[position].getOwner().getAvatar_url(),imageloader.getImageListener(viewHolder.imageViewItem,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
        viewHolder.textViewItem.setText("TEEEEEEEEEEESTANDO");

        return convertView;
    }

    static class ViewHolderItem {
        ImageView imageViewItem;
        TextView textViewItem;
    }
}
