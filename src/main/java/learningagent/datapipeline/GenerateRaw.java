package learningagent.datapipeline;//package learningagent.datapipeline;
//
//import learningagent.database.SelfPlay;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class GenerateRaw {
//
//    SelfPlay sp = new SelfPlay();
//    OneHotEncoding encode = new OneHotEncoding();
//
//    // Run a given amount of games to generate board position with their Win/Loss/Draw information
//    public CustomMap<String, WLD> run(int numberOfGames){
//
//        // list of outcomes of a number of games
//        List<  HashMap<String, WLD>   > outcomesList = new ArrayList<>();
//
//        // run a given number of games
//        for(int i=0; i<numberOfGames; i++){
//
//            // play one game
//            ArrayList<Outcome> outcome = sp.playChess();
//            outcomesList.add(    sp.outcomesToHashmap(  outcome ));
//
//        }
//
//        // merge these outcome maps of single games into a big map
//        CustomMap<String, WLD> mergedMap = new CustomMap<>();
//
//        for(int i=0; i<outcomesList.size(); i++){
//
//            mergedMap.putAll( outcomesList.get(i) );
//
//        }
//
//        return mergedMap;
//
//    }
//
//
//    // Generate encoded simplified FEN with winProb for a given team data
//    //      0 is black
//    //      1 is white
//    public HashMap<double[], Double> getEncodedTeamData(int numberOfGames, int team){
//
//        // get data for N a
//        CustomMap<String, WLD> data = run(numberOfGames);
//
//        HashMap<double[], Double> dataEncodeTeamWP =  new HashMap<>();
//
//        if(team==0){
//
//            for (Map.Entry<String, WLD> entry : data.entrySet()) {
//
//                String FEN = entry.getKey();
//                WLD value = entry.getValue();
//
//                double blackWP  = value.getBlackWP();
//                double[] encodedFEN = encode.oneHotEncodeSimplifiedFEN(FEN);
//
//                dataEncodeTeamWP.put(encodedFEN, blackWP);
//                // do something with the position and value
//            }
//
//
//
//        } else {
//
//            for (Map.Entry<String, WLD> entry : data.entrySet()) {
//
//                String FEN = entry.getKey();
//                WLD value = entry.getValue();
//
//                double whiteWP  = value.getWhiteWP();
//                double[] encodedFEN = encode.oneHotEncodeSimplifiedFEN(FEN);
//
//                dataEncodeTeamWP.put(encodedFEN, whiteWP);
//
//            }
//
//
//        }
//
//        return dataEncodeTeamWP;
//
//    }
//
//
//    /** AUXILIARY */
//    // Override the behaviour of putAll
//    public class CustomMap<K, V extends WLD> extends HashMap<K, V> {
//
//        @Override
//        public void putAll(Map<? extends K, ? extends V> m) {
//            for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
//                K key = entry.getKey();
//                V value = entry.getValue();
//                if (containsKey(key)) {
//                    V currentWLD = this.get(key);
//
//                    currentWLD.setWhiteWINS(currentWLD.getWhiteWINS() + value.getWhiteWINS());
//                    currentWLD.setBlackWINS(currentWLD.getBlackWINS() + value.getBlackWINS());
//                    currentWLD.setDRAWS(currentWLD.getDRAWS() + value.getDRAWS());
//
//                    this.put(key, currentWLD);
//                } else {
//                    this.put(key, value);
//                }
//            }
//        }
//    }
//
//}
