public class PlayfairDecode {
  public static void main(String[] args){
    String text = args[0].toUpperCase(); //the text to be decoded
    String keyText = args[1]; //the key letters for decoding
    decode(text, keyText); //run the methods to encoding the ciphertext
  }


  public static void decode(String text, String keyText){
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
    //String[] pairs = makePairs(text); //insert x's if needed and return the pairs for decoding
  }

  public static String[][] makeKey(String keyText){
    String [][] key = new String [5][5];
    int count = 0;
    for (int i = 0; i < key.length; i++){
      for (int j = 0; j < key[0].length; j++){
        String letter = keyText.substring(count, count + 1);
        key[i][j] = letter;
        count++;
      }
    }
    return key;
  }

}
