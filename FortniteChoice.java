import java.util.Scanner;

public class FortniteChoice {
	public static void main(String[]args){
		System.out.println("You are put into a lobby with random fills. They have no mic but all put their markers on Tilted Towers. You want to land Snobby Shores. Do you follow them? Y or N?");
		
		Scanner userInput = new Scanner(System.in);
		String answer = userInput.nextLine();
		
		if (answer.equals("Y")) {
			System.out.println ("There is power in numbers. You all drop into Tilted without incident and begin loot the area. You stumble upon a blue hunting rifle and a purple suppressed pistol in the same area. You only have one spot. Which one do you pick up? Hunting rifle (H) or suppressed pistol (P)?");
		} else {
			System.out.println ("You fly into Snobby Shores by yourself. In your peripheral vision, you can see two other full squads coming in with you. When you get onto the house. a whole squad pushes you and kills you.");
			System.out.println ("THE END");
			}
			
		
	
		answer = userInput.nextLine();
		
		if (answer.equals("H")) {
			System.out.println ("You pick up the sniper and jump out of the building. On the way down you fire and hit a snipe from 200 metres, however take 32 fall damage. Luckily, you have a med kit in your inventory. Do you use it? Y or N?");
		} else {
			System.out.println ("The purple pistol looks good in your 4th slot. You make your way out of the building and then bump into a no-skin down the road. You don't have any other long range weapons so you fire at him a couple times with the pistol. He was a fake no-skin and pushes you with the suppressed SCAR and the double-barrel shotgun. Your pistol is no help and he hits you with a 207-damage headshot.");
			System.out.println ("THE END");
			return;
			}
			
		answer = userInput.nextLine();
		
		if (answer.equals("Y")) { 
			System.out.println ("You use it and are healed.");
			System.out.println ("There are two squads left. The fills in your squad decide to push the others. Do you build up with them or stay in the base with your hunting rifle? Stay (S) or Push (P)?");
		} else { 
			System.out.println ("You fall again and get knocked. Your squad is too far away to help.");
			System.out.println ("THE END");
			return;
		}
		
		answer = userInput.nextLine();
		
		if (answer.equals("P")) {
			System.out.println ("You're pushing the squad and build up. Placing down a bouncer, you plummet towards them with your shotgun ready. A quick triple-headshot play and you win!");
			System.out.println ("#1 VICTORY ROYALE");
		} else { 
			System.out.println ("When scoped in on your sniper, you don't notice that bush behind you. He runs up on you and pops a few shots. You've died in second place.");
			System.out.println ("THE END");
			return;
		}
				
	}
}