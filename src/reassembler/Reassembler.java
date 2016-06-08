package reassembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reassembler
{

	public static void main(String[] args)
	{
		if(args.length==0) //if user do not input the file name of the result of Human-OCR
		{
			System.err.println("Please input \"output.txt\" after \"Reassembler.jar\"");
			System.exit(-1);
		}
		int i, j, nowKnown=0, nowUnknown=0;
		BufferedReader r1 = null;
		BufferedReader r2 = null;
		List<String> unknown = new ArrayList<String>();
		List<String> known = new ArrayList<String>();
		String[][] answer = new String[15][15];
		
		try 
		{
			r1 = new BufferedReader(new FileReader(args[0]));
			r2 = new BufferedReader(new FileReader("known_words.txt"));
		} 
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		
		try 
		{
			while(r1.ready()) unknown.add(r1.readLine());
			while(r2.ready()) known.add(r2.readLine());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		Collections.sort(unknown); //sort the result of Human-OCR
		//known word is sorted already
		
		for(i=0; i<15; i++)
		{
			for(j=0; j<15; j++)
			{
				answer[i][j] = "__"; //default set
				//if the location is match, add it into answer
				if(nowKnown<known.size() && known.get(nowKnown).contains(String.format("%02d-%02d", i+1, j+1)))
				{
					answer[i][j] = known.get(nowKnown).substring(10);
					nowKnown++;
				}
				else if(nowUnknown<unknown.size() && unknown.get(nowUnknown).contains(String.format("%02d-%02d", i+1, j+1)))
				{
					answer[i][j] = unknown.get(nowUnknown).substring(10);
					nowUnknown++;
				}
				System.out.print(answer[i][j] + " "); //print out answer
			}
			System.out.print("\n");
		}
		
	}

}
