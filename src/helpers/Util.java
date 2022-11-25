package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class with helper methods that could be used anywhere in the project
 * @author yugapriya
 */
public class Util {
	
	public static <K> Map<Integer, List<K>> getInvertedIndexMap(Map<K, Integer> inputMap) {
		HashMap<Integer, List<K>> outputMap = new HashMap<>();
		Set<K> inputMapKeys =inputMap.keySet();
		for (K inputMapKey : inputMapKeys) {
			Integer valueOfInputMap = inputMap.get(inputMapKey);
			Integer keyOfOutputMap = valueOfInputMap;
			if(outputMap.get(keyOfOutputMap) == null) {
				List<K> valueList = new ArrayList<>();
				valueList.add(inputMapKey);
				outputMap.put(valueOfInputMap, valueList);
			} else {
				List<K> valueList = outputMap.get(keyOfOutputMap);
				valueList.add(inputMapKey);
				outputMap.put(keyOfOutputMap, valueList);
			}
			
		}
		return outputMap;
	}
	
	public static List<String> listify(String[] input) {
		List<String> keywords = new ArrayList<>();
		for (String in : input) {
			keywords.add(in);
		}
		return keywords;
		
	}

}
