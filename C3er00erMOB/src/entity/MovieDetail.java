package entity;

public class MovieDetail {
    private String title;
    private String status;
    private String type;
    private String synopsis;
    private String director;


    public MovieDetail(String title, String status, String type, String synopsis, String director) {
        this.title = title;
        this.status = status;
        this.type = type;
        this.synopsis = synopsis;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDirector() {
        return director;
    }
}