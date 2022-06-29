package reinforcedai;

import game.GameBot;
import game.observer.TextStateObserver;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;

import static reinforcedai.ReinforcedAIMain.CIRCLE_TXT;
import static reinforcedai.ReinforcedAIMain.CROSS_TXT;

public class ReinforcedVSAiMain {

    public static void main(String[] args) {
        MultiLayerNetwork crossNetwork = NetConfig.createNet();
        MultiLayerNetwork circleNetwork = NetConfig.createNet();
        Trainers.trainMultiLayerVersusNetworks(crossNetwork, circleNetwork);

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
