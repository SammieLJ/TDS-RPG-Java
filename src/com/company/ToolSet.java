package com.company;

import java.io.*;
import java.util.*;
import java.util.Random;
import java.util.regex.Pattern;

class ToolSet {

    private static Pattern alphanumeric = Pattern.compile("^[a-zA-Z0-9]*$");
    private static Pattern numeric = Pattern.compile("^[0-9]*$");

    // READ FIRST: FIX FILE PATH FOR YOUR CASE!!!
    private String WORKING_FILES = "D:\\ToGitHub\\TDS-RPG-Java";

    public void two_dictionary_passwd_gen_banner()
    {
        String two_dictionary_passwd_gen_banner =
        "##############################################################\n"+
        "#  JAVA  - Dictionary shuffler and Random Password Generetor #\n"+
        "##############################################################\n"+
        "#                         CONTACT                            #\n"+
        "##############################################################\n"+
        "#               DEVELOPER : SAMMY 76 LJ                      #\n"+
        "#          Mail Address : sammy76lj@gmail.com                #\n"+
        "#     DESC: Loads two dictionaries from file for shuffeling  #\n"+
        "#     DESC: Shuffles 1 or 2 Slovenian male and female name,  #\n"+
        "#     DESC: and adds rand. numbers to fill up min 25 chars   #\n"+
        "#            USAGE: Intended as internal tool, now it's o.s. #\n"+
        "##############################################################\n";
        System.out.println(two_dictionary_passwd_gen_banner);
    }

    public String[] readFileNames(String fileName)
    {
        //String[] lines = System.IO.File.ReadAllLines(fileName);
        //return lines;

        List<String> items = new ArrayList<String>();; // use ArrayList if you don't know how many
        String file = WORKING_FILES + "\\" + fileName;

        /*try {
            Scanner sc = new Scanner(new File(WORKING_FILES + "\\" + fileName));
            int i = 0;
            while(sc.hasNextLine() && i < items.size()) {
                items.add(sc.nextLine());
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                items.add(line);
            }
            // line is not visible here.
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] stringArray = items.toArray(new String[0]);
        return stringArray;
    }

    public void writeDownToFile(List<String> genList, String fileName)throws IOException
    {
        // Open a file
        System.out.println("Write to file: " + fileName);

        //System.IO.File.WriteAllLines(fileName, genList); C#

        /*FileOutputStream fos = new FileOutputStream(WORKING_FILES + "\\" + fileName);
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeUTF(genList.toString());
        outStream.close(); fos.close(); */

        FileWriter fw = new FileWriter(WORKING_FILES + "\\" + fileName);

        for (String str : genList) {
            fw.write(str + System.lineSeparator());
        }
        fw.close();
    }

    public String[] random_names_from_list(String[] nameList, Random rnd)
    {
        //Random rnd = new Random();
        int OnceOrTwice = rnd.nextInt(3);
        OnceOrTwice += 1; // generate between bound [1, 3]

        List<String> namesStrList = new ArrayList<String>();

        for (int i = 0; i < OnceOrTwice; i++) {
            namesStrList.add(nameList[rnd.nextInt(nameList.length)]);
        }
        String[] names = namesStrList.toArray(new String[0]);;
        return names;
    }

    public String GetRandomCharacters(String text, int noNumbers, Random rng)
    {
        StringBuilder tempText = new StringBuilder();
        //Random rng = new Random();
        for (int x = 0; x < noNumbers; x++)
        {
            tempText.append(GetRandomCharacter(text, rng));
        }

        if (!CheckForDashesAndUnderscores(tempText.toString()))
        {
            GetRandomCharacters(text, noNumbers, rng);
        }
        return tempText.toString();
    }

    public char GetRandomCharacter(String text, Random rng)
    {
        //Random rng = new Random();
        int index = rng.nextInt(text.length());
        return text.charAt(index);
    }

    public boolean CheckForDashesAndUnderscores(String text)
    {
        //System.out.println("[FROM toolset!] Word " + text + " is not by the rules! Mandatory char _ or - was not found");
        if (!text.contains("_") && !text.contains("-"))
            return false;
        return true;
    }

    public void Shuffle(List<String> list, Random rnd)
    {
        int n = list.size();
        //Random rnd = new Random();
        while (n > 1)
        {
            int k = (rnd.nextInt(n) % n);
            n--;
            String value = list.get(k);
            list.set(k, list.get(n));
            list.set(n, value);
        }
    }

    public boolean ContainsAlphaNumeric(String strToCheck)
    {
        for (char c : strToCheck.toCharArray())
        {
            if (Character.isLetterOrDigit(c))
            {
                return true;
            }
        }
        return false;
    }

    public boolean ContainsAlpha(String strToCheck)
    {
        for (char c : strToCheck.toCharArray())
        {
            if (Character.isLetterOrDigit(c))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlphaNumeric(String s) {
        return alphanumeric.matcher(s).find();
    }

    public static boolean isNumeric(String s) {
        return numeric.matcher(s).find();
    }

    public boolean checkIfPasswdMeetsRules(String str)
    {
        // PRVI ZNAK!
        if (Character.isDigit(str.charAt(0)))
        {
            //System.out.println("Word " + str + " is not by the rules!" + "First char is " + str[0]);
            return false;
        }

        if (str.startsWith("_"))
        {
            //System.out.println("Word " + str + " is not by the rules!" + "First char is " + str[0]);
            return false;
        }

        if (str.startsWith("-"))
        {
            //System.out.println("Word " + str + " is not by the rules!" + "First char is " + str[0]);
            return false;
        }

        // ZADNJI ZNAK
        if (Character.isDigit(str.charAt(str.length() - 1)))
        {
            //System.out.println("Word " + str + " is not by the rules!" + "Last char is " + str[str.Length - 1]);
            return false;
        }

        if (str.endsWith("_"))
        {
            //System.out.println("Word " + str + " is not by the rules!" + "Last char is " + str[str.Length - 1]);
            return false;
        }

        if (str.endsWith("-"))
        {
            //System.out.println("Word " + str + " is not by the rules!" + "Last char is " + str[str.Length - 1]);
            return false;
        }

        // check rule that every password should have _ or -
        if (!str.contains("_") && !str.contains("-"))
        {
            //System.out.println("Word " + str + " is not by the rules! Mandatory char _ or - was not found");
            return false;
        }

        // check rule if only chars are in the password
        if (!ContainsAlphaNumeric(str))
        {
            //System.out.println("Word " + str + " is not by the rules!" + "JUST ALL CHARS, NO NUMBERS OR SPEC. CHARS!");
            return false;
        }
        return true;
    }

}
