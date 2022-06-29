package reinforcedai;

import game.GameBot;
import game.observer.TextStateObserver;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;

public class ReinforcedAIMain {

    public static final String CROSS_TXT = "Cross.txt";
    public static final String CIRCLE_TXT = "Circle.txt";

    public static void main(String[] args) {
        MultiLayerNetwork crossNetwork = NetConfig.createNet();
        MultiLayerNetwork circleNetwork = NetConfig.createNet();
        Trainers.trainMultiLayerNetworks(crossNetwork, circleNetwork);

        GameBot gameBot = new GameBot(new NNGamePlayer(crossNetwork), new NNGamePlayer(circleNetwork), new TextStateObserver());
        gameBot.runGameRound();

        saveNetworks(crossNetwork, circleNetwork);
    }

    private static void saveNetworks(MultiLayerNetwork cross, MultiLayerNetwork circle){
        try {
            cross.save(new File(CROSS_TXT));
            circle.save(new File(CIRCLE_TXT));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
