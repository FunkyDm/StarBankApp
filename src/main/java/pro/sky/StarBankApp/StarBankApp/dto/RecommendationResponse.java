package pro.sky.StarBankApp.StarBankApp.dto;

import java.util.List;

public class RecommendationResponse {
    private String userId;

    private List<Recommendation> recommendations;

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public List<Recommendation> getRecommendations(){
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations){
        this.recommendations = recommendations;
    }

    public static class Recommendation{
        private String name;
        private String id;
        private String text;

        public Recommendation(String name, String id, String text) {
            this.name = name;
            this.id = id;
            this.text = text;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }


    }
}