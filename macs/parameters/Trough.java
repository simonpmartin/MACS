/** 
 * MACS - Multi-Agent Cooperative Search is a framework to develop cooperating agents using 
 * different Metaheuristics Copyright (C) 2016 Simon Martin. This file is part of MACS. 
 * 
 * MACS is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 * 
 * MACS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with MACS. 
 * If not, see <http://www.gnu.org/licenses/>.
 */

package macs.parameters;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;

import macs.ontologies.SolutionWrapper;





/**
 * @author simon
 *
 */
public class Trough {
	//internal variables
	private Deque<Double> solutions = null;
	private boolean trough = false;
	private int queuesize = 0;
	private boolean robust = false;
	
	//constructors 
	public Trough(Integer queuesize,boolean robust ){
		solutions = new ArrayDeque<Double>(queuesize);
		this.queuesize = queuesize;
		this.robust = robust;
	}

	
	//methods
	/** the Deque has length queuesize from constructor. 
	 * Add elements until limit is reached
	 * When limit is reached check that all the elements are the same
	 * if they are the same return true
	 * if not remove first element add new element to the Deque
	 * return false
	 */
   
   public void setQueue(int queuesize){
	   solutions = new ArrayDeque<Double>(queuesize);
	   this.queuesize = queuesize;
   }
   
	public void addElement(double value ){
		if(queuesize !=0){
			
		
			if(solutions.size() < queuesize){
				solutions.addLast(value);
			}		
			else{
				solutions.removeFirst();
				solutions.addLast(value);
			}	
		}
		
	}
	public boolean isBestImproving(Integer sol){
		boolean result = false;
		if(Collections.min(solutions)>= sol){
			
			result = true;
		}
		return result;		
	}
	public boolean isImproving(Integer sol){
		boolean result = false;
		if(Collections.max(solutions)> sol){
			result = true;
		}
		return result;		
	}
	
	
	public boolean isTrough(double value){
		if((solutions.size() == queuesize) && (Collections.frequency(solutions, value) == queuesize)){
				trough = true;
				if(robust){
					SolutionWrapper.getInstance().setOptCount();
					SolutionWrapper.getInstance().setLocalOpt(value);
				}
				
		}
		else if(queuesize==0){
			trough = false;
		}
	return trough;
	}
	
	
	
	public void getQueue(){
		for(Double k : solutions ){
			System.out.println("TROUGH QUEUE "+ k);
		}
	}
	

}
