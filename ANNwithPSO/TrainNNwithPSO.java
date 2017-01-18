package trainnnwithpso;

import java.text.NumberFormat;

/**
 * @author sachinpandey
 */
public class TrainNNwithPSO {
    
    public static void main(String[] args) {
        int hiddenLayers = 2;
        int iterations = 1000;
        double xorInput [][] =
        {
            {0.0,0.0},
            {1.0,0.0},
            {0.0,1.0},
            {1.0,1.0}
        };
        
        double xorIdeal[][] =
        {{0.0},{1.0},{1.0},{0.0}};
        
        
        
        //initialize the speed and position of the particles
        ParticleProcess PSOProcess = new ParticleProcess();
        double [] bWeights = PSOProcess.execute();
        
        //intitalize the wights and thresholds of BP Network
        Network network = new Network(2,hiddenLayers,1, 0.7, 0.9, bWeights);
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(4);
        System.out.println("Applying BackPropogation Neural Network:");
        for (int i = 0; i < iterations; i++)
        {
            for (int j=0;j<xorInput.length;j++)
            {
                network.computeOutputs(xorInput[j]);
                network.calcError(xorIdeal[j]);
                network.learn();
            }
            System.out.println( "\tTrial #" + i + ",Error:" +
            percentFormat .format(network.getError(xorInput.length)) );
        }
        
        System.out.println("Final Result:");
        for (int i=0;i<xorInput.length;i++)
        {
            for (int j=0;j<xorInput[0].length;j++)
            {
                System.out.print( "\t"+xorInput[i][j] +":" );
            }
            double out[] = network.computeOutputs(xorInput[i]);
            System.out.println("="+out[0]);
        }
    }
    
}
