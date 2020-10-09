public class PlayfairEncode {
  public static void main(String[] args){
    String text = args[0].toUpperCase(); //the text to be encoded
    String keyText = args[1]; //the key letters for encoding
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
    String[] pairs = makePairs(text); //insert x's if needed and return the pairs for encoding
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

  //make the two-letter pairs used to encode the text; return as an array
  public static String[] makePairs(String text){
    String [] pairs = new String [text.length()]; //extra indexes for when x's are added
    int n = 0;
    int count = 0;
    //go through text in pairs of 2; add x's where needed
    while (n < text.length()){
      String pair = text.substring(n, n + 2);
      //if the pair is a double letter, add an x and increment n by 1 to keep the pairs in order
      if (pair.substring(0, 1).equals(pair.substring(1))){
        String newPair = pair.substring(0, 1) + 'X';
        n += 1;
        pairs[count] = newPair;
        count++;
      }
      //else don't change the pair
      else {
      pairs[count] = pair;
      count++;
      n += 2;
      }
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

  public static String regularEncode(String pair, String[][] key){
    String letter0 = pair.substring(0, 1);
    String letter1 = pair.substring(1);
    int row0 = getRow(letter0, key);
    int column0 = getColumn(letter0, key);
    int row1 = getRow(letter1, key);
    int column1 = getColumn(letter1, key);
    String encoded = key[row0][column1] + key[row1][column0];
    return encoded;
  }

  public static String horizontalEncode(String pair, String[][] key){
    String letter0 = pair.substring(0, 1);
    String letter1 = pair.substring(1);
    int row0 = getRow(letter0, key);
    int column0 = getColumn(letter0, key);
    int row1 = getRow(letter1, key);
    int column1 = getColumn(letter1, key);
    if (column1 == 4){
      String encoded = key[row0][0] + key[row1][0];
      return encoded;
    }
    else {
      String encoded = key[row0][column0 + 1] + key[row1][column1 + 1];
      return encoded;
    }
  }

  public static String verticalEncode(String pair, String[][] key){
    String letter0 = pair.substring(0, 1);
    String letter1 = pair.substring(1);
    int row0 = getRow(letter0, key);
    int column0 = getColumn(letter0, key);
    int row1 = getRow(letter1, key);
    int column1 = getColumn(letter1, key);
    if (row1 == 4){ //letters on bottom row must wrap to top row
      String encoded = key[0][column0] + key[0][column1];
      return encoded;
    }
    else {
      String encoded = key[row0 + 1][column0] + key[row1 + 1][column1];
      return encoded;
    }
  }
}
