package itbam.desafio.com.br.desafioitbam.models;

public class RequestsModel {

    private String title;
    private String body;
    private String html_url;
    private String created_at;
    private UserModel user;

    public RequestsModel(String title,String body,
                             String html_url, String created_at,
                             UserModel user){
        this.title = title;
        this.body = body;
        this.html_url = html_url;
        this.created_at = created_at;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
