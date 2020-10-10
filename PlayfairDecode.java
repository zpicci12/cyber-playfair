public class PlayfairDecode {
  public static void main(String[] args){
    String text = args[0].toUpperCase(); //the text to be decoded
    String keyText = args[1]; //the key letters for decoding
    decode(text, keyText); //run the methods to encoding the ciphertext
  }


  public static void decode(String text, String keyText){
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
    String[] pairs = makePairs(text); //insert x's if needed and return the pairs for decoding
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

  //make the two-letter pairs used to encode the text; return as an array
  public static String[] makePairs(String text){
    String [] pairs = new String [text.length()];
    int n = 0;
    int count = 0;
    //go through text in pairs of 2; add x's where needed
    while (n < text.length()){
      String pair = text.substring(n, n + 2);
      pairs[count] = pair;
      count++;
      n += 2;
    }
    return pairs;
  }

  //get the row of a letter in a pair
  public static int getRow(String letter, String[][] key){
    for (int i = 0; i < key.length; i++){
      for (int j = 0; j < key[0].length; j++){
        if (letter.equals(key[i][j])){
          return i;
        }
      }
    }
    return -1;
  }

  //get the column of a letter in a pair
  public static int getColumn(String letter, String[][] key){
    for (int i = 0; i < key.length; i++){
      for (int j = 0; j < key[0].length; j++){
        if (letter.equals(key[i][j])){
          return j;
        }
      }
    }
    return -1;
  }
}
