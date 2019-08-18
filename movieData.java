package tds.com.moviezlub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class movieData {
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("genres")
    @Expose
    private String genres;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("playlink")
    @Expose
    private String playlink;
    @SerializedName("releasedate")
    @Expose
    private String releasedate;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uploaddate")
    @Expose
    private String uploaddate;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("year")
    @Expose
    private String year;

    movieData() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String str) {
        this.img = str;
    }

    public String getPlaylink() {
        return this.playlink;
    }

    public void setPlaylink(String str) {
        this.playlink = str;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String str) {
        this.year = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getGenres() {
        return this.genres;
    }

    public void setGenres(String str) {
        this.genres = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getUploaddate() {
        return this.uploaddate;
    }

    public void setUploaddate(String str) {
        this.uploaddate = str;
    }

    public String getReleasedate() {
        return this.releasedate;
    }

    public void setReleasedate(String str) {
        this.releasedate = str;
    }

    public String getViews() {
        return this.views;
    }

    public void setViews(String str) {
        this.views = str;
    }
}
