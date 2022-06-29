package reinforcedai;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NetConfig {
    public static final double LEARNING_RATE = 0.001;
    public static final int PRINT_ITERATIONS_COUNT = 5_000;
    public static final int NUMBER_OF_HIDDEN_LAYERS = 25;
    public static final int SEED = 12345;

    public static MultiLayerNetwork createNet(){
        return createNet(LEARNING_RATE,  NUMBER_OF_HIDDEN_LAYERS, SEED);
    }

    public static MultiLayerNetwork createNet(double learningRate, int numberOfHiddenLayers, long seed){
        MultiLayerNetwork net = new MultiLayerNetwork(
                createNetBuilder(learningRate, numberOfHiddenLayers, seed)
                .build()
        );
        net.init();
        //net.setListeners(new ScoreIterationListener(PRINT_ITERATIONS_COUNT));
        return net;
    }

    public static MultiLayerConfiguration.Builder createNetBuilder(double learningRate, int numberOfHiddenLayers, long seed){
        int numOutputs = 9;
        int layerCount = 0;
        return new NeuralNetConfiguration.Builder()
                .seed(seed)
                //.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                .list()
                .layer(layerCount++, new DenseLayer.Builder().nIn(numOutputs).nOut(numberOfHiddenLayers)
                        .activation(Activation.RELU)
                        .build())
                .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                        .activation(Activation.RELU)
                        .build())
                .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                        .activation(Activation.RELU)
                        .build())
                .layer(layerCount++, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(numberOfHiddenLayers).nOut(numOutputs).build())
                .backpropType(BackpropType.Standard);
    }

    public static MultiLayerNetwork createMultiLayerNetNet(double learningRate, int numberOfHiddenLayers, long seed){
        int numOutputs = 9;
        int layerCount = 0;
        MultiLayerNetwork net = new MultiLayerNetwork(
                new NeuralNetConfiguration.Builder()
                        .seed(seed)
                        .weightInit(WeightInit.XAVIER)
                        .updater(new Adam(learningRate))
                        .list()
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numOutputs).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new DenseLayer.Builder().nIn(numberOfHiddenLayers).nOut(numberOfHiddenLayers)
                                .activation(Activation.RELU)
                                .build())
                        .layer(layerCount++, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                                .activation(Activation.IDENTITY)
                                .nIn(numberOfHiddenLayers).nOut(numOutputs).build())
                        .backpropType(BackpropType.Standard)
                        .build()
        );
        net.init();
        //net.setListeners(new ScoreIterationListener(PRINT_ITERATIONS_COUNT));
        return net;
    }
}
