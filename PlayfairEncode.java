public class PlayfairEncode {
  public static void main(String[] args){
    String text = args[0]; //the text to be encoded
    String keyText = args[1]; //the key letters for encoding
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
  }

  //store key letters in a 5x5 array
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
