public class PlayfairDecode {
  public static void main(String[] args){
    String text = args[0].toUpperCase(); //the text to be decoded
    String keyText = args[1]; //the key letters for decoding
    decode(text, keyText); //run the methods to encoding the ciphertext
  }


  public static void decode(String text, String keyText){
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
    String[] pairs = makePairs(text); //insert x's if needed and return the pairs for decoding
    decodePairs(pairs, key);
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

  public static void decodePairs(String[] pairs, String[][] key){
    //go through each pair and figure out which encoding it needs ("determineEncode")
    //add that encoding to a new String [][] encodedPairs
    //skip null pairs! (find # of pairs that aren't null before going through each pair)
    int n_pairs = 0;
    for (int i = 0; i < pairs.length; i++){
      if (pairs[i] != null){
        n_pairs++;
      }
    }

    String[] decodedPairs = new String[n_pairs];

    for (int i = 0; i < n_pairs; i++){
      String decoded_pair = determineDecode(pairs[i], key);
      decodedPairs[i] = decoded_pair;
    }

    //first guess for decoding (x's included)
    String decoded = "";
    System.out.print("1. ");
    for (int i = 0; i < decodedPairs.length; i++){
      System.out.print(decodedPairs[i]);
      decoded += decodedPairs[i];
    }
    System.out.println();

    //second guess (removing x's)
    System.out.print("2. ");
    for (int i = 0; i < decoded.length(); i++){
      String letter = decoded.substring(i, i + 1);
      if (!letter.equals("X")){
        System.out.print(letter);
      }
    }

    System.out.println();
  }

  public static String determineDecode(String pair, String[][] key){
    String letter0 = pair.substring(0, 1);
    String letter1 = pair.substring(1);
    int row0 = getRow(letter0, key);
    int column0 = getColumn(letter0, key);
    int row1 = getRow(letter1, key);
    int column1 = getColumn(letter1, key);
    if (row0 == row1){
      String decoded = verticalDecode(row0, row1, column0, column1, key);
      return decoded;
    }
    else if (column0 == column1){
      String decoded = horizontalDecode(row0, row1, column0, column1, key);
      return decoded;
    }
    else {
      String decoded = regularDecode(row0, row1, column0, column1, key);
      return decoded;
    }
  }


  //decode regular letters (different rows and different colums)
  public static String regularDecode(int row0, int row1, int column0, int column1, String[][] key){
    String decoded = key[row0][column1] + key[row1][column0];
    return decoded;
  }

  public static String verticalDecode(int row0, int row1, int column0, int column1, String[][] key){
    if (row1 == 0){ //letters on top row must wrap to bottom row
      String decoded = key[4][column0] + key[4][column1];
      return decoded;
    }
    else {
      String decoded = key[row0 - 1][column0] + key[row1 - 1][column1];
      return decoded;
    }
  }

  //encode letters in the same column
  public static String horizontalDecode(int row0, int row1, int column0, int column1, String[][] key){
    if (column1 == 0){ //letters in rightmost column must wrap to leftmost column
      String decoded = key[row0][4] + key[row1][4];
      return decoded;
    }
    else {
      String decoded = key[row0][column0 - 1] + key[row1][column1 - 1];
      return decoded;
    }
  }


}
