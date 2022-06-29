package reinforcedai;

import game.player.ConsolePlayer;
import game.GameBot;
import game.observer.TextStateObserver;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;

public class PlayVSai {
    public static void main(String[] args) {
        MultiLayerNetwork cross = loadCross();
        MultiLayerNetwork circle = loadCircle();

        GameBot bot = new GameBot(new ConsolePlayer(), new NNGamePlayer(circle), new TextStateObserver());
        //GameBot bot = new GameBot(new NNGamePlayer(cross),new ConsolePlayer(),new TextStateObserver());
        bot.runGameRound();
    }

    public static MultiLayerNetwork loadCross(){
        return load(ReinforcedMultipleVSAiMain.CROSS_TXT+0);
    }

    public static MultiLayerNetwork loadCircle(){
        return load(ReinforcedMultipleVSAiMain.CIRCLE_TXT+1);
    }

    public static MultiLayerNetwork load(String netowrkfileName){
        MultiLayerNetwork net = null;
        try {
            net = MultiLayerNetwork.load(new File(netowrkfileName),false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return net;
    }
}
