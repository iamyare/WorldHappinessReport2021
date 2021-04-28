package Utils;


public class Pais {
    public String pais;
    public String region;
    public Double Social_support;
    public Double Healthy_life_expectancy;
    public Double Freedom_to_make_life_choices;
    public Double Generosity;
    public Double Perception_of_corruption;


    public Pais(String pais, String region, Double social_support, Double healthy_life_expectancy, Double freedom_to_make_life_choices, Double generosity, Double perception_of_corruption) {
        this.pais = pais;
        this.region = region;
        Social_support = social_support;
        Healthy_life_expectancy = healthy_life_expectancy;
        Freedom_to_make_life_choices = freedom_to_make_life_choices;
        Generosity = generosity;
        Perception_of_corruption = perception_of_corruption;
    }

    public String getPais() {
        return pais;
    }
    public String getRegion() {
        return region;
    }
    public Double getSocial_support() {
        return Social_support;
    }
    public Double getHealthy_life_expectancy() {
        return Healthy_life_expectancy;
    }
    public Double getFreedom_to_make_life_choices() {
        return Freedom_to_make_life_choices;
    }
    public Double getGenerosity() {
        return Generosity;
    }
    public Double getPerception_of_corruption() {
        return Perception_of_corruption;
    }


}
