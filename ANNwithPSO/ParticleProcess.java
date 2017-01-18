package trainnnwithpso;

/**
 * @author sachinpandey
 */

import java.util.Random;
import java.util.Vector;
public class ParticleProcess implements PSOConstants{
    
    private Vector<Particle> swarm = new Vector<Particle>();
	private double[] pBest = new double[SWARM_SIZE];
	private Vector<Location> pBestLocation = new Vector<Location>();
	private double gBest;
	private Location gBestLocation;
	private double[] fitnessValueList = new double[SWARM_SIZE];
	
	Random generator = new Random();
	
	public double[] execute() {
		initializeSwarm();
		updateFitnessList();
		
		for(int i=0; i<SWARM_SIZE; i++) {
			pBest[i] = fitnessValueList[i];
			pBestLocation.add(swarm.get(i).getLocation());
		}
		
		int t = 0;
		double w = 0.75;
		double err = 0.0001;
		
		while(t < MAX_ITERATION ){//&& err > ProblemSet.ERR_TOLERANCE) {
			// step 1 - update pBest
			for(int i=0; i<SWARM_SIZE; i++) {
				if(fitnessValueList[i] < pBest[i]) {
					pBest[i] = fitnessValueList[i];
					pBestLocation.set(i, swarm.get(i).getLocation());
				}
			}
				
			// step 2 - update gBest
			int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
			if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
				gBest = fitnessValueList[bestParticleIndex];
				gBestLocation = swarm.get(bestParticleIndex).getLocation();
			}
			
			for(int i=0; i<SWARM_SIZE; i++) {
				double r1 = generator.nextDouble();
				double r2 = generator.nextDouble();
				
				Particle p = swarm.get(i);
				
				// step 3 - update velocity
				double[] newVel = new double[PROBLEM_DIMENSION];
                                for (int x =0;x<PROBLEM_DIMENSION;x++)
                                {
                                    newVel[x] = (w * p.getVelocity().getPos()[x]) + 
							(r1 * C1) * (pBestLocation.get(i).getLoc()[x] - p.getLocation().getLoc()[x]) +
							(r2 * C2) * (gBestLocation.getLoc()[x] - p.getLocation().getLoc()[x]);
                                }
				
				// step 4 - update location
				double[] newLoc = new double[PROBLEM_DIMENSION];
                                for (int z = 0; z<PROBLEM_DIMENSION;z++)
                                {
                                   newLoc[z] = p.getLocation().getLoc()[z] + newVel[z]; 
                                }
				
				Location loc = new Location(newLoc);
				p.setLocation(loc);
			}
			
			err = ProblemSet.evaluate(gBestLocation) - 0; // minimizing the functions means it's getting closer to 0
                        
			t++;
			updateFitnessList();
		}
		
		double bestWeights [] = new double[PROBLEM_DIMENSION];
                System.out.println("After PSO:");
                System.out.println("\nSolution found at iteration " + (t - 1) + ", the solutions is:");
                for (int i2 = 0; i2 < PROBLEM_DIMENSION; i2++)
                {
                    bestWeights[i2] = gBestLocation.getLoc()[i2];
                    System.out.println("\tBest location: " + gBestLocation.getLoc()[i2]);
                }
                System.out.println();
                return bestWeights;
        }
	
	public void initializeSwarm() {
		Particle p;
		for(int i=0; i<SWARM_SIZE; i++)
                {
			p = new Particle();
			
			// randomize location inside a space defined in Problem Set
			double[] loc = new double[PROBLEM_DIMENSION];
                        for (int j = 0; j < PROBLEM_DIMENSION; j++)
                        {
                            loc[j] = Math.random();
                            
                        }
			Location location = new Location(loc);
			
			// randomize velocity in the range defined in Problem Set
			double[] vel = new double[PROBLEM_DIMENSION];
                        for (int k =0; k < PROBLEM_DIMENSION; k++)
                        {
                            vel[k] = Math.random();
                        }
			
			Velocity velocity = new Velocity(vel);
			
			p.setLocation(location);
			p.setVelocity(velocity);
			swarm.add(p);
		}
	}
	
	public void updateFitnessList() {
		for(int i=0; i<SWARM_SIZE; i++) {
			fitnessValueList[i] = swarm.get(i).getFitnessValue();
		}
	}
    
}
