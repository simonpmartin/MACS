/** 
 * MACS - Multi-Agent Cooperative Search is a framework to develop cooperating agents using 
 * different Metaheuristics Copyright (C) 2016 Simon Martin, Angel Alejandro Juan Perez. This file is part of MACS. 
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
package macs.uoc.pfsp.baker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import macs.uoc.pfsp.api.InputManager;
import macs.uoc.pfsp.api.PFSPInputs;

public class BakerInputsManager implements InputManager {

	/*******************************************************************************
	 * INSTANCE FIELDS
	 ******************************************************************************/

	private String filePath; // path of the inputs file

	/*******************************************************************************
	 * CLASS CONSTRUCTOR
	 ******************************************************************************/

	public BakerInputsManager(String inputsFilePath) {
		filePath = inputsFilePath;
	}

	/*******************************************************************************
	 * METHOD getInputs()
	 ******************************************************************************/

	public PFSPInputs getInputs() {
		BakerInputs bi = null;
		try {
			System.out.println(filePath);
			FileReader reader = new FileReader(filePath);

			Scanner in = new Scanner(new BufferedReader(reader));
			// The two first lines of this file should be like this:
			// # nJobs | nMachines <- line 0
			// 20 5 <- line 1
			in.nextLine(); // Skip line 0
			int nJobs = in.nextInt();
			int nMachines = in.nextInt();

			//System.out.printf(" jobs %d machines %d\n", nJobs, nMachines);

			bi = new BakerInputs(nJobs, nMachines);

			// The rest of the lines should be like this:
			// # m0 | m1 | m2 <- line 2 (times job0)
			// 21 23 32 <- line 3 (times job1)
			// 30 31 10 <- line 4 (times job2)
			// ... ... ...
			
			in.nextLine();
	
				PFSPInputs inputs = bi;
				// Go to the beginning of line 2
				in.nextLine(); // Go to the beginning of line 3
				for (int i = 0; i < nJobs; i++) {

					BakerJob iJob = (BakerJob) inputs.getJobs()[i];
					int totalTime = 0;
					int time = 0;
					for (int j = 0; j < nMachines; j++) {
						time = in.nextInt();
						iJob.setProcessingTime(j, time);
						totalTime = totalTime + time;
						//System.out.printf("%3d\t", time);
					}
					for (int j = 0; j < nMachines; j++) {
						int variance = in.nextInt();
						iJob.setVariance(j, variance); 
						//System.out.printf("%3d\t", variance);
					}
					iJob.setTotalProcessingTime(totalTime);
					//System.out.println();
				}
	
			in.close();
		} catch (IOException exception) {
			System.out.println("Error processing inputs file: " + exception);
		}
		return bi;
	}

}
