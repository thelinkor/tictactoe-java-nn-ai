package reinforcedai;

import reinforcedai.ais.*;

import java.io.File;
import java.util.Calendar;

public class ReinforcedMultipleVSAiMain {
    public static final String CROSS_TXT = "Cross";
    public static final String CIRCLE_TXT = "Circle";

    public static void main(String[] args) {
        long start = Calendar.getInstance().getTimeInMillis();
        testAndSaveAis(0.0005,40,1231,50_000);
        testAndSaveAis(0.0003,40,12341,100_000);
        testAndSaveAis(0.00003,27,12341,150_000);
        testAndSaveAis(0.0003,100,1231,25_000);
        testAndSaveAis(0.001,200,1234,20_000);
        System.out.println((Calendar.getInstance().getTimeInMillis() - start)*1_000 + " seconds to run.");
    }

    public static void testAndSaveAis(double learningRate, int numberOfHiddenLayers, long seed, int numberOfGameRounds){
        NNai[] crossNetworks = new NNai[]{
                new NormalNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new DefensiveNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new NormalNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new DefensiveNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new OffensiveNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new LongGameNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new LongWinnerNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed))
        };
        NNai[] circleNetworks = new NNai[]{
                new NormalNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new NormalNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new DefensiveNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new DefensiveNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new OffensiveNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new LongGameNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed)),
                new LongWinnerNNai(NetConfig.createNet(learningRate,numberOfHiddenLayers,seed))
        };

        Trainers.trainMultiLayerVersusNetworks(
                crossNetworks,
                circleNetworks, numberOfGameRounds);
/*
        for (NNai crossNetwork : crossNetworks) {
            for (NNai circleNetwork : circleNetworks) {
                System.out.println("\n\n"+crossNetwork.getName() + " vs "+circleNetwork.getName());
                GameBot gameBot = new GameBot(new NNGamePlayer(crossNetwork.getNeuralNet()), new NNGamePlayer(circleNetwork.getNeuralNet()), new TextStateObserver());
                gameBot.runGameRound();
            }
        }*/

        System.out.println("-----------------");
        System.out.println("Parameters: ");
        System.out.printf("%s %1.8f\n","Learningrate:", learningRate);
        System.out.printf("%s %,d\n","Hidden Layers:", numberOfHiddenLayers);
        System.out.printf("%s%,d\n","Game Rounds: " , numberOfGameRounds);
        System.out.println("-----------------");
        for (NNai crossNetwork : crossNetworks) {
            AiTester.testCrossAi(crossNetwork);
        }
        System.out.println("------------");
        for (NNai circlenetwork : circleNetworks) {
            AiTester.testCircleAi(circlenetwork);
        }

        saveNetworks(crossNetworks, circleNetworks);
    }

    private static void saveNetworks(NNai[] cross, NNai[] circle){
        try {
            for(int i = 0 ; i < cross.length ; i++) {
                cross[i].getNeuralNet().save(new File(CROSS_TXT+i));
            }
            for(int i = 0 ; i < cross.length ; i++) {
                circle[i].getNeuralNet().save(new File(CIRCLE_TXT+i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
