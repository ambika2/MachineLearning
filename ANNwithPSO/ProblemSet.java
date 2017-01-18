/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainnnwithpso;

/**
 *
 * @author sachinpandey
 */
public class ProblemSet {
    public static final double ERR_TOLERANCE = 1E-20; // the smaller the tolerance, the more accurate the result
                                                     //but the number of iteration is increased
    public static double evaluate(Location location) {
    //input for the xor
    double [][] input =
    {
        {0.0,0.0},
        {1.0,0.0},
        {0.0,1.0},
        {1.0,1.0}
    };
    
    //ideal output of the xor
    double [] outputArray = {0.0,1.0,1.0,0.0};
    
    //output for different layers
    double hidden1out[] = new double[4];//hidden neuron 1
    double hidden2out[] = new double[4];//= null;//hidden newuron 2
    double output[] = new double[4];//= null;//output    
    
    //output for hidden layer
    for (int i = 0; i < input.length; i++)
    {
        hidden1out[i] = (1/(1 + (Math.exp(-(location.getLoc()[0]*input[i][0]+location.getLoc()[1]*input[i][1]-0.8)))));
        hidden2out[i] = (1/(1 + (Math.exp(-(location.getLoc()[2]*input[i][0]+location.getLoc()[3]*input[i][1]-(-0.1))))));
    }
    
    //output for output layer
    for (int i2 =0; i2 < outputArray.length; i2++)
    {
        output[i2] = (1/(1 + (Math.exp(-(location.getLoc()[4]*hidden1out[i2]+location.getLoc()[5]*hidden2out[i2]-0.3)))));
    }
    
    //to calculate error for each set of input and output
    double [] result2 = {0.0, 0.0, 0.0, 0.0};
    for (int i3 = 0; i3 < outputArray.length; i3++)
        {
            result2[i3] = outputArray[i3] - output[i3];
                    
        }
    double errorSum = 0;
    for (int i4 = 0; i4 < 4; i4++)
    {
        errorSum += result2[i4];
    }
    double avgError = errorSum/4;                
    return avgError;
    }
    
}
