package RBT;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
* Joshua Alexander
* RBTDriver.java
* COSC 336
* Homework #3
* Double Linked List Red Black Tree
* Worked with Robert Smith and Will (Classmates)
 */
public class RBTDriver {

    public static void main(String[] args) throws NumberFormatException, FileNotFoundException, IOException {
        Scanner scan = new Scanner(System.in);
        int cont = 1, cont2 = 2, del;
        RBT tree = new RBT();

        System.out.println("Red Black Tree");

        while (cont != 0) {
            input(tree); // Takes input from file
            if (tree.getRoot() != null) {

                System.out.println("\nIn Order Traversal of Tree\n");
                tree.inOrder();

                cont2 = 1;
                while (cont2 != 0) {

                    System.out.println("\nEnter an integer in tree to delete: ");
                    del = scan.nextInt();

                    if (tree.search(del).getData() == del) {
                        System.out.println("\nFound Node, deleting: " + tree.search(del).getData());
                        tree.delete(del);
                        System.out.println("\nIn Order Traversal of Tree after deleting " + del + "\n");
                        tree.inOrder();
                    } else {
                        System.out.println("\nInt to delete not found.");
                        System.out.println("\nEnter any integer to choose another value or 0 to continue: ");
                        cont2 = scan.nextInt();
                    }
                }
            }

            System.out.println("\n\nEnter any integer to select input file or 0 to exit.");
            cont = scan.nextInt();
        }
    }

    public static void input(RBT tree) throws IOException, FileNotFoundException, NumberFormatException {
        // Opens a text file and inserts integers into a bst
        JFileChooser openFile = new JFileChooser();
        FileNameExtensionFilter txtfilter = new FileNameExtensionFilter("Text Files", "txt", "text");
        openFile.setFileFilter(txtfilter);
        FileInputStream fis;
        BufferedReader br;

        int returnVal = openFile.showOpenDialog(openFile);

        // clears tree for next input
        tree.clearTree();

        // If selected approve
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                fis = new FileInputStream(openFile.getSelectedFile());
                br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    // Add int to tree while line is not empty
                    tree.insert(Integer.parseInt(line));
                }
                br.close();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found.");
            } catch (IOException ex) {
                System.out.println("Error reading file.");
            } catch (NumberFormatException ex) {
                System.out.println("Text file does not contain all integers.");
            }
        } else {
            System.out.println("No File Chosen.");
        }
    }
}
