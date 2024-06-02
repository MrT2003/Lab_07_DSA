// hash.java
// demonstrates hash table with linear probing
// to run this program: C:>java HashTableApp
import java.io.*;
////////////////////////////////////////////////////////////////
class HashTableApp
{
    public static void main(String[] args) throws IOException
    {
        DataItem aDataItem;
        int aKey, size, n, keysPerCell;
        int a[] = new int[100];
        // get sizes
        System.out.print("Enter size of hash table: ");
        size = getInt();
        System.out.print("Enter initial number of items: ");
        n = getInt();
        keysPerCell = 10;
        // make table
        HashTable_2 theHashTable = new HashTable_2(size);
        int totalProbes = 0;

        System.out.print("Initial Keys: ");
        for(int j=0; j<n; j++)        // insert data
        {
            aKey = (int)(Math.random() *
                    keysPerCell * size);
            a[j] = aKey;
            aDataItem = new DataItem(aKey);
            totalProbes += theHashTable.insert(aDataItem);
        }
        for(int i = 0; i < n; i++) {
            System.out.println(a[i]);
        }
        System.out.println();
        double averageProbeLength = (double) totalProbes / n;
        System.out.println("Average probe length for initial filling: " + averageProbeLength);

        while(true)                   // interact with user
        {
            System.out.print("Enter first letter of ");
            System.out.print("show, insert, delete, or find: ");
            char choice = getChar();
            switch(choice)
            {
                case 's':
                    theHashTable.displayTable();
                    break;
                case 'i':
                    System.out.print("Enter key value to insert: ");
                    aKey = getInt();
                    aDataItem = new DataItem(aKey);
                    theHashTable.insert(aDataItem);
                    break;
                case 'd':
                    System.out.print("Enter key value to delete: ");
                    aKey = getInt();
                    theHashTable.delete(aKey);
                    break;
                case 'f':
                    System.out.print("Enter key value to find: ");
                    aKey = getInt();
                    aDataItem = theHashTable.find(aKey);
                    if(aDataItem != null)
                    {
                        System.out.println("Found " + aKey);
                    }
                    else
                        System.out.println("Could not find " + aKey);
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }  // end switch
        }  // end while
    }  // end main()
    //--------------------------------------------------------------
    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    //--------------------------------------------------------------
    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }
    //-------------------------------------------------------------
    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }
//--------------------------------------------------------------
}  // end class HashTableApp
////////////////////////////////////////////////////////////////

class DataItem
{                                // (could have more data)
    private int iData;               // data item (key)
    //--------------------------------------------------------------
    public DataItem(int ii)          // constructor
    { iData = ii; }
    //--------------------------------------------------------------
    public int getKey()
    { return iData; }
//--------------------------------------------------------------
}  // end class DataItem
////////////////////////////////////////////////////////////////
class HashTable_2
{
    private DataItem[] hashArray;    // array holds hash table
    private int arraySize;
    private DataItem nonItem;        // for deleted items
    // -------------------------------------------------------------
    public HashTable_2(int size)       // constructor
    {
        arraySize = size;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem(-1);   // deleted item key is -1
    }
    // -------------------------------------------------------------
    public void displayTable()
    {
        System.out.print("Table: ");
        for(int j=0; j<arraySize; j++)
        {
            if(hashArray[j] != null)
                System.out.print(hashArray[j].getKey() + " ");
            else
                System.out.print("** ");
        }
        System.out.println("");
    }
    // -------------------------------------------------------------
    public int hashFunc(int key)
    {
        return key % arraySize;       // hash function
    }
    // -------------------------------------------------------------
    public int insert(DataItem item) // insert a DataItem
    // (assumes table not full)
    {
        int key = item.getKey();      // extract key
        int hashVal = hashFunc(key);  // hash the key
        int probeCount = 0;

        // until empty cell or -1,
        System.out.println("Inserting key: " +key+ " - Initial hash value: " +hashVal);
        while(hashArray[hashVal] != null &&
                hashArray[hashVal].getKey() != -1)
        {
            System.out.println("Probe " + probeCount + " - Hash value: " + hashVal);
            ++hashVal;                 // go to next cell
            hashVal %= arraySize;
            ++probeCount;
        }
        hashArray[hashVal] = item;    // insert item
        System.out.println("Inserted at index: " + hashVal + " with probe length: " + (probeCount + 1));
        return probeCount + 1;
    }  // end insert()
    // -------------------------------------------------------------
    public DataItem delete(int key)  // delete a DataItem
    {
        int hashVal = hashFunc(key);  // hash the key

        while(hashArray[hashVal] != null)  // until empty cell,
        {                               // found the key?
            if(hashArray[hashVal].getKey() == key)
            {
                DataItem temp = hashArray[hashVal]; // save item
                hashArray[hashVal] = nonItem;       // delete item
                return temp;                        // return item
            }
            ++hashVal;                 // go to next cell
            hashVal %= arraySize;      // wraparound if necessary
        }
        return null;                  // can't find item
    }  // end delete()
    // -------------------------------------------------------------
    public DataItem find(int key)    // find item with key
    {
        int hashVal = hashFunc(key);  // hash the key
        System.out.println("Finding key: " + key + " - Initial hash value: " + hashVal);

        int probeCount = 0;
        while(hashArray[hashVal] != null)  // until empty cell,
        {                               // found the key?
            if(hashArray[hashVal].getKey() == key) {
                System.out.println("Found key " + key + " at index " + hashVal + " with probe length: " + (probeCount + 1));
                return hashArray[hashVal];
            }
                  // yes, return item
            ++hashVal;                 // go to next cell
            hashVal %= arraySize;      // wraparound if necessary
            probeCount++;
        }
        System.out.println("Key " + key + " not found with probe length: " + probeCount);
        return null;                  // can't find item
    }
// -------------------------------------------------------------
}  // end class HashTable
////////////////////////////////////////////////////////////////