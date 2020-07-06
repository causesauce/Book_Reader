import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BookReader {

        public static void main(String[] args) {

            String book = "C:\\Users\\Adrian\\Desktop\\melville_moby_dick.txt";




            int minLen = 5; // ignore words shorter than minLen

            try (Stream<String> str =
                         Files.lines(Paths.get(book)))
            {
                Map<Integer, List<String>> map = str.flatMap(e -> Stream.of(e.split("\\P{L}+")))
                        .distinct()
                        .filter(
                                //beginning of lambda ( predicate )
                                e -> {
                                    if(e.length()<minLen)return false;
                                    if(e.length()==1)return true;
                                    for(int i = 0; i < e.length(); i++)
                                        for(int j = 0; j < e.length(); j++ )
                                            if(Character.toLowerCase(e.charAt(i))==Character.toLowerCase(e.charAt(j)) && i!=j)
                                                return false;

                                    return true;
                                }
                                //end of lambda ( predicate )
                        ).collect(Collectors.groupingBy(e -> e.length()));
                        /* one chain of stream operations */;

                // just printing
                List<Integer> lastTwo =
                        map.keySet().stream().sorted()
                                .collect(Collectors.toList());
                System.out.println("Two lists of the longest " +
                        "words with all letters different:");
                int len = lastTwo.get(lastTwo.size()-2);
                System.out.println("length " + len + ": " +
                        map.get(len));
                len = lastTwo.get(lastTwo.size()-1);
                System.out.println("length " + len + ": " +
                        map.get(len));
            } catch(IOException e) {
                System.out.println("Something wrong...");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }


