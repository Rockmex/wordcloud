import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class WordFrequency {

    public static HashMap<String,Integer> sortByValue(HashMap<String,Integer> hm) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());
        Collections.sort(list, new java.util.Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    public static void main(String[] args) throws IOException{
        File file=new File("alec_benjamin.txt");
        Scanner input=new Scanner(file);
        PrintWriter out=new PrintWriter("output.txt");

        HashMap<String,Integer> wordCount=new HashMap<>();
        while(input.hasNext()){
            String next=input.next().toLowerCase();
            String clean=next;
            if(!next.contains("'")) {
                clean = next.replaceAll("\\p{Punct}", "").toLowerCase();
            }
            if(!wordCount.containsKey(clean)){
                wordCount.put(clean,1);
            } else {
                wordCount.put(clean,wordCount.get(clean)+1);
            }
        }
        //out.println("Total words:"+wordCount.size());
        System.out.println("Total words:"+wordCount.size());

        HashMap<String,Integer> sortedMapAsc=sortByValue(wordCount);

        for(String word: sortedMapAsc.keySet()){
            int count=sortedMapAsc.get(word);
            System.out.println(count+ ":\t"+ word);
            //out.println(count+ ":\t"+ word);
        }

        final FrequencyAnalyzer frequencyAnalyzer=new FrequencyAnalyzer();
        final List<com.kennycason.kumo.WordFrequency> wordFrequencies=frequencyAnalyzer.load(file);
        final Dimension dimension=new Dimension(300,300);
        final WordCloud wordCloud=new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(150));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        //wordCloud.writeToFile("lyric_wordcloud.png");

        input.close();
        out.close();
    }

}
