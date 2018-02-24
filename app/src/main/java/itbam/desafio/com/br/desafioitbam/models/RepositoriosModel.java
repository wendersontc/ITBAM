package itbam.desafio.com.br.desafioitbam.models;

public class RepositoriosModel {

    private String full_name;
    public String description;
    public OwnerModel owner;
    private Integer stargazers_count;
    private Integer forks;
    private String pulls_url;

    public RepositoriosModel(String description,OwnerModel owner,
                             String full_name, Integer stargazers_count,
                             Integer forks,String pulls_url){
        this.description = description;
        this.owner = owner;
        this.full_name = full_name;
        this.stargazers_count = stargazers_count;
        this.forks = forks;
        this.pulls_url = pulls_url;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }

    public Integer getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(Integer stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public String getPulls_url() {
        return pulls_url;
    }

    public void setPulls_url(String pulls_url) {
        this.pulls_url = pulls_url;
    }
}
