package com.company;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        String configFileName = args[0];
        //read file, set array and start program
        //init measure time
        Instant starts = Instant.now();

        //create toolset object
        ToolSet toolset = new ToolSet();

        //show ico banner
        toolset.two_dictionary_passwd_gen_banner();

        //check if number of parameters are ok
        System.out.println("List of arguments: " + args[0]); //String.Join("", list.ToArray())
        System.out.println("No of arguments: " + args.length);

        // if only parameter is missing, then exit program
        if (args.length == 0)
        {
            System.out.println("Parameters are missing! Enter: config.txt file name!");
            System.exit(0); // return 1;
        }

        String[] configList = toolset.readFileNames(configFileName);

        /*
         * config.txt:
         * Password length -                25
         * First dictionary text file -     TrendyMaleNames.txt
         * Second dictionary text file -    TrendyFemaleNames.txt
         * Final file to write passwords -  Generirana_gesla.txt
         * Amount of passwds. to generate - 50
         *
         */

        // check if third argument has some value, if not default is 25
        int max_password_len = Integer.parseInt(configList[0]);

        // value shouldn't be less than 25 chars, because algorithm is not optimized for less
        if (max_password_len < 25)
            max_password_len = 25;

        int amountPwdsToGen = Integer.parseInt(configList[4]);
        System.out.println("Maximalna dolžina: " + max_password_len);
        System.out.println("Number of passwords to generate " + amountPwdsToGen);

        String[] male_names_array = toolset.readFileNames(configList[1]);
        String[] female_names_array = toolset.readFileNames(configList[2]);


        List<String> gendNamesList = new ArrayList<String>();
        for (int i = 0; i < amountPwdsToGen; i++) {
            gendNamesList.add(PickAndCheckPassword(toolset, male_names_array, female_names_array, max_password_len));
        }

        // Write down names to file
        try {
            toolset.writeDownToFile(gendNamesList, configList[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Instant ends = Instant.now();
        //System.out.println(Duration.between(starts, ends));

        System.out.println("Generated passes - ratio of all / met-criteria: " + GetGenRandPasses() + "/" + amountPwdsToGen);
        System.out.println("Measured time: " + Duration.between(starts, ends) + " in sec");
        System.in.read();
    }

    // set (global) recursion counter for method getRandomAndShuffledPassword (it will be called more than No of passes to gen.)
    static int genRandPasses = 0;

    static void AddOneToGenRandPasses()
    {
        genRandPasses++;
    }

    static int GetGenRandPasses()
    {
        return genRandPasses;
    }

    static String PickAndCheckPassword(ToolSet toolset, String[] male_names_array, String[] female_names_array, int max_password_len)
    {
        String final_password_str = "";

        boolean checkPasswd = false;
        while (!checkPasswd)
        {
            final_password_str = getRandomAndShuffledPassword(toolset, male_names_array, female_names_array, max_password_len);
            checkPasswd = toolset.checkIfPasswdMeetsRules(final_password_str);
        }

        return final_password_str;
    }

    static String getRandomAndShuffledPassword(ToolSet toolset, String[] male_names_array, String[] female_names_array, int max_password_len)
    {
        AddOneToGenRandPasses();
        Random random = new Random();

        String[] male_names = toolset.random_names_from_list(male_names_array, random);
        String[] female_names = toolset.random_names_from_list(female_names_array, random);

        // calculate how much numbers we need to add to max length of password
        // String.Join("", list.ToArray())
        int maxLenDiff = (String.join("", male_names) + String.join("", female_names)).length();
        int noNumbers = max_password_len - maxLenDiff;// - 2 (dash and underscore)

        // print ("Number of Numbers :" + str(noNumbers))

        // set array of number and special chars
        String numeric_chars = "1234567890_-";
        //var underscore_char = "_";
        //var dash_char = "-";
        String numbersAndSpecialChars = "";

        if (noNumbers > 0) {
            numbersAndSpecialChars = toolset.GetRandomCharacters(numeric_chars, noNumbers, random);
        }

        //var final_password = String.Join("", male_names.ToArray()) + String.Join("", female_names.ToArray()) + String.Join("", numbersAndSpecialChars.ToArray());
        List<String> final_password = new ArrayList<String>();

        final_password.addAll(Arrays.asList(male_names));
        final_password.addAll(Arrays.asList(female_names));
        final_password.add(numbersAndSpecialChars);
        //final_password.Add(underscore_char);
        //final_password.Add(dash_char);


        // debug purposes
        // Console.WriteLine("Final prepaired words for password: ");
        // Console.WriteLine(final_password);

        toolset.Shuffle(final_password, random);

        // Console.WriteLine((final_password)
        String final_password_str = String.join("", final_password);

        //return CheckIfPasswdMeetsRules(final_password_str) ? final_password_str : getRandomAndShuffledPassword(male_names_array, female_names_array, max_password_len);
        // Console.WriteLine(("Password : " + final_password_str)
        return final_password_str;
    }
}
