import java.util.*; 

class Main {
  public static void main(String[] args) {
    Main program = new Main();
    program.start();
  }

  public void start() {
    String[] dict = {"Hello","Hellya","goodbye","goofball","gooey","jimmy","johns", "johnny"};
    Autocomplete(dict);

    List<String> listOfFound = getWordsForPrefix("goo");
    System.out.println("List of found words:");
    for(String s : listOfFound)
      System.out.print(s + ", ");
  }

  // Trie node class
  private class Node {
      String prefix;
      HashMap<Character, Node> children;
          
      // Does this node represent the last character in a word?
      boolean isWord;
          
      private Node(String prefix) {
          this.prefix = prefix;
          this.children = new HashMap<Character, Node>();
      }
  }
      
  // // The trie
  private static Node trie;
      
  // Construct the trie from the dictionary
  public void Autocomplete(String[] dict) {
      trie = new Node("");
      for (String s : dict) {
        System.out.println("Insert word into dict: \"" + s + "\"");
        insertWord(s);
      }
  }
      
  // Insert a word into the trie
  private void insertWord(String s) {
      // Iterate through each character in the string. If the character is not
      // already in the trie then add it
      Node curr = trie;

      for (int i = 0; i < s.length(); i++) {
          System.out.println("@ char '" + s.charAt(i) + 
                             "' = keySet:" + curr.children.keySet());
          if (!curr.children.containsKey(s.charAt(i))) {
            curr.children.put(s.charAt(i), 
                              new Node(s.substring(0,i+1)));
            System.out.println("Put \""+ s.substring(0,i+1) +"\" into key ["+ 
                              s.charAt(i) + "]");
          }

          curr = curr.children.get(s.charAt(i));
          if (i == s.length() - 1) { // End of the word?
            curr.isWord = true;
            System.out.println("\""+s.substring(0,i+1)+ "\", isWord=true");
            System.out.println();
          } 
      }
  }
      
  // Find all words in trie that start with prefix
  public static List<String> getWordsForPrefix(String pre) {
      List<String> results = new LinkedList<String>();
      
      System.out.println("Searching for: " + pre);

      // Iterate to the end of the prefix
      Node curr = trie;
      for (char c : pre.toCharArray()) {
          System.out.println("@ char '"+ c +"'-> keySet=" +
                             curr.children.keySet() );
          if (curr.children.containsKey(c)) {
              curr = curr.children.get(c);
          } else {
              return results; // This will always return an empty list
          }
      }

      System.out.println("Found prefix:" + pre);
      System.out.println("Now recursively searching for all child words..."); 

      // At the end of the prefix, find all child words
      findAllChildWords(curr, results);
      return results;
  }
      
  // Recursively find every child word
  private static void findAllChildWords(Node n, List<String> results) {
      if (n.isWord) {
        System.out.println("Found word:" + n.prefix);
        results.add(n.prefix);
      }

      for (Character c : n.children.keySet()) {
          System.out.println("@'"+c+"' = " + n.children.keySet());
          findAllChildWords(n.children.get(c), results);
      }
  }

}
