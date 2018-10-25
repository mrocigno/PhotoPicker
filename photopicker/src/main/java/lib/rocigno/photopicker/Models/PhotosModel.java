package lib.rocigno.photopicker.Models;

import android.net.Uri;

public class PhotosModel {
    String descricao;
    Uri img;

    public PhotosModel(String descricao, Uri img) {
        this.descricao = descricao;
        this.img = img;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "PhotosModel{" +
                "descricao='" + descricao + '\'' +
                '}';
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
