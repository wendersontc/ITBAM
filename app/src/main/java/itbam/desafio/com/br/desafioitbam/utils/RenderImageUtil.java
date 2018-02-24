package itbam.desafio.com.br.desafioitbam.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RenderImageUtil {
    ImageLoader imageLoader;
    /**
     * Metodo para fazer conexão com a url e transformar a imagem em bitmap
     * @param image
     * @return img
     */
    public Bitmap UrlToBitmap(String image){
        Bitmap img = null;

        try{
            URL url = new URL(image);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            img = BitmapFactory.decodeStream(input);
        }
        catch(IOException e){}

        return img;
    }

    /**
     * Metodo para gerar cache de ImageLoader
     * @param rq
     * @return imageLoader
     */
    public ImageLoader CacheImageLoader(RequestQueue rq){
        return imageLoader = new ImageLoader(rq, new ImageLoader.ImageCache(){
            private final LruCache<String, Bitmap> cache = new LruCache<String,Bitmap>(10);
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }
}
