import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		double loadFactor = 0.0;
		int whichFunction = 1;
		Scanner read = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			//Determining which function to use and load factor
			try {
				System.out.println("----------OPTIONS----------");
				System.out.println("Which hash function would you like to use ?");
				System.out.println("Press: ");
				System.out.println("1- Teacher's Function");
				System.out.println("2- Student's Function");
				int choice1 = read.nextInt();
				System.out.println("Choose load factor ?");
				System.out.println("Press: ");
				System.out.println("1- 50%");
				System.out.println("2- 70%");
				int choice2 = read.nextInt();
				if(choice1 == 1) {
					whichFunction = 1;
					flag = false;
				}else if(choice1 == 2) {
					whichFunction = 2;
					flag = false;
				}else {
					System.out.println("You must enter 1 or 2");
					flag = true;
				}
				if(choice2 == 1) {
					loadFactor = 0.5;
					flag = false;
				}else if(choice2 == 2) {
					loadFactor = 0.7;
					flag = false;
				}else {
					System.out.println("You must enter 1 or 2");
					flag = true;
				}
					
			} catch (Exception e) {
				System.out.println("You must select 1 or 2");
				flag = true;
			}
		}
		read.close();
		
		FileReader fileReader = new FileReader("story.txt");
		String line;
		BufferedReader br = new BufferedReader(fileReader);
		HashTable<String, Integer> hashTable = new HashTable<String, Integer>(whichFunction, loadFactor);
		long startTime = System.nanoTime();//to keep starting time
		while ((line = br.readLine()) != null) {//reading story file
			if (!line.equals("") && !line.equals(" ")) {
				//necessary changes
				line = line.toLowerCase();
				line = line.replace('ý', 'i');
				line = line.replace("â€”", " ");
				line = line.replace("/n", " ");
				
				//taking the words and putting it in to hash table
				String words[] = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					if (!words[i].equals("")) {
						String word = words[i];
						System.out.println(word);
						hashTable.put(word, 1);
					}

				}
			}

		}
		//hashTable.print();
		//System.out.println(hashTable.CollisionCount()); -> printing collision count
		long endTime = System.nanoTime();//finishing time
		long estimatedTime = endTime - startTime;//total time spent.
		long seconds = estimatedTime;
		System.out.println(seconds);
		
		FileReader fileReader2 = new FileReader("search.txt");
		String line2;
		BufferedReader br2 = new BufferedReader(fileReader2);
		long min = 999999999;
		long max = 0;
		long total = 0;
		System.out.println("");
		while ((line2 = br2.readLine()) != null && !line2.equals("")) {//reading search file
			long startTime2 = System.nanoTime();
			hashTable.get(line2);//search function
			long endTime2 = System.nanoTime();
			long estimatedTime2 = endTime2 - startTime2;
			long seconds2 = estimatedTime2;
			if(seconds2 < min)
				min = seconds2;
			if(seconds2 > max)
				max = seconds2;
			total += seconds2;		
		}
		System.out.println("Avarage Search Time: " + total/100);
		System.out.println("Min Search Time: " + min);
		System.out.println("Max Search Time: " + max);
		br.close();
		br2.close();
	}
}

