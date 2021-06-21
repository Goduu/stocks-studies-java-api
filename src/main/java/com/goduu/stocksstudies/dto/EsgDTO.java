package com.goduu.stocksstudies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EsgDTO is the class to handle the Esg Risk entity
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EsgDTO implements Serializable {

    /**
     * The final result of the analysis 
     * Ex. UNDER_PERF,AVG_PERF, LAG_PERF
     */
    String performance;

    Double value;

    /**
     * Scores by category with title, value and its peers min, max values
     */
    List<Score> scores = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    private class Score implements Serializable {
        String title;
        Double value;
        List<Peer> peers = new ArrayList<>();

        @Getter
        @Setter
        @NoArgsConstructor
        private class Peer implements Serializable {
            String label;
            Double value;
        }

        public void setPeer(JsonObject obj){
            Peer p = new Peer();
            p.setLabel("Min");
            p.setValue(obj.get("min").getAsDouble());
            this.peers.add(p);
            p = new Peer();
            p.setLabel("Max");
            p.setValue(obj.get("max").getAsDouble());
            this.peers.add(p);
            p = new Peer();
            p.setLabel("Avg");
            p.setValue(obj.get("avg").getAsDouble());
            this.peers.add(p);
        }

    }
    /**
     * <p>Add all scores to the Esg (totalEsg,environmentScore,socialScore & governanceScore) 
     * </p>
     * @param JsonObject an object in Json format with all the fields above
     * @return void since it sets the values in the entity
     * @since 1.0
     */
    public void addScores(JsonObject obj){

        Score sc = new Score();
        sc.setTitle("totalEsg");
        sc.setValue(obj.get("totalEsg") != null ? obj.get("totalEsg").getAsJsonObject().get("raw").getAsDouble() : 0d);
        sc.setPeer(obj.get("peerEsgScorePerformance").getAsJsonObject());
        scores.add(sc);
        sc = new Score();
        sc.setTitle("environmentScore");
        sc.setValue(obj.get("environmentScore") != null ? obj.get("environmentScore").getAsJsonObject().get("raw").getAsDouble() : 0d);
        sc.setPeer(obj.get("peerEnvironmentPerformance").getAsJsonObject());
        scores.add(sc);
        sc = new Score();
        sc.setTitle("socialScore");
        sc.setValue(obj.get("socialScore") != null ? obj.get("socialScore").getAsJsonObject().get("raw").getAsDouble() : 0d);
        sc.setPeer(obj.get("peerSocialPerformance").getAsJsonObject());
        scores.add(sc);
        sc = new Score();
        sc.setTitle("governanceScore");
        sc.setValue(obj.get("governanceScore") != null ? obj.get("governanceScore").getAsJsonObject().get("raw").getAsDouble() : 0d);
        sc.setPeer(obj.get("peerGovernancePerformance").getAsJsonObject());
        scores.add(sc);

    }

}
