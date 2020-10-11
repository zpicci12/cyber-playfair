public class Playfair {
  public static void main(String[] args){
    String algorithm = args[0]; //argument for decoding or encoding
    String text = args[1].toUpperCase().replaceAll("\\p{Punct}",""); //the text to be encoded
    String keyText = args[2]; //the key letters for encoding
    if (algorithm.equals("encode")){
        text = text.replace("J", "I"); //J gets encoded as I
        encode(text, keyText); //run methods to encode the ciphertext
    }
    else if (algorithm.equals("decode")){
        decode(text, keyText); //run methode to decode the plaintext
    }
    else {
        System.out.println("You did not choose one of the valid options: encode or decode");
    }
  }

  public static void encode(String text, String keyText){
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
    String[] pairs = makeEncodePairs(text); //insert x's if needed and return the pairs for encoding
    encodePairs(pairs, key); //encode each pair and print as string
  }


  public static void decode(String text, String keyText){
    String [][] key = makeKey(keyText); //store key letters in a 5x5 array
    String[] pairs = makeDecodePairs(text); //return the pairs for decoding
    decodePairs(pairs, key); //decode each pair and print as string
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
  public static String[] makeEncodePairs(String text){
    String [] pairs = new String [text.length()]; //extra indexes for when x's are added
    int n = 0;
    int count = 0;
    //go through text in pairs of 2; add x's where needed
    while (n < text.length()){
      if (n == text.length() - 1){
        pairs[count] = text.substring(n) + "Z";
        break;
      }

      else {
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
    }
    return pairs;
  }

  //make the two-letter pairs used to decode the text; return as an array
  public static String[] makeDecodePairs(String text){
    String [] pairs = new String [text.length()];
    int n = 0;
    int count = 0;
    //go through text in pairs of 2
    while (n < text.length()){
      String pair = text.substring(n, n + 2);
      pairs[count] = pair;
      count++;
      n += 2;
    }
    return pairs;
  }

  public static void encodePairs(String[] pairs, String[][] key){ //encode pairs of two letters
    int n_pairs = 0; //count # of letter pairs
    for (int i = 0; i < pairs.length; i++){
      if (pairs[i] != null){
        n_pairs++;
      }
    }
    String[] encodedPairs = new String[n_pairs];
    for (int i = 0; i < n_pairs; i++){ //encode each pair based on which rule it follows (vertical, horizontal or regular)
      String encoded_pair = determineRule("encode", pairs[i], key);
      encodedPairs[i] = encoded_pair;
    }

    for (int i = 0; i < encodedPairs.length; i++){ //print the encoded pairs
      System.out.print(encodedPairs[i]);
    }
    System.out.println();
  }

  public static void decodePairs(String[] pairs, String[][] key){
    int n_pairs = 0; //count # of letter pairs
    for (int i = 0; i < pairs.length; i++){
      if (pairs[i] != null){
        n_pairs++;
      }
    }

    String[] decodedPairs = new String[n_pairs];

    for (int i = 0; i < n_pairs; i++){ //encode each pair based on which rule it follows (vertical, horizontal or regular)
      String decoded_pair = determineRule("decode", pairs[i], key);
      decodedPairs[i] = decoded_pair;
    }


    String decoded = ""; //add decoded pairs into a string
    for (int i = 0; i < decodedPairs.length; i++){
      decoded += decodedPairs[i];
    }

    for (int i = 0; i < decoded.length(); i++){ //check for X's and remove them; print decoded string
      String letter = decoded.substring(i, i + 1);
          System.out.print(letter);
    }

    System.out.println();
  }

  public static int getRow(String letter, String[][] key){   //get the row of a letter in a pair
    for (int i = 0; i < key.length; i++){
      for (int j = 0; j < key[0].length; j++){
        if (letter.equals(key[i][j])){
          return i;
        }
      }
    }
    return -1;
  }

  public static int getColumn(String letter, String[][] key){   //get the column of a letter in a pair
    for (int i = 0; i < key.length; i++){
      for (int j = 0; j < key[0].length; j++){
        if (letter.equals(key[i][j])){
          return j;
        }
      }
    }
    return -1;
  }

  public static String determineRule(String algorithm, String pair, String[][] key){ //determine which rule the encoding must follow + execute the method for that rule
    String letter0 = pair.substring(0, 1);
    String letter1 = pair.substring(1);
    int row0 = getRow(letter0, key);
    int column0 = getColumn(letter0, key);
    int row1 = getRow(letter1, key);
    int column1 = getColumn(letter1, key);
    if (row0 == row1){ //rows are the same; must use vertical rule
      String solved = vertical(algorithm, row0, row1, column0, column1, key);
      return solved;
    }
    else if (column0 == column1){ //columns are the same; must use horizontal rule
      String solved = horizontal(algorithm, row0, row1, column0, column1, key);
      return solved;
    }
    else { //different rows + columns; must use regular rule
      String solved = regular(algorithm, row0, row1, column0, column1, key);
      return solved;
    }
  }

  //encode regular letters (different rows and different colums)
  public static String regular(String algorithm, int row0, int row1, int column0, int column1, String[][] key){
    if (algorithm.equals("encode")){ //rule for regular encode
      String encoded = key[row0][column1] + key[row1][column0];
      return encoded;
    }
    else { //rule for regular decode
      String decoded = key[row0][column1] + key[row1][column0];
      return decoded;
    }
  }

  //encode letters in the same column
  public static String horizontal(String algorithm, int row0, int row1, int column0, int column1, String[][] key){
    if (algorithm.equals("encode")){ //rule for horizontal encode
      if (column1 == 4){ //letters in rightmost column must wrap to leftmost column
        String encoded = key[row0][0] + key[row1][0];
        return encoded;
      }
      else {
        String encoded = key[row0][column0 + 1] + key[row1][column1 + 1];
        return encoded;
      }
    }
    else { //rule for horizontal decode
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

  //encode letters in the same row
  public static String vertical(String algorithm, int row0, int row1, int column0, int column1, String[][] key){
    if (algorithm.equals("encode")){ //rule for vertical encode
      if (row1 == 4){ //letters on bottom row must wrap to top row
        String encoded = key[0][column0] + key[0][column1];
        return encoded;
      }
      else {
        String encoded = key[row0 + 1][column0] + key[row1 + 1][column1];
        return encoded;
      }
    }
    else { //rule for vertical decode
      if (row1 == 0){ //letters on top row must wrap to bottom row
        String decoded = key[4][column0] + key[4][column1];
        return decoded;
      }
      else {
        String decoded = key[row0 - 1][column0] + key[row1 - 1][column1];
        return decoded;
      }
    }
  }
}
