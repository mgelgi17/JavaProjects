package code;

import given.*;

/*  Modify this file such that:
 * 
 * - This class extends from the Animal class
 * - This class implements the iBehavior interface (which you will implement)
 * - This class has the correct name
 * - The required fields/variables/data are defined
 * - The required methods are defined and implemented
 * 
 * Some useful information
 * - Cats generally make a "meow" sound
 * - Cats are referred as "common cat" if we do not know their breed
 * - Cats on average move with 17 units of speed.
 * - Cats live on "land"
 * - Cats like "mild" temperature
 * - Cats are not migratory
 * - Cats eat "cat food"
 * - Cats are "carnivorous"
 * - The sound a cat makes cannot be less than 3 characters long! 
 * - We consider most cats as pets and not wild animals
 * - Cats can run and climb
 * 
 * */



public class Cat extends Animal implements iBehavior { 
	//Declare fields sound and breed as protected strings
	protected String breed;
	protected String sound;
	
	// Implement Default constructor 
	// Make sure you instantiate all the variables. 
	// Note that some variables may come from the abstract class! 
	public Cat()
	{
		super(17);
		super.name="cat";
		this.sound="meow";
		this.breed="common cat";
		
		Util.NotImplementedYetSoft();
	}
	
	// Implement over loaded constructor with the given two parameters
	// Remember, the sound a cat makes cannot be less than 3 characters so make sure this is avoided
	public Cat(String breed, String sound)
	{
		super(17);
		super.name="cat";
		this.breed = breed;
		if(sound.length() < 3) {
			this.sound="meow";
		} else {
		this.sound = sound;
		}
		
		Util.NotImplementedYetSoft();
	 }
	
	private void purr()
	{
		System.out.println("Most cats purr to express contentment or pleasure");
	}
	
	public void makeSound()
	{
		System.out.println("The " + breed + " makes a " + sound + " sound");
	}
	
	@Override 
	protected void move()
	{
		System.out.println("Cats can run and climb");
	}
	
	// Override protected description() 
	// Call the superclass function. 
	// In addition, use the methods makeSound(), purr() and the variable breed in it to print additional information about the Cat
	@Override 
	public void description()
	{
		super.description();
		makeSound();
		purr();
		Util.NotImplementedYetSoft();
	}
	
	// implement all the other methods you need here, they are not even started for you!
	@Override
	public String food() {
		// TODO Auto-generated method stub
		return "cat food";
	}

	@Override
	public String dietaryStyle() {
		// TODO Auto-generated method stub
		return "carnivorous";
	}

	@Override
	public String location() {
		// TODO Auto-generated method stub
		return "land";
	}

	@Override
	public String temperature() {
		return "mild";
	}

	@Override
	public boolean isWild() {
		return false;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Running with" + moveSpeed + "speed");
	}

	@Override
	public void sleep(int hours) {
		if(hours < 1)
			throw new IllegalArgumentException("sleeping hours cannot be less than 1");
		String z="Z";
		for(int i= 0 ;i<hours;i++) {
			z+="z";
		}
		System.out.println(z);
		
	}
	
	@Override
	public boolean isMigratory()
	{
		return false; //assuming cats animals are not migratory
	}
}