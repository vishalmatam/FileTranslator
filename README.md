# FileTranslator

## Functionality:
1. App takes 3 fileNames - `Set1.txt, Set2.txt, Set3.txt` from the command line which contain one english word per line. 
2. For each fileName, for each English word Google Translate URL is called and the word is translated to Swedish and written to the file. `For Set1.txt we get Set1_translated.txt, Set2.txt -> Set2_translated.txt and Set3_translated.txt`
3. The BatchFileProcessor concurrently reads the translated files and writes them to `Batched_timestamp.txt` (Here, each batch consists of the first line from the corresponding translated file and each batch is sorted in alphabetical order) 

## Instructions to run the application:
1. Run the application `App.java`
2. From the command line provide the input files as shown: `Provide 3 arguments - /dir/acton/Set1.txt /dir/acton/Set2.txt /dir/acton/Set3.txt`
3. In the `/dir/acton` we get `Set1_translated.txt, Set2_translated.txt, Set3_translated.txt` and `Batched_timestamp.txt`

