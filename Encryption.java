
public class Encryption {

	// method takes in text and a password and shifts each letter in text according to the letters in password
	public static String encrypt(String text, String password){
		char[] letters = password.toCharArray(); // translate the password param to char array for easier manipulation 
		int[] shiftNums = new int[letters.length]; // array to hold shift values for each letter
		

		for(int i = 0 ; i < letters.length; i++){
			if(Character.isUpperCase(letters[i])){
				shiftNums[i] = ((int) letters[i]) - 64; // subtracting 64 puts Ascii number for position in alphabet for uppercase letters
			}
			else{
				shiftNums[i] = ((int) letters[i]) - 96; // similar idea for lower case letters
			}
		}
		
		
		// created Iterator inner class to handle the arrays iteration
		ShiftNumIterator iterator = new ShiftNumIterator(shiftNums); 
		// copied the string into a stringbuilder for mutability 
		StringBuilder encryptedString = new StringBuilder(text); 
		char setChar; // variable will hold shifted char that I aim to place in StringBuilder
		
		
		for(int i = 0; i < encryptedString.length(); i++){
			// ignore characters that are not letters
			if(!Character.isLetter(encryptedString.charAt(i))){
				continue; 
			}
			
			else if(Character.isUpperCase(encryptedString.charAt(i))){
				int code = encryptedString.charAt(i) + shiftNums[iterator.getIndex()]; 
				// if statement checks to see if the ascii value is greater than 90 which is the upper case letter for 'Z'. 
				//Subtracts 26 to get a letter 
				if(code > 90){
					code -= 26; 
				}
				
				setChar = (char) code;
				encryptedString.setCharAt(i, setChar);
				
			}
			
			else{
					int code = encryptedString.charAt(i) + shiftNums[iterator.getIndex()]; 
					// thes is does the same thing but with lower case values
					if(code > 122){
						code -= 26; 
					}
					
					setChar = (char) code; 
					encryptedString.setCharAt(i, setChar);
					
				}
			}
		
		return encryptedString.toString(); 
			
		}
	
	
	// method reverses the operaton done by the encrypt method
	public static String decrypt(String cipher, String password){
		// similar tactics as uses in encrypt to get the shift amounts from the letters
		char[] letters = password.toCharArray(); 
		int[] shiftNums = new int[letters.length]; 
		
		for(int i = 0 ; i < letters.length; i++){
			if(Character.isUpperCase(letters[i])){
				shiftNums[i] = ((int) letters[i]) - 64;
			}
			else{
				shiftNums[i] = ((int) letters[i]) - 96;
			}
		}
		
		// Made another instance of the iterator class I wrote for the shiftNums array
		ShiftNumIterator iterator = new ShiftNumIterator(shiftNums); 
		StringBuilder encryptedString = new StringBuilder(cipher); 
		char setChar; 
		
		// similar for loop to the one in encrypt but reversed
		for(int i = 0; i < cipher.length(); i++){
			if(!Character.isLetter(cipher.charAt(i))){
				continue; 
			}
			else if(Character.isUpperCase(cipher.charAt(i))){
				int code = (int) cipher.charAt(i) - shiftNums[iterator.getIndex()];
				
				if(code < 65){
					code += 26; 
				}
				
				setChar = (char) code; 
				encryptedString.setCharAt(i, setChar);
			}
			
			else{
				int code = (int) cipher.charAt(i) - shiftNums[iterator.getIndex()]; 
				
				if(code < 97){
					code += 26; 
				}
				
				setChar = (char) code; 
				encryptedString.setCharAt(i, setChar);
			}
		}
		
		return encryptedString.toString();
		
		
	}
		
		
}
	// innter class shift iterator to ensure that the array indexes in shiftnums would be circular
	class ShiftNumIterator{
		int iterator; 
		int maxSize;
		
		public ShiftNumIterator(int[] array){
			maxSize = array.length - 1; 
		}
		
		public void increment(){
			iterator++; 
			if(iterator > maxSize){
				iterator = 0; 
			}
		}
		
		public int getIndex(){
			int returner = iterator; 
			increment(); 
			return returner; 
		}
		
		
	}
	
	
	

