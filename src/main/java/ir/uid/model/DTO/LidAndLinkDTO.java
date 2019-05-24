package ir.uid.model.DTO;

public class LidAndLinkDTO {
    private String link;
    private String lid;

    public LidAndLinkDTO(String link, String lid) {
        this.link = link;
        this.lid = lid;
    }

    public String getLink() {
        return link;
    }

    public String getLid() {
        return lid;
    }
}
