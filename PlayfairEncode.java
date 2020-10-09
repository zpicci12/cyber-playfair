public class PlayfairEncode {
  public static void main(String[] args){
    String text = args[0].toUpperCase(); //the text to be encoded
    String keyText = args[1]; //the key letters for encoding
    encode(text, keyText); //run the methods to encode the ciphertext
  }

  public static void encode(String text, String keyText){
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
    String[] pairs = makePairs(text); //insert x's if needed and return the pairs for encoding
    encodePairs(pairs, key);
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

  public static void encodePairs(String[] pairs, String[][] key){
    //go through each pair and figure out which encoding it needs ("determineEncode")
    //add that encoding to a new String [][] encodedPairs
    //skip null pairs! (find # of pairs that aren't null before going through each pair)
    int n_pairs = 0;
    for (int i = 0; i < pairs.length; i++){
      if (pairs[i] != null){
        n_pairs++;
      }
    }
    String[] encodedPairs = new String[n_pairs];
    for (int i = 0; i < n_pairs; i++){
      String encoded_pair = determineEncode(pairs[i], key);
      encodedPairs[i] = encoded_pair;
    }

    for (int i = 0; i < encodedPairs.length; i++){
      System.out.print(encodedPairs[i]);
    }
    System.out.println();
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

  //determine which rule the encoding must follow + execute the method for that rule
  public static String determineEncode(String pair, String[][] key){
    String letter0 = pair.substring(0, 1);
    String letter1 = pair.substring(1);
    int row0 = getRow(letter0, key);
    int column0 = getColumn(letter0, key);
    int row1 = getRow(letter1, key);
    int column1 = getColumn(letter1, key);
    if (row0 == row1){
      String encoded = verticalEncode(row0, row1, column0, column1, key);
      return encoded;
    }
    else if (column0 == column1){
      String encoded = horizontalEncode(row0, row1, column0, column1, key);
      return encoded;
    }
    else {
      String encoded = regularEncode(row0, row1, column0, column1, key);
      return encoded;
    }
  }

  //encode regular letters (different rows and different colums)
  public static String regularEncode(int row0, int row1, int column0, int column1, String[][] key){
    String encoded = key[row0][column1] + key[row1][column0];
    return encoded;
  }

  //encode letters in the same column
  public static String horizontalEncode(int row0, int row1, int column0, int column1, String[][] key){
    if (column1 == 4){ //letters in rightmost column must wrap to leftmost column
      String encoded = key[row0][0] + key[row1][0];
      return encoded;
    }
    else {
      String encoded = key[row0][column0 + 1] + key[row1][column1 + 1];
      return encoded;
    }
  }

  //encode letters in the same row
  public static String verticalEncode(int row0, int row1, int column0, int column1, String[][] key){
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
