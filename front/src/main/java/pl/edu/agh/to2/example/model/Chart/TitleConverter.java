package pl.edu.agh.to2.example.model.Chart;

public class TitleConverter {
    private static int PREFERED_TITLE_LENGTH = 20;
    public TitleConverter(){};
    public String getTitleWithShortenedLength(String title) {
        String res = title;
        if(title.length() < PREFERED_TITLE_LENGTH){
            return title;
        }
        else{
            res = title.substring(0, PREFERED_TITLE_LENGTH-3);
            res += "...";
        }
        return res;
    }
}
